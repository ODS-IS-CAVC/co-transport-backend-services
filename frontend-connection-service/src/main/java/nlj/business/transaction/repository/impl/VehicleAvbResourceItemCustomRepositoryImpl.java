package nlj.business.transaction.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.util.BaseUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.domain.CnsLineItemByDate;
import nlj.business.transaction.domain.VehicleAvbResourceItem;
import nlj.business.transaction.dto.ProposeAbilityDTO;
import nlj.business.transaction.dto.request.TransportAbilityProposalRequest;
import nlj.business.transaction.dto.request.TransportAbilityPublicResponseDTO;
import nlj.business.transaction.dto.request.TransportAbilityPublicSearch;
import nlj.business.transaction.mapper.DateTimeMapper;
import nlj.business.transaction.repository.VehicleAvbResourceItemCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両情報アイテムのカスタムリポジトリ実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class VehicleAvbResourceItemCustomRepositoryImpl implements VehicleAvbResourceItemCustomRepository {

    private final EntityManager entityManager;
    private final DateTimeMapper dateTimeMapper;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Resource(name = "userContext")
    private UserContext userContext;

    /**
     * 運送能力のページング検索を行う。
     *
     * @param searchRequest 検索条件
     * @return 運送能力のページング検索結果
     */
    @Override
    public Page<TransportAbilityPublicResponseDTO> getPagedTransportAbility(
        TransportAbilityPublicSearch searchRequest) {
        int page = Integer.parseInt(searchRequest.getPage()) - 1;
        int limit = Integer.parseInt(searchRequest.getLimit());
        String selectFields =
            "vehicle.id, vehicle.operator_id, vehicle.operator_code, car_info_id, vehicle_avb_resource_id, departure_from, arrival_to, day, trip_name, departure_time_min, departure_time_max, "
                + "arrival_time, adjustment_price, vehicle_type, vehicle.display_order, vehicle.assign_type, vehicle.max_payload, vehicle.total_length, vehicle.total_width, vehicle.total_height, vehicle.vehicle_size, vehicle.temperature_range, vehicle.price, vehicle.status, vehicle.giai, "
                + "cut_off_info_id, cut_off_time, cut_off_fee, free_time_info_id, free_time_time, free_time_fee, vehicle.trans_type, vehicle.operator_name, "
                + "car_info.tractor_idcr, car_info.car_license_plt_num_id, car_info.trailer_license_plt_num_id, car_info.service_no, vehicle.created_date, vehicle.updated_date, vehicle.vehicle_name ";
        String countFields = " count(*) ";

        Query mainQuery = createTransportAbilityPublicQuery(searchRequest, selectFields, true);
        Query countQuery = createTransportAbilityPublicQuery(searchRequest, countFields, false);

        List<TransportAbilityPublicResponseDTO> result = objectMapper.convertValue(mainQuery.getResultList(),
            objectMapper.getTypeFactory().constructCollectionType(List.class, TransportAbilityPublicResponseDTO.class));
        long totalElements = ((Number) countQuery.getSingleResult()).longValue();
        return new PageImpl<>(result, PageRequest.of(page, limit), totalElements);
    }

    /**
     * 運送能力のクエリを作成する。
     *
     * @param searchRequest 検索条件
     * @param selectFields  選択するフィールド
     * @param isPageable    ページングするかどうか
     * @return 運送能力のクエリ
     */
    private Query createTransportAbilityPublicQuery(TransportAbilityPublicSearch searchRequest, String selectFields,
        boolean isPageable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMMSS);
        String queryString = "SELECT " + selectFields + " FROM vehicle_avb_resource_item vehicle "
            + "LEFT JOIN car_info ON vehicle.car_info_id = car_info.id "
            + "WHERE departure_from = :departureFrom AND arrival_to = :arrivalTo "
            + "AND status IN (0,1,2) ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("departureFrom", Long.parseLong(searchRequest.getDepId()));
        parameters.put("arrivalTo", Long.parseLong(searchRequest.getArrId()));

        if (!BaseUtil.isNull(searchRequest.getDepDate())) {
            queryString += "AND :depDate <= day ";
            parameters.put("depDate", dateTimeMapper.stringToLocalDate(searchRequest.getDepDate().trim()));
        }

        if (!BaseUtil.isNull(searchRequest.getArrDate())) {
            queryString += "AND day <= :arrDate ";
            parameters.put("arrDate", dateTimeMapper.stringToLocalDate(searchRequest.getArrDate().trim()));
        }

        if (!BaseUtil.isNull(searchRequest.getDepTime())) {
            queryString += "AND departure_time_min <= :depTime AND :depTime <= departure_time_max ";
            parameters.put("depTime", LocalTime.parse(convertTimeFormat(searchRequest.getDepTime()), formatter));
        }

        if (searchRequest.getDayWeek() != null && !searchRequest.getDayWeek().isEmpty()) {
            queryString += "AND EXTRACT(ISODOW FROM day) IN :dayWeek ";
            parameters.put("dayWeek", searchRequest.getDayWeek());
        }

        if (!BaseUtil.isNull(searchRequest.getFreightRateMin())) {
            queryString += "AND :freightRateMin <= price ";
            parameters.put("freightRateMin", new BigDecimal(searchRequest.getFreightRateMin()));
        }

        if (!BaseUtil.isNull(searchRequest.getFreightRateMax())) {
            queryString += "AND price <= :freightRateMax ";
            parameters.put("freightRateMax", new BigDecimal(searchRequest.getFreightRateMax()));
        }
        Query query = null;
        if (isPageable) {
            int page = Integer.parseInt(searchRequest.getPage()) - 1;
            int limit = Integer.parseInt(searchRequest.getLimit());
            queryString += "LIMIT :limit OFFSET :offset ";
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
     * 時間をフォーマットする。
     *
     * @param inputTime 入力時間
     * @return フォーマットされた時間
     */
    private String convertTimeFormat(String inputTime) {
        if (BaseUtil.isNull(inputTime)) {
            return null;
        }
        inputTime = inputTime.replace(":", "").trim();
        if (inputTime.length() == 4) {
            inputTime += "00";
        }
        return inputTime;
    }

    /**
     * 運送能力を取得する。
     *
     * @param searchRequest 検索条件
     * @param listParam     リストパラメータ
     * @return 運送能力
     */
    @Override
    public Page<TransportAbilityPublicResponseDTO> getTransportAbility(TransportAbilityPublicSearch searchRequest,
        List<Map<String, String>> listParam) {
        String selectFields = " DISTINCT ON(vehicle_item.vehicle_avb_resource_id) vehicle_item.*, "
            + "vehicle_item.vehicle_avb_resource_id AS vehicle_availability_resource_id, "
            + "car_info.tractor_idcr, car_info.car_license_plt_num_id, car_info.trailer_license_plt_num_id, car_info.service_no, car_info.service_strt_date, "
            + "tali.road_carr_depa_sped_org_name_txt, tali.trsp_cli_tel_cmm_cmp_num_txt, car_info.service_strt_time, car_info.service_end_time ";

        StringBuilder baseQuery = new StringBuilder(" FROM vehicle_avb_resource_item vehicle_item "
            + "LEFT JOIN car_info ON vehicle_item.car_info_id = car_info.id "
            + "LEFT JOIN trsp_ability_line_item tali ON tali.id = car_info.trsp_ability_line_item_id "
            + "WHERE 1=1 "
        );
        baseQuery.append(" AND vehicle_item.status IN :status ");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("status",
            List.of(
                DataBaseConstant.VehicleAvbResourceItem.Status.MARKET,
                DataBaseConstant.VehicleAvbResourceItem.Status.AUTOMATIC_MATCHING
            )
        );
        if (!BaseUtil.isNull(searchRequest.getDepId())) {
            baseQuery.append(" AND vehicle_item.departure_from = :departureFrom ");
            parameters.put("departureFrom", Long.parseLong(searchRequest.getDepId()));
        }
        if (!BaseUtil.isNull(searchRequest.getArrId())) {
            baseQuery.append(" AND vehicle_item.arrival_to = :arrivalTo ");
            parameters.put("arrivalTo", Long.parseLong(searchRequest.getArrId()));
        }

        if (!BaseUtil.isNull(searchRequest.getDepDate())) {
            baseQuery.append("AND :depDate <= vehicle_item.day ");
            parameters.put("depDate", dateTimeMapper.stringToLocalDate(searchRequest.getDepDate().trim()));
        }

        if (!BaseUtil.isNull(searchRequest.getArrDate())) {
            baseQuery.append("AND vehicle_item.day <= :arrDate ");
            parameters.put("arrDate", dateTimeMapper.stringToLocalDate(searchRequest.getArrDate().trim()));
        }

        if (searchRequest.getDayWeek() != null && !searchRequest.getDayWeek().isEmpty()) {
            baseQuery.append("AND EXTRACT(ISODOW FROM vehicle_item.day) IN :dayWeek ");
            parameters.put("dayWeek", searchRequest.getDayWeek());
        }

        if (!BaseUtil.isNull(searchRequest.getFreightRateMin())) {
            baseQuery.append("AND :freightRateMin <= vehicle_item.price ");
            parameters.put("freightRateMin", new BigDecimal(searchRequest.getFreightRateMin()));
        }

        if (!BaseUtil.isNull(searchRequest.getFreightRateMax())) {
            baseQuery.append("AND vehicle_item.price <= :freightRateMax ");
            parameters.put("freightRateMax", new BigDecimal(searchRequest.getFreightRateMax()));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMMSS);
        if (!BaseUtil.isNull(searchRequest.getDepTime())) {
            baseQuery.append(" AND vehicle_item.departure_time_max >= :depTime ");
            parameters.put("depTime", LocalTime.parse(convertTimeFormat(searchRequest.getDepTime()), formatter));
        }

        if (!BaseUtil.isNull(searchRequest.getArrTime())) {
            baseQuery.append(" AND vehicle_item.departure_time_min <= :arrTime ");
            parameters.put("arrTime", LocalTime.parse(convertTimeFormat(searchRequest.getArrTime()), formatter));
        }

        if (searchRequest.getTemperatureRange() != null
            && !searchRequest.getTemperatureRange().isEmpty()) {
            baseQuery.append(
                "AND (string_to_array(replace(vehicle_item.temperature_range, ' ', ''), ',') && CAST(:temperatureRange AS text[])) ");
            String tempRangeString = "{" + searchRequest.getTemperatureRange().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")) + "}";
            parameters.put("temperatureRange", tempRangeString);
        }

        if (!BaseUtil.isNull(searchRequest.getKeyword())) {
            baseQuery.append(" AND (vehicle_item.vehicle_name LIKE :keyword OR vehicle_item.trip_name LIKE :keyword " +
                "OR TO_CHAR(vehicle_item.created_date, 'YYYY年MM月DD日') = :keywordDate OR TO_CHAR(vehicle_item.day, 'YYYY年MM月DD日') = :keywordDate)");
            parameters.put("keyword", "%" + searchRequest.getKeyword() + "%");
            parameters.put("keywordDate", searchRequest.getKeyword());
        }

        boolean firstParam = true;
        for (int i = 0; i < listParam.size(); i++) {
            Map<String, String> param = listParam.get(i);
            String serviceNo = param.get("serviceNo");
            LocalDate serviceStrtDate = dateTimeMapper.stringToLocalDate(param.get("serviceStrtDate").trim());

            if (firstParam) {
                baseQuery.append(" AND (");
                firstParam = false;
            } else {
                baseQuery.append(" OR ");
            }

            baseQuery.append(" (car_info.service_no = :serviceNo_").append(listParam.indexOf(param))
                .append(" AND car_info.service_strt_date = :serviceStrtDate_").append(listParam.indexOf(param))
                //.append(" AND vehicle_item.price = :freightRate_").append(listParam.indexOf(param))
                .append(") ");

            parameters.put("serviceNo_" + listParam.indexOf(param), serviceNo);
            parameters.put("serviceStrtDate_" + listParam.indexOf(param), serviceStrtDate);

            if (i == listParam.size() - 1) {
                baseQuery.append(")");
            }
        }

        if (!userContext.getUser().isShipper()) {
            baseQuery.append(" AND vehicle_item.operator_id != :companyId ");
            parameters.put("companyId", userContext.getUser().getCompanyId());
        }

        String advanceOrder = "";
        Map<String, Object> advanceOrderParameters = new HashMap<>();
        if (searchRequest.getPricePerUnit() != null) {
            advanceOrder += " ABS(sub.price - :pricePerUnit) ASC, ";
            advanceOrderParameters.put("pricePerUnit", searchRequest.getPricePerUnit());
        }
        if (!BaseUtil.isNull(searchRequest.getCollectionDate())) {
            advanceOrder += " ABS(EXTRACT(EPOCH FROM (sub.day\\:\\:timestamp - :collectionDate\\:\\:timestamp))) ASC, ";
            advanceOrderParameters.put("collectionDate",
                dateTimeMapper.stringToLocalDate(searchRequest.getCollectionDate()));
        }
        String dataQueryStr = "SELECT * FROM (SELECT " + selectFields + baseQuery + ") AS sub ORDER BY " + advanceOrder
            + " sub.created_date DESC, sub.updated_date DESC LIMIT :limit OFFSET :offset";
        String countQueryStr = "SELECT COUNT(DISTINCT vehicle_item.vehicle_avb_resource_id) " + baseQuery;
        int page = Integer.parseInt(searchRequest.getPage()) - 1;
        int limit = Integer.parseInt(searchRequest.getLimit());

        Query mainQuery = entityManager.createNativeQuery(dataQueryStr, Map.class)
            .setParameter("limit", limit)
            .setParameter("offset", page * limit);
        Query countQuery = entityManager.createNativeQuery(countQueryStr);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            mainQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Object> entry : advanceOrderParameters.entrySet()) {
            mainQuery.setParameter(entry.getKey(), entry.getValue());
        }

        List<TransportAbilityPublicResponseDTO> result = objectMapper.convertValue(
            mainQuery.getResultList(),
            objectMapper.getTypeFactory().constructCollectionType(List.class,
                TransportAbilityPublicResponseDTO.class));//TransportAbilityPublicResponseDTO
        Long totalCount = (Long) countQuery.getSingleResult();
        return new PageImpl<>(result, PageRequest.of(page, limit), totalCount);
    }

    /**
     * ステータスを更新する。
     *
     * @param ids          更新するIDのリスト
     * @param updateStatus 更新するステータス
     * @return 更新された行数
     */
    @Override
    public int updateStatusById(List<Long> ids, Integer updateStatus) {
        String queryString = "UPDATE vehicle_avb_resource_item " +
            "SET status = :updateStatus " +
            "WHERE id IN (:ids)";
        Query query = entityManager.createNativeQuery(queryString)
            .setParameter("updateStatus", updateStatus)
            .setParameter("ids", ids);
        return query.executeUpdate();
    }

    /**
     * 車両情報アイテムをマッチング検索する。
     *
     * @param cnsLineItemByDate 運送計画の指示
     * @return 車両情報アイテムのリスト
     */
    @Override
    public List<VehicleAvbResourceItem> matchingCarrierTrailer(CnsLineItemByDate cnsLineItemByDate) {
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

        if (cnsLineItemByDate.getWeight() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("maxPayload"), cnsLineItemByDate.getWeight()));
        }
        predicates.add(cb.equal(root.get("price"), cnsLineItemByDate.getPricePerUnit()));
        predicates.add(cb.greaterThanOrEqualTo(
            cb.prod(root.get("totalLength"),
                cb.prod(root.get("totalWidth"), root.get("totalHeight"))),
            cnsLineItemByDate.getIstdTollVolMeas())
        );

        predicates.add(root.get("status")
            .in(List.of(
                DataBaseConstant.VehicleAvbResourceItem.Status.INITIALIZED,
                DataBaseConstant.VehicleAvbResourceItem.Status.MARKET,
                DataBaseConstant.VehicleAvbResourceItem.Status.AUTOMATIC_MATCHING)));
        predicates.add(cb.notEqual(root.get("operatorId"), cnsLineItemByDate.getOperatorId()));
        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.desc(root.get("price")));
        return entityManager.createQuery(query)
            .getResultList();
    }

    @Override
    public Page<ProposeAbilityDTO> getProposalItem(TransportAbilityProposalRequest request) {
        String proposal = "WITH propose AS ( " +
            "    SELECT DISTINCT ON (vehicle_avb_resource_item.car_info_id) " +
            "        vehicle_avb_resource_item.*, " +
            "        car_info.service_no, " +
            "        car_info.service_strt_date " +
            "    FROM vehicle_avb_resource_item " +
            "    JOIN car_info ON vehicle_avb_resource_item.car_info_id = car_info.id " +
            "    WHERE vehicle_avb_resource_item.status IN (1, 2) " +
            "        AND vehicle_avb_resource_item.operator_id = :operatorId " +
            "    ORDER BY vehicle_avb_resource_item.car_info_id, vehicle_avb_resource_item.created_date DESC " +
            ") ";

        String fromQuery = "FROM propose item " +
            "WHERE item.departure_from = :departureFrom " +
            "AND item.arrival_to = :arrivalTo ";
        String selectFields = "SELECT item.*, ( " +
            "    SELECT jsonb_agg(jsonb_build_object( " +
            "        'id', sub_car_info.id, " +
            "        'service_strt_date', sub_car_info.service_strt_date, " +
            "        'service_strt_time', sub_car_info.service_strt_time, " +
            "        'service_end_date', sub_car_info.service_end_date, " +
            "        'service_end_time', sub_car_info.service_end_time, " +
            "        'freight_rate', sub_car_info.freight_rate, " +
            "        'giai', sub_car_info.giai, " +
            "        'service_no', sub_car_info.service_no, " +
            "        'service_name', sub_car_info.service_name, " +
            "        'car_ctrl_num_id', sub_car_info.car_ctrl_num_id, " +
            "        'car_license_plt_num_id', sub_car_info.car_license_plt_num_id, " +
            "        'car_body_num_cd', sub_car_info.car_body_num_cd, " +
            "        'car_cls_of_size_cd', sub_car_info.car_cls_of_size_cd, " +
            "        'tractor_idcr', sub_car_info.tractor_idcr, " +
            "        'trailer_license_plt_num_id', sub_car_info.trailer_license_plt_num_id, " +
            "        'car_weig_meas', sub_car_info.car_weig_meas, " +
            "        'car_lngh_meas', sub_car_info.car_lngh_meas, " +
            "        'car_wid_meas', sub_car_info.car_wid_meas, " +
            "        'car_hght_meas', sub_car_info.car_hght_meas, " +
            "        'car_max_load_capacity1_meas', sub_car_info.car_max_load_capacity1_meas, " +
            "        'car_max_load_capacity2_meas', sub_car_info.car_max_load_capacity2_meas, " +
            "        'car_vol_of_hzd_item_meas', sub_car_info.car_vol_of_hzd_item_meas, " +
            "        'car_spc_grv_of_hzd_item_meas', sub_car_info.car_spc_grv_of_hzd_item_meas, " +
            "        'car_trk_bed_hght_meas', sub_car_info.car_trk_bed_hght_meas, " +
            "        'car_trk_bed_wid_meas', sub_car_info.car_trk_bed_wid_meas, " +
            "        'car_trk_bed_lngh_meas', sub_car_info.car_trk_bed_lngh_meas, " +
            "        'car_trk_bed_grnd_hght_meas', sub_car_info.car_trk_bed_grnd_hght_meas, " +
            "        'car_max_load_vol_meas', sub_car_info.car_max_load_vol_meas, " +
            "        'pcke_frm_cd', sub_car_info.pcke_frm_cd, " +
            "        'pcke_frm_name_cd', sub_car_info.pcke_frm_name_cd, " +
            "        'car_max_untl_cp_quan', sub_car_info.car_max_untl_cp_quan, " +
            "        'car_cls_of_shp_cd', sub_car_info.car_cls_of_shp_cd, " +
            "        'car_cls_of_tlg_lftr_exst_cd', sub_car_info.car_cls_of_tlg_lftr_exst_cd, " +
            "        'car_cls_of_wing_body_exst_cd', sub_car_info.car_cls_of_wing_body_exst_cd, " +
            "        'car_cls_of_rfg_exst_cd', sub_car_info.car_cls_of_rfg_exst_cd, " +
            "        'trms_of_lwr_tmp_meas', sub_car_info.trms_of_lwr_tmp_meas, " +
            "        'trms_of_upp_tmp_meas', sub_car_info.trms_of_upp_tmp_meas, " +
            "        'car_cls_of_crn_exst_cd', sub_car_info.car_cls_of_crn_exst_cd, " +
            "        'car_rmk_about_eqpm_txt', sub_car_info.car_rmk_about_eqpm_txt, " +
            "        'vehicle_name', CASE WHEN sub_car_info.tractor_idcr = '1' THEN vi.vehicle_name ELSE vari.vehicle_name END, "
            +
            "        'temperature_range', vari.temperature_range, " +
            "        'display_order', vari.display_order, " +
            "        'car_cmpn_name_of_gtp_crtf_exst_txt', sub_car_info.car_cmpn_name_of_gtp_crtf_exst_txt, " +
            "        'cut_off_fee', vari.cut_off_fee, " +
            "        'cut_off_time', vari.cut_off_time, " +
            "        'order_status', ( " +
            "            SELECT MAX(tto.status) " +
            "            FROM t_trans_order tto " +
            "            LEFT JOIN vehicle_avb_resource var ON var.id = tto.vehicle_avb_resource_id " +
            "            WHERE var.car_info_id = sub_car_info.id " +
            "            AND tto.status NOT IN (996, 997, 998, 999) " +
            "        ) " +
            "    ))" +
            " FROM car_info sub_car_info " +
            "LEFT JOIN ( " +
            "    SELECT DISTINCT ON (car_info_id) * " +
            "    FROM vehicle_avb_resource_item " +
            "    WHERE car_info_id IS NOT NULL " +
            "    ORDER BY car_info_id " +
            ") vari ON vari.car_info_id = sub_car_info.id " +
            "LEFT JOIN ( " +
            "    SELECT DISTINCT ON (giai) * " +
            "    FROM c_vehicle_info " +
            ") vi ON sub_car_info.giai = vi.giai " +
            "WHERE sub_car_info.service_no = item.service_no " +
            "AND sub_car_info.service_strt_date = item.service_strt_date " +
            ") AS car_info " +
            fromQuery +
            "ORDER BY " +
            "    ABS(item.price - :pricePerUnit) ASC, " +
            "    ABS(EXTRACT(EPOCH FROM (item.day\\:\\:timestamp - :collectionDate))) ASC, " +
            "    ABS(EXTRACT(EPOCH FROM (item.departure_time_min - :collectionTimeFrom))) ASC, " +
            "    ABS(EXTRACT(EPOCH FROM (item.arrival_time - :collectionTimeTo))) ASC, " +
            "    item.created_date DESC " +
            "LIMIT :limit OFFSET :offset";

        int page = Integer.parseInt(request.getPage()) - 1;
        int limit = Integer.parseInt(request.getLimit());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("departureFrom", Long.parseLong(request.getDepId()));
        parameters.put("arrivalTo", Long.parseLong(request.getArrId()));
        parameters.put("pricePerUnit", (request.getPricePerUnit()));
        parameters.put("collectionDate", (dateTimeMapper.stringToLocalDate(request.getCollectionDate().trim())));
        parameters.put("collectionTimeFrom", LocalTime.parse(request.getCollectTimeFrom(), formatter));
        parameters.put("collectionTimeTo", LocalTime.parse(request.getCollectTimeTo(), formatter));

        parameters.put("operatorId", userContext.getUser().getCompanyId());
        parameters.put("limit", limit);
        parameters.put("offset", page);

        String dataQueryStr = proposal + selectFields;
        Query dataQuery = entityManager.createNativeQuery(dataQueryStr, Map.class);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            dataQuery.setParameter(entry.getKey(), entry.getValue());
        }
        List<Map<String, Object>> querry = dataQuery.getResultList();
        List<ProposeAbilityDTO> result = objectMapper.convertValue(querry,
            objectMapper.getTypeFactory().constructCollectionType(List.class, ProposeAbilityDTO.class));

        String countQueryString = proposal + " SELECT COUNT(item.id) " + fromQuery;
        Query countQuery = entityManager.createNativeQuery(countQueryString);
        countQuery.setParameter("operatorId", userContext.getUser().getCompanyId());
        countQuery.setParameter("departureFrom", Long.parseLong(request.getDepId()));
        countQuery.setParameter("arrivalTo", Long.parseLong(request.getArrId()));

        Long totalCount = (Long) countQuery.getSingleResult();

        return new PageImpl<>(result, PageRequest.of(page, limit), totalCount);
    }
}
