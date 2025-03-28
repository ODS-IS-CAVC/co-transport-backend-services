package nlj.business.transaction.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.util.BaseUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.constant.ParamConstant;
import nlj.business.transaction.converter.StringToIntegerListDeserializer;
import nlj.business.transaction.dto.CnsLineItemByDateSnapshot;
import nlj.business.transaction.dto.ShipperTransactionDTO;
import nlj.business.transaction.dto.TransactionCarrierDTO;
import nlj.business.transaction.dto.VehicleAvbResourceItemSnapshot;
import nlj.business.transaction.dto.request.TransactionShipperSearch;
import nlj.business.transaction.dto.response.CarrierProposalItemResponseDTO;
import nlj.business.transaction.repository.TransOrderCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運送計画のカスタムリポジトリ実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class TransOrderCustomRepositoryImpl implements TransOrderCustomRepository {

    private final EntityManager entityManager;

    @Resource(name = "userContext")
    private UserContext userContext;

    /**
     * 運送計画のページング検索を行う。
     *
     * @param searchRequest 検索条件
     * @return 運送計画のページング検索結果
     */
    @Override
    public Page<ShipperTransactionDTO> getPagedShipperTransaction(TransactionShipperSearch searchRequest) {
        int page = Integer.parseInt(searchRequest.getPage()) - 1;
        int limit = Integer.parseInt(searchRequest.getLimit());
        String selectFields = " trans_order.* ";
        String countFields = " count(*) ";
        Query mainQuery = createShipperTransactionQuery(searchRequest, selectFields, true);
        Query countQuery = createShipperTransactionQuery(searchRequest, countFields, false);
        List<Map<String, Object>> mapResult = mainQuery.getResultList();
        List<ShipperTransactionDTO> result = new ObjectMapper().registerModule(new JavaTimeModule())
            .convertValue(mapResult, new TypeReference<List<ShipperTransactionDTO>>() {
            });
        long totalElements = ((Number) countQuery.getSingleResult()).longValue();
        return new PageImpl<>(result, PageRequest.of(page, limit), totalElements);
    }

    /**
     * 運送計画のクエリを作成する。
     *
     * @param searchRequest 検索条件
     * @param selectFields  選択するフィールド
     * @param isPageable    ページングするかどうか
     * @return 運送計画のクエリ
     */
    private Query createShipperTransactionQuery(TransactionShipperSearch searchRequest, String selectFields,
        boolean isPageable) {
        String queryString = "SELECT " + selectFields
            + " FROM t_trans_order trans_order "
            + "LEFT JOIN vehicle_avb_resource_item ON trans_order.vehicle_avb_resource_item_id = vehicle_avb_resource_item.id "
            + "LEFT JOIN cns_line_item_by_date ON trans_order.cns_line_item_by_date_id = cns_line_item_by_date.id "
            + "WHERE trans_order.trans_type = :transType "
            + "AND trans_order.shipper_operator_id = :companyId ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("transType", DataBaseConstant.TransOrder.TransType.SHIPPER_REQUEST);
        parameters.put("companyId", userContext.getUser().getCompanyId());
        List<Integer> listStatus = getShipperSearchStatus(searchRequest);
        if (listStatus != null && !listStatus.isEmpty()) {
            parameters.put("status", listStatus);
            queryString += " AND trans_order.status IN :status ";
        }

        List<Integer> advanceStatus = getShipperAdvanceStatus(searchRequest);
        if (!advanceStatus.isEmpty()) {
            parameters.put("advanceStatus", advanceStatus);
            queryString += " AND trans_order.status IN :advanceStatus ";
        }

        if (!BaseUtil.isNull(searchRequest.getFreeWord())) {
            queryString += " AND (trans_order.free_word ILIKE :freeWord "
                + "OR trans_order.shipper_operator_name LIKE :freeWord "
                + "OR trans_order.carrier_operator_name LIKE :freeWord "
                + "OR trans_order.carrier2_operator_name LIKE :freeWord "
                + "OR trans_order.service_name LIKE :freeWord "
                + "OR trans_order.item_name_txt LIKE :freeWord "
                + ")";
            parameters.put("freeWord", "%" + searchRequest.getFreeWord() + "%");
        }
        if (searchRequest.getTemperatureRange() != null && !searchRequest.getTemperatureRange().isEmpty()) {
            queryString +=
                " AND (string_to_array(replace(vehicle_avb_resource_item.temperature_range, ' ', ''), ',') && CAST(:temperatureRange AS text[]) "
                    + " OR string_to_array(replace(cns_line_item_by_date.temperature_range, ' ', ''), ',') && CAST(:temperatureRange AS text[]))";
            String tempRangeString = "{" + searchRequest.getTemperatureRange().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")) + "}";
            parameters.put("temperatureRange", tempRangeString);
        }

        Query query = null;
        if (isPageable) {
            int page = Integer.parseInt(searchRequest.getPage()) - 1;
            int limit = Integer.parseInt(searchRequest.getLimit());
            queryString += " ORDER BY trans_order.created_date DESC LIMIT :limit OFFSET :offset ";
            parameters.put("limit", limit);
            parameters.put("offset", page * limit);
            query = entityManager.createNativeQuery(queryString, Map.class);
        } else {
            query = entityManager.createNativeQuery(queryString, Long.class);
        }
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query;
    }

    /**
     * 運送計画のステータスを取得する。
     *
     * @param searchRequest 検索条件
     * @return 運送計画のステータス
     */
    private List<Integer> getShipperSearchStatus(TransactionShipperSearch searchRequest) {
        if (BaseUtil.isNull(searchRequest.getStatus())) {
            return new ArrayList<>();
        }

        List<Integer> status = switch (searchRequest.getStatus()) {
            case ParamConstant.TransOrderSearchStatus.PROPOSE -> List.of(
                DataBaseConstant.TransOrderStatus.SHIPPER_REQUEST,
                DataBaseConstant.TransOrderStatus.SHIPPER_RE_REQUEST,
                DataBaseConstant.TransOrderStatus.CARRIER_PROPOSAL,
                DataBaseConstant.TransOrderStatus.CARRIER_RE_PROPOSAL);
            case ParamConstant.TransOrderSearchStatus.APPROVAL -> List.of(
                DataBaseConstant.TransOrderStatus.SHIPPER_APPROVE,
                DataBaseConstant.TransOrderStatus.CARRIER_APPROVE);
            case ParamConstant.TransOrderSearchStatus.CONTRACT -> List.of(
                DataBaseConstant.TransOrderStatus.SHIPPER_MAKE_CONTRACT);
            case ParamConstant.TransOrderSearchStatus.PAYMENT -> List.of(
                DataBaseConstant.TransOrderStatus.SHIPPER_MAKE_PAYMENT);
            case ParamConstant.TransOrderSearchStatus.TRANSPORT -> List.of(
                DataBaseConstant.TransOrderStatus.SHIPPER_START_TRANSPORT);
            case ParamConstant.TransOrderSearchStatus.COMPLETE ->
                List.of(DataBaseConstant.TransOrderStatus.SHIPPER_COMPLETE_TRANSPORT);
            case ParamConstant.TransOrderSearchStatus.CONFIRM -> List.of(
                DataBaseConstant.TransOrderStatus.SHIPPER_REQUEST,
                DataBaseConstant.TransOrderStatus.SHIPPER_RE_REQUEST,
                DataBaseConstant.TransOrderStatus.CARRIER_PROPOSAL,
                DataBaseConstant.TransOrderStatus.CARRIER_RE_PROPOSAL,
                DataBaseConstant.TransOrderStatus.SHIPPER_APPROVE,
                DataBaseConstant.TransOrderStatus.CARRIER_APPROVE,
                DataBaseConstant.TransOrderStatus.SHIPPER_MAKE_CONTRACT,
                DataBaseConstant.TransOrderStatus.SHIPPER_MAKE_PAYMENT,
                DataBaseConstant.TransOrderStatus.SHIPPER_START_TRANSPORT,
                DataBaseConstant.TransOrderStatus.SHIPPER_COMPLETE_TRANSPORT,
                DataBaseConstant.TransOrderStatus.TRANSPORT_DECISION,
                DataBaseConstant.TransOrderStatus.SHIPPER_REJECT_PROPOSE,
                DataBaseConstant.TransOrderStatus.CARRIER_APPROVE_REJECT
            );
            default -> Collections.emptyList();
        };
        return new ArrayList<>(status);
    }

    /**
     * フリーワードをフォーマットする。
     *
     * @param freeWord フリーワード
     * @return フォーマットされたフリーワード
     */
    private String formatFreeWordQuery(String freeWord) {
        return freeWord.trim().replace(" ", " | ");
    }

    /**
     * 運送計画の詳細を取得する。
     *
     * @param transOrderId 運送計画ID
     * @return 運送計画の詳細
     */
    @Override
    public CarrierProposalItemResponseDTO getCarrierProposalItem(Long transOrderId) {
        String queryString = "SELECT trans.*, "
            + "trsp_ability_line_item.trsp_cli_prty_name_txt, trsp_plan_line_item.cnsg_prty_name_txt, "
            + "sub_car_info.service_no, TO_CHAR(sub_car_info.service_strt_date, 'YYYY-MM-DD') as service_strt_date, "
            + "sub_car_info.car_info_id as car_info_id, sub_car_info.trip_name as trip_name, "
            + " (SELECT trailer_license_plt_num_id FROM vehicle_avb_resource_item vi "
            + "LEFT JOIN car_info ci ON ci.id = vi.car_info_id "
            + "WHERE vi.id = trans.vehicle_avb_resource_item_id LIMIT 1 "
            + ") as trailer_license_plt_num_id "
            + "FROM t_trans_order trans "
            + "LEFT JOIN (SELECT DISTINCT ON (trsp_cli_prty_head_off_id) * FROM trsp_ability_line_item) AS trsp_ability_line_item ON trans.carrier_operator_code = trsp_ability_line_item.trsp_cli_prty_head_off_id "
            + "LEFT JOIN (SELECT DISTINCT ON (cnsg_prty_head_off_id) * FROM trsp_plan_line_item) AS trsp_plan_line_item ON trans.carrier2_operator_code= trsp_plan_line_item.cnsg_prty_head_off_id "
//                + "LEFT JOIN vehicle_avb_resource_item ON vehicle_avb_resource_item.id = trans.vehicle_avb_resource_item_id "
//                + "LEFT JOIN car_info ON  vehicle_avb_resource_item.car_info_id = car_info.id "
            + "LEFT JOIN ("
            + "  SELECT"
            + "    cns_line_item_by_date.id AS cns_line_item_by_date_id, "
            + "    car_info.service_no AS service_no, "
            + "    car_info.service_strt_date AS service_strt_date, "
            + "    car_info.id AS car_info_id, "
            + "    vehicle_avb_resource_item.trip_name AS trip_name, "
            + "    car_info.trailer_license_plt_num_id AS trailer_license_plt_num_id "
            + "  FROM cns_line_item_by_date "
            + "  LEFT JOIN t_trans_order ON  cns_line_item_by_date.trans_order_id = t_trans_order.id "
            + "  LEFT JOIN vehicle_avb_resource_item ON  vehicle_avb_resource_item.id = t_trans_order.vehicle_avb_resource_item_id "
            + "  LEFT JOIN car_info ON  car_info.id = vehicle_avb_resource_item.car_info_id "
            + "  WHERE cns_line_item_by_date.trans_type = 1 "
            + ") AS sub_car_info ON  sub_car_info.cns_line_item_by_date_id = trans.cns_line_item_by_date_id "
            + "WHERE trans.id = :id ";
        Query query = entityManager.createNativeQuery(queryString, Map.class)
            .setParameter("id", transOrderId);
        Map<String, Object> result = (Map<String, Object>) query.getSingleResult();
        CarrierProposalItemResponseDTO resutl = new ObjectMapper().registerModule(new JavaTimeModule())
            .convertValue(result, CarrierProposalItemResponseDTO.class);
        CnsLineItemByDateSnapshot requestSnapshot = resutl.getRequestSnapshot();
        if (requestSnapshot != null && requestSnapshot.getTransOrderId() != null) {
            resutl.setShipperInfo(getShipperNameByOrderId(requestSnapshot.getTransOrderId()));
        }

        String userCompanyId = userContext.getUser().getCompanyId();
        resutl.setIsRequestCarrier(
            resutl.getCarrierOperatorId() != null && resutl.getCarrierOperatorId().equals(userCompanyId));
        resutl.setCarInfo(this.getCarInfo((String) result.get("service_no"), (String) result.get("service_strt_date"),
            (Long) result.get("car_info_id")));
        if (resutl.getVehicleDiagramItemTrailerId() != null) {
            resutl.setSiblingOrderStatus(this.findSiblingTrailerOrderStatus(resutl.getVehicleDiagramItemTrailerId()));
        }
        return resutl;
    }

    /**
     * 運送計画の詳細を取得する。
     *
     * @param id 運送計画ID
     * @return 運送計画の詳細
     */
    @Override
    public TransactionCarrierDTO getDetailTransaction(Long id) {
        String queryString = "SELECT trans.*, TO_CHAR(trans.created_date, 'YYYY-MM-DD') created_date, "
            + "trailer.vehicle_diagram_item_id,"
            + "tpli.road_carr_depa_sped_org_name_txt, tpli.trsp_cli_tel_cmm_cmp_num_txt, "
            + "car_info.service_no, TO_CHAR(car_info.service_strt_date, 'YYYY-MM-DD') as service_strt_date, car_info.id as car_info_id ,vehicle_avb_resource_item.cut_off_time, vehicle_avb_resource_item.cut_off_fee, trans.parent_order_id "
            + "FROM t_trans_order trans "
            + "LEFT JOIN req_trsp_plan_line_item ON req_trsp_plan_line_item.cnsg_prty_head_off_id = trans.shipper_operator_code "
            + "LEFT JOIN vehicle_avb_resource ON vehicle_avb_resource.id = trans.vehicle_avb_resource_id "
            + "LEFT JOIN car_info ON  vehicle_avb_resource.car_info_id = car_info.id "
            + "LEFT JOIN vehicle_avb_resource_item ON vehicle_avb_resource_item.id = trans.vehicle_avb_resource_item_id "
            + "LEFT JOIN c_vehicle_diagram_item_trailer trailer ON trailer.id = trans.vehicle_diagram_item_trailer_id "
            + "LEFT JOIN cns_line_item cli ON cli.id = trans.cns_line_item_id "
            + "LEFT JOIN trsp_plan_line_item tpli ON tpli.id = cli.trsp_plan_line_item_id "
            + "WHERE trans.id = :id LIMIT 1 ";
        try {
            Query query = entityManager.createNativeQuery(queryString, Map.class)
                .setParameter("id", id);
            Map<String, Object> result = (Map<String, Object>) query.getSingleResult();
            TransactionCarrierDTO resutl = new ObjectMapper().registerModule(new JavaTimeModule())
                .convertValue(result, TransactionCarrierDTO.class);
            if (resutl.getCarInfoId() != null) {
                resutl.setCarInfo(
                    this.getCarInfo((String) result.get("service_no"), (String) result.get("service_strt_date"),
                        (Long) result.get("car_info_id")));
            }
            if (resutl.getVehicleDiagramItemTrailerId() != null) {
                resutl.setSiblingOrderStatus(
                    this.findSiblingTrailerOrderStatus(resutl.getVehicleDiagramItemTrailerId()));
            }
            return resutl;
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * 車両情報を取得する。
     *
     * @param serviceNo   車両登録番号
     * @param serviceDate 車両サービス開始日
     * @param carInfoId   車両情報ID
     * @return 車両情報
     */
    private List<Map<String, Object>> getCarInfo(String serviceNo, String serviceDate, Long carInfoId) {

        if (!BaseUtil.isNull(serviceNo) && !BaseUtil.isNull(serviceDate)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(serviceDate, formatter);

            String query = "SELECT DISTINCT ON (ci.id) ci.*, " +
                "(CASE WHEN ci.tractor_idcr = '1' THEN vi.vehicle_name ELSE vehicle_avb_resource_item.vehicle_name END) as vehicle_name, "
                +
                "vehicle_avb_resource_item.temperature_range, " +
                "(CASE WHEN ci.id = :id THEN true ELSE false END) as is_matched " +
                "FROM car_info ci " +
                "LEFT JOIN vehicle_avb_resource ON ci.id = vehicle_avb_resource.car_info_id " +
                "LEFT JOIN vehicle_avb_resource_item ON vehicle_avb_resource_item.vehicle_avb_resource_id = vehicle_avb_resource.id "
                +
                "LEFT JOIN c_vehicle_diagram vd ON CAST(vd.id AS VARCHAR) = ci.service_no " +
                "LEFT JOIN c_carrier_operator co ON co.id = vd.operator_id " +
                "LEFT JOIN c_vehicle_info vi ON co.id = vi.operator_id " +
                "WHERE ci.service_no = :serviceNo AND ci.service_strt_date = :serviceDate ";

            List<Map<String, Object>> resultList = entityManager.createNativeQuery(query, Map.class)
                .setParameter("serviceNo", serviceNo)
                .setParameter("serviceDate", localDate)
                .setParameter("id", carInfoId)
                .getResultList();
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(List.class, new StringToIntegerListDeserializer());
            mapper.registerModule(module);
            resultList.forEach(result -> {
                if (result.get("temperature_range") != null) {
                    try {
                        List<Integer> temperatureRange = mapper.readValue(result.get("temperature_range").toString(),
                            List.class);
                        result.put("temperature_range", temperatureRange);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return resultList;
        }

        return null;
    }

    /**
     * 運送計画のシッパー名を取得する。
     *
     * @param transOrderId 運送計画ID
     * @return 運送計画のシッパー名
     */
    private Object getShipperNameByOrderId(Long transOrderId) {
        String queryString =
            "SELECT req_trsp_plan_line_item.cnsg_prty_name_txt, req_cns_line_item.item_name_txt, trans.departure_from, trans.arrival_to, trans.request_collection_time_from, trans.request_collection_time_to, "
                + "trans.transport_date, trans.propose_snapshot, trans.request_snapshot "
                + "FROM t_trans_order trans "
                + "LEFT JOIN (SELECT DISTINCT ON (cnsg_prty_head_off_id) * FROM req_trsp_plan_line_item) as req_trsp_plan_line_item ON trans.shipper_operator_code = req_trsp_plan_line_item.cnsg_prty_head_off_id "
                + "LEFT JOIN req_cns_line_item ON trans.req_cns_line_item_id = req_cns_line_item.id "
                + "WHERE trans.id = :id";
        try {
            Query query = entityManager.createNativeQuery(queryString, Map.class)
                .setParameter("id", transOrderId);
            Map<String, Object> resultMap = (Map<String, Object>) query.getSingleResult();

            //convert json string to object
            try {
                ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                if (resultMap.get("propose_snapshot") != null) {
                    resultMap.put("propose_snapshot",
                        objectMapper.readValue(resultMap.get("propose_snapshot").toString(),
                            CnsLineItemByDateSnapshot.class));
                }

                if (resultMap.get("request_snapshot") != null) {
                    resultMap.put("request_snapshot",
                        objectMapper.readValue(resultMap.get("request_snapshot").toString(),
                            VehicleAvbResourceItemSnapshot.class));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return resultMap;
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * 運送計画の進捗ステータスを取得する。
     *
     * @param searchRequest 検索条件
     * @return 運送計画の進捗ステータス
     */
    private List<Integer> getShipperAdvanceStatus(TransactionShipperSearch searchRequest) {
        List<Integer> listStatus = new ArrayList<>();
        if (searchRequest == null || searchRequest.getAdvanceStatus() == null || searchRequest.getAdvanceStatus()
            .isEmpty()) {
            return listStatus;
        }
        for (String status : searchRequest.getAdvanceStatus()) {
            switch (status) {
                case ParamConstant.AdvanceStatus.PROPOSE -> {
                    listStatus.addAll(
                        List.of(
                            DataBaseConstant.TransOrderStatus.SHIPPER_REQUEST,
                            DataBaseConstant.TransOrderStatus.SHIPPER_RE_REQUEST,
                            DataBaseConstant.TransOrderStatus.CARRIER_PROPOSAL,
                            DataBaseConstant.TransOrderStatus.CARRIER_RE_PROPOSAL,
                            DataBaseConstant.TransOrderStatus.SHIPPER_REJECT_PROPOSE,
                            DataBaseConstant.TransOrderStatus.CARRIER_APPROVE_REJECT
                        )
                    );
                }
                case ParamConstant.AdvanceStatus.CONTRACT -> {
                    listStatus.addAll(
                        List.of(
                            DataBaseConstant.TransOrderStatus.SHIPPER_APPROVE,
                            DataBaseConstant.TransOrderStatus.CARRIER_APPROVE,
                            DataBaseConstant.TransOrderStatus.SHIPPER_MAKE_CONTRACT
                        )
                    );
                }
                case ParamConstant.AdvanceStatus.DECISION_TRANSPORT ->
                    listStatus.add(DataBaseConstant.TransOrderStatus.TRANSPORT_DECISION);
                case ParamConstant.AdvanceStatus.TRANSPORT ->
                    listStatus.add(DataBaseConstant.TransOrderStatus.SHIPPER_START_TRANSPORT);
                case ParamConstant.AdvanceStatus.PAYMENT ->
                    listStatus.add(DataBaseConstant.TransOrderStatus.SHIPPER_MAKE_PAYMENT);
                case ParamConstant.AdvanceStatus.COMPLETE ->
                    listStatus.add(DataBaseConstant.TransOrderStatus.SHIPPER_COMPLETE_TRANSPORT);
            }
        }
        return listStatus;
    }

    /**
     * 運送計画のシッパー名を取得する。
     *
     * @param vehicleDiagramItemTrailerId 車両図形アイテムトレイラID
     * @return 運送計画のシッパー名
     */
    @Override
    public Integer findSiblingTrailerOrderStatus(Long vehicleDiagramItemTrailerId) {
        String statusListCancel = String.format("%d, %d, %d, %d",
            DataBaseConstant.TransOrderStatus.CANCEL,
            DataBaseConstant.TransOrderStatus.SHIPPER_CANCEL,
            DataBaseConstant.TransOrderStatus.CARRIER2_CANCEL,
            DataBaseConstant.TransOrderStatus.CARRIER1_CANCEL
        );

        StringBuilder queryString = new StringBuilder(String.format("""
            SELECT max(tto.status) status
            FROM t_trans_order tto
            JOIN c_vehicle_diagram_item_trailer cvdit ON tto.vehicle_diagram_item_trailer_id = cvdit.id
            WHERE
                tto.status NOT IN (%s) AND
                tto.vehicle_diagram_item_trailer_id IN (SELECT id
                    FROM c_vehicle_diagram_item_trailer
                    WHERE vehicle_diagram_item_id = (
                        SELECT vehicle_diagram_item_id
                        FROM c_vehicle_diagram_item_trailer
                        WHERE id = :vehicleDiagramItemTrailerId
                    ) AND id != :vehicleDiagramItemTrailerId)
            """, statusListCancel));
        try {
            Query query = entityManager.createNativeQuery(queryString.toString(), Integer.class)
                .setParameter("vehicleDiagramItemTrailerId", vehicleDiagramItemTrailerId);
            Object result = query.getSingleResult();
            if (result != null) {
                return ((Number) result).intValue();
            }
        } catch (NoResultException e) {
            //return null
        }
        return null;
    }
}
