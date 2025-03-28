package nlj.business.transaction.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.next.logistic.util.BaseUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.constant.ParamConstant;
import nlj.business.transaction.domain.CnsLineItemByDate;
import nlj.business.transaction.domain.VehicleAvbResourceItem;
import nlj.business.transaction.domain.trans.TransOrder;
import nlj.business.transaction.dto.CnsLineItemByDateSnapshot;
import nlj.business.transaction.dto.TrspAbilityMatchingDetailDTO;
import nlj.business.transaction.dto.matching.HeadResponse;
import nlj.business.transaction.dto.matching.PartnerTrailerResponse;
import nlj.business.transaction.dto.matching.TransMatchingAbilityDetailResponse;
import nlj.business.transaction.dto.matching.TransMatchingAbilityResponse;
import nlj.business.transaction.dto.matching.TransMatchingAbilitySaleHeadResponse;
import nlj.business.transaction.dto.matching.TransMatchingAbilitySaleResponse;
import nlj.business.transaction.dto.matching.TransMatchingDTOResponse;
import nlj.business.transaction.dto.matching.TransMatchingHeadResponse;
import nlj.business.transaction.dto.matching.TransMatchingIdDTOResponse;
import nlj.business.transaction.dto.matching.TransMatchingTrailerIdResponse;
import nlj.business.transaction.dto.matching.TrspPlanIdDTOResponse;
import nlj.business.transaction.mapper.TransMatchingHeadResponseMapper;
import nlj.business.transaction.repository.CutOffInfoRepository;
import nlj.business.transaction.repository.TransMatchingCustomRepository;
import nlj.business.transaction.repository.TransOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 配送マッチングを実行するクラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@AllArgsConstructor
public class TransMatchingCustomRepositoryImpl implements TransMatchingCustomRepository {

    private final EntityManager entityManager;
    private final TransMatchingHeadResponseMapper transMatchingHeadResponseMapper;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final CutOffInfoRepository cutOffInfoRepository;
    private final TransOrderRepository transOrderRepository;

    /**
     * マッチングが必要な計画一覧
     *
     * @param page  結果の開始位置 - ページネーションに使用。スキップする結果の数
     * @param limit 結果の最大件数 - 1ページあたりに取得する結果の数
     * @return マッチングが必要な計画一覧
     */
    @Override
    public Page<TransMatchingDTOResponse> getTransPlanMatching(String companyId, String freeWord,
        List<Integer> temperatureRange, int page, int limit) {
        Page<TransMatchingDTOResponse> matchingData = getMatchingData(companyId, freeWord, temperatureRange, page,
            limit,
            DataBaseConstant.TransMatching.CNS_LINE_ITEM_BY_DATE_ID);
        return matchingData;
    }

    /**
     * マッチングが必要な計画一覧 by transportPlanItemId
     *
     * @param transportPlanItemId transport_plan_item_ids
     * @return マッチングが必要な計画一覧
     */
    @Override
    public List<TrspPlanIdDTOResponse> getTransPlanMatchingById(Long transportPlanItemId) {
        return getMatchingDataByField(null, DataBaseConstant.TransMatching.CNS_LINE_ITEM_BY_DATE_ID,
            transportPlanItemId);
    }

    /**
     * マッチングが必要な輸送能力一覧
     *
     * @param page  結果の開始位置 - ページネーションに使用。スキップする結果の数
     * @param limit 結果の最大件数 - 1ページあたりに取得する結果の数
     * @return マッチングが必要な輸送能力一覧
     */
    @Override
    public Page<TransMatchingHeadResponse> getTransMatchingByTrailer(String transType, String companyId,
        String freeWord, List<Integer> temperatureRange, int page, int limit, Integer isEmergency) {
        String sqlQuery = "WITH result AS ( " +
            "    SELECT " +
            "        subquery.*, " +
            "        count_subquery.total_count " +
            "    FROM " +
            "        ( " +
            "            SELECT " +
            "                DISTINCT ON (t_trans_matching.vehicle_avb_resource_id) " +
            "ARRAY(SELECT CAST(unnest(STRING_TO_ARRAY(REPLACE(vehicle_avb_resource_item.temperature_range, ' ', ''), ',')) AS integer)) AS temperature_range, "
            +
            "                t_trans_matching.trans_type AS trans_type, " +
            "                t_trans_matching.free_word AS free_word, " +
            "                t_trans_order.id AS order_id, " +
            "                TO_CHAR(t_trans_matching.created_date,'YYYY-MM-DD HH24:MI:SS') AS created_at,  " +
            "                t_trans_matching.id AS matching_id, " +
            "                t_trans_matching.shipper_operator_id AS shipper_operator_id, " +
            "                t_trans_matching.carrier_operator_id AS carrier_operator_id, " +
            "                t_trans_matching.carrier2_operator_id AS carrier2_operator_id, " +
            "                t_trans_matching.carrier2_operator_name AS carrier2_operator_name, " +
            sqlFieldParent() +
            "                t_trans_matching.trans_type AS matching_type, " +
            "                t_trans_matching.status AS matching_status, " +
            "                t_trans_order.status AS order_status, " +
            "                t_trans_matching.vehicle_avb_resource_id AS vehicle_avb_resource_id_1, " +
            "                vehicle_avb_resource_item.trip_name as trip_name, " +
            "                t_trans_matching.is_emergency as is_emergency " +
            "            FROM " +
            "                t_trans_matching " +
            "            LEFT JOIN t_trans_order " +
            "                ON t_trans_matching.vehicle_avb_resource_id = t_trans_order.vehicle_avb_resource_id " +
            "            LEFT JOIN vehicle_avb_resource " +
            "                ON t_trans_matching.vehicle_avb_resource_id = vehicle_avb_resource.id " +
            "            LEFT JOIN vehicle_avb_resource_item " +
            "                ON t_trans_matching.vehicle_avb_resource_item_id = vehicle_avb_resource_item.id " +
            "            LEFT JOIN car_info " +
            "                ON vehicle_avb_resource.car_info_id = car_info.id " +
            "            LEFT JOIN c_vehicle_diagram_item_trailer " +
            "                ON c_vehicle_diagram_item_trailer.id = t_trans_matching.vehicle_diagram_item_trailer_id " +
            "            LEFT JOIN cns_line_item_by_date " +
            "                ON cns_line_item_by_date.id = t_trans_matching.cns_line_item_by_date_id " +
            "            WHERE " +
            "                (car_info.tractor_idcr != '1' OR car_info.tractor_idcr IS NULL) " +
            "               AND (t_trans_matching.carrier_operator_id  = '" + companyId +
            "' OR t_trans_matching.carrier2_operator_id = '" + companyId + "' ) ";
        sqlQuery += "            ORDER BY " +
            "                t_trans_matching.vehicle_avb_resource_id, t_trans_matching.created_date DESC " +
            "        ) AS subquery " +
            "    LEFT JOIN " +
            "        ( " +
            "            SELECT " +
            "                t_trans_matching.vehicle_avb_resource_id AS vehicle_avb_resource_id, " +
            "                COUNT(*) AS total_count " +
            "            FROM " +
            "                t_trans_matching " +
            "                WHERE t_trans_matching.status = 1 " +
            "            GROUP BY " +
            "                t_trans_matching.vehicle_avb_resource_id,t_trans_matching.status " +
            "        ) AS count_subquery " +
            "    ON subquery.vehicle_avb_resource_id_1 = count_subquery.vehicle_avb_resource_id " +
            "), ordered_result AS (  " +
            "SELECT DISTINCT ON (r1.service_no, r1.service_strt_date) " +
            sqlFieldResponse() +
            sqlSelfJoin() +
            "WHERE ((r1.matching_status = 1 OR r2.matching_status = 1) AND (r1.order_status < 151 OR r2.order_status < 151 OR r1.order_status IS NULL OR r1.order_status IS NULL)) ";
        if (isEmergency != null) {
            sqlQuery += "AND (r1.is_emergency = :isEmergency OR r2.is_emergency = :isEmergency)";
        }
        if (!BaseUtil.isNull(freeWord)) {
            sqlQuery += " AND (r1.free_word ILIKE '%" + freeWord + "%' " +
                "OR r2.free_word ILIKE '%" + freeWord + "%' " +
                "OR r1.vehicle_name ILIKE '%" + freeWord + "%' " +
                "OR r2.vehicle_name ILIKE '%" + freeWord + "%') ";
        }
        if (temperatureRange != null && !temperatureRange.isEmpty()) {
            sqlQuery += " AND ((r1.temperature_range @> " + convertTempeRange(temperatureRange) + ") " +
                " OR (r2.temperature_range @> " + convertTempeRange(temperatureRange) + ")) ";
        }
        if (!BaseUtil.isNull(transType)) {
            sqlQuery +=
                " AND (r1.trans_type = " + Integer.parseInt(transType) + " OR r2.trans_type = " + Integer.parseInt(
                    transType) + ")";
        }
        sqlQuery += " ) SELECT *FROM ordered_result ORDER BY created_at_1 DESC, service_strt_date_1 DESC LIMIT :limit OFFSET :offset ";

        Query query = entityManager.createNativeQuery(sqlQuery, TransMatchingAbilityResponse.class);
        query.setParameter("limit", limit);
        query.setParameter("offset", limit * page);

        if (isEmergency != null) {
            query.setParameter("isEmergency", isEmergency);
        }

        StringBuilder countQuery = new StringBuilder();
        countQuery.append("WITH result AS ( "
            + "    SELECT "
            + "        subquery.*, "
            + "        count_subquery.total_count "
            + "    FROM "
            + "        ( "
            + "            SELECT "
            + "                DISTINCT ON (t_trans_matching.vehicle_avb_resource_id) "
            + "                t_trans_matching.id AS matching_id, "
            + "                car_info.id AS car_info_id, "
            + "                car_info.service_no AS service_no, "
            + "                car_info.service_strt_date AS service_strt_date, "
            + "                t_trans_matching.trans_type AS trans_type, "
            + "                t_trans_matching.free_word AS free_word, "
            + "ARRAY(SELECT CAST(unnest(STRING_TO_ARRAY(REPLACE(vehicle_avb_resource_item.temperature_range, ' ', ''), ',')) AS integer)) AS temperature_range, "
            + "                t_trans_matching.status AS matching_status, "
            + "                t_trans_order.status AS order_status, "
            + "                vehicle_avb_resource_item.vehicle_name AS vehicle_name, "
            + "                t_trans_matching.vehicle_avb_resource_id AS vehicle_avb_resource_id_1, "
            + "                t_trans_matching.is_emergency as is_emergency "
            + "            FROM "
            + "                t_trans_matching "
            + "            LEFT JOIN vehicle_avb_resource "
            + "                ON t_trans_matching.vehicle_avb_resource_id = vehicle_avb_resource.id "
            + "            LEFT JOIN vehicle_avb_resource_item "
            + "                ON t_trans_matching.vehicle_avb_resource_item_id = vehicle_avb_resource_item.id "
            + "            LEFT JOIN car_info "
            + "                ON vehicle_avb_resource.car_info_id = car_info.id "
            + "            LEFT JOIN t_trans_order "
            + "                ON t_trans_matching.vehicle_avb_resource_id = t_trans_order.vehicle_avb_resource_id "
            + "            WHERE "
            + "                (car_info.tractor_idcr != '1' OR car_info.tractor_idcr IS NULL) "
            + "               AND (t_trans_matching.carrier_operator_id  = '" + companyId
            + "' OR t_trans_matching.carrier2_operator_id = '" + companyId + "' ) ");
        countQuery.append(" ORDER BY "
            + "                t_trans_matching.vehicle_avb_resource_id, t_trans_matching.created_date DESC "
            + "        ) AS subquery "
            + "    LEFT JOIN "
            + "        ( "
            + "            SELECT "
            + "                t_trans_matching.vehicle_avb_resource_id AS vehicle_avb_resource_id, "
            + "                COUNT(*) AS total_count "
            + "            FROM "
            + "                t_trans_matching "
            + "                WHERE t_trans_matching.status = 1 "
            + "            GROUP BY "
            + "                t_trans_matching.vehicle_avb_resource_id, t_trans_matching.status "
            + "        ) AS count_subquery "
            + "    ON subquery.vehicle_avb_resource_id_1 = count_subquery.vehicle_avb_resource_id "
            + ") "
            + "SELECT COUNT(*) "
            + "FROM ( "
            + "    SELECT DISTINCT ON (r1.service_no, r1.service_strt_date) r1.car_info_id "
            + "    FROM result r1 "
            + "    LEFT JOIN result r2 "
            + "        ON r1.service_no = r2.service_no "
            + "        AND r1.service_strt_date = r2.service_strt_date "
            + "        AND r1.car_info_id != r2.car_info_id "
            + "   WHERE ((r1.matching_status = 1 OR r2.matching_status = 1) AND (r1.order_status < 151 OR r2.order_status < 151  OR r1.order_status IS NULL OR r1.order_status IS NULL))  ");
        if (isEmergency != null) {
            countQuery.append("AND (r1.is_emergency = :isEmergency OR r2.is_emergency = :isEmergency) ");
        }
        if (!BaseUtil.isNull(freeWord)) {
            countQuery.append(" AND (r1.free_word ILIKE '%" + freeWord + "%' " +
                "OR r2.free_word ILIKE '%" + freeWord + "%' " +
                "OR r1.vehicle_name ILIKE '%" + freeWord + "%' " +
                "OR r1.vehicle_name ILIKE '%" + freeWord + "%') ");
        }
        if (temperatureRange != null && !temperatureRange.isEmpty()) {
            countQuery.append(" AND ((r1.temperature_range @> " + convertTempeRange(temperatureRange) + ") " +
                " OR (r2.temperature_range @> " + convertTempeRange(temperatureRange) + ")) ");
        }
        if (!BaseUtil.isNull(transType)) {
            countQuery.append(
                " AND (r1.trans_type = " + Integer.parseInt(transType) + " OR r2.trans_type = " + Integer.parseInt(
                    transType) + ")");
        }
        countQuery.append(") AS distinct_car_info");
        Query countNativeQuery = entityManager.createNativeQuery(countQuery.toString());
        if (isEmergency != null) {
            countNativeQuery.setParameter("isEmergency", isEmergency);
        }
        long total = ((Number) countNativeQuery.getSingleResult()).longValue();
        List<TransMatchingHeadResponse> result = transMatchingHeadResponseMapper.toMapperList(query.getResultList());

        for (TransMatchingHeadResponse headResponse : result) {
            Query headQueryNative = entityManager.createNativeQuery(sqlGetTruck(), HeadResponse.class);
            headQueryNative.setParameter("serviceNo", headResponse.getServiceNo());
            headQueryNative.setParameter("serviceStrtDate", headResponse.getServiceStrtDate());
            List<HeadResponse> headResponses = headQueryNative.getResultList();
            if (!headResponses.isEmpty()) {
                HeadResponse headResponseResult = headResponses.get(0);
                if (headResponseResult.getCarCtrlNumId() != null) {
                    headResponse.setHeadResponse(headResponseResult);
                }
            }
            if (headResponse.getTrailer1().getVehicleAvbResourceId() != null) {
                headResponse.getTrailer1().setCutOffInfos(cutOffInfoRepository.findByVehicleAvbResourceId(
                    Long.parseLong(headResponse.getTrailer1().getVehicleAvbResourceId())));
            }
            if (headResponse.getTrailer2().getVehicleAvbResourceId() != null) {
                headResponse.getTrailer2().setCutOffInfos(cutOffInfoRepository.findByVehicleAvbResourceId(
                    Long.parseLong(headResponse.getTrailer2().getVehicleAvbResourceId())));
            }

        }
        return new PageImpl<>(result, PageRequest.of(page, limit), total);
    }

