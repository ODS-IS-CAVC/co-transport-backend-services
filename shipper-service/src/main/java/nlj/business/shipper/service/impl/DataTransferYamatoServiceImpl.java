package nlj.business.shipper.service.impl;

import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.shipper.domain.ShipperOperator;
import nlj.business.shipper.domain.TransportPlan;
import nlj.business.shipper.domain.TransportPlanItem;
import nlj.business.shipper.repository.ShipperOperatorRepository;
import nlj.business.shipper.service.DataTransferYamatoService;
import nlj.business.shipper.service.ShipperOperatorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * ヤマトデータ転送サービス実装クラス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DataTransferYamatoServiceImpl implements DataTransferYamatoService {

    @Resource(name = "userContext")
    private final UserContext userContext;

    private final ShipperOperatorRepository shipperOperatorRepository;
    private final ShipperOperatorService shipperOperatorService;
    private final EntityManager entityManager;

    /**
     * ヤマトデータ転送
     *
     * @param transportPlan      輸送計画
     * @param transportPlanItems 輸送計画アイテム
     */
    @Override
    public void transferDataToYamato(TransportPlan transportPlan, List<TransportPlanItem> transportPlanItems) {
        transferData(transportPlan, transportPlanItems);
    }

    /**
     * ヤマトデータ転送更新
     *
     * @param transportPlan      輸送計画
     * @param transportPlanItems 輸送計画アイテム
     */
    @Override
    public void transferDataToYamatoUpdate(TransportPlan transportPlan, List<TransportPlanItem> transportPlanItems) {
        transferData(transportPlan, transportPlanItems);
    }

    /**
     * ヤマトデータ転送
     *
     * @param transportPlan      輸送計画
     * @param transportPlanItems 輸送計画アイテム
     */
    private void transferData(TransportPlan transportPlan, List<TransportPlanItem> transportPlanItems) {
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            throw new NextWebException(new NextAPIError(HttpStatus.BAD_REQUEST,
                SoaResponsePool.get("soa.code.system_err_001")));
        }

        Long msgInfoId = insertMsgInfo(user);

        Long lineItemId = insertReqTrspLineItem(transportPlan, msgInfoId, user);
        insertReqCnsLineItem(transportPlan, lineItemId, user, transportPlanItems);
        insertShipFrom(transportPlan, lineItemId, user);
        insertShipTo(transportPlan, lineItemId, user);

    }

    /**
     * ヤマトデータ転送メッセージ情報を作成する
     *
     * @param user ユーザー
     * @return メッセージ情報ID
     */
    private Long insertMsgInfo(User user) {
        String sqlMsgInfo = "INSERT INTO req_trsp_plan_msg_info (operator_id, created_user, created_date) VALUES "
            + "(:operatorId, :createdUser, :createdDate) RETURNING id";
        return (Long) entityManager.createNativeQuery(sqlMsgInfo)
            .setParameter("operatorId", user.getCompanyId())
            .setParameter("createdUser", user.getUsername())
            .setParameter("createdDate", LocalDateTime.now())
            .getSingleResult();
    }

    /**
     * ヤマトデータ転送輸送計画行アイテムを作成する
     *
     * @param transportPlan 輸送計画
     * @param msgInfoId     メッセージ情報ID
     * @param user          ユーザー
     * @return 輸送計画行アイテムID
     */
    private Long insertReqTrspLineItem(TransportPlan transportPlan, Long msgInfoId, User user) {
        String operatorId = userContext.getUser().getCompanyId();
        ShipperOperator shipperOperator = shipperOperatorRepository.getShipperOperatorById(operatorId);
        if (shipperOperator == null) {
            shipperOperator = shipperOperatorService.createShipperOperator(operatorId);
        }
        String sqlLineItem = "INSERT INTO req_trsp_plan_line_item (operator_id, req_trsp_plan_msg_info_id, "
            + "trsp_instruction_id, dsed_cll_from_date, dsed_cll_to_date, dsed_cll_from_time, dsed_cll_to_time, "
            + "freight_rate, istd_totl_pcks_quan, istd_totl_weig_meas, istd_totl_vol_meas, cnsg_prty_head_off_id, "
            + "cnsg_prty_brnc_off_id, cnsg_prty_name_txt, cnsg_sct_sped_org_name_txt, cnsg_tel_cmm_cmp_num_txt, "
            + "cnsg_pstl_adrs_line_one_txt, cnsg_pstc_cd,  created_user, created_date) VALUES (:operatorId, :msgInfoId, "
            + ":instructionId, :fromDate, :toDate, :fromTime, :toTime, :freightRate, :totalPacks, :totalWeight, "
            + ":totalVolume, :headOffId, :branchOffId, :cnsgPrtyNameTxt, :cnsgSctSpedOrgNameTxt, :cnsgTelCmmCmpNumTxt, "
            + ":cnsgPstlAdrsLineOneTxt, :cnsgPstcCd, :createdUser, :createdDate) RETURNING id";
        return (Long) entityManager.createNativeQuery(sqlLineItem)
            .setParameter("operatorId", user.getCompanyId())
            .setParameter("msgInfoId", msgInfoId)
            .setParameter("instructionId", transportPlan.getId().toString())
            .setParameter("fromDate", transportPlan.getCollectionDateFrom())
            .setParameter("toDate", transportPlan.getCollectionDateTo())
            .setParameter("fromTime", transportPlan.getCollectionTimeFrom())
            .setParameter("toTime", transportPlan.getCollectionTimeTo())
            .setParameter("freightRate", transportPlan.getPricePerUnit())
            .setParameter("totalPacks", BigDecimal.valueOf(transportPlan.getTrailerNumber()))
            .setParameter("totalWeight", transportPlan.getTotalWeight())
            .setParameter("totalVolume", null)
            .setParameter("headOffId", shipperOperator.getOperatorCode())
            .setParameter("branchOffId", shipperOperator.getOperatorCode())
            .setParameter("cnsgPrtyNameTxt", shipperOperator.getOperatorName())
            .setParameter("cnsgSctSpedOrgNameTxt", shipperOperator.getManagerName())
            .setParameter("cnsgTelCmmCmpNumTxt", shipperOperator.getPhoneNumber())
            .setParameter("cnsgPstlAdrsLineOneTxt", shipperOperator.getAddress1() + " " + shipperOperator.getAddress2())
            .setParameter("cnsgPstcCd", shipperOperator.getPostalCode())
            .setParameter("createdUser", user.getUsername())
            .setParameter("createdDate", LocalDateTime.now())
            .getSingleResult();
    }

    /**
     * ヤマトデータ転送輸送計画行アイテムを作成する
     *
     * @param transportPlan      輸送計画
     * @param lineItemId         輸送計画行アイテムID
     * @param user               ユーザー
     * @param transportPlanItems 輸送計画アイテム
     */
    private void insertReqCnsLineItem(TransportPlan transportPlan, Long lineItemId,
        User user, List<TransportPlanItem> transportPlanItems) {
        String sqlReqCnsLineItem =
            "INSERT INTO req_cns_line_item (item_name_txt, pcke_frm_cd, crg_hnd_trms_spcl_isrs_txt, "
                + "operator_id, req_trsp_plan_line_item_id, created_user, created_date) VALUES (:itemName, :packageCode, "
                + ":specialInstructions, :operatorId, :lineItemId, :createdUser, :createdDate) RETURNING id";
        Long idReqCnsLineItem = (Long) entityManager.createNativeQuery(sqlReqCnsLineItem)
            .setParameter("itemName", null)
            .setParameter("packageCode", null)
            .setParameter("specialInstructions", transportPlan.getSpecialInstructions())
            .setParameter("operatorId", user.getCompanyId())
            .setParameter("lineItemId", lineItemId)
            .setParameter("createdUser", user.getUsername())
            .setParameter("createdDate", LocalDateTime.now())
            .getSingleResult();

        insertCnsLineItemByDate(transportPlan, lineItemId, idReqCnsLineItem, user, transportPlanItems);
    }

    /**
     * ヤマトデータ転送輸送計画行アイテムを作成する
     *
     * @param transportPlan 輸送計画
     * @param lineItemId    輸送計画行アイテムID
     * @param user          ユーザー
     */
    private void insertShipFrom(TransportPlan transportPlan, Long lineItemId, User user) {
        String sqlShipFrom = "INSERT INTO req_ship_from_prty (from_gln_prty_id, operator_id, "
            + "req_trsp_plan_line_item_id, created_user, created_date) VALUES (:addressLine, :operatorId, "
            + ":lineItemId, :createdUser, :createdDate) RETURNING id";
        entityManager.createNativeQuery(sqlShipFrom)
            .setParameter("addressLine", String.valueOf(transportPlan.getDepartureFrom()))
            .setParameter("operatorId", user.getCompanyId())
            .setParameter("lineItemId", lineItemId)
            .setParameter("createdUser", user.getUsername())
            .setParameter("createdDate", LocalDateTime.now())
            .getSingleResult();
    }

    /**
     * ヤマトデータ転送輸送計画行アイテムを作成する
     *
     * @param transportPlan 輸送計画
     * @param lineItemId    輸送計画行アイテムID
     * @param user          ユーザー
     */
    private void insertShipTo(TransportPlan transportPlan, Long lineItemId, User user) {
        String sqlShipTo = "INSERT INTO req_ship_to_prty (to_gln_prty_id, operator_id, "
            + "req_trsp_plan_line_item_id, created_user, created_date) VALUES (:addressLine, :operatorId, "
            + ":lineItemId, :createdUser, :createdDate) RETURNING id";
        entityManager.createNativeQuery(sqlShipTo)
            .setParameter("addressLine", String.valueOf(transportPlan.getArrivalTo()))
            .setParameter("operatorId", user.getCompanyId())
            .setParameter("lineItemId", lineItemId)
            .setParameter("createdUser", user.getUsername())
            .setParameter("createdDate", LocalDateTime.now())
            .getSingleResult();
    }

    /**
     * ヤマトデータ転送輸送計画行アイテムを作成する
     *
     * @param transportPlan      輸送計画
     * @param idReqTrspLineItem  輸送計画行アイテムID
     * @param idReqCnsLineItem   輸送計画行アイテムID
     * @param user               ユーザー
     * @param transportPlanItems 輸送計画アイテム
     */
    private void insertCnsLineItemByDate(TransportPlan transportPlan, Long idReqTrspLineItem, Long idReqCnsLineItem,
        User user, List<TransportPlanItem> transportPlanItems) {
        String operatorId = userContext.getUser().getCompanyId();
        ShipperOperator shipperOperator = shipperOperatorRepository.getShipperOperatorById(operatorId);
        if (shipperOperator == null) {
            shipperOperator = shipperOperatorService.createShipperOperator(operatorId);
        }
        ShipperOperator finalShipperOperator = shipperOperator;
        transportPlanItems.forEach(item -> {
            String sqlCnsLineItemByDate =
                "INSERT INTO cns_line_item_by_date (created_user, created_date, req_cns_line_item_id, "
                    + "arrival_to, collection_date, collection_time_from, collection_time_to, departure_from, operator_id, "
                    + "outer_package_code, price_per_unit, trailer_number, trailer_number_rest, transport_code, "
                    + "transport_name, temperature_range, trans_plan_id, operator_code, trans_type, status, total_height, "
                    + "total_length, total_width,weight, transport_plan_item_id, operator_name, transport_date, propose_price, propose_depature_time, propose_arrival_time) VALUES(:createdUser, :createdDate, :reqCnsLineItemId, :arrivalTo, "
                    + ":collectionDate, :collectionTimeFrom, :collectionTimeTo, :departureFrom, :operatorId, "
                    + ":outerPackageCode, :pricePerUnit, :trailerNumber, :trailerNumberRest, :transportCode, "
                    + ":transportName, :temperatureRange, :transPlanId, :operatorCode, :transType, :status, "
                    + ":totalHeight, :totalLength, :totalWidth, :weight, :transportPlanItemId, :operatorName, :transportDate, :proposePrice, :proposeDepatureTime, :proposeArrivalTime) RETURNING id";
            entityManager.createNativeQuery(sqlCnsLineItemByDate)
                .setParameter("createdUser", user.getUsername())
                .setParameter("createdDate", LocalDateTime.now())
                .setParameter("reqCnsLineItemId", idReqCnsLineItem)
                .setParameter("arrivalTo", transportPlan.getArrivalTo())
                .setParameter("collectionDate", item.getCollectionDate())
                .setParameter("collectionTimeFrom", transportPlan.getCollectionTimeFrom())
                .setParameter("collectionTimeTo", transportPlan.getCollectionTimeTo())
                .setParameter("departureFrom", transportPlan.getDepartureFrom())
                .setParameter("operatorId", user.getCompanyId())
                .setParameter("outerPackageCode", item.getOuterPackageCode())
                .setParameter("pricePerUnit", item.getPricePerUnit())
                .setParameter("trailerNumber", BigDecimal.ONE)
                .setParameter("trailerNumberRest", BigDecimal.ZERO)
                .setParameter("transportCode", item.getTransportCode())
                .setParameter("transportName", item.getTransportName())
                .setParameter("temperatureRange", String.join(", ", item.getTempRange()
                    .stream()
                    .map(String::valueOf)
                    .toArray(String[]::new)))
                .setParameter("transPlanId", transportPlan.getId())
                .setParameter("operatorCode", finalShipperOperator.getOperatorCode())
                .setParameter("transType", 0)
                .setParameter("status", 0)
                .setParameter("totalHeight", item.getTotalHeight() != null ? item.getTotalHeight() : BigDecimal.ZERO)
                .setParameter("totalLength", item.getTotalLength() != null ? item.getTotalLength() : BigDecimal.ZERO)
                .setParameter("totalWidth", item.getTotalWidth() != null ? item.getTotalWidth() : BigDecimal.ZERO)
                .setParameter("weight", item.getWeight() != null ? item.getWeight() : BigDecimal.ZERO)
                .setParameter("transportPlanItemId", item.getId())
                .setParameter("operatorName", finalShipperOperator.getOperatorName())
                .setParameter("transportDate", item.getCollectionDate())
                .setParameter("proposePrice", item.getPricePerUnit())
                .setParameter("proposeDepatureTime", item.getCollectionTimeFrom())
                .setParameter("proposeArrivalTime", item.getCollectionTimeTo())
                .getSingleResult();
        });
    }
}
