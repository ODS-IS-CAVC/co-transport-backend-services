package nlj.business.transaction.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.util.BaseUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nlj.business.transaction.constant.DataBaseConstant.CnsLineItemByDate;
import nlj.business.transaction.dto.CarInfoDTO;
import nlj.business.transaction.dto.CnsLineItemByDateByTrspAbility;
import nlj.business.transaction.dto.CnsLineItemByDateDTO;
import nlj.business.transaction.dto.CnsLineItemByDateResponseDTO;
import nlj.business.transaction.dto.request.CarrierListOrderSearch;
import nlj.business.transaction.dto.request.CarrierTransportPlanRequest;
import nlj.business.transaction.dto.request.TransportAbilityPublicResponseDTO;
import nlj.business.transaction.dto.request.TransportPlanPublicSearch;
import nlj.business.transaction.dto.response.ProposeTrspPlanLineItemDTO;
import nlj.business.transaction.dto.shipperTrspCapacity.ShipperInfoDTO;
import nlj.business.transaction.mapper.DateTimeMapper;
import nlj.business.transaction.repository.CnsLineItemByDateCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 運送計画明細カスタムリポジトリ実装。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class CnsLineItemByDateCustomRepositoryImpl implements CnsLineItemByDateCustomRepository {

    private final EntityManager entityManager;
    private final DateTimeMapper dateTimeMapper;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Resource(name = "userContext")
    private UserContext userContext;

    /**
     * 運送計画をページングして取得する。
     *
     * @param request 運送計画検索条件
     * @return 運送計画ページング結果
     */
    @Override
    public Page<CnsLineItemByDateResponseDTO> getPagedTransportPlan(TransportPlanPublicSearch request) {
        int page = Integer.parseInt(request.getPage()) - 1;
        int limit = Integer.parseInt(request.getLimit());
        String selectFields = " item.* ";
        String countFields = " count(DISTINCT item.id) ";

        Query mainQuery = createTransportPlanPublicQuery(request, selectFields, true);
        Query countQuery = createCountTransportPlanPublicQuery(request, countFields);
        List<Map<String, Object>> querry = mainQuery.getResultList();
        List<CnsLineItemByDateResponseDTO> result = objectMapper.convertValue(querry,
            objectMapper.getTypeFactory().constructCollectionType(List.class, CnsLineItemByDateResponseDTO.class));
        long totalElements = ((Number) countQuery.getSingleResult()).longValue();
        return new PageImpl<>(result, PageRequest.of(page, limit), totalElements);
    }

    /**
     * 運送計画をページングして取得する。
     *
     * @param params 運送計画検索条件
     * @return 運送計画ページング結果
     */
    @Override
    public Page<CnsLineItemByDateDTO> getPagedTransportPlanPublic(List<Map<String, String>> params, String pageNum,
        String pageSize, CarrierTransportPlanRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        int page = Integer.parseInt(pageNum) - 1;
        int limit = Integer.parseInt(pageSize);
        String proposeQuery = "WITH propose AS ( " +
            "  SELECT DISTINCT ON (car_info.service_no, car_info.service_strt_date) " +
            "    res.*, " +
            "    car_info.service_no, " +
            "    car_info.service_strt_date " +
            "  FROM vehicle_avb_resource_item res " +
            "  LEFT JOIN car_info ON res.car_info_id = car_info.id " +
            "    AND res.status IN (1, 2) " +
            "    AND res.operator_id = :operatorId " +
            ")";
        String selectFields = "item.*,resource.vehicle_name,car.trailer_license_plt_num_id, "
            + "trans.request_snapshot, "
            + "trans.propose_snapshot AS parent_order_propose_snapshot, "
            + "trans.created_date AS parent_order_created_date, "
            + "(SELECT jsonb_agg(jsonb_build_object("
            + "'id', res.id, "
            + "'operator_id', res.operator_id, "
            + "'operator_code', res.operator_code, "
            + "'car_info_id', res.car_info_id, "
            + "'vehicle_avb_resource_id', res.vehicle_avb_resource_id, "
            + "'departure_from', res.departure_from, "
            + "'arrival_to', res.arrival_to, "
            + "'day', res.day, "
            + "'trip_name', res.trip_name, "
            + "'departure_time_min', res.departure_time_min, "
            + "'departure_time_max', res.departure_time_max, "
            + "'arrival_time', res.arrival_time, "
            + "'adjustment_price', res.adjustment_price, "
            + "'vehicle_type', res.vehicle_type, "
            + "'display_order', res.display_order, "
            + "'assign_type', res.assign_type, "
            + "'max_payload', res.max_payload, "
            + "'total_length', res.total_length, "
            + "'total_width', res.total_width, "
            + "'total_height', res.total_height, "
            + "'vehicle_size', res.vehicle_size, "
            + "'vehicle_name', res.vehicle_name, "
            + "'temperature_range', ARRAY(SELECT CAST(unnest(STRING_TO_ARRAY(REPLACE(res.temperature_range, ' ', ''), ',')) AS integer)), "
            + "'price', res.price, "
            + "'status', res.status, "
            + "'giai', res.giai,"
            + "'cut_off_time', res.cut_off_time, "
            + "'cut_off_fee', res.cut_off_fee,"
            + "'created_date', TO_CHAR(res.created_date, 'YYYY-MM-DD HH24:MI:SS'),"
            + "'car_info', (SELECT jsonb_agg(jsonb_build_object("
            + "'id', sub_car_info.id, "
            + "'service_strt_date', sub_car_info.service_strt_date, "
            + "'service_strt_time', sub_car_info.service_strt_time, "
            + "'service_end_date', sub_car_info.service_end_date, "
            + "'service_end_time', sub_car_info.service_end_time, "
            + "'freight_rate', sub_car_info.freight_rate, "
            + "'giai', sub_car_info.giai, "
            + "'service_no', sub_car_info.service_no, "
            + "'service_name', sub_car_info.service_name, "
            + "'car_ctrl_num_id', sub_car_info.car_ctrl_num_id, "
            + "'car_license_plt_num_id', sub_car_info.car_license_plt_num_id, "
            + "'car_body_num_cd', sub_car_info.car_body_num_cd, "
            + "'car_cls_of_size_cd', sub_car_info.car_cls_of_size_cd, "
            + "'tractor_idcr', sub_car_info.tractor_idcr, "
            + "'trailer_license_plt_num_id', sub_car_info.trailer_license_plt_num_id, "
            + "'car_weig_meas', sub_car_info.car_weig_meas, "
            + "'car_lngh_meas', sub_car_info.car_lngh_meas, "
            + "'car_wid_meas', sub_car_info.car_wid_meas, "
            + "'car_hght_meas', sub_car_info.car_hght_meas, "
            + "'car_max_load_capacity1_meas', sub_car_info.car_max_load_capacity1_meas, "
            + "'car_max_load_capacity2_meas', sub_car_info.car_max_load_capacity2_meas, "
            + "'car_vol_of_hzd_item_meas', sub_car_info.car_vol_of_hzd_item_meas, "
            + "'car_spc_grv_of_hzd_item_meas', sub_car_info.car_spc_grv_of_hzd_item_meas, "
            + "'car_trk_bed_hght_meas', sub_car_info.car_trk_bed_hght_meas, "
            + "'car_trk_bed_wid_meas', sub_car_info.car_trk_bed_wid_meas, "
            + "'car_trk_bed_lngh_meas', sub_car_info.car_trk_bed_lngh_meas, "
            + "'car_trk_bed_grnd_hght_meas', sub_car_info.car_trk_bed_grnd_hght_meas, "
            + "'car_max_load_vol_meas', sub_car_info.car_max_load_vol_meas, "
            + "'pcke_frm_cd', sub_car_info.pcke_frm_cd, "
            + "'pcke_frm_name_cd', sub_car_info.pcke_frm_name_cd, "
            + "'car_max_untl_cp_quan', sub_car_info.car_max_untl_cp_quan, "
            + "'car_cls_of_shp_cd', sub_car_info.car_cls_of_shp_cd, "
            + "'car_cls_of_tlg_lftr_exst_cd', sub_car_info.car_cls_of_tlg_lftr_exst_cd, "
            + "'car_cls_of_wing_body_exst_cd', sub_car_info.car_cls_of_wing_body_exst_cd, "
            + "'car_cls_of_rfg_exst_cd', sub_car_info.car_cls_of_rfg_exst_cd, "
            + "'trms_of_lwr_tmp_meas', sub_car_info.trms_of_lwr_tmp_meas, "
            + "'trms_of_upp_tmp_meas', sub_car_info.trms_of_upp_tmp_meas, "
            + "'car_cls_of_crn_exst_cd', sub_car_info.car_cls_of_crn_exst_cd, "
            + "'car_rmk_about_eqpm_txt', sub_car_info.car_rmk_about_eqpm_txt, "
            + "'vehicle_name', CASE WHEN sub_car_info.tractor_idcr = '1' THEN vi.vehicle_name ELSE vari.vehicle_name END, "
            + "'temperature_range', vari.temperature_range, "
            + "'display_order', vari.display_order, "
            + "'car_cmpn_name_of_gtp_crtf_exst_txt', sub_car_info.car_cmpn_name_of_gtp_crtf_exst_txt, "
            + "'cut_off_fee', vari.cut_off_fee, "
            + "'cut_off_time', vari.cut_off_time, "
            + "'order_status', ("
            + "SELECT MAX(tto.status) FROM t_trans_order tto "
            + " LEFT JOIN vehicle_avb_resource var ON var.id = tto.vehicle_avb_resource_id "
            + " WHERE var.car_info_id = sub_car_info.id"
            + " AND tto.status NOT IN (996,997,998,999)"
            + ")"
            + ")) "
            + "FROM car_info sub_car_info "
            + "LEFT JOIN ( "
            + "    SELECT DISTINCT ON (car_info_id) * "
            + "    FROM vehicle_avb_resource_item "
            + "    WHERE car_info_id IS NOT NULL "
            + "    ORDER BY car_info_id "
            + ") vari ON vari.car_info_id = sub_car_info.id "
            + " LEFT JOIN (SELECT DISTINCT ON (giai) * FROM c_vehicle_info) vi ON sub_car_info.giai = vi.giai "
            + "WHERE sub_car_info.service_no = res.service_no "
            + "AND sub_car_info.service_strt_date = res.service_strt_date) "
            + ")) "
            + "FROM ("
            + " SELECT res.* FROM propose res "
            + "WHERE res.departure_from = item.departure_from "
            + "AND res.arrival_to = item.arrival_to "
            + " ORDER BY ABS(res.price - item.price_per_unit) ASC, "
            + " ABS(EXTRACT(EPOCH FROM (res.day\\:\\:timestamp - item.collection_date\\:\\:timestamp))) ASC, "
            + " ABS(EXTRACT(EPOCH FROM (res.departure_time_min - item.collection_time_from))) ASC,"
            + " ABS(EXTRACT(EPOCH FROM (res.arrival_time - item.collection_time_to))) ASC,"
            + " res.created_date DESC "
            + " LIMIT 20) res) "
            + " AS propose_trsp_ability ";

        StringBuilder baseQuery = new StringBuilder(" FROM cns_line_item_by_date item "
            + "LEFT JOIN cns_line_item ON item.cns_line_item_id = cns_line_item.id "
            + "LEFT JOIN trsp_plan_line_item ON cns_line_item.trsp_plan_line_item_id = trsp_plan_line_item.id "
            + "LEFT JOIN t_trans_order trans ON item.trans_order_id = trans.id "
            + "LEFT JOIN vehicle_avb_resource_item resource ON trans.vehicle_avb_resource_item_id = resource.id "
            + "LEFT JOIN car_info car ON resource.car_info_id = car.id "
            + "WHERE item.trans_type = 1 "
            + "AND item.status IN :status ");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("status", List.of(CnsLineItemByDate.Status.INITIALIZED, CnsLineItemByDate.Status.MARKET,
            CnsLineItemByDate.Status.AUTOMATIC_MATCHING));

        if (request.getTemperatureRange() != null && !request.getTemperatureRange().isEmpty()) {
            baseQuery.append(
                " AND (string_to_array(replace(item.temperature_range, ' ', ''), ',') && CAST(:temperatureRange AS text[]))");
            String tempRangeString = "{" + request.getTemperatureRange().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")) + "}";
            parameters.put("temperatureRange", tempRangeString);
        }
        if (request.getKeyword() != null) {
            String keyword = "%" + request.getKeyword() + "%";
            String keywordDate = request.getKeyword();
            baseQuery.append(" AND (item.transport_name LIKE :keyword " +
                " OR CAST(item.price_per_unit AS TEXT) LIKE :keyword " +
                "OR TO_CHAR(item.transport_date, 'YYYY年MM月DD日') = :keywordDate " +
                "OR TO_CHAR(item.created_date, 'YYYY年MM月DD日') = :keywordDate) ");
            parameters.put("keyword", keyword);
            parameters.put("keywordDate", keywordDate);
        }

        if (!BaseUtil.isNull(request.getDepId())) {
            baseQuery.append("AND item.departure_from = :departureFrom ");
            parameters.put("departureFrom", Long.parseLong(request.getDepId()));
        }

        if (!BaseUtil.isNull(request.getArrId())) {
            baseQuery.append("AND item.arrival_to = :arrivalTo ");
            parameters.put("arrivalTo", Long.parseLong(request.getArrId()));
        }

        if (!BaseUtil.isNull(request.getDepDate())) {
            baseQuery.append("AND :depDate <= item.collection_date ");
            parameters.put("depDate", dateTimeMapper.stringToLocalDate(request.getDepDate().trim()));
        }

        if (!BaseUtil.isNull(request.getArrDate())) {
            baseQuery.append("AND item.collection_date <= :arrDate ");
            parameters.put("arrDate", dateTimeMapper.stringToLocalDate(request.getArrDate().trim()));
        }

        if (!BaseUtil.isNull(request.getCollectTimeFrom())) {
            baseQuery.append("AND item.collection_time_from >= :collectionTimeFrom ");
            parameters.put("collectionTimeFrom", LocalTime.parse(request.getCollectTimeFrom() + ":00", formatter));
        }

        if (!BaseUtil.isNull(request.getCollectTimeTo())) {
            baseQuery.append("AND item.collection_time_to <= :collectionTimeTo ");
            parameters.put("collectionTimeTo", LocalTime.parse(request.getCollectTimeTo() + ":00", formatter));
        }

        if (request.getDayWeek() != null && !request.getDayWeek().isEmpty()) {
            baseQuery.append("AND EXTRACT(ISODOW FROM item.collection_date) IN :dayWeek ");
            parameters.put("dayWeek", request.getDayWeek());
        }

        if (!BaseUtil.isNull(request.getFreightRateMin())) {
            baseQuery.append("AND :freightRateMin <= item.price_per_unit ");
            parameters.put("freightRateMin", new BigDecimal(request.getFreightRateMin()));
        }

        if (!BaseUtil.isNull(request.getFreightRateMax())) {
            baseQuery.append("AND item.price_per_unit <= :freightRateMax ");
            parameters.put("freightRateMax", new BigDecimal(request.getFreightRateMax()));
        }

        User user = userContext.getUser();
        if (!BaseUtil.isNull(user.getCompanyId())) {
            baseQuery.append("AND item.operator_id != :operatorId ");
            parameters.put("operatorId", user.getCompanyId());
        }

        boolean firstParam = true;
        baseQuery.append("AND ( 1=1 ");

        for (Map<String, String> param : params) {
            String serviceNo = param.get("serviceNo");
            String serviceStrtDate = param.get("serviceStrtDate");

            if (serviceNo != null && serviceStrtDate != null) {
                if (firstParam) {
                    baseQuery.append("AND (trsp_plan_line_item.service_no = :serviceNo_").append(params.indexOf(param))
                        .append(" AND TO_CHAR(trsp_plan_line_item.service_strt_date, 'yyyyMMdd') = :serviceStrtDate_")
                        .append(params.indexOf(param)).append(") ");
                    firstParam = false;
                } else {
                    baseQuery.append("OR (trsp_plan_line_item.service_no = :serviceNo_").append(params.indexOf(param))
                        .append(" AND TO_CHAR(trsp_plan_line_item.service_strt_date, 'yyyyMMdd') = :serviceStrtDate_")
                        .append(params.indexOf(param)).append(") ");
                }
                // Set the parameters for this pair
                parameters.put("serviceNo_" + params.indexOf(param), serviceNo);
                parameters.put("serviceStrtDate_" + params.indexOf(param), serviceStrtDate);
            }
        }
        baseQuery.append(" )");
        // Query to fetch data
        String dataQueryStr = proposeQuery + "SELECT " + selectFields + baseQuery
            + " ORDER BY COALESCE(item.updated_date, item.created_date) DESC LIMIT :limit OFFSET :offset";
        parameters.put("limit", limit);
        parameters.put("offset", page * limit);
        Query dataQuery = entityManager.createNativeQuery(dataQueryStr, Map.class);

        // Setting parameters to the data query
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            dataQuery.setParameter(entry.getKey(), entry.getValue());
        }
        dataQuery.setParameter("operatorId", userContext.getUser().getCompanyId());

        List<Map<String, Object>> querry = dataQuery.getResultList();
        List<CnsLineItemByDateDTO> result = objectMapper.convertValue(querry,
            objectMapper.getTypeFactory().constructCollectionType(List.class, CnsLineItemByDateDTO.class));

        // Query to count total records
        String countQueryStr = "SELECT COUNT(item.id) " + baseQuery;
        Query countQuery = entityManager.createNativeQuery(countQueryStr);

        // Setting parameters to the count query
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            if (!entry.getKey().equals("limit") && !entry.getKey().equals("offset")) {
                countQuery.setParameter(entry.getKey(), entry.getValue());
            }
        }

        Long totalCount = (Long) countQuery.getSingleResult();

        // Returning as Page
        return new PageImpl<>(result, PageRequest.of(page, limit), totalCount);
    }

    /**
     * 運送計画をカウントするためのクエリを作成する。
     *
     * @param searchRequest 運送計画検索条件
     * @param selectFields  選択フィールド
     * @return 運送計画カウントクエリ
     */
    private Query createCountTransportPlanPublicQuery(TransportPlanPublicSearch searchRequest, String selectFields) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String queryString = "SELECT DISTINCT " + selectFields + " FROM cns_line_item_by_date item "
            + "LEFT JOIN req_trsp_plan_line_item pl ON item.operator_code = pl.cnsg_prty_head_off_id "
            + "WHERE item.trans_type = :transType "
            + "AND item.status IN :status ";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("transType", CnsLineItemByDate.TransType.SHIPPER_REQUEST);
        parameters.put("status", List.of(CnsLineItemByDate.Status.INITIALIZED, CnsLineItemByDate.Status.MARKET,
            CnsLineItemByDate.Status.AUTOMATIC_MATCHING));

        if (!BaseUtil.isNull(searchRequest.getKeyword())) {
            queryString += "AND (item.transport_name LIKE :keyword " +
                "OR CAST(item.price_per_unit AS TEXT) LIKE :keyword " +
                "OR TO_CHAR(item.transport_date, 'YYYY年MM月DD日') LIKE :keyword " +
                "OR TO_CHAR(item.created_date, 'YYYY年MM月DD日') LIKE :keyword) ";

            parameters.put("keyword", "%" + searchRequest.getKeyword() + "%");
        }

        if (!BaseUtil.isNull(searchRequest.getDepId())) {
            queryString += "AND item.departure_from = :departureFrom ";
            parameters.put("departureFrom", Long.parseLong(searchRequest.getDepId()));
        }

        if (!BaseUtil.isNull(searchRequest.getArrId())) {
            queryString += "AND item.arrival_to = :arrivalTo ";
            parameters.put("arrivalTo", Long.parseLong(searchRequest.getArrId()));
        }

        if (!BaseUtil.isNull(searchRequest.getDepDate())) {
            queryString += "AND :depDate <= item.collection_date ";
            parameters.put("depDate", dateTimeMapper.stringToLocalDate(searchRequest.getDepDate().trim()));
        }

        if (!BaseUtil.isNull(searchRequest.getArrDate())) {
            queryString += "AND item.collection_date <= :arrDate ";
            parameters.put("arrDate", dateTimeMapper.stringToLocalDate(searchRequest.getArrDate().trim()));
        }

        if (!BaseUtil.isNull(searchRequest.getCollectTimeFrom())) {
            queryString += "AND item.collection_time_from >= :collectionTimeFrom ";
            parameters.put("collectionTimeFrom",
                LocalTime.parse(searchRequest.getCollectTimeFrom() + ":00", formatter));
        }

        if (!BaseUtil.isNull(searchRequest.getCollectTimeTo())) {
            queryString += "AND item.collection_time_to <= :collectionTimeTo ";
            parameters.put("collectionTimeTo", LocalTime.parse(searchRequest.getCollectTimeTo() + ":00", formatter));
        }

        if (!BaseUtil.isNull(searchRequest.getTemperatureRange())) {
            queryString += "AND item.temperature_range LIKE :temperatureRange ";
            parameters.put("temperatureRange", "%" + searchRequest.getTemperatureRange() + "%");
        }

        if (searchRequest.getDayWeek() != null && !searchRequest.getDayWeek().isEmpty()) {
            queryString += "AND EXTRACT(ISODOW FROM collection_date) IN :dayWeek ";
            parameters.put("dayWeek", searchRequest.getDayWeek());
        }

        if (!BaseUtil.isNull(searchRequest.getFreightRateMin())) {
            queryString += "AND :freightRateMin <= price_per_unit ";
            parameters.put("freightRateMin", new BigDecimal(searchRequest.getFreightRateMin()));
        }

        if (!BaseUtil.isNull(searchRequest.getFreightRateMax())) {
            queryString += "AND price_per_unit <= :freightRateMax ";
            parameters.put("freightRateMax", new BigDecimal(searchRequest.getFreightRateMax()));
        }
        Query query = null;

        query = entityManager.createNativeQuery(queryString, Long.class);

        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query;
    }

    /**
     * 運送計画を取得するためのクエリを作成する。
     *
     * @param searchRequest 運送計画検索条件
     * @param selectFields  選択フィールド
     * @param isPageable    ページングフラグ
     * @return 運送計画クエリ
     */
    private Query createTransportPlanPublicQuery(TransportPlanPublicSearch searchRequest, String selectFields,
        boolean isPageable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String proposeQuery = "WITH propose AS ( " +
            "  SELECT DISTINCT ON (car_info.service_no, car_info.service_strt_date) " +
            "    res.*, " +
            "    car_info.service_no, " +
            "    car_info.service_strt_date " +
            "  FROM vehicle_avb_resource_item res " +
            "  LEFT JOIN car_info ON res.car_info_id = car_info.id " +
            "    AND res.status IN (1, 2) " +
            "    AND res.operator_id = :operatorId " +
            ")";
        String queryString = proposeQuery + "SELECT " + selectFields + ", "
            + "(SELECT c.price_per_unit from cns_line_item_by_date c WHERE c.id = t_trans_order.cns_line_item_by_date_id) AS carrier_price , "
            + "(SELECT jsonb_agg(jsonb_build_object("
            + "'id', res.id, "
            + "'operator_id', res.operator_id, "
            + "'operator_code', res.operator_code, "
            + "'car_info_id', res.car_info_id, "
            + "'vehicle_avb_resource_id', res.vehicle_avb_resource_id, "
            + "'departure_from', res.departure_from, "
            + "'arrival_to', res.arrival_to, "
            + "'day', res.day, "
            + "'trip_name', res.trip_name, "
            + "'departure_time_min', res.departure_time_min, "
            + "'departure_time_max', res.departure_time_max, "
            + "'arrival_time', res.arrival_time, "
            + "'adjustment_price', res.adjustment_price, "
            + "'vehicle_type', res.vehicle_type, "
            + "'display_order', res.display_order, "
            + "'assign_type', res.assign_type, "
            + "'max_payload', res.max_payload, "
            + "'total_length', res.total_length, "
            + "'total_width', res.total_width, "
            + "'total_height', res.total_height, "
            + "'vehicle_size', res.vehicle_size, "
            + "'vehicle_name', res.vehicle_name, "
            + "'created_date', TO_CHAR(res.created_date, 'YYYY-MM-DD HH24:MI:SS'),"
            + "'temperature_range', ARRAY(SELECT CAST(unnest(STRING_TO_ARRAY(REPLACE(res.temperature_range, ' ', ''), ',')) AS integer)), "
            + "'price', res.price, "
            + "'status', res.status, "
            + "'giai', res.giai,"
            + "'cut_off_time', res.cut_off_time, "
            + "'cut_off_fee', res.cut_off_fee,"
            + "'car_info', (SELECT jsonb_agg(DISTINCT jsonb_build_object("
            + "'id', sub_car_info.id, "
            + "'service_strt_date', sub_car_info.service_strt_date, "
            + "'service_strt_time', sub_car_info.service_strt_time, "
            + "'service_end_date', sub_car_info.service_end_date, "
            + "'service_end_time', sub_car_info.service_end_time, "
            + "'freight_rate', sub_car_info.freight_rate, "
            + "'giai', sub_car_info.giai, "
            + "'service_no', sub_car_info.service_no, "
            + "'service_name', sub_car_info.service_name, "
            + "'car_ctrl_num_id', sub_car_info.car_ctrl_num_id, "
            + "'car_license_plt_num_id', sub_car_info.car_license_plt_num_id, "
            + "'car_body_num_cd', sub_car_info.car_body_num_cd, "
            + "'car_cls_of_size_cd', sub_car_info.car_cls_of_size_cd, "
            + "'tractor_idcr', sub_car_info.tractor_idcr, "
            + "'trailer_license_plt_num_id', sub_car_info.trailer_license_plt_num_id, "
            + "'car_weig_meas', sub_car_info.car_weig_meas, "
            + "'car_lngh_meas', sub_car_info.car_lngh_meas, "
            + "'car_wid_meas', sub_car_info.car_wid_meas, "
            + "'car_hght_meas', sub_car_info.car_hght_meas, "
            + "'car_max_load_capacity1_meas', sub_car_info.car_max_load_capacity1_meas, "
            + "'car_max_load_capacity2_meas', sub_car_info.car_max_load_capacity2_meas, "
            + "'car_vol_of_hzd_item_meas', sub_car_info.car_vol_of_hzd_item_meas, "
            + "'car_spc_grv_of_hzd_item_meas', sub_car_info.car_spc_grv_of_hzd_item_meas, "
            + "'car_trk_bed_hght_meas', sub_car_info.car_trk_bed_hght_meas, "
            + "'car_trk_bed_wid_meas', sub_car_info.car_trk_bed_wid_meas, "
            + "'car_trk_bed_lngh_meas', sub_car_info.car_trk_bed_lngh_meas, "
            + "'car_trk_bed_grnd_hght_meas', sub_car_info.car_trk_bed_grnd_hght_meas, "
            + "'car_max_load_vol_meas', sub_car_info.car_max_load_vol_meas, "
            + "'pcke_frm_cd', sub_car_info.pcke_frm_cd, "
            + "'pcke_frm_name_cd', sub_car_info.pcke_frm_name_cd, "
            + "'car_max_untl_cp_quan', sub_car_info.car_max_untl_cp_quan, "
            + "'car_cls_of_shp_cd', sub_car_info.car_cls_of_shp_cd, "
            + "'car_cls_of_tlg_lftr_exst_cd', sub_car_info.car_cls_of_tlg_lftr_exst_cd, "
            + "'car_cls_of_wing_body_exst_cd', sub_car_info.car_cls_of_wing_body_exst_cd, "
            + "'car_cls_of_rfg_exst_cd', sub_car_info.car_cls_of_rfg_exst_cd, "
            + "'trms_of_lwr_tmp_meas', sub_car_info.trms_of_lwr_tmp_meas, "
            + "'trms_of_upp_tmp_meas', sub_car_info.trms_of_upp_tmp_meas, "
            + "'car_cls_of_crn_exst_cd', sub_car_info.car_cls_of_crn_exst_cd, "
            + "'car_rmk_about_eqpm_txt', sub_car_info.car_rmk_about_eqpm_txt, "
            + "'vehicle_name', CASE WHEN sub_car_info.tractor_idcr = '1' THEN vi.vehicle_name ELSE vari.vehicle_name END, "
            + "'temperature_range', vari.temperature_range, "
            + "'display_order', vari.display_order, "
            + "'car_cmpn_name_of_gtp_crtf_exst_txt', sub_car_info.car_cmpn_name_of_gtp_crtf_exst_txt, "
            + "'cut_off_fee', vari.cut_off_fee, "
            + "'cut_off_time', vari.cut_off_time, "
            + "'order_status', ("
            + "SELECT MAX(tto.status) FROM t_trans_order tto "
            + " LEFT JOIN vehicle_avb_resource var ON var.id = tto.vehicle_avb_resource_id "
            + " WHERE var.car_info_id = sub_car_info.id"
            + " AND tto.status NOT IN (996,997,998,999)"
            + ")"
            + ")) "
            + "FROM car_info sub_car_info "
            + " LEFT JOIN (SELECT DISTINCT ON (giai) * FROM c_vehicle_info) vi ON sub_car_info.giai = vi.giai "
            + "LEFT JOIN ( "
            + "    SELECT DISTINCT ON (car_info_id) * "
            + "    FROM vehicle_avb_resource_item "
            + "    WHERE car_info_id IS NOT NULL "
            + "    ORDER BY car_info_id "
            + ") vari ON vari.car_info_id = sub_car_info.id "
            + "WHERE sub_car_info.service_no = res.service_no "
            + "AND sub_car_info.service_strt_date = res.service_strt_date) "
            + ")) "
            + "FROM ("
            + " SELECT res.* FROM propose res "
            + "WHERE res.departure_from = item.departure_from "
            + "AND res.arrival_to = item.arrival_to "
            + " ORDER BY ABS(res.price - item.price_per_unit) ASC, "
            + " ABS(EXTRACT(EPOCH FROM (res.day\\:\\:timestamp - item.collection_date\\:\\:timestamp))) ASC, "
            + " ABS(EXTRACT(EPOCH FROM (res.departure_time_min - item.collection_time_from))) ASC,"
            + " ABS(EXTRACT(EPOCH FROM (res.arrival_time - item.collection_time_to))) ASC,"
            + " res.created_date DESC "
            + " LIMIT 20) res) "
            + " AS propose_trsp_ability "
            + "FROM cns_line_item_by_date item "
            + "LEFT JOIN (SELECT DISTINCT ON (cnsg_prty_head_off_id) * FROM req_trsp_plan_line_item) AS req_trsp_plan_line_item ON item.operator_code = req_trsp_plan_line_item.cnsg_prty_head_off_id "
            + "LEFT JOIN t_trans_order ON t_trans_order.id = item.trans_order_id "
            + "WHERE item.trans_type = :transType "
            + "AND item.status IN :status ";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("transType", CnsLineItemByDate.TransType.SHIPPER_REQUEST);
        parameters.put("status", List.of(CnsLineItemByDate.Status.INITIALIZED, CnsLineItemByDate.Status.MARKET,
            CnsLineItemByDate.Status.AUTOMATIC_MATCHING));
        if (!BaseUtil.isNull(searchRequest.getKeyword())) {
            queryString += "AND (item.transport_name LIKE :keyword " +
                "OR CAST(item.price_per_unit AS TEXT) LIKE :keyword " +
                "OR TO_CHAR(item.transport_date, 'YYYY年MM月DD日') LIKE :keyword " +
                "OR TO_CHAR(item.created_date, 'YYYY年MM月DD日') LIKE :keyword) ";

            parameters.put("keyword", "%" + searchRequest.getKeyword() + "%");
        }

        if (!BaseUtil.isNull(searchRequest.getDepId())) {
            queryString += "AND item.departure_from = :departureFrom ";
            parameters.put("departureFrom", Long.parseLong(searchRequest.getDepId()));
        }

        if (!BaseUtil.isNull(searchRequest.getArrId())) {
            queryString += "AND item.arrival_to = :arrivalTo ";
            parameters.put("arrivalTo", Long.parseLong(searchRequest.getArrId()));
        }

        if (!BaseUtil.isNull(searchRequest.getDepDate())) {
            queryString += "AND :depDate <= item.collection_date ";
            parameters.put("depDate", dateTimeMapper.stringToLocalDate(searchRequest.getDepDate().trim()));
        }

        if (!BaseUtil.isNull(searchRequest.getArrDate())) {
            queryString += "AND item.collection_date <= :arrDate ";
            parameters.put("arrDate", dateTimeMapper.stringToLocalDate(searchRequest.getArrDate().trim()));
        }

        if (!BaseUtil.isNull(searchRequest.getCollectTimeFrom())) {
            queryString += "AND item.collection_time_from >= :collectionTimeFrom ";
            parameters.put("collectionTimeFrom",
                LocalTime.parse(searchRequest.getCollectTimeFrom() + ":00", formatter));
        }

        if (!BaseUtil.isNull(searchRequest.getCollectTimeTo())) {
            queryString += "AND item.collection_time_to <= :collectionTimeTo ";
            parameters.put("collectionTimeTo", LocalTime.parse(searchRequest.getCollectTimeTo() + ":00", formatter));
        }

        if (!BaseUtil.isNull(searchRequest.getTemperatureRange())) {
            queryString += "AND item.temperature_range LIKE :temperatureRange ";
            parameters.put("temperatureRange", "%" + searchRequest.getTemperatureRange() + "%");
        }

        if (searchRequest.getDayWeek() != null && !searchRequest.getDayWeek().isEmpty()) {
            queryString += "AND EXTRACT(ISODOW FROM collection_date) IN :dayWeek ";
            parameters.put("dayWeek", searchRequest.getDayWeek());
        }

        if (!BaseUtil.isNull(searchRequest.getFreightRateMin())) {
            queryString += "AND :freightRateMin <= price_per_unit ";
            parameters.put("freightRateMin", new BigDecimal(searchRequest.getFreightRateMin()));
        }

        if (!BaseUtil.isNull(searchRequest.getFreightRateMax())) {
            queryString += "AND price_per_unit <= :freightRateMax ";
            parameters.put("freightRateMax", new BigDecimal(searchRequest.getFreightRateMax()));
        }
        parameters.put("operatorId", userContext.getUser().getCompanyId());
        Query query = null;
        if (isPageable) {
            int page = Integer.parseInt(searchRequest.getPage()) - 1;
            int limit = Integer.parseInt(searchRequest.getLimit());
            queryString += " ORDER BY COALESCE(item.updated_date, item.created_date) DESC LIMIT :limit OFFSET :offset ";
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
     * 運送計画をページングして取得する。
     *
     * @param searchRequest 運送計画検索条件
     * @return 運送計画ページング結果
     */
    @Override
    public Page<CnsLineItemByDateDTO> getPagedListCarrierOrderOnSale(CarrierListOrderSearch searchRequest) {
        int page = Integer.parseInt(searchRequest.getPage()) - 1;
        int limit = Integer.parseInt(searchRequest.getLimit());
        String selectFields =
            "id, trans_type, operator_id, cns_line_item_id, trans_plan_id, TO_CHAR(collection_date, 'yyyy-MM-dd'), collection_time_from, collection_time_to, outer_package_code, price_per_unit, status, total_height, "
                + "total_length, total_width, trailer_number, trailer_number_rest, weight, arrival_to, departure_from, transport_code, transport_name, special_instructions, vehicle_condition";
        String countFields = " count(*) ";
        Query mainQuery = createListCarrierOrderOnSale(searchRequest, selectFields, true);
        Query countQuery = createListCarrierOrderOnSale(searchRequest, countFields, false);
        List<CnsLineItemByDateDTO> result = mainQuery.getResultList();
        long totalElements = ((Number) countQuery.getSingleResult()).longValue();
        return new PageImpl<>(result, PageRequest.of(page, limit), totalElements);
    }

    /**
     * 運送計画を取得するためのクエリを作成する。
     *
     * @param searchRequest 運送計画検索条件
     * @param selectFields  選択フィールド
     * @param isPageable    ページングフラグ
     * @return 運送計画クエリ
     */
    private Query createListCarrierOrderOnSale(CarrierListOrderSearch searchRequest, String selectFields,
        boolean isPageable) {
        String queryString = "SELECT " + selectFields + " FROM cns_line_item_by_date "
            + "WHERE trans_type = :transType "
            + "AND status IN :status ";
        Query query = null;
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("status", List.of(CnsLineItemByDate.Status.INITIALIZED, CnsLineItemByDate.Status.MARKET,
            CnsLineItemByDate.Status.AUTOMATIC_MATCHING));
        parameters.put("transType", CnsLineItemByDate.TransType.CARRIER_REQUEST);
        if (isPageable) {
            int page = Integer.parseInt(searchRequest.getPage()) - 1;
            int limit = Integer.parseInt(searchRequest.getLimit());
            queryString += "LIMIT :limit OFFSET :offset ";
            parameters.put("limit", limit);
            parameters.put("offset", page * limit);
            query = entityManager.createNativeQuery(queryString, CnsLineItemByDateDTO.class);
        } else {
            query = entityManager.createNativeQuery(queryString, Long.class);
        }
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query;
    }

    /**
     * 運送計画のステータスを更新する。
     *
     * @param ids          運送計画IDリスト
     * @param updateStatus 更新ステータス
     * @return 更新行数
     */
    @Override
    public int updateStatusById(List<Long> ids, Integer updateStatus) {
        String queryString = "UPDATE cns_line_item_by_date " +
            "SET status = :updateStatus " +
            "WHERE id IN (:ids)";
        Query query = entityManager.createNativeQuery(queryString)
            .setParameter("updateStatus", updateStatus)
            .setParameter("ids", ids);
        return query.executeUpdate();
    }

    /**
     * 運送計画を運送能力に基づいて取得する。
     *
     * @param transportAbilityPublicResponseDTO 運送能力応答DTO
     * @return 運送計画リスト
     */
    @Override
    public List<CnsLineItemByDateByTrspAbility> findByTransportAbilityPublic(
        TransportAbilityPublicResponseDTO transportAbilityPublicResponseDTO) {
        List<Integer> listStatuses = List.of(
            CnsLineItemByDate.Status.INITIALIZED,
            CnsLineItemByDate.Status.MARKET,
            CnsLineItemByDate.Status.AUTOMATIC_MATCHING
        );
        String queryString = """
            WITH result AS (
                SELECT DISTINCT ON (trsp_plan.service_no, trsp_plan.service_strt_date)
                cld.id id,
                trsp_plan.id trsp_plan_line_item_id,
                cld.price_per_unit,
                cld.collection_date,
                cld.collection_time_from,
                cld.collection_time_to,
                trsp_plan.service_no,
                trsp_plan.service_name,
                trsp_plan.service_strt_date,
                trsp_plan.service_strt_time,
                trsp_plan.service_end_date,
                trsp_plan.service_end_time,
                cld.created_date,
                cld.departure_from,
                cld.arrival_to,
                cld.transport_name,
                (
                SELECT
                    jsonb_build_object(
                        'id', clibd.id,
                        'operator_name', clibd.operator_name ,
                        'outer_package_code', clibd.outer_package_code ,
                        'collection_date', clibd.collection_date ,
                        'collection_time_from', clibd.collection_time_from ,
                        'collection_time_to', clibd.collection_time_to,
                        'special_instructions', clibd.special_instructions,
                        'temperature_range', clibd.temperature_range )
                FROM t_trans_order tto
                LEFT JOIN cns_line_item_by_date clibd ON tto.cns_line_item_by_date_id = clibd.id
                WHERE tto.id = cld.trans_order_id ) AS shipper_info
                FROM cns_line_item_by_date cld
                LEFT JOIN cns_line_item cns ON cns.id = cld.cns_line_item_id
                JOIN trsp_plan_line_item trsp_plan ON cns.trsp_plan_line_item_id = trsp_plan.id
                WHERE cld.status IN :statuses
                AND cld.departure_from = :departureFrom
                AND cld.arrival_to = :arrivalTo
                AND cld.operator_id = :companyId
                AND cld.trans_type = :transType
                AND EXISTS (
                    SELECT 1 FROM car_info ci
                    LEFT JOIN vehicle_avb_resource_item vari ON vari.car_info_id = ci.id
                    WHERE ci.service_no = trsp_plan.service_no
                    AND ci.service_strt_date = trsp_plan.service_strt_date
                    AND vari.status IN (1,2)
                )
            )
            SELECT * FROM result
            ORDER BY
            ABS(result.price_per_unit - :price) ASC,
            ABS(EXTRACT(EPOCH FROM (result.collection_date\\:\\:timestamp - :collectionDate\\:\\:timestamp))) ASC,
            ABS(EXTRACT(EPOCH FROM (result.collection_time_from - :departureTimeMin))) ASC,
            ABS(EXTRACT(EPOCH FROM (result.collection_time_to - :arrivalTime))) ASC,
            result.created_date DESC
            LIMIT :limit
            """;

        Query query = entityManager.createNativeQuery(queryString, Map.class)
            //.setParameter("transType", CnsLineItemByDate.TransType.CARRIER_REQUEST)
            .setParameter("statuses", listStatuses)
            .setParameter("departureFrom", transportAbilityPublicResponseDTO.getDepartureFrom())
            .setParameter("arrivalTo", transportAbilityPublicResponseDTO.getArrivalTo())
            .setParameter("collectionDate", transportAbilityPublicResponseDTO.getDay())
            .setParameter("price", transportAbilityPublicResponseDTO.getPrice())
            //.setParameter("departureTimeMax", transportAbilityPublicResponseDTO.getDepartureTimeMax())
            .setParameter("departureTimeMin", transportAbilityPublicResponseDTO.getDepartureTimeMin())
            .setParameter("arrivalTime", transportAbilityPublicResponseDTO.getArrivalTime())
            .setParameter("companyId", userContext.getUser().getCompanyId())
            .setParameter("transType", CnsLineItemByDate.TransType.CARRIER_REQUEST)
            .setParameter("limit", 20);
        List<CnsLineItemByDateByTrspAbility> results = objectMapper.convertValue(query.getResultList(),
            objectMapper.getTypeFactory().constructCollectionType(List.class, CnsLineItemByDateByTrspAbility.class));
        results.forEach(item -> {
            item.setTrspPlanLineItem(findTrspPlanItemByService(item.getServiceNo(), item.getServiceStrtDate(),
                item.getTrspPlanLineItemId()));
            if (item.getShipperInfo() != null) {
                try {
                    item.setShipperInfo(objectMapper.readValue(item.getShipperInfo().toString(), ShipperInfoDTO.class));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return results;
    }

    /**
     * 運送計画を運送能力に基づいて取得する。
     *
     * @param serviceNo          サービス番号
     * @param serviceStrtDate    サービス開始日
     * @param trspPlanLineItemId 運送計画項目ID
     * @return 運送計画リスト
     */
    private List<ProposeTrspPlanLineItemDTO> findTrspPlanItemByService(String serviceNo, Date serviceStrtDate,
        Long trspPlanLineItemId) {
        String queryString = """
            select distinct on(ci.service_no, ci.service_strt_date)
            trsp_line.id,
            trsp_line.service_no,
            trsp_line.service_name,
            trsp_line.service_strt_date,
            trsp_line.service_strt_time,
            trsp_line.service_end_date,
            trsp_line.service_end_time,
            trsp_line.freight_rate,
            trsp_line.created_date,
            vari.vehicle_name,
            ci.trailer_license_plt_num_id,
            vari.display_order,
                (case
                when trsp_line.id = :trspPlanLineItemId then true
                else false
                end) as is_matched,
                (
                    (
                    SELECT JSON_AGG(jsonb_build_object(
                    'id', ci_sub.id,
                    'tractor_idcr', ci_sub.tractor_idcr,
                    'service_strt_date', ci_sub.service_strt_date,
                    'service_strt_time', ci_sub.service_strt_time,
                    'service_end_date', ci_sub.service_end_date,
                    'service_end_time', ci_sub.service_end_time,
                    'freight_rate', ci_sub.freight_rate,
                    'service_no', ci_sub.service_no,
                    'service_name', ci_sub.service_name,
                    'car_license_plt_num_id', ci_sub.car_license_plt_num_id,
                    'trailer_license_plt_num_id', ci_sub.trailer_license_plt_num_id,
                    'vehicle_name', (case when ci_sub.tractor_idcr = '1' then vi_sub.vehicle_name else vari_sub.vehicle_name end),
                    'display_order', vari_sub.display_order,
                    'cut_off_fee', vari_sub.cut_off_fee,
                    'cut_off_time', vari_sub.cut_off_time,
                    'order_status', (
                            CASE WHEN ci_sub.tractor_idcr = '1' OR (resell_cns.id IS NOT NULL) THEN null
                            ELSE COALESCE((
                                SELECT MAX(tto.status) FROM t_trans_order tto
                                LEFT JOIN vehicle_avb_resource var_sub ON var_sub.id = tto.vehicle_avb_resource_id
                                WHERE var_sub.car_info_id = ci_sub.id
                                AND tto.status NOT IN (996,997,998,999)
                            ), -1) END
                        )
                    ))
                FROM car_info ci_sub
                left join (select distinct on (giai) * from c_vehicle_info) vi_sub on vi_sub.giai = ci_sub.giai
                left join (select distinct on (car_info_id)  * from vehicle_avb_resource_item) vari_sub on vari_sub.car_info_id = ci_sub.id
                left join (select distinct on (vehicle_avb_resource_id) * from t_trans_order) trans on trans.vehicle_avb_resource_id  = vari_sub.vehicle_avb_resource_id
                left join cns_line_item_by_date resell_cns on resell_cns.trans_order_id = trans.id
                WHERE ci_sub.service_no = trsp_line.service_no
                AND ci_sub.service_name = trsp_line.service_name
                AND ci_sub.service_strt_date = trsp_line.service_strt_date
                )
            ) as car_info
            from trsp_plan_line_item trsp_line
            join car_info ci on
            ci.service_no = trsp_line.service_no
            and ci.service_name = trsp_line.service_name
            and ci.service_strt_date = trsp_line.service_strt_date
            left join (select distinct on (car_info_id) * from vehicle_avb_resource_item) vari on vari.car_info_id = ci.id
            where trsp_line.service_no  = :serviceNo and trsp_line.service_strt_date = :serviceStrtDate
            """;

        Query query = entityManager.createNativeQuery(queryString, Map.class)
            .setParameter("serviceNo", serviceNo)
            .setParameter("serviceStrtDate", serviceStrtDate)
            .setParameter("trspPlanLineItemId", trspPlanLineItemId);
        List<Map<String, Object>> result = query.getResultList();
        List<ProposeTrspPlanLineItemDTO> resultList = objectMapper.convertValue(result,
            new TypeReference<List<ProposeTrspPlanLineItemDTO>>() {
            });
        resultList.forEach(item -> {
            if (item.getCarInfo() != null) {
                try {
                    List<CarInfoDTO> carInfoDTOS = objectMapper.readValue(item.getCarInfo().toString(),
                        new TypeReference<List<CarInfoDTO>>() {
                        });
                    item.setCarInfo(carInfoDTOS);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return resultList;
    }
}