    /**
     * 運送計画のトラックIDに基づいて運送計画を取得する。
     *
     * @param vehicleAvbResourceId 運送計画のトラックID
     * @param transType            運送計画の種類
     * @return 運送計画詳細応答
     */
    @Override
    public TransMatchingAbilityDetailResponse getTransMatchingByTrailerId(Long vehicleAvbResourceId, String transType) {
        List<TrspPlanIdDTOResponse> transMatchingIdDTOResponses = getMatchingDataByField(transType,
            DataBaseConstant.TransMatching.VEHICLE_AVB_RESOURCE_ID,
            vehicleAvbResourceId);
        List<TrspAbilityMatchingDetailDTO> matchingList = new ArrayList<>();
        for (TrspPlanIdDTOResponse transMatchingIdDTOResponse : transMatchingIdDTOResponses) {
            CnsLineItemByDateSnapshot cnsLineItemByDateSnapshot = transMatchingIdDTOResponse.getRequestSnapshot();
            cnsLineItemByDateSnapshot.setMatchingId(transMatchingIdDTOResponse.getId());
            cnsLineItemByDateSnapshot.setShipperOperatorName(transMatchingIdDTOResponse.getShipperOperatorName());
            TrspAbilityMatchingDetailDTO trspAbilityMatchingDetailDTO = objectMapper.convertValue(
                cnsLineItemByDateSnapshot, TrspAbilityMatchingDetailDTO.class);
            trspAbilityMatchingDetailDTO.setVehicleAvbResourceItemId(
                transMatchingIdDTOResponse.getVehicleAvbResourceItemId());
            trspAbilityMatchingDetailDTO.setParentOrderProposeSnapshot(
                transMatchingIdDTOResponse.getParentOrderProposeSnapshot());
            trspAbilityMatchingDetailDTO.setVehicleName(transMatchingIdDTOResponse.getVehicleName());
            trspAbilityMatchingDetailDTO.setTrailerLicensePltNumId(
                transMatchingIdDTOResponse.getTrailerLicensePltNumId());
            trspAbilityMatchingDetailDTO.setCreatedAt(transMatchingIdDTOResponse.getCreatedDate());
            trspAbilityMatchingDetailDTO.setCarrierOperatorId(transMatchingIdDTOResponse.getCarrierOperatorId());
            trspAbilityMatchingDetailDTO.setCarrierOperatorName(transMatchingIdDTOResponse.getCarrierOperatorName());
            trspAbilityMatchingDetailDTO.setCarrier2OperatorId(transMatchingIdDTOResponse.getCarrier2OperatorId());
            trspAbilityMatchingDetailDTO.setCarrier2OperatorName(transMatchingIdDTOResponse.getCarrier2OperatorName());
            if (cnsLineItemByDateSnapshot.getTransType() == 1 && cnsLineItemByDateSnapshot.getTransOrderId() != null) {
                Optional<TransOrder> transOrder = transOrderRepository.findById(
                    cnsLineItemByDateSnapshot.getTransOrderId());
                transOrder.ifPresent(
                    order -> trspAbilityMatchingDetailDTO.setShipperOperatorId(order.getShipperOperatorId()));
            }
            matchingList.add(trspAbilityMatchingDetailDTO);
        }
        String sqlQuery = "SELECT " +
            "  car_info.service_no, " +
            "  car_info.service_name, " +
            "  car_info.service_strt_date, " +
            "  car_info.service_strt_time, " +
            "  car_info.service_end_date, " +
            "  car_info.service_end_time, " +
            "  vehicle_avb_resource_item.price, " +
            "  car_info.trailer_license_plt_num_id, " +
            "  car_info.giai, " +
            "  vehicle_avb_resource.trsp_op_strt_area_line_one_txt as departure_from, " +
            "  vehicle_avb_resource.trsp_op_end_area_line_one_txt as arrival_to, " +
            "  vehicle_avb_resource_item.operator_name as carrier_operator_name, " +
            "  vehicle_avb_resource_item.vehicle_name as vehicle_name, " +
            "ARRAY(SELECT CAST(unnest(STRING_TO_ARRAY(REPLACE(vehicle_avb_resource_item.temperature_range, ' ', ''), ',')) AS integer)) AS temperature_range, "
            +
            "  vehicle_avb_resource_item.cut_off_fee as cut_off_fee " +
            "FROM car_info " +
            "    LEFT JOIN vehicle_avb_resource ON car_info.id = vehicle_avb_resource.car_info_id " +
            "    LEFT JOIN vehicle_avb_resource_item ON vehicle_avb_resource.id = vehicle_avb_resource_item.vehicle_avb_resource_id "
            +
            "    WHERE vehicle_avb_resource.id= :vehicleAvbResourceId";

        Query query = entityManager.createNativeQuery(sqlQuery, TransMatchingTrailerIdResponse.class);
        query.setParameter("vehicleAvbResourceId", vehicleAvbResourceId);
        TransMatchingTrailerIdResponse transMatchingTrailerIdResponse = (TransMatchingTrailerIdResponse) query.getResultList()
            .get(0);
        return new TransMatchingAbilityDetailResponse(transMatchingTrailerIdResponse, matchingList);
    }

    /**
     * 運送計画のトラックIDに基づいて運送計画を取得する。
     *
     * @param transType        運送計画の種類
     * @param advanceStatus    運送計画のステータス
     * @param companyId        運送業者オペレーターID
     * @param status           運送計画のステータス
     * @param freeWord         運送計画のフリーワード
     * @param temperatureRange 運送計画の温度範囲
     * @param page             ページ番号
     * @param limit            ページサイズ
     * @param isEmergency      運送計画の緊急ステータス
     * @return 運送計画ヘッダー応答のページ
     */
    @Override
    public Page<TransMatchingHeadResponse> getTransactionByTrailer(String transType, List<String> advanceStatus,
        String companyId, String status, String freeWord, List<Integer> temperatureRange, int page, int limit,
        Integer isEmergency) {
        String sqlQuery = "WITH result AS ( " +
            "    SELECT " +
            "        subquery.*, " +
            "        count_subquery.total_count " +
            "    FROM " +
            "        ( " +
            "            SELECT " +
            "                DISTINCT ON (t_trans_order.vehicle_avb_resource_id) " +
            "ARRAY(SELECT CAST(unnest(STRING_TO_ARRAY(REPLACE(vehicle_avb_resource_item.temperature_range, ' ', ''), ',')) AS integer)) AS temperature_range, "
            +
            "                t_trans_order.trans_type AS trans_type, " +
            "                t_trans_order.id AS order_id, " +
            "                TO_CHAR(t_trans_order.created_date,'YYYY-MM-DD HH24:MI:SS') AS created_at,  " +
            "                t_trans_matching.id AS matching_id, " +
            "                t_trans_order.shipper_operator_id AS shipper_operator_id, " +
            "                t_trans_order.carrier_operator_id AS carrier_operator_id, " +
            "                t_trans_order.carrier2_operator_id AS carrier2_operator_id, " +
            "                t_trans_order.carrier2_operator_name AS carrier2_operator_name, " +
            "                t_trans_order.free_word AS free_word, " +
            sqlFieldParent() +
            "                t_trans_matching.trans_type AS matching_type, " +
            "                t_trans_matching.status AS matching_status, " +
            "                t_trans_order.status AS order_status, " +
            "                t_trans_matching.vehicle_avb_resource_id AS vehicle_avb_resource_id_1, " +
            "                vehicle_avb_resource_item.trip_name AS trip_name, " +
            "                t_trans_order.is_emergency as is_emergency " +
            "            FROM " +
            "                t_trans_order " +
            "            LEFT JOIN t_trans_matching " +
            "                ON t_trans_matching.id = t_trans_order.trans_matching_id " +
            "            LEFT JOIN vehicle_avb_resource " +
            "                ON t_trans_order.vehicle_avb_resource_id = vehicle_avb_resource.id " +
            "            LEFT JOIN vehicle_avb_resource_item " +
            "                ON t_trans_order.vehicle_avb_resource_item_id = vehicle_avb_resource_item.id " +
            "            LEFT JOIN car_info " +
            "                ON vehicle_avb_resource.car_info_id = car_info.id " +
            "            LEFT JOIN c_vehicle_diagram_item_trailer " +
            "                ON c_vehicle_diagram_item_trailer.id = t_trans_order.vehicle_diagram_item_trailer_id " +
            "            LEFT JOIN cns_line_item_by_date " +
            "                ON cns_line_item_by_date.id = t_trans_matching.cns_line_item_by_date_id " +
            "            WHERE " +
            "                (car_info.tractor_idcr != '1' OR car_info.tractor_idcr IS NULL) " +
            "                AND (t_trans_order.cns_line_item_by_date_id != 0 " +
            "                AND t_trans_order.vehicle_avb_resource_id != 0 AND t_trans_order.vehicle_avb_resource_item_id != 0 ) "
            +
            "                AND vehicle_avb_resource_item.operator_id =  '" + companyId + "' ";
        sqlQuery += "            ORDER BY " +
            "                t_trans_order.vehicle_avb_resource_id, t_trans_order.trans_type DESC " +
            "        ) AS subquery " +
            "    LEFT JOIN " +
            "        ( " +
            "            SELECT " +
            "                t_trans_order.vehicle_avb_resource_id AS vehicle_avb_resource_id, " +
            "                COUNT(*) AS total_count " +
            "            FROM " +
            "                t_trans_order " +
            "            GROUP BY " +
            "                t_trans_order.vehicle_avb_resource_id " +
            "        ) AS count_subquery " +
            "    ON subquery.vehicle_avb_resource_id_1 = count_subquery.vehicle_avb_resource_id " +
            "),  ordered_result AS (" +
            "SELECT DISTINCT ON (service_no_1, service_strt_date_1) " +
            sqlFieldResponse() +
            sqlSelfJoin();

        if (status != null ||
            (advanceStatus != null && !advanceStatus.isEmpty()) ||
            (temperatureRange != null && !temperatureRange.isEmpty()) ||
            !BaseUtil.isNull(freeWord) ||
            transType != null) {
            sqlQuery += " WHERE ";
            boolean firstCondition = true;

            if (isEmergency != null) {
                if (!firstCondition) {
                    sqlQuery += " AND ";
                }
                firstCondition = false;
                sqlQuery += " (r1.is_emergency = :isEmergency OR r2.is_emergency = :isEmergency) ";
            }

            if (!BaseUtil.isNull(freeWord)) {
                if (!firstCondition) {
                    sqlQuery += " AND ";
                }
                sqlQuery += " (r1.free_word ILIKE '%" + freeWord + "%' " +
                    "OR r2.free_word ILIKE '%" + freeWord + "%' " +
                    "OR r1.vehicle_name ILIKE '%" + freeWord + "%' " +
                    "OR r2.vehicle_name ILIKE '%" + freeWord + "%') ";
                firstCondition = false;
            }
            if (temperatureRange != null && !temperatureRange.isEmpty()) {
                if (!firstCondition) {
                    sqlQuery += " AND ";
                }
                sqlQuery += "((r1.temperature_range @> " + convertTempeRange(temperatureRange) + ") " +
                    " OR (r2.temperature_range @> " + convertTempeRange(temperatureRange) + ")) ";
                firstCondition = false;
            }
            if (transType != null) {
                if (!firstCondition) {
                    sqlQuery += " AND ";
                }
                sqlQuery +=
                    "  (r1.trans_type = " + Integer.parseInt(transType) + " OR r2.trans_type = " + Integer.parseInt(
                        transType) + ")";
                firstCondition = false;
            }

            if (status != null) {
                if (!firstCondition) {
                    sqlQuery += " AND ";
                }
                switch (status) {
                    case ParamConstant.OrderStatus.PROPOSE -> sqlQuery +=
                        " (r1.order_status IN (" + ParamConstant.OrderStatus.PROPOSE_110 + ", "
                            + ParamConstant.OrderStatus.PROPOSE_111 + ", " + ParamConstant.OrderStatus.PROPOSE_120
                            + ", " + ParamConstant.OrderStatus.PROPOSE_121 + ") OR r2.order_status IN ("
                            + ParamConstant.OrderStatus.PROPOSE_110 + ", " + ParamConstant.OrderStatus.PROPOSE_111
                            + ", " + ParamConstant.OrderStatus.PROPOSE_120 + ", "
                            + ParamConstant.OrderStatus.PROPOSE_121 + ")) ";
                    case ParamConstant.OrderStatus.APPROVAL -> sqlQuery +=
                        "  (r1.order_status IN (" + ParamConstant.OrderStatus.APPROVAL_130 + ", "
                            + ParamConstant.OrderStatus.APPROVAL_131 + ") OR r2.order_status IN ("
                            + ParamConstant.OrderStatus.APPROVAL_130 + ", " + ParamConstant.OrderStatus.APPROVAL_131
                            + "))";
                    case ParamConstant.OrderStatus.CONTRACT -> sqlQuery +=
                        "  (r1.order_status = " + ParamConstant.OrderStatus.CONTRACT_140 + " OR r2.order_status = "
                            + ParamConstant.OrderStatus.CONTRACT_140 + ")";
                    case ParamConstant.OrderStatus.PAYMENT -> sqlQuery +=
                        "  (r1.order_status = " + ParamConstant.OrderStatus.PAYMENT_150 + " OR r2.order_status = "
                            + ParamConstant.OrderStatus.PAYMENT_150 + ")";
                    case ParamConstant.OrderStatus.TRANSPORT -> sqlQuery +=
                        "  (r1.order_status = " + ParamConstant.OrderStatus.TRANSPORT_160 + " OR r2.order_status = "
                            + ParamConstant.OrderStatus.TRANSPORT_160 + ")";
                    case ParamConstant.OrderStatus.COMPLETE -> sqlQuery +=
                        "  (r1.order_status = " + ParamConstant.OrderStatus.COMPLETE_161 + " OR r2.order_status = "
                            + ParamConstant.OrderStatus.COMPLETE_161 + ")";
                    case ParamConstant.OrderStatus.CONFIRM -> sqlQuery +=
                        "  (r1.order_status IN " + DataBaseConstant.Status.LIST_STATUS_CONFIRM
                            + " OR r2.order_status IN " + DataBaseConstant.Status.LIST_STATUS_CONFIRM + ")";
                }
                firstCondition = false;
            }
            if (advanceStatus != null && !advanceStatus.isEmpty()) {
                if (!firstCondition) {
                    sqlQuery += " AND ";
                }
                sqlQuery += "(";
                boolean firstAdvanceStatus = true;
                if (advanceStatus.contains(ParamConstant.AdvanceStatus.PROPOSE)) {
                    if (!firstAdvanceStatus) {
                        sqlQuery += " OR ";
                    }
                    sqlQuery += " r1.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_PROPOSE
                        + " OR r2.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_PROPOSE;
                    firstAdvanceStatus = false;
                }
                if (advanceStatus.contains(ParamConstant.AdvanceStatus.CONTRACT)) {
                    if (!firstAdvanceStatus) {
                        sqlQuery += " OR ";
                    }
                    sqlQuery += " r1.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_CONTRACT
                        + " OR r2.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_CONTRACT;
                    firstAdvanceStatus = false;
                }
                if (advanceStatus.contains(ParamConstant.AdvanceStatus.DECISION_TRANSPORT)) {
                    if (!firstAdvanceStatus) {
                        sqlQuery += " OR ";
                    }
                    sqlQuery += " r1.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_DECISION_TRANSPORT
                        + " OR r2.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_DECISION_TRANSPORT;
                    firstAdvanceStatus = false;
                }
                if (advanceStatus.contains(ParamConstant.AdvanceStatus.TRANSPORT)) {
                    if (!firstAdvanceStatus) {
                        sqlQuery += " OR ";
                    }
                    sqlQuery += " r1.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_TRANSPORT
                        + " OR r2.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_TRANSPORT;
                    firstAdvanceStatus = false;
                }
                if (advanceStatus.contains(ParamConstant.AdvanceStatus.PAYMENT)) {
                    if (!firstAdvanceStatus) {
                        sqlQuery += " OR ";
                    }
                    sqlQuery += " r1.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_PAYMENT
                        + " OR r2.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_PAYMENT;
                    firstAdvanceStatus = false;
                }
                if (advanceStatus.contains(ParamConstant.AdvanceStatus.COMPLETE)) {
                    if (!firstAdvanceStatus) {
                        sqlQuery += " OR ";
                    }
                    sqlQuery += " r1.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_COMPLETE
                        + " OR r2.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_COMPLETE;
                }
                sqlQuery += ")";
            }
        }

        sqlQuery += ") " +
            " SELECT * FROM ordered_result ORDER BY created_at_1 DESC ";
        sqlQuery += " LIMIT :limit OFFSET :offset";
        Query query = entityManager.createNativeQuery(sqlQuery, TransMatchingAbilityResponse.class);
        query.setParameter("limit", limit);
        query.setParameter("offset", limit * page);
        if (isEmergency != null) {
            query.setParameter("isEmergency", isEmergency);
        }

        StringBuilder countQuery = new StringBuilder();
        countQuery.append("WITH result AS ( "
            + "    SELECT "
            + "        subquery.*, "
            + "        count_subquery.total_count "
            + "    FROM "
            + "        ( "
            + "            SELECT "
            + "                DISTINCT ON (t_trans_order.vehicle_avb_resource_id) "
            + "                t_trans_order.id AS order_id, "
            + "                t_trans_order.free_word AS free_word, "
            + "                t_trans_order.trans_type AS trans_type, "
            + "                car_info.id AS car_info_id, "
            + "                car_info.service_no AS service_no, "
            + "                car_info.service_strt_date AS service_strt_date, "
            + "                t_trans_order.status AS order_status, "
            + "                vehicle_avb_resource_item.vehicle_name AS vehicle_name, "
            + "ARRAY(SELECT CAST(unnest(STRING_TO_ARRAY(REPLACE(vehicle_avb_resource_item.temperature_range, ' ', ''), ',')) AS integer)) AS temperature_range, "
            + "                t_trans_order.vehicle_avb_resource_id AS vehicle_avb_resource_id_1, "
            + "                t_trans_order.is_emergency as is_emergency "
            + "            FROM "
            + "                t_trans_order "
            + "            LEFT JOIN vehicle_avb_resource "
            + "                ON t_trans_order.vehicle_avb_resource_id = vehicle_avb_resource.id "
            + "            LEFT JOIN vehicle_avb_resource_item "
            + "                ON t_trans_order.vehicle_avb_resource_item_id = vehicle_avb_resource_item.id "
            + "            LEFT JOIN car_info "
            + "                ON vehicle_avb_resource.car_info_id = car_info.id "
            + "            WHERE "
            + "                (car_info.tractor_idcr != '1' OR car_info.tractor_idcr IS NULL) "
            + "                AND (t_trans_order.cns_line_item_by_date_id != 0 "
            + "                AND t_trans_order.vehicle_avb_resource_id != 0 AND t_trans_order.vehicle_avb_resource_item_id != 0 ) "
            + "                AND vehicle_avb_resource_item.operator_id =  '" + companyId + "' ");
        countQuery.append(" ORDER BY "
            + "                t_trans_order.vehicle_avb_resource_id, t_trans_order.trans_type DESC "
            + "        ) AS subquery "
            + "    LEFT JOIN "
            + "        ( "
            + "            SELECT "
            + "                t_trans_order.vehicle_avb_resource_id AS vehicle_avb_resource_id, "
            + "                COUNT(*) AS total_count "
            + "            FROM "
            + "                t_trans_order "
            + "            GROUP BY "
            + "                t_trans_order.vehicle_avb_resource_id "
            + "        ) AS count_subquery "
            + "    ON subquery.vehicle_avb_resource_id_1 = count_subquery.vehicle_avb_resource_id "
            + ") "
            + "SELECT COUNT(*) "
            + "FROM ( "
            + "    SELECT DISTINCT ON (r1.service_no, r1.service_strt_date) r1.car_info_id "
            + "    FROM result r1 "
            + "    LEFT JOIN result r2 "
            + "        ON r1.service_no = r2.service_no "
            + "        AND r1.service_strt_date = r2.service_strt_date "
            + "        AND r1.car_info_id != r2.car_info_id ");

        if (status != null ||
            (advanceStatus != null && !advanceStatus.isEmpty()) ||
            (temperatureRange != null && !temperatureRange.isEmpty()) ||
            !BaseUtil.isNull(freeWord) ||
            transType != null) {
            countQuery.append(" WHERE ");
            boolean firstCondition = true;

            if (isEmergency != null) {
                if (!firstCondition) {
                    countQuery.append(" AND ");
                }
                firstCondition = false;
                countQuery.append(" (r1.is_emergency = :isEmergency OR r2.is_emergency = :isEmergency) ");
            }

            if (!BaseUtil.isNull(freeWord)) {
                if (!firstCondition) {
                    countQuery.append(" AND ");
                }
                countQuery.append(" (r1.free_word ILIKE '%" + freeWord + "%' " +
                    "OR r2.free_word ILIKE '%" + freeWord + "%' " +
                    "OR r1.vehicle_name ILIKE '%" + freeWord + "%' " +
                    "OR r2.vehicle_name ILIKE '%" + freeWord + "%') ");
                firstCondition = false;
            }
            if (temperatureRange != null && !temperatureRange.isEmpty()) {
                if (!firstCondition) {
                    countQuery.append(" AND ");
                }
                countQuery.append("((r1.temperature_range @> " + convertTempeRange(temperatureRange) + ") " +
                    " OR (r2.temperature_range @> " + convertTempeRange(temperatureRange) + ")) ");
                firstCondition = false;
            }
            if (transType != null) {
                if (!firstCondition) {
                    countQuery.append(" AND ");
                }
                countQuery.append(
                    "  (r1.trans_type = " + Integer.parseInt(transType) + " OR r2.trans_type = " + Integer.parseInt(
                        transType) + ")");
                firstCondition = false;
            }

            if (status != null) {
                if (!firstCondition) {
                    countQuery.append(" AND ");
                }
                switch (status) {
                    case ParamConstant.OrderStatus.PROPOSE -> countQuery.append(
                        "  (r1.order_status IN (" + ParamConstant.OrderStatus.PROPOSE_110 + ", "
                            + ParamConstant.OrderStatus.PROPOSE_111 + ", " + ParamConstant.OrderStatus.PROPOSE_120
                            + ", " + ParamConstant.OrderStatus.PROPOSE_121 + ") OR r2.order_status IN ("
                            + ParamConstant.OrderStatus.PROPOSE_110 + ", " + ParamConstant.OrderStatus.PROPOSE_111
                            + ", " + ParamConstant.OrderStatus.PROPOSE_120 + ", "
                            + ParamConstant.OrderStatus.PROPOSE_121 + "))");
                    case ParamConstant.OrderStatus.APPROVAL -> countQuery.append(
                        "  (r1.order_status IN (" + ParamConstant.OrderStatus.APPROVAL_130 + ", "
                            + ParamConstant.OrderStatus.APPROVAL_131 + ") OR r2.order_status IN ("
                            + ParamConstant.OrderStatus.APPROVAL_130 + ", " + ParamConstant.OrderStatus.APPROVAL_131
                            + "))");
                    case ParamConstant.OrderStatus.CONTRACT -> countQuery.append(
                        "  (r1.order_status = " + ParamConstant.OrderStatus.CONTRACT_140 + " OR r2.order_status = "
                            + ParamConstant.OrderStatus.CONTRACT_140 + ")");
                    case ParamConstant.OrderStatus.PAYMENT -> countQuery.append(
                        "  (r1.order_status = " + ParamConstant.OrderStatus.PAYMENT_150 + " OR r2.order_status = "
                            + ParamConstant.OrderStatus.PAYMENT_150 + ")");
                    case ParamConstant.OrderStatus.TRANSPORT -> countQuery.append(
                        "  (r1.order_status = " + ParamConstant.OrderStatus.TRANSPORT_160 + " OR r2.order_status = "
                            + ParamConstant.OrderStatus.TRANSPORT_160 + ")");
                    case ParamConstant.OrderStatus.COMPLETE -> countQuery.append(
                        "  (r1.order_status = " + ParamConstant.OrderStatus.COMPLETE_161 + " OR r2.order_status = "
                            + ParamConstant.OrderStatus.COMPLETE_161 + ")");
                    case ParamConstant.OrderStatus.CONFIRM -> countQuery.append(
                        "  (r1.order_status IN " + DataBaseConstant.Status.LIST_STATUS_CONFIRM
                            + " OR r2.order_status IN " + DataBaseConstant.Status.LIST_STATUS_CONFIRM + ")");
                }
                firstCondition = false;
            }
            if (advanceStatus != null && !advanceStatus.isEmpty()) {
                if (!firstCondition) {
                    countQuery.append(" AND ");
                }
                countQuery.append("(");
                boolean firstAdvanceStatus = true;
                if (advanceStatus.contains(ParamConstant.AdvanceStatus.PROPOSE)) {
                    if (!firstAdvanceStatus) {
                        countQuery.append(" OR ");
                    }
                    countQuery.append(" r1.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_PROPOSE
                        + " OR r2.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_PROPOSE);
                    firstAdvanceStatus = false;
                }
                if (advanceStatus.contains(ParamConstant.AdvanceStatus.CONTRACT)) {
                    if (!firstAdvanceStatus) {
                        countQuery.append(" OR ");
                    }
                    countQuery.append(" r1.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_CONTRACT
                        + " OR r2.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_CONTRACT);
                    firstAdvanceStatus = false;
                }
                if (advanceStatus.contains(ParamConstant.AdvanceStatus.DECISION_TRANSPORT)) {
                    if (!firstAdvanceStatus) {
                        countQuery.append(" OR ");
                    }
                    countQuery.append(
                        " r1.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_DECISION_TRANSPORT
                            + " OR r2.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_DECISION_TRANSPORT);
                    firstAdvanceStatus = false;
                }
                if (advanceStatus.contains(ParamConstant.AdvanceStatus.TRANSPORT)) {
                    if (!firstAdvanceStatus) {
                        countQuery.append(" OR ");
                    }
                    countQuery.append(" r1.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_TRANSPORT
                        + " OR r2.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_TRANSPORT);
                    firstAdvanceStatus = false;
                }
                if (advanceStatus.contains(ParamConstant.AdvanceStatus.PAYMENT)) {
                    if (!firstAdvanceStatus) {
                        countQuery.append(" OR ");
                    }
                    countQuery.append(" r1.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_PAYMENT
                        + " OR r2.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_PAYMENT);
                    firstAdvanceStatus = false;
                }
                if (advanceStatus.contains(ParamConstant.AdvanceStatus.COMPLETE)) {
                    if (!firstAdvanceStatus) {
                        countQuery.append(" OR ");
                    }
                    countQuery.append(" r1.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_COMPLETE
                        + " OR r2.order_status IN " + ParamConstant.AdvanceStatus.LIST_STATUS_COMPLETE);
                }
                countQuery.append(")");
            }
        }
        countQuery.append(") AS distinct_car_info");

        Query countNativeQuery = entityManager.createNativeQuery(countQuery.toString());
        if (isEmergency != null) {
            countNativeQuery.setParameter("isEmergency", isEmergency);
        }
        long total = ((Number) countNativeQuery.getSingleResult()).longValue();
        List<TransMatchingHeadResponse> result = transMatchingHeadResponseMapper.toMapperList(query.getResultList());

        for (TransMatchingHeadResponse headResponse : result) {
            Query headQueryNative = entityManager.createNativeQuery(sqlGetTruck(), HeadResponse.class);
            headQueryNative.setParameter("serviceNo", headResponse.getServiceNo());
            headQueryNative.setParameter("serviceStrtDate", headResponse.getServiceStrtDate());
            List<HeadResponse> headResponses = headQueryNative.getResultList();
            if (!headResponses.isEmpty()) {
                HeadResponse headResponseResult = headResponses.get(0);
                if (headResponseResult.getCarCtrlNumId() != null) {
                    headResponse.setHeadResponse(headResponseResult);
                }
            }
            if (headResponse.getTrailer1().getVehicleAvbResourceId() != null) {
                headResponse.getTrailer1().setCutOffInfos(cutOffInfoRepository.findByVehicleAvbResourceId(
                    Long.parseLong(headResponse.getTrailer1().getVehicleAvbResourceId())));
            }
            if (headResponse.getTrailer2().getVehicleAvbResourceId() != null) {
                headResponse.getTrailer2().setCutOffInfos(cutOffInfoRepository.findByVehicleAvbResourceId(
                    Long.parseLong(headResponse.getTrailer2().getVehicleAvbResourceId())));
            }
            if (headResponse.getTrailer1().getOrderId() != null) {
                headResponse.setPartner1(getPartnerTrailer(Long.parseLong(headResponse.getTrailer1().getOrderId())));
            }
            if (headResponse.getTrailer2().getOrderId() != null) {
                headResponse.setPartner2(getPartnerTrailer(Long.parseLong(headResponse.getTrailer2().getOrderId())));
            }

            if (headResponse.getTrailer1().getOrderId() != null) {
                TransOrder transOrder = transOrderRepository.findFirstByParentOrderId(
                    Long.parseLong(headResponse.getTrailer1().getOrderId()));
                if (transOrder != null) {
                    headResponse.getTrailer1().setOrderId(transOrder.getId().toString());
                    headResponse.getTrailer1().setOrderStatus(transOrder.getStatus().toString());
                    headResponse.getTrailer1().setTransType(Long.valueOf(transOrder.getTransType()));
                }
            }

            if (headResponse.getTrailer2().getOrderId() != null) {
                TransOrder transOrder = transOrderRepository.findFirstByParentOrderId(
                    Long.parseLong(headResponse.getTrailer2().getOrderId()));
                if (transOrder != null) {
                    headResponse.getTrailer2().setOrderId(transOrder.getId().toString());
                    headResponse.getTrailer2().setOrderStatus(transOrder.getStatus().toString());
                    headResponse.getTrailer2().setTransType(Long.valueOf(transOrder.getTransType()));
                }
            }
        }
        return new PageImpl<>(result, PageRequest.of(page, limit), total);
    }

    /**
     * 運送計画を運送能力に基づいて取得する。
     *
     * @param vehicleAvbResourceItem 運送計画のトラックID
     * @param transType              運送計画の種類
     * @param statuses               運送計画のステータス
     * @param cnsLineItemByDateIds   運送計画のID
     * @return 運送計画リスト
     */
    public List<CnsLineItemByDate> shippers(VehicleAvbResourceItem vehicleAvbResourceItem, Integer transType,
        List<Integer> statuses, List<Long> cnsLineItemByDateIds) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CnsLineItemByDate> query = cb.createQuery(CnsLineItemByDate.class);
        Root<CnsLineItemByDate> root = query.from(CnsLineItemByDate.class);
        List<Predicate> predicates = new ArrayList<>();
        if (vehicleAvbResourceItem.getDepartureFrom() != null) {
            predicates.add(cb.equal(root.get("departureFrom"), vehicleAvbResourceItem.getDepartureFrom()));
        }
        if (vehicleAvbResourceItem.getArrivalTo() != null) {
            predicates.add(cb.equal(root.get("arrivalTo"), vehicleAvbResourceItem.getArrivalTo()));
        }
        if (vehicleAvbResourceItem.getDepartureTimeMin() != null
            && vehicleAvbResourceItem.getDepartureTimeMax() != null) {
            predicates.add(
                cb.lessThanOrEqualTo(root.get("collectionTimeFrom"), vehicleAvbResourceItem.getDepartureTimeMax()));
            predicates.add(
                cb.greaterThanOrEqualTo(root.get("collectionTimeTo"), vehicleAvbResourceItem.getDepartureTimeMin()));
        }
        if (vehicleAvbResourceItem.getDay() != null) {
            predicates.add(cb.equal(root.get("collectionDate"), vehicleAvbResourceItem.getDay()));
        }
        if (transType != null) {
            predicates.add(cb.equal(root.get("transType"), transType));
        }
        if (statuses != null && !statuses.isEmpty()) {
            CriteriaBuilder.In<Integer> inClause = cb.in(root.get("status"));
            for (Integer status : statuses) {
                inClause.value(status);
            }
            predicates.add(inClause);
        }
        if (vehicleAvbResourceItem.getTotalLength() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("totalLength"), vehicleAvbResourceItem.getTotalLength()));
        }
        if (vehicleAvbResourceItem.getTotalWidth() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("totalWidth"), vehicleAvbResourceItem.getTotalWidth()));
        }
        if (vehicleAvbResourceItem.getTotalHeight() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("totalHeight"), vehicleAvbResourceItem.getTotalHeight()));
        }
        if (vehicleAvbResourceItem.getMaxPayload() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("weight"), vehicleAvbResourceItem.getMaxPayload()));
        }
        if (cnsLineItemByDateIds != null && !cnsLineItemByDateIds.isEmpty()) {
            predicates.add(cb.not(root.get("id").in(cnsLineItemByDateIds)));
        }
        predicates.add(cb.equal(root.get("pricePerUnit"), vehicleAvbResourceItem.getPrice()));
        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.desc(root.get("pricePerUnit")));
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * 運送計画を運送能力に基づいて取得する。
     *
     * @param cnsLineItemByDate 運送計画明細
     * @param transType         運送計画の種類
     * @param statuses          運送計画のステータス
     * @return 運送計画リスト
     */
    @Override
    public List<VehicleAvbResourceItem> carriers(CnsLineItemByDate cnsLineItemByDate, Integer transType,
        List<Integer> statuses) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleAvbResourceItem> query = cb.createQuery(VehicleAvbResourceItem.class);
        Root<VehicleAvbResourceItem> root = query.from(VehicleAvbResourceItem.class);
        List<Predicate> predicates = new ArrayList<>();
        if (cnsLineItemByDate.getDepartureFrom() != null) {
            predicates.add(cb.equal(root.get("departureFrom"), cnsLineItemByDate.getDepartureFrom()));
        }
        if (cnsLineItemByDate.getArrivalTo() != null) {
            predicates.add(cb.equal(root.get("arrivalTo"), cnsLineItemByDate.getArrivalTo()));
        }
        if (cnsLineItemByDate.getCollectionTimeFrom() != null && cnsLineItemByDate.getCollectionTimeTo() != null) {
            predicates.add(
                cb.greaterThanOrEqualTo(root.get("departureTimeMax"), cnsLineItemByDate.getCollectionTimeFrom()));
            predicates.add(cb.lessThanOrEqualTo(root.get("departureTimeMin"), cnsLineItemByDate.getCollectionTimeTo()));
        }
        if (cnsLineItemByDate.getCollectionDate() != null) {
            predicates.add(cb.equal(root.get("day"), cnsLineItemByDate.getCollectionDate()));
        }
        if (statuses != null && !statuses.isEmpty()) {
            CriteriaBuilder.In<Integer> inClause = cb.in(root.get("status"));
            for (Integer status : statuses) {
                inClause.value(status);
            }
            predicates.add(inClause);
        }
        if (cnsLineItemByDate.getTotalLength() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("totalLength"), cnsLineItemByDate.getTotalLength()));
        }
        if (cnsLineItemByDate.getTotalWidth() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("totalWidth"), cnsLineItemByDate.getTotalWidth()));
        }
        if (cnsLineItemByDate.getTotalHeight() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("totalHeight"), cnsLineItemByDate.getTotalHeight()));
        }
        if (cnsLineItemByDate.getWeight() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("maxPayload"), cnsLineItemByDate.getWeight()));
        }
        predicates.add(cb.equal(root.get("price"), cnsLineItemByDate.getPricePerUnit()));
        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.asc(root.get("price")));
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * 運送計画を運送能力に基づいて取得する。
     *
     * @param orderId 運送計画のID
     * @param page    ページ番号
     * @param limit   ページサイズ
     * @return 運送計画ヘッダー応答のページ
     */
    @Override
    public Page<TransMatchingHeadResponse> getTransCarrierMatchingByTrailer(Long orderId, int page, int limit) {
        String sqlQuery = "WITH result AS ( " +
            "    SELECT " +
            "        subquery.*, " +
            "        count_subquery.total_count " +
            "    FROM " +
            "        ( " +
            "            SELECT " +
            "                DISTINCT ON (t_trans_matching.vehicle_avb_resource_id) " +
            "                TO_CHAR(t_trans_matching.created_date,'YYYY-MM-DD HH24:MI:SS') AS created_at,  " +
            "ARRAY(SELECT CAST(unnest(STRING_TO_ARRAY(REPLACE(vehicle_avb_resource_item.temperature_range, ' ', ''), ',')) AS integer)) AS temperature_range, "
            +
            "                t_trans_matching.trans_type AS trans_type, " +
            "                t_trans_order.id AS order_id, " +
            "                t_trans_matching.id AS matching_id, " +
            "                t_trans_matching.shipper_operator_id AS shipper_operator_id, " +
            "                t_trans_matching.carrier_operator_id AS carrier_operator_id, " +
            "                t_trans_matching.carrier2_operator_id AS carrier2_operator_id, " +
            "                t_trans_matching.carrier2_operator_name AS carrier2_operator_name, " +
            sqlFieldParent() +
            "                t_trans_matching.trans_type AS matching_type, " +
            "                t_trans_matching.status AS matching_status, " +
            "                t_trans_order.status AS order_status, " +
            "                t_trans_matching.vehicle_avb_resource_id AS vehicle_avb_resource_id_1, " +
            "                cns_line_item_by_date.trans_order_id AS c_order_id, " +
            "                vehicle_avb_resource_item.trip_name AS trip_name, " +
            "                t_trans_order.is_emergency AS is_emergency " +
            "            FROM " +
            "                t_trans_matching " +
            "            LEFT JOIN t_trans_order " +
            "                ON t_trans_matching.id = t_trans_order.trans_matching_id " +
            "            LEFT JOIN vehicle_avb_resource " +
            "                ON t_trans_matching.vehicle_avb_resource_id = vehicle_avb_resource.id " +
            "            LEFT JOIN vehicle_avb_resource_item " +
            "                ON t_trans_matching.vehicle_avb_resource_item_id = vehicle_avb_resource_item.id " +
            "            LEFT JOIN car_info " +
            "                ON vehicle_avb_resource.car_info_id = car_info.id " +
            "            LEFT JOIN cns_line_item_by_date " +
            "                ON t_trans_matching.cns_line_item_by_date_id = cns_line_item_by_date.id " +
            "            LEFT JOIN c_vehicle_diagram_item_trailer " +
            "                ON c_vehicle_diagram_item_trailer.id = t_trans_matching.vehicle_diagram_item_trailer_id " +
            "            WHERE " +
            "                (car_info.tractor_idcr != '1' OR car_info.tractor_idcr IS NULL) " +
            "                AND t_trans_matching.status = 1 " +
            "            ORDER BY " +
            "                t_trans_matching.vehicle_avb_resource_id " +
            "        ) AS subquery " +
            "    LEFT JOIN " +
            "        ( " +
            "            SELECT " +
            "                t_trans_matching.vehicle_avb_resource_id AS vehicle_avb_resource_id, " +
            "                COUNT(*) AS total_count " +
            "            FROM " +
            "                t_trans_matching " +
            "                WHERE t_trans_matching.status = 1 " +
            "            GROUP BY " +
            "                t_trans_matching.vehicle_avb_resource_id,t_trans_matching.status " +
            "        ) AS count_subquery " +
            "    ON subquery.vehicle_avb_resource_id_1 = count_subquery.vehicle_avb_resource_id " +
            "), ordered_result AS (" +
            "SELECT DISTINCT ON (r1.service_no, r1.service_strt_date) " +
            sqlFieldResponse() +
            sqlSelfJoin();
        if (orderId != null) {
            sqlQuery += " WHERE r1.c_order_id = " + orderId + " OR r2.c_order_id  = " + orderId;
        }
        sqlQuery += ") SELECT *FROM ordered_result ORDER BY created_at_1 DESC ";
        sqlQuery += " LIMIT :limit OFFSET :offset";

        Query query = entityManager.createNativeQuery(sqlQuery, TransMatchingAbilityResponse.class);
        query.setParameter("limit", limit);
        query.setParameter("offset", limit * page);

        StringBuilder countQuery = new StringBuilder();
        countQuery.append("WITH result AS ( "
            + "    SELECT "
            + "        subquery.*, "
            + "        count_subquery.total_count "
            + "    FROM "
            + "        ( "
            + "            SELECT "
            + "                DISTINCT ON (t_trans_matching.vehicle_avb_resource_id) "
            + "                t_trans_matching.id AS matching_id, "
            + "                car_info.id AS car_info_id, "
            + "                car_info.service_no AS service_no, "
            + "                car_info.service_strt_date AS service_strt_date, "
            + "                t_trans_matching.vehicle_avb_resource_id AS vehicle_avb_resource_id_1, "
            + "                cns_line_item_by_date.trans_order_id AS c_order_id "
            + "            FROM "
            + "                t_trans_matching "
            + "            LEFT JOIN vehicle_avb_resource "
            + "                ON t_trans_matching.vehicle_avb_resource_id = vehicle_avb_resource.id "
            + "            LEFT JOIN vehicle_avb_resource_item "
            + "                ON t_trans_matching.vehicle_avb_resource_item_id = vehicle_avb_resource_item.id "
            + "            LEFT JOIN car_info "
            + "                ON vehicle_avb_resource.car_info_id = car_info.id "
            + "            LEFT JOIN cns_line_item_by_date "
            + "                ON cns_line_item_by_date.id = t_trans_matching.cns_line_item_by_date_id "
            + "            WHERE "
            + "                (car_info.tractor_idcr != '1' OR car_info.tractor_idcr IS NULL) "
            + "                AND t_trans_matching.status = 1  "
            + "            ORDER BY "
            + "                t_trans_matching.vehicle_avb_resource_id "
            + "        ) AS subquery "
            + "    LEFT JOIN "
            + "        ( "
            + "            SELECT "
            + "                t_trans_matching.vehicle_avb_resource_id AS vehicle_avb_resource_id, "
            + "                COUNT(*) AS total_count "
            + "            FROM "
            + "                t_trans_matching "
            + "            GROUP BY "
            + "                t_trans_matching.vehicle_avb_resource_id "
            + "        ) AS count_subquery "
            + "    ON subquery.vehicle_avb_resource_id_1 = count_subquery.vehicle_avb_resource_id "
            + ") "
            + "SELECT COUNT(DISTINCT r1.car_info_id) "
            + "FROM result r1 "
            + "LEFT JOIN result r2 "
            + "    ON r1.service_no = r2.service_no "
            + "    AND r1.service_strt_date = r2.service_strt_date "
            + "    AND r1.car_info_id != r2.car_info_id ");
        if (orderId != null) {
            countQuery.append(" WHERE r1.c_order_id = " + orderId + " OR r2.c_order_id  = " + orderId);
        }
        Query countNativeQuery = entityManager.createNativeQuery(countQuery.toString());
        long total = ((Number) countNativeQuery.getSingleResult()).longValue();
        List<TransMatchingAbilityResponse> transMatchingDTOList = query.getResultList();
        List<TransMatchingHeadResponse> result = transMatchingHeadResponseMapper.toMapperList(transMatchingDTOList);
        for (TransMatchingHeadResponse headResponse : result) {
            if (headResponse.getTrailer1().getMatchingStatus() != null && !headResponse.getTrailer1()
                .getMatchingStatus().equals(DataBaseConstant.TranMatchingStatus.MATCHING_OK.toString())) {
                headResponse.setTrailer1(null);
            }
            if (headResponse.getTrailer2().getMatchingStatus() != null && !headResponse.getTrailer2()
                .getMatchingStatus().equals(DataBaseConstant.TranMatchingStatus.MATCHING_OK.toString())) {
                headResponse.setTrailer2(null);
            }
        }
        return new PageImpl<>(result, PageRequest.of(page, limit), total);
    }

    /**
     * 運送計画を運送能力に基づいて取得する。
     *
     * @param transType        運送計画の種類
     * @param temperatureRange 運送計画の温度範囲
     * @param companyId        運送業者オペレーターID
     * @param page             ページ番号
     * @param limit            ページサイズ
     * @return 運送計画ヘッダー応答のページ
     */
    @Override
    public Page<TransMatchingAbilitySaleHeadResponse> getTransportAbilitySale(String transType,
        List<Integer> temperatureRange, String companyId, int page, int limit) {
        String sqlQuery =
            "WITH result AS ( " +
                "    SELECT DISTINCT ON (vehicle_avb_resource_item.vehicle_avb_resource_id) " +
                " TO_CHAR(vehicle_avb_resource_item.created_date,'YYYY-MM-DD HH24:MI:SS') AS created_at,  " +
                " TO_CHAR(vehicle_avb_resource_item.updated_date,'YYYY-MM-DD HH24:MI:SS') AS updated_at,  " +
                "        car_info.id AS car_info_id, " +
                "        car_info.service_no AS service_no, " +
                "        car_info.service_name AS service_name, " +
                "        car_info.service_strt_date AS service_strt_date, " +
                "        car_info.service_strt_time AS service_strt_time, " +
                "        car_info.service_end_time AS service_end_time, " +
                "        car_info.trailer_license_plt_num_id AS trailer_license_plt_num_id, " +
                "        vehicle_avb_resource_item.price AS price, " +
                "        vehicle_avb_resource.trsp_op_strt_area_line_one_txt AS departure_from, " +
                "        vehicle_avb_resource.trsp_op_end_area_line_one_txt AS arrival_to, " +
                "        vehicle_avb_resource_item.cut_off_time AS cut_off_time, " +
                "        vehicle_avb_resource_item.vehicle_avb_resource_id AS vehicle_avb_resource_id, " +
                "        vehicle_avb_resource_item.status AS status, " +
                "        vehicle_avb_resource_item.trip_name AS trip_name, " +
                "        vehicle_avb_resource_item.vehicle_name AS vehicle_name, " +
                "        vehicle_avb_resource_item.display_order AS display_order, " +
                "        c_vehicle_diagram_item_trailer.vehicle_diagram_item_id AS vehicle_diagram_item_id, " +
                "        vehicle_avb_resource_item.departure_time_max, " +
                "        vehicle_avb_resource_item.departure_time_min, " +
                "        vehicle_avb_resource_item.cut_off_fee, " +
                "        car_info.service_end_date, " +
                "        TO_CHAR(car_info.created_date,'YYYY-MM-DD HH24:MI:SS') AS created_date, " +
                "        ARRAY(SELECT CAST(unnest(STRING_TO_ARRAY(REPLACE(vehicle_avb_resource_item.temperature_range, ' ', ''), ',')) AS integer)) AS temperature_range "
                +
                "    FROM " +
                "        vehicle_avb_resource_item " +
                "    LEFT JOIN " +
                "        vehicle_avb_resource ON vehicle_avb_resource_item.vehicle_avb_resource_id = vehicle_avb_resource.id "
                +
                "    LEFT JOIN " +
                "        car_info ON vehicle_avb_resource.car_info_id = car_info.id " +
                "    LEFT JOIN c_vehicle_diagram_item_trailer " +
                "         ON c_vehicle_diagram_item_trailer.id = vehicle_avb_resource_item.vehicle_diagram_item_trailer_id "
                +
                "    WHERE " +
                "        (car_info.tractor_idcr != '1' OR car_info.tractor_idcr IS NULL) " +
                "        AND vehicle_avb_resource_item.operator_id = '" + companyId + "'  ";
        if (temperatureRange != null && !temperatureRange.isEmpty()) {
            sqlQuery += " AND string_to_array(replace(vehicle_avb_resource_item.temperature_range, ' ', ''), ',') && CAST(:temperatureRange AS text[]) ";
        }
        if (transType != null) {
            sqlQuery += " AND vehicle_avb_resource_item.trans_type = " + Integer.parseInt(transType);
        }
        sqlQuery += "  ORDER BY " +
            "        vehicle_avb_resource_item.vehicle_avb_resource_id " +
            "), ordered_result AS ( " +
            "SELECT DISTINCT ON (r1.service_no, r1.service_strt_date) " +
            "    r1.created_at AS created_at_1 , " +
            "    r1.created_at AS created_at_2 , " +
            "    r1.updated_at AS updated_at_1 , " +
            "    r1.updated_at AS updated_at_2 , " +
            "    r1.car_info_id AS car_info_id_1, " +
            "    r1.service_no AS service_no_1, " +
            "    r1.service_name AS service_name_1, " +
            "    r1.service_strt_date AS service_strt_date_1, " +
            "    r1.service_strt_time AS service_strt_time_1, " +
            "    r1.service_end_time AS service_end_time_1, " +
            "    r1.trailer_license_plt_num_id AS trailer_license_plt_num_id_1, " +
            "    r1.price AS price_1, " +
            "    r1.departure_from AS departure_from_1, " +
            "    r1.arrival_to AS arrival_to_1, " +
            "    r1.cut_off_time AS cut_off_time_1, " +
            "    r2.car_info_id AS car_info_id_2, " +
            "    r2.service_no AS service_no_2, " +
            "    r2.service_name AS service_name_2, " +
            "    r2.trailer_license_plt_num_id AS trailer_license_plt_num_id_2, " +
            "    r2.price AS price_2, " +
            "    r2.cut_off_time AS cut_off_time_2, " +
            "    r1.vehicle_avb_resource_id AS vehicle_avb_resource_id_1, " +
            "    r2.vehicle_avb_resource_id AS vehicle_avb_resource_id_2, " +
            "    r1.trip_name AS trip_name_1, " +
            "    r2.trip_name AS trip_name_2, " +
            "    r1.temperature_range AS temperature_range_1, " +
            "    r2.temperature_range AS temperature_range_2, " +
            "    r1.vehicle_name AS vehicle_name_1, " +
            "    r2.vehicle_name AS vehicle_name_2, " +
            "    r1.vehicle_diagram_item_id AS vehicle_diagram_item_id_1, " +
            "    r2.vehicle_diagram_item_id AS vehicle_diagram_item_id_2, " +
            "    r1.display_order AS display_order_1, " +
            "    r2.display_order AS display_order_2, " +
            "    r1.departure_time_max AS departure_time_max_1, " +
            "    r2.departure_time_max AS departure_time_max_2, " +
            "    r1.departure_time_min AS departure_time_min_1, " +
            "    r2.departure_time_min AS departure_time_min_2, " +
            "    r1.cut_off_fee AS cut_off_fee_1, " +
            "    r2.cut_off_fee AS cut_off_fee_2, " +
            "    r2.service_strt_date AS service_strt_date_2, " +
            "    r1.service_strt_time AS service_strt_time_1, " +
            "    r2.service_strt_time AS service_strt_time_2, " +
            "    r1.service_end_date AS service_end_date_1, " +
            "    r2.service_end_date AS service_end_date_2, " +
            "    r1.created_date AS created_date " +
            "FROM " +
            "    result r1 " +
            "LEFT JOIN " +
            "    result r2 ON r1.service_no = r2.service_no " +
            "    AND r1.service_strt_date = r2.service_strt_date " +
            "    AND r1.car_info_id != r2.car_info_id " +
            " WHERE r1.status IN (1, 8, 9) AND (r2.status IN (1, 8, 9) OR r2.status IS NULL))" +
            " SELECT *FROM ordered_result ORDER BY COALESCE(updated_at_1, created_at_1) DESC, service_strt_date_1 DESC";

        sqlQuery += " LIMIT :limit OFFSET :offset";
        Query query = entityManager.createNativeQuery(sqlQuery, TransMatchingAbilitySaleResponse.class);
        if (temperatureRange != null && !temperatureRange.isEmpty()) {
            String tempRangeString =
                "{" + temperatureRange.stream().map(String::valueOf).collect(Collectors.joining(",")) + "}";
            query.setParameter("temperatureRange", tempRangeString);
        }
        query.setParameter("limit", limit);
        query.setParameter("offset", limit * page);

        String countQuery =
            "WITH result AS ( " +
                "    SELECT DISTINCT ON (vehicle_avb_resource_item.vehicle_avb_resource_id) " +
                "        car_info.id AS car_info_id, " +
                "        car_info.service_no AS service_no, " +
                "        car_info.service_name AS service_name, " +
                "        car_info.service_strt_date AS service_strt_date, " +
                "        car_info.service_strt_time AS service_strt_time, " +
                "        car_info.service_end_time AS service_end_time, " +
                "        vehicle_avb_resource_item.vehicle_avb_resource_id AS vehicle_avb_resource_id, " +
                "        vehicle_avb_resource_item.status AS status " +
                "    FROM " +
                "        vehicle_avb_resource_item " +
                "    LEFT JOIN " +
                "        vehicle_avb_resource ON vehicle_avb_resource_item.vehicle_avb_resource_id = vehicle_avb_resource.id "
                +
                "    LEFT JOIN " +
                "        car_info ON vehicle_avb_resource.car_info_id = car_info.id " +
                "    WHERE " +
                "        (car_info.tractor_idcr != '1' OR car_info.tractor_idcr IS NULL) " +
                "        AND vehicle_avb_resource_item.operator_id = '" + companyId + "'  ";
        if (temperatureRange != null && !temperatureRange.isEmpty()) {
            countQuery += " AND string_to_array(replace(vehicle_avb_resource_item.temperature_range, ' ', ''), ',') && CAST(:temperatureRange AS text[]) ";
        }
        if (transType != null) {
            countQuery += " AND vehicle_avb_resource_item.trans_type = " + Integer.parseInt(transType);
        }
        countQuery += "    ORDER BY " +
            "        vehicle_avb_resource_item.vehicle_avb_resource_id " +
            ") " +
            "SELECT COUNT(*) " +
            "FROM ( " +
            "    SELECT DISTINCT ON (r1.service_no, r1.service_strt_date) r1.car_info_id " +
            "    FROM result r1 " +
            "    LEFT JOIN result r2 " +
            "        ON r1.service_no = r2.service_no " +
            "        AND r1.service_strt_date = r2.service_strt_date " +
            "        AND r1.car_info_id != r2.car_info_id " +
            " WHERE r1.status IN (1, 8, 9) AND r2.status IN (1, 8, 9) " +
            ") AS distinct_car_info";

        Query countNativeQuery = entityManager.createNativeQuery(countQuery);
        if (temperatureRange != null && !temperatureRange.isEmpty()) {
            String tempRangeString =
                "{" + temperatureRange.stream().map(String::valueOf).collect(Collectors.joining(",")) + "}";
            countNativeQuery.setParameter("temperatureRange", tempRangeString);
        }
        long total = ((Number) countNativeQuery.getSingleResult()).longValue();
        List<TransMatchingAbilitySaleHeadResponse> result = transMatchingHeadResponseMapper.toSaleMapperList(
            query.getResultList());
        for (TransMatchingAbilitySaleHeadResponse headResponse : result) {
            Query headQueryNative = entityManager.createNativeQuery(sqlGetTruck(), HeadResponse.class);
            headQueryNative.setParameter("serviceNo", headResponse.getServiceNo());
            headQueryNative.setParameter("serviceStrtDate", headResponse.getServiceStrtDate());
            List<HeadResponse> headResponses = headQueryNative.getResultList();
            if (!headResponses.isEmpty()) {
                HeadResponse headResponseResult = headResponses.get(0);
                if (headResponseResult.getCarCtrlNumId() != null) {
                    headResponse.setHeadResponse(headResponseResult);
                }
            }
            if (headResponse.getTrailer1().getVehicleAvbResourceId() != null) {
                headResponse.getTrailer1().setCutOffInfos(cutOffInfoRepository.findByVehicleAvbResourceId(
                    Long.parseLong(headResponse.getTrailer1().getVehicleAvbResourceId())));
            }
            if (headResponse.getTrailer2().getVehicleAvbResourceId() != null) {
                headResponse.getTrailer2().setCutOffInfos(cutOffInfoRepository.findByVehicleAvbResourceId(
                    Long.parseLong(headResponse.getTrailer2().getVehicleAvbResourceId())));
            }
        }
        return new PageImpl<>(result, PageRequest.of(page, limit), total);
    }


    /**
     * transport plan または transport ability のマッチングが必要なデータを取得します。 ソート、ページネーション、結果数の計算をサポートするパラメータを使用します。
     *
     * @param page            結果の開始位置（ページネーション用にスキップするレコード数）
     * @param limit           結果の最大件数（1ページあたりに取得する結果数）
     * @param trailerIdColumn トレーラーIDのカラム名
     * @return ページネーションとソートを適用したマッチングデータのリスト
     */
    private Page<TransMatchingDTOResponse> getMatchingData(String companyId, String freeWord,
        List<Integer> temperatureRange, int page, int limit, String trailerIdColumn) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ( ")
            .append("    SELECT DISTINCT ON (").append(trailerIdColumn).append(") ")
            .append(selectFields())
            .append("    FROM ").append(DataBaseConstant.TransMatching.TABLE).append(" tm ")
            .append("    LEFT JOIN ").append(DataBaseConstant.TransportAbilityLineItem.TABLE).append(" ta ")
            .append("    ON tm.").append(DataBaseConstant.TransMatching.CARRIER_OPERATOR_CODE).append(" = ta.")
            .append(DataBaseConstant.TransportAbilityLineItem.TRSP_CLI_PRTY_HEAD_OFF_ID)
            .append(
                "    LEFT JOIN vehicle_avb_resource_item ON tm.vehicle_avb_resource_item_id  = vehicle_avb_resource_item.id")
            .append("    LEFT JOIN cns_line_item_by_date ON tm.cns_line_item_by_date_id  = cns_line_item_by_date.id")
            .append("    WHERE tm.").append(DataBaseConstant.TransMatching.STATUS).append(" = 1 ")
            .append("    AND tm.").append(DataBaseConstant.TransMatching.TRANS_TYPE).append(" = 0 ")
            .append("    AND tm.").append(DataBaseConstant.TransMatching.SHIPPER_OPERATOR_ID).append(" = '")
            .append(companyId).append("' ");

        if (!BaseUtil.isNull(freeWord)) {
            sql.append(" AND (tm.free_word ILIKE '%" + freeWord + "%') ");
        }
        if (temperatureRange != null && !temperatureRange.isEmpty()) {
            sql.append(
                " AND string_to_array(replace(vehicle_avb_resource_item.temperature_range, ' ', ''), ',') && CAST(:temperatureRange AS text[]) ");
        }
        sql.append(" ORDER BY tm.").append(trailerIdColumn);
        sql.append(") AS subquery ");
        sql.append("LEFT JOIN (")
            .append("    SELECT cns_line_item_by_date_id , COUNT(*) AS total ")
            .append("    FROM ").append(DataBaseConstant.TransMatching.TABLE)
            .append("    WHERE ").append(DataBaseConstant.TransMatching.STATUS).append(" = 1 ")
            .append("    AND ").append(DataBaseConstant.TransMatching.TRANS_TYPE).append(" = 0 ")
            .append("    GROUP BY cns_line_item_by_date_id")
            .append(
                ") AS count_subquery ON subquery.cns_line_item_by_date_id = count_subquery.cns_line_item_by_date_id ");
        sql.append(" ORDER BY subquery.created_date DESC LIMIT :limit OFFSET :offset");
        Query query = entityManager.createNativeQuery(sql.toString(), TransMatchingDTOResponse.class);

        if (temperatureRange != null && !temperatureRange.isEmpty()) {
            String tempRangeString =
                "{" + temperatureRange.stream().map(String::valueOf).collect(Collectors.joining(",")) + "}";
            query.setParameter("temperatureRange", tempRangeString);
        }

        query.setParameter("limit", limit);
        query.setParameter("offset", page * limit);

        List<TransMatchingDTOResponse> resultList = query.getResultList();

        StringBuilder countQuery = new StringBuilder();
        countQuery.append("SELECT COUNT(DISTINCT tm.").append(trailerIdColumn).append(") ")
            .append("FROM ").append(DataBaseConstant.TransMatching.TABLE).append(" tm ")
            .append("LEFT JOIN ").append(DataBaseConstant.TransportAbilityLineItem.TABLE).append(" ta ")
            .append("ON tm.").append(DataBaseConstant.TransMatching.CARRIER_OPERATOR_CODE).append(" = ta.")
            .append(DataBaseConstant.TransportAbilityLineItem.TRSP_CLI_PRTY_HEAD_OFF_ID).append(" ")
            .append(
                "LEFT JOIN vehicle_avb_resource_item ON tm.vehicle_avb_resource_item_id = vehicle_avb_resource_item.id ")
            .append("LEFT JOIN cns_line_item_by_date ON tm.cns_line_item_by_date_id = cns_line_item_by_date.id ")
            .append("WHERE tm.").append(DataBaseConstant.TransMatching.STATUS).append(" = 1 ")
            .append("AND tm.").append(DataBaseConstant.TransMatching.TRANS_TYPE).append(" = 0 ")
            .append("AND tm.").append(DataBaseConstant.TransMatching.SHIPPER_OPERATOR_ID).append(" = '")
            .append(companyId).append("' ");
        if (!BaseUtil.isNull(freeWord)) {
            countQuery.append(" AND (tm.free_word ILIKE '%" + freeWord + "%') ");
        }
        if (temperatureRange != null && !temperatureRange.isEmpty()) {
            countQuery.append(
                " AND string_to_array(replace(vehicle_avb_resource_item.temperature_range, ' ', ''), ',') && CAST(:temperatureRange AS text[]) ");
        }
        Query countNativeQuery = entityManager.createNativeQuery(countQuery.toString());
        if (temperatureRange != null && !temperatureRange.isEmpty()) {
            String tempRangeString =
                "{" + temperatureRange.stream().map(String::valueOf).collect(Collectors.joining(",")) + "}";
            countNativeQuery.setParameter("temperatureRange", tempRangeString);
        }
        long total = ((Number) countNativeQuery.getSingleResult()).longValue();
        return new PageImpl<>(resultList, PageRequest.of(page, limit), total);
    }


    /**
     * マッチングが必要なデータ一覧を取得する汎用メソッド transportPlanItemId または vehicleDiagramItemTrailerId によるフィルタリングをサポート
     *
     * @param matchingField マッチング対象のフィールド（"transportPlanItemId" または "vehicleDiagramItemTrailerId"）
     * @param matchingValue フィルタリングに使用する値（transportPlanItemId または vehicleDiagramItemTrailerId の値）
     * @return マッチングが必要なデータ一覧
     */
    public List<TrspPlanIdDTOResponse> getMatchingDataByField(String transType, String matchingField,
        Long matchingValue) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT ON (tm.id) ").append(selectFields())
            .append(", ")
            .append("CASE WHEN tm.")
            .append(DataBaseConstant.TransMatching.TRANS_TYPE)
            .append(" = ")
            .append(DataBaseConstant.TransMatching.TransType.CARRIER_REQUEST)
            .append(
                " THEN parent_ci.trailer_license_plt_num_id ELSE ci.trailer_license_plt_num_id END AS trailer_license_plt_num_id, ")

            .append("CASE WHEN tm.")
            .append(DataBaseConstant.TransMatching.TRANS_TYPE)
            .append(" = ")
            .append(DataBaseConstant.TransMatching.TransType.CARRIER_REQUEST)
            .append(
                " THEN TO_CHAR(parent_ci.created_date, 'YYYY-MM-DD HH24:MI:SS') ELSE TO_CHAR(ci.created_date, 'YYYY-MM-DD HH24:MI:SS') END AS created_at, ")

            .append("CASE WHEN tm.")
            .append(DataBaseConstant.TransMatching.TRANS_TYPE)
            .append(" = ")
            .append(DataBaseConstant.TransMatching.TransType.CARRIER_REQUEST)
            .append(
                " THEN TO_CHAR(parent_ci.service_strt_time, 'HH24:MI:SS') ELSE TO_CHAR(ci.service_strt_time, 'HH24:MI:SS') END AS service_strt_time, ")

            .append("CASE WHEN tm.")
            .append(DataBaseConstant.TransMatching.TRANS_TYPE)
            .append(" = ")
            .append(DataBaseConstant.TransMatching.TransType.CARRIER_REQUEST)
            .append(
                " THEN TO_CHAR(parent_ci.service_end_time, 'HH24:MI:SS') ELSE TO_CHAR(ci.service_end_time, 'HH24:MI:SS') END AS service_end_time, ")

            .append(" parent_order.propose_snapshot parent_order_propose_snapshot, ")
            .append(" parent_vi.vehicle_name ")
            .append(" FROM ").append(DataBaseConstant.TransMatching.TABLE).append(" tm ")
            .append(" LEFT JOIN ").append(DataBaseConstant.TransportAbilityLineItem.TABLE).append(" ta ")
            .append(" ON tm.").append(DataBaseConstant.TransMatching.CARRIER_OPERATOR_CODE).append(" = ta.")
            .append(DataBaseConstant.TransportAbilityLineItem.TRSP_CLI_PRTY_HEAD_OFF_ID)
            .append(" LEFT JOIN vehicle_avb_resource_item vi ON tm.vehicle_avb_resource_item_id = vi.id ")
            .append(" LEFT JOIN car_info ci ON ci.id = vi.car_info_id ")
            .append(
                " LEFT JOIN t_trans_order parent_order ON (tm.request_snapshot ->> 'trans_order_id')\\:\\:int = parent_order.id ")
            .append(
                " LEFT JOIN vehicle_avb_resource_item parent_vi ON parent_order.vehicle_avb_resource_item_id = parent_vi.id ")
            .append(" LEFT JOIN car_info parent_ci ON parent_vi.car_info_id = parent_ci.id ")
            .append(" WHERE tm.").append(DataBaseConstant.TransMatching.STATUS).append(" = 1 ");

        sql.append(" AND tm.").append(matchingField).append(" = :id ");
        if (transType != null) {
            sql.append(" AND tm.").append(DataBaseConstant.TransMatching.TRANS_TYPE).append(" = :transType ");
        }
        Query query = entityManager.createNativeQuery(sql.toString(), TransMatchingIdDTOResponse.class);
        query.setParameter("id", matchingValue);
        if (transType != null) {
            query.setParameter("transType", Long.parseLong(transType));
        }
        List<TransMatchingIdDTOResponse> resultList = query.getResultList();
        return transMatchingHeadResponseMapper.toMapperTrspPlanIdList(resultList);
    }

    /**
     * 運送計画のフィールドを選択する。
     *
     * @return 運送計画のフィールド
     */
    private String selectFields() {
        return "tm." + DataBaseConstant.TransMatching.ID + ", " +
            "tm." + DataBaseConstant.TransMatching.TRANS_TYPE + ", " +
            "tm." + DataBaseConstant.AbstractAuditingEntity.CREATED_USER + ", " +
            "TO_CHAR(tm." + DataBaseConstant.AbstractAuditingEntity.CREATED_DATE + ", 'YYYY-MM-DD HH24:MI:SS') as "
            + DataBaseConstant.AbstractAuditingEntity.CREATED_DATE + ", " +
            "tm." + DataBaseConstant.AbstractAuditingEntity.UPDATED_USER + ", " +
            "TO_CHAR(tm." + DataBaseConstant.AbstractAuditingEntity.UPDATED_DATE + ", 'YYYY-MM-DD HH24:MI:SS'), " +
            "tm." + DataBaseConstant.TransMatching.CARRIER_OPERATOR_ID + ", " +
            "tm." + DataBaseConstant.TransMatching.SHIPPER_OPERATOR_ID + ", " +
            "tm." + DataBaseConstant.TransMatching.CARRIER_OPERATOR_CODE + ", " +
            "tm." + DataBaseConstant.TransMatching.SHIPPER_OPERATOR_CODE + ", " +
            "tm." + DataBaseConstant.TransMatching.CARRIER2_OPERATOR_ID + ", " +
            "tm." + DataBaseConstant.TransMatching.CARRIER2_OPERATOR_CODE + ", " +
            "tm." + DataBaseConstant.TransMatching.BATCH_ID + ", " +
            "tm." + DataBaseConstant.TransMatching.CNS_LINE_ITEM_BY_DATE_ID + ", " +
            "tm." + DataBaseConstant.TransMatching.VEHICLE_AVB_RESOURCE_ITEM_ID + ", " +
            "tm." + DataBaseConstant.TransMatching.TRAILER_NUMBER + ", " +
            "tm." + DataBaseConstant.TransMatching.DEPARTURE_FROM + ", " +
            "tm." + DataBaseConstant.TransMatching.ARRIVAL_TO + ", " +
            "TO_CHAR(tm." + DataBaseConstant.TransMatching.TRANSPORT_DATE + ", 'YYYY-MM-DD') as "
            + DataBaseConstant.TransMatching.TRANSPORT_DATE + ", " +
            "tm." + DataBaseConstant.TransMatching.STATUS + ", " +
            "tm." + DataBaseConstant.TransMatching.REQUEST_SNAPSHOT + ", " +
            "tm." + DataBaseConstant.TransMatching.PROPOSE_SNAPSHOT + ", " +
            "ta." + DataBaseConstant.TransportAbilityLineItem.ROAD_CARR_DEPA_SPED_ORG_NAME_TXT + " as "
            + DataBaseConstant.TransportAbilityLineItem.ROAD_CARR_DEPA_SPED_ORG_NAME_TXT + ", " +
            "ta." + DataBaseConstant.TransportAbilityLineItem.TRSP_CLI_TEL_CMM_CMP_NUM_TXT + " as "
            + DataBaseConstant.TransportAbilityLineItem.TRSP_CLI_TEL_CMM_CMP_NUM_TXT + ", " +
            "(CASE WHEN tm." + DataBaseConstant.TransMatching.TRANS_TYPE + "="
            + DataBaseConstant.TransMatching.TransType.CARRIER_REQUEST +
            " THEN (SELECT " + DataBaseConstant.TransOrder.SHIPPER_OPERATOR_NAME + " FROM "
            + DataBaseConstant.TransOrder.TABLE + " tto " +
            " WHERE tto." + DataBaseConstant.TransOrder.ID + " = " + "(tm."
            + DataBaseConstant.TransMatching.REQUEST_SNAPSHOT + " ->> 'trans_order_id')\\:\\:BIGINT) " +
            "ELSE tm." + DataBaseConstant.TransMatching.SHIPPER_OPERATOR_NAME + " END ) as "
            + DataBaseConstant.TransMatching.SHIPPER_OPERATOR_NAME + ", " +
            "tm." + DataBaseConstant.TransMatching.CARRIER_OPERATOR_NAME + " as "
            + DataBaseConstant.TransMatching.CARRIER_OPERATOR_NAME + ", " +
            "tm." + DataBaseConstant.TransMatching.CARRIER2_OPERATOR_NAME + " as "
            + DataBaseConstant.TransMatching.CARRIER2_OPERATOR_NAME + " ";

    }

    /**
     * 運送計画の親フィールドを選択する。
     *
     * @return 運送計画の親フィールド
     */
    private String sqlFieldParent() {
        return "   car_info.id AS car_info_id, " +
            "  car_info.service_no AS service_no, " +
            "  car_info.service_name AS service_name, " +
            "  car_info.service_strt_date AS service_strt_date, " +
            "  car_info.service_strt_time AS service_strt_time, " +
            "  car_info.service_end_date AS service_end_date, " +
            "  car_info.service_end_time AS service_end_time, " +
            "  car_info.trailer_license_plt_num_id AS trailer_license_plt_num_id, " +
            "  vehicle_avb_resource_item.price AS price, " +
            "  vehicle_avb_resource.trsp_op_strt_area_line_one_txt AS departure_from, " +
            "  vehicle_avb_resource.trsp_op_end_area_line_one_txt AS arrival_to, " +
            "  vehicle_avb_resource_item.cut_off_time AS cut_off_time, " +
            "  vehicle_avb_resource_item.vehicle_name AS vehicle_name, " +
            "  c_vehicle_diagram_item_trailer.vehicle_diagram_item_id AS vehicle_diagram_item_id, " +
            "  vehicle_avb_resource_item.display_order AS display_order, " +
            "  vehicle_avb_resource_item.departure_time_max, " +
            "  vehicle_avb_resource_item.departure_time_min, " +
            "  vehicle_avb_resource_item.cut_off_fee, " +
            "  TO_CHAR(car_info.created_date,'YYYY-MM-DD HH24:MI:SS') AS created_date, " +
            "  vehicle_avb_resource_item.id AS vehicle_avb_resource_item_id, " +
            "  cns_line_item_by_date.id AS cns_line_item_by_date_id, ";
    }

    /**
     * 運送計画の応答フィールドを選択する。
     *
     * @return 運送計画の応答フィールド
     */
    private String sqlFieldResponse() {
        return " r1.temperature_range AS temperature_range_1, " +
            "         CASE " +
            "          WHEN r2.temperature_range IS NULL THEN ARRAY(SELECT CAST(unnest(STRING_TO_ARRAY(REPLACE(vi.temperature_range, ' ', ''), ',')) AS integer) AS temperature_range)"
            +
            "          ELSE r2.temperature_range " +
            "        END AS temperature_range_2, " +
            " r1.vehicle_diagram_item_id AS vehicle_diagram_item_id_1, " +
            " r2.vehicle_diagram_item_id AS vehicle_diagram_item_id_2, " +
            " r1.trans_type AS trans_type_1, " +
            " COALESCE(r2.trans_type, tm.trans_type, tto.trans_type) AS trans_type_2," +
            " r1.order_id AS order_id_1, " +
            " COALESCE(r2.order_id, tto.id) AS order_id_2," +
            " r1.created_at AS created_at_1, " +
            "         CASE " +
            "          WHEN r2.created_at IS NULL THEN TO_CHAR(ci.created_date,'YYYY-MM-DD HH24:MI:SS') " +
            "          ELSE r2.created_at " +
            "        END AS created_at_2, " +
            " r1.matching_id AS matching_id_1, " +
            "        CASE " +
            "          WHEN r2.matching_id IS NULL THEN tm.id" +
            "          ELSE r2.matching_id " +
            "        END AS matching_id_2, " +
            " r1.car_info_id AS car_info_id_1, " +
            " r1.service_no AS service_no_1, " +
            " r1.service_name AS service_name_1, " +
            " r1.service_strt_date AS service_strt_date_1, " +
            " r1.service_strt_time AS service_strt_time_1, " +
            " r1.service_end_time AS service_end_time_1, " +
            " r1.trailer_license_plt_num_id AS trailer_license_plt_num_id_1, " +
            " r1.price AS price_1, " +
            " r1.departure_from AS departure_from_1, " +
            " r1.arrival_to AS arrival_to_1, " +
            " r1.cut_off_time AS cut_off_time_1, " +
            " r1.matching_type AS matching_type_1, " +
            " r1.matching_status AS matching_status_1, " +
            " r1.order_status AS order_status_1, " +
            " r1.total_count AS total_count_1, " +
            " r2.car_info_id AS car_info_id_2, " +
            " r2.service_no AS service_no_2, " +
            " r2.service_name AS service_name_2, " +
            "         CASE " +
            "          WHEN r2.trailer_license_plt_num_id IS NULL THEN ci.trailer_license_plt_num_id" +
            "          ELSE r2.trailer_license_plt_num_id " +
            "        END AS trailer_license_plt_num_id_2," +
            "        CASE " +
            "          WHEN r2.price IS NULL THEN vi.price" +
            "          ELSE r2.price " +
            "        END AS price_2," +
            "        CASE " +
            "          WHEN r2.cut_off_time IS NULL THEN vi.cut_off_time" +
            "          ELSE r2.cut_off_time " +
            "        END AS cut_off_time_2," +
            "        CASE " +
            "          WHEN r2.matching_type IS NULL THEN tm.matching_type" +
            "          ELSE r2.matching_type " +
            "        END AS matching_type_2, " +
            "        CASE " +
            "          WHEN r2.matching_status IS NULL THEN tm.status" +
            "          ELSE r2.matching_status " +
            "        END AS matching_status_2, " +
            "        CASE " +
            "           WHEN r2.order_status IS NULL THEN tto.status " +
            "           ELSE r2.order_status END AS order_status_2, " +
            "         CASE " +
            "            WHEN r2.total_count = 0 OR r2.total_count IS NULL " +
            "            THEN (SELECT COUNT(t_trans_matching.vehicle_avb_resource_id) " +
            "                  FROM t_trans_matching " +
            "                  WHERE t_trans_matching.status = 1 " +
            "                  AND t_trans_matching.vehicle_avb_resource_id = vi.vehicle_avb_resource_id)" +
            "            ELSE r2.total_count " +
            "        END AS total_count_2," +
            " r1.vehicle_avb_resource_id_1 AS vehicle_avb_resource_id_1, " +
            "        CASE " +
            "          WHEN r2.vehicle_avb_resource_id_1 IS NULL THEN vi.vehicle_avb_resource_id" +
            "          ELSE r2.vehicle_avb_resource_id_1 " +
            "        END AS vehicle_avb_resource_id_2, " +
            " r1.trip_name AS trip_name_1, " +
            "        CASE " +
            "          WHEN r2.trip_name IS NULL THEN vi.trip_name" +
            "          ELSE r2.trip_name " +
            "        END AS trip_name_2, " +
            " r1.vehicle_name AS vehicle_name_1, " +
            "        CASE " +
            "          WHEN r2.vehicle_name IS NULL THEN vi.vehicle_name" +
            "          ELSE r2.vehicle_name " +
            "        END AS vehicle_name_2, " +
            " r1.shipper_operator_id AS shipper_operator_id_1, " +
            " r1.carrier_operator_id AS carrier_operator_id_1, " +
            " r1.carrier2_operator_id AS carrier2_operator_id_1, " +
            "        CASE " +
            "          WHEN r2.shipper_operator_id IS NULL THEN tm.shipper_operator_id" +
            "          ELSE r2.shipper_operator_id " +
            "        END AS shipper_operator_id_2, " +
            "        CASE " +
            "          WHEN r2.carrier_operator_id IS NULL THEN tm.carrier_operator_id" +
            "          ELSE r2.carrier_operator_id " +
            "        END AS carrier_operator_id_2, " +
            "        CASE " +
            "          WHEN r2.carrier2_operator_id IS NULL THEN tm.carrier2_operator_id" +
            "          ELSE r2.carrier2_operator_id " +
            "        END AS carrier2_operator_id_2, " +
            " r1.carrier2_operator_name AS carrier2_operator_name_2, " +
            "        CASE " +
            "          WHEN r2.carrier2_operator_name IS NULL THEN tm.carrier2_operator_name" +
            "          ELSE r2.carrier2_operator_name " +
            "        END AS carrier2_operator_name_2, " +
            " r1.display_order AS display_order_1, " +
            "        CASE " +
            "          WHEN r2.display_order IS NULL THEN vi.display_order" +
            "          ELSE r2.display_order " +
            "        END AS display_order_2, " +
            " r1.departure_time_max AS departure_time_max_1, " +
            "        CASE " +
            "          WHEN r2.departure_time_max IS NULL THEN vi.departure_time_max" +
            "          ELSE r2.departure_time_max " +
            "        END AS departure_time_max_2, " +
            " r1.departure_time_min AS departure_time_min_1, " +
            "        CASE " +
            "          WHEN r2.departure_time_min IS NULL THEN vi.departure_time_min" +
            "          ELSE r2.departure_time_min " +
            "        END AS departure_time_min_2, " +
            " r1.cut_off_fee AS cut_off_fee_1, " +
            "        CASE " +
            "          WHEN r2.cut_off_fee IS NULL THEN vi.cut_off_fee" +
            "          ELSE r2.cut_off_fee " +
            "        END AS cut_off_fee_2, " +
            "        CASE " +
            "          WHEN r2.service_strt_date IS NULL THEN ci.service_strt_date" +
            "          ELSE r2.service_strt_date " +
            "        END AS service_strt_date_2, " +
            " r1.service_strt_time AS service_strt_time_1, " +
            "        CASE " +
            "          WHEN r2.service_strt_time IS NULL THEN ci.service_strt_time" +
            "          ELSE r2.service_strt_time " +
            "        END AS service_strt_time_2, " +
            " r1.service_end_date AS service_end_date_1, " +
            "        CASE " +
            "          WHEN r2.service_end_date IS NULL THEN ci.service_end_date" +
            "          ELSE r2.service_end_date " +
            "        END AS service_end_date_2, " +
            " r1.is_emergency AS is_emergency_1, " +
            "        CASE " +
            "          WHEN r2.is_emergency IS NULL THEN tm.is_emergency" +
            "          ELSE r2.is_emergency " +
            "        END AS is_emergency_2, " +
            " r1.created_date AS created_date, " +
            " r1.vehicle_avb_resource_item_id AS vehicle_avb_resource_item_id_1, " +
            "        CASE " +
            "          WHEN r2.vehicle_avb_resource_item_id IS NULL THEN vi.id" +
            "          ELSE r2.vehicle_avb_resource_item_id " +
            "        END AS vehicle_avb_resource_item_id_2, " +
            " r1.cns_line_item_by_date_id AS cns_line_item_by_date_id_1, " +
            "        CASE " +
            "          WHEN r2.cns_line_item_by_date_id IS NULL THEN tm.cns_line_item_by_date_id" +
            "          ELSE r2.cns_line_item_by_date_id " +
            "        END AS cns_line_item_by_date_id_2 ";
    }

    /**
     * 運送計画のトラックを取得する。
     *
     * @return 運送計画のトラック
     */
    String sqlGetTruck() {
        return "SELECT ci.car_license_plt_num_id AS car_license_plt_num_id, vi.vehicle_name " +
            "FROM car_info ci " +
            "LEFT JOIN c_vehicle_info vi ON ci.giai = vi.giai " +
            "WHERE ci.service_no = :serviceNo " +
            "AND ci.service_strt_date = :serviceStrtDate " +
            "AND ci.tractor_idcr = '1' ";
    }

    /**
     * 運送計画の自己結合を取得する。
     *
     * @return 運送計画の自己結合
     */
    String sqlSelfJoin() {
        return " FROM result r1" +
            "    LEFT JOIN result r2" +
            "        ON r1.service_no = r2.service_no" +
            "        AND r1.service_strt_date = r2.service_strt_date" +
            "        AND r1.car_info_id != r2.car_info_id" +
            "    LEFT JOIN car_info ci" +
            "        ON r1.service_no = ci.service_no" +
            "        AND r1.service_strt_date = ci.service_strt_date" +
            "        AND ci.tractor_idcr != '1' " +
            "        AND ci.id != r1.car_info_id " +
            "    LEFT JOIN vehicle_avb_resource_item vi ON ci.id = vi.car_info_id " +
            "    LEFT JOIN (SELECT *, ROW_NUMBER() OVER (PARTITION BY vehicle_avb_resource_item_id ORDER BY created_date DESC) AS rn FROM t_trans_matching) tm ON tm.vehicle_avb_resource_item_id = vi.id AND tm.rn = 1 "
            +
            "    LEFT JOIN t_trans_order tto ON tto.vehicle_avb_resource_id = vi.vehicle_avb_resource_id ";
    }

    /**
     * 運送計画の温度範囲を取得する。
     *
     * @param temperatureRange 運送計画の温度範囲
     * @return 運送計画の温度範囲
     */
    String convertTempeRange(List<Integer> temperatureRange) {
        return "ARRAY[" + temperatureRange.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(", ")) + "]";
    }

    /**
     * 運送計画のトラックを取得する。
     *
     * @param orderId 運送計画のID
     * @return 運送計画のトラック
     */
    public PartnerTrailerResponse getPartnerTrailer(Long orderId) {
        String sqlQuery = " SELECT " +
            "   car_info.service_no, " +
            "   car_info.trailer_license_plt_num_id," +
            "  vehicle_avb_resource_item.vehicle_avb_resource_id, " +
            "  vehicle_avb_resource_item.price, " +
            "  vehicle_avb_resource_item.trip_name," +
            "  vehicle_avb_resource_item.vehicle_name " +
            " FROM trsp_ability_line_item" +
            " LEFT JOIN car_info ON car_info.trsp_ability_line_item_id = trsp_ability_line_item.id " +
            " LEFT JOIN vehicle_avb_resource_item ON car_info.id = vehicle_avb_resource_item.car_info_id " +
            " INNER JOIN (" +
            "   SELECT" +
            "     trsp_plan_line_item.service_no," +
            "     trsp_plan_line_item.service_strt_date," +
            "     trsp_plan_line_item.trsp_cli_prty_head_off_id," +
            "     trsp_plan_line_item.trsp_cli_prty_brnc_off_id" +
            "   FROM trsp_plan_line_item " +
            "   LEFT JOIN cns_line_item ON trsp_plan_line_item.id = cns_line_item.trsp_plan_line_item_id" +
            "   LEFT JOIN cns_line_item_by_date ON cns_line_item.id = cns_line_item_by_date.cns_line_item_id" +
            "   LEFT JOIN t_trans_order ON cns_line_item_by_date.id = t_trans_order.cns_line_item_by_date_id" +
            "   WHERE t_trans_order.id = :orderId " +
            "   LIMIT 1" +
            " ) AS plan_data " +
            " ON car_info.service_no = plan_data.service_no " +
            " AND car_info.service_strt_date = plan_data.service_strt_date" +
            " AND trsp_ability_line_item.trsp_cli_prty_head_off_id = plan_data.trsp_cli_prty_head_off_id" +
            " AND trsp_ability_line_item.trsp_cli_prty_brnc_off_id = plan_data.trsp_cli_prty_brnc_off_id ";

        try {
            Query query = entityManager.createNativeQuery(sqlQuery, PartnerTrailerResponse.class);
            query.setParameter("orderId", orderId);
            return (PartnerTrailerResponse) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
