package nlj.business.carrier.link.repository.impl;

import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.link.constant.DataBaseConstant;
import nlj.business.carrier.link.constant.MessageConstant.Validate;
import nlj.business.carrier.link.constant.ParamConstant;
import nlj.business.carrier.link.domain.CarInfo;
import nlj.business.carrier.link.domain.TrspPlanLineItem;
import nlj.business.carrier.link.domain.VehicleAvailabilityResource;
import nlj.business.carrier.link.dto.trspPlanLineItem.request.TrspPlanLineItemSearchRequest;
import nlj.business.carrier.link.repository.TrspPlanLineItemCustomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画明細項目カスタムリポジトリ実装.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
@RequiredArgsConstructor
public class TrspPlanLineItemCustomRepositoryImpl implements TrspPlanLineItemCustomRepository {

    private final EntityManager entityManager;

    @Resource(name = "userContext")
    private final UserContext userContext;

    /**
     * 輸送計画明細項目検索.<BR>
     *
     * @param request 輸送計画明細項目検索DTO
     * @return 輸送計画明細項目検索結果
     */
    @Override
    public List<TrspPlanLineItem> searchTransportPlanItem(TrspPlanLineItemSearchRequest request) {

        DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern(
            DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT);
        DateTimeFormatter inputTimeFormatter = DateTimeFormatter.ofPattern(
            DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMMSS);
        if (!BaseUtil.isNull(request.getMaxTrspOpDateTrmStrtDate()) && !BaseUtil.isNull(
            request.getMinTrspOpDateTrmStrtDate())) {
            checkDateMaxMin(request.getMaxTrspOpDateTrmStrtDate(), request.getMinTrspOpDateTrmStrtDate(),
                ParamConstant.MIN_TRSP_STRT_DATE, ParamConstant.MAX_TRSP_STRT_DATE);
        }
        if (!BaseUtil.isNull(request.getMaxTrspOpDateTrmEndDate()) && !BaseUtil.isNull(
            request.getMinTrspOpDateTrmEndDate())) {
            checkDateMaxMin(request.getMaxTrspOpDateTrmEndDate(), request.getMinTrspOpDateTrmEndDate(),
                ParamConstant.MIN_TRSP_END_DATE, ParamConstant.MAX_TRSP_END_DATE);
        }
        if (!BaseUtil.isNull(request.getMaxTrspOpPlanDateTrmStrtTime()) && !BaseUtil.isNull(
            request.getMinTrspOpPlanDateTrmStrtTime())) {
            checkTimeMaxMin(request.getMaxTrspOpPlanDateTrmStrtTime(), request.getMinTrspOpPlanDateTrmStrtTime(),
                ParamConstant.MIN_TRSP_STRT_TIME, ParamConstant.MAX_TRSP_STRT_TIME);
        }
        if (!BaseUtil.isNull(request.getMaxTrspOpPlanDateTrmEndTime()) && !BaseUtil.isNull(
            request.getMinTrspOpPlanDateTrmEndTime())) {
            checkTimeMaxMin(request.getMaxTrspOpPlanDateTrmEndTime(), request.getMinTrspOpPlanDateTrmEndTime(),
                ParamConstant.MIN_TRSP_END_TIME, ParamConstant.MAX_TRSP_END_TIME);
        }
        if ((!BaseUtil.isNull(request.getMaxTrspOpDateTrmEndDate())
            || !BaseUtil.isNull(request.getMinTrspOpDateTrmEndDate()))
            && (!BaseUtil.isNull(request.getMaxTrspOpDateTrmStrtDate())
            || !BaseUtil.isNull(request.getMinTrspOpDateTrmStrtDate()))) {
            String startDate = null;
            String endDate = null;
            if (!BaseUtil.isNull(request.getMaxTrspOpDateTrmStrtDate())) {
                startDate = request.getMaxTrspOpDateTrmStrtDate();
            }
            if (!BaseUtil.isNull(request.getMinTrspOpDateTrmStrtDate())) {
                startDate = request.getMinTrspOpDateTrmStrtDate();
            }
            if (!BaseUtil.isNull(request.getMinTrspOpDateTrmEndDate())) {
                endDate = request.getMinTrspOpDateTrmEndDate();
            }
            if (!BaseUtil.isNull(request.getMaxTrspOpDateTrmEndDate())) {
                endDate = request.getMaxTrspOpDateTrmEndDate();
            }
            checkDateMaxMin(endDate, startDate, DataBaseConstant.VehicleAvailabilityResource.TRSP_OP_DATE_TRM_STRT_DATE,
                DataBaseConstant.VehicleAvailabilityResource.TRSP_OP_DATE_TRM_END_DATE);
        }

        if ((!BaseUtil.isNull(request.getMaxTrspOpPlanDateTrmStrtTime()) || !BaseUtil.isNull(
            request.getMinTrspOpPlanDateTrmStrtTime())) && (!BaseUtil.isNull(request.getMaxTrspOpPlanDateTrmEndTime())
            || !BaseUtil.isNull(
            request.getMinTrspOpPlanDateTrmEndTime()))) {
            compareTime(request.getMaxTrspOpPlanDateTrmStrtTime(), request.getMinTrspOpPlanDateTrmStrtTime(),
                request.getMaxTrspOpPlanDateTrmEndTime(), request.getMinTrspOpPlanDateTrmEndTime());
        }
        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            return new ArrayList<>();
        }
        boolean allParamRequestSubQueryNull = true;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrspPlanLineItem> query = cb.createQuery(TrspPlanLineItem.class);
        Root<TrspPlanLineItem> root = query.from(TrspPlanLineItem.class);

        // Subquery for carInfo
        Subquery<CarInfo> carInfoSubquery = query.subquery(CarInfo.class);
        Root<CarInfo> carInfoRoot = carInfoSubquery.from(CarInfo.class);
        Join<CarInfo, VehicleAvailabilityResource> vehicleJoin = carInfoRoot.join("vehicleAvailabilityResources",
            JoinType.LEFT);

        // Predicates for subquery
        List<Predicate> subqueryPredicates = new ArrayList<>();
        if (request.getCarMaxLoadCapacity1Meas() != null) {
            allParamRequestSubQueryNull = false;
            subqueryPredicates.add(
                cb.equal(carInfoRoot.get("carMaxLoadCapacity1Meas"), request.getCarMaxLoadCapacity1Meas()));
        }
        if (!BaseUtil.isNull(request.getTrspOpStrtAreaLineOneTxt())) {
            allParamRequestSubQueryNull = false;
            subqueryPredicates.add(cb.like(cb.lower(vehicleJoin.get("trspOpStrtAreaLineOneTxt")),
                "%" + request.getTrspOpStrtAreaLineOneTxt().replaceAll(ParamConstant.TRIM_SPACE_TWO_BYTE, "")
                    .toLowerCase() + "%"));
        }
        if (!BaseUtil.isNull(request.getTrspOpEndAreaLineOneTxt())) {
            allParamRequestSubQueryNull = false;
            subqueryPredicates.add(cb.like(cb.lower(vehicleJoin.get("trspOpEndAreaLineOneTxt")),
                "%" + request.getTrspOpEndAreaLineOneTxt().replaceAll(ParamConstant.TRIM_SPACE_TWO_BYTE, "")
                    .toLowerCase() + "%"));
        }
        if (!BaseUtil.isNull(request.getMaxTrspOpDateTrmStrtDate())) {
            allParamRequestSubQueryNull = false;
            LocalDate maxDate = LocalDate.parse(request.getMaxTrspOpDateTrmStrtDate(), inputDateFormatter);
            subqueryPredicates.add(cb.lessThanOrEqualTo(vehicleJoin.get("trspOpDateTrmStrtDate"), maxDate));
        }
        if (!BaseUtil.isNull(request.getMinTrspOpDateTrmStrtDate())) {
            allParamRequestSubQueryNull = false;
            LocalDate minDate = LocalDate.parse(request.getMinTrspOpDateTrmStrtDate(), inputDateFormatter);
            subqueryPredicates.add(cb.greaterThanOrEqualTo(vehicleJoin.get("trspOpDateTrmStrtDate"), minDate));
        }
        if (!BaseUtil.isNull(request.getMaxTrspOpDateTrmEndDate())) {
            allParamRequestSubQueryNull = false;
            LocalDate maxDate = LocalDate.parse(request.getMaxTrspOpDateTrmEndDate(), inputDateFormatter);
            subqueryPredicates.add(cb.lessThanOrEqualTo(vehicleJoin.get("trspOpDateTrmEndDate"), maxDate));
        }
        if (!BaseUtil.isNull(request.getMinTrspOpDateTrmEndDate())) {
            allParamRequestSubQueryNull = false;
            LocalDate minDate = LocalDate.parse(request.getMinTrspOpDateTrmEndDate(), inputDateFormatter);
            subqueryPredicates.add(cb.greaterThanOrEqualTo(vehicleJoin.get("trspOpDateTrmEndDate"), minDate));
        }
        if (!BaseUtil.isNull(request.getMaxTrspOpPlanDateTrmStrtTime())) {
            allParamRequestSubQueryNull = false;
            request.setMaxTrspOpPlanDateTrmStrtTime(formatTime(request.getMaxTrspOpPlanDateTrmStrtTime()));
            LocalTime maxTime = LocalTime.parse(request.getMaxTrspOpPlanDateTrmStrtTime(), inputTimeFormatter);
            subqueryPredicates.add(cb.lessThanOrEqualTo(vehicleJoin.get("trspOpPlanDateTrmStrtTime"), maxTime));
        }
        if (!BaseUtil.isNull(request.getMinTrspOpPlanDateTrmStrtTime())) {
            allParamRequestSubQueryNull = false;
            request.setMinTrspOpPlanDateTrmStrtTime(formatTime(request.getMinTrspOpPlanDateTrmStrtTime()));
            LocalTime minTime = LocalTime.parse(request.getMinTrspOpPlanDateTrmStrtTime(), inputTimeFormatter);
            subqueryPredicates.add(cb.greaterThanOrEqualTo(vehicleJoin.get("trspOpPlanDateTrmStrtTime"), minTime));
        }
        if (!BaseUtil.isNull(request.getMaxTrspOpPlanDateTrmEndTime())) {
            allParamRequestSubQueryNull = false;
            request.setMaxTrspOpPlanDateTrmEndTime(formatTime(request.getMaxTrspOpPlanDateTrmEndTime()));
            LocalTime maxTime = LocalTime.parse(request.getMaxTrspOpPlanDateTrmEndTime(), inputTimeFormatter);
            subqueryPredicates.add(cb.lessThanOrEqualTo(vehicleJoin.get("trspOpPlanDateTrmEndTime"), maxTime));
        }
        if (!BaseUtil.isNull(request.getMinTrspOpPlanDateTrmEndTime())) {
            allParamRequestSubQueryNull = false;
            request.setMinTrspOpPlanDateTrmEndTime(formatTime(request.getMinTrspOpPlanDateTrmEndTime()));
            LocalTime minTime = LocalTime.parse(request.getMinTrspOpPlanDateTrmEndTime(), inputTimeFormatter);
            subqueryPredicates.add(cb.greaterThanOrEqualTo(vehicleJoin.get("trspOpPlanDateTrmEndTime"), minTime));
        }
        subqueryPredicates.add(cb.and(cb.equal(root.get("serviceNo"), carInfoRoot.get("serviceNo")),
            cb.equal(root.get("serviceStrtDate"), carInfoRoot.get("serviceStrtDate")),
            cb.equal(root.get("operatorId"), carInfoRoot.get("operatorId"))
        ));

        carInfoSubquery.select(carInfoRoot).where(subqueryPredicates.toArray(new Predicate[0]));

        // Predicates for main query
        List<Predicate> mainQueryPredicates = new ArrayList<>();
        mainQueryPredicates.add(cb.conjunction()); // Adds 1=1 as the default condition
        if (!allParamRequestSubQueryNull) {
            mainQueryPredicates.add(cb.exists(carInfoSubquery));
        }
        if (!BaseUtil.isNull(request.getServiceNo())) {
            mainQueryPredicates.add(
                cb.equal(cb.lower(root.get("serviceNo")), request.getServiceNo().trim().toLowerCase()));
        }
        if (!BaseUtil.isNull(request.getServiceName())) {
            mainQueryPredicates.add(
                cb.like(cb.lower(root.get("serviceName")),
                    "%" + request.getServiceName().replaceAll(ParamConstant.TRIM_SPACE_TWO_BYTE, "")
                        .toLowerCase() + "%"));
        }
        String operatorId = user.getCompanyId();
        mainQueryPredicates.add(cb.equal(root.get("operatorId"), operatorId));

        query.select(root).where(mainQueryPredicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * 日付最大最小チェック.<BR>
     *
     * @param max    最大日付
     * @param min    最小日付
     * @param field1 フィールド1
     * @param field2 フィールド2
     */
    private void checkDateMaxMin(String max, String min, String field1, String field2) {
        try {
            DateTimeFormatter inputDateFormatter = DateTimeFormatter.ofPattern(
                DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT);
            LocalDate maxDateParsed = LocalDate.parse(max, inputDateFormatter);
            LocalDate minDateParsed = LocalDate.parse(min, inputDateFormatter);
            if (maxDateParsed.isBefore(minDateParsed)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_DATE_TIME_MAX_MIN),
                        field1, field2)
                );
            }
        } catch (DateTimeException e) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_DATE_FORMAT),
                    field1, DataBaseConstant.DATE_TIME_FORMAT.DATE_FORMAT)
            );
        }

    }

    /**
     * 時間最大最小チェック.<BR>
     *
     * @param max    最大時間
     * @param min    最小時間
     * @param field1 フィールド1
     * @param field2 フィールド2
     */
    private void checkTimeMaxMin(String max, String min, String field1, String field2) {
        try {
            DateTimeFormatter inputTimeFormatter = DateTimeFormatter.ofPattern(
                DataBaseConstant.DATE_TIME_FORMAT.TIME_FORMAT_HHMMSS);
            max = formatTime(max);
            min = formatTime(min);
            LocalTime maxTimeParsed = LocalTime.parse(max, inputTimeFormatter);
            LocalTime minTimeParsed = LocalTime.parse(min, inputTimeFormatter);
            if (maxTimeParsed.isBefore(minTimeParsed)) {
                throw new NextWebException(
                    new NextAPIError(HttpStatus.BAD_REQUEST,
                        SoaResponsePool.get(Validate.VALID_DATE_TIME_MAX_MIN),
                        field1, field2)
                );
            }
        } catch (DateTimeException e) {
            throw new NextWebException(
                new NextAPIError(HttpStatus.BAD_REQUEST,
                    SoaResponsePool.get(Validate.VALID_TIME_FORMAT),
                    field1, field2)
            );
        }

    }

    /**
     * 時間比較.<BR>
     *
     * @param startTimeMax 開始時間最大
     * @param startTimeMin 開始時間最小
     * @param endTimeMax   終了時間最大
     * @param endTimeMin   終了時間最小
     */
    private void compareTime(String startTimeMax, String startTimeMin, String endTimeMax, String endTimeMin) {

        String startTime = null;
        String endTime = null;
        if (!BaseUtil.isNull(startTimeMax)) {
            startTime = formatTime(startTimeMax);
        }
        if (!BaseUtil.isNull(startTimeMin)) {
            startTime = formatTime(startTimeMin);
        }
        if (!BaseUtil.isNull(endTimeMin)) {
            endTime = formatTime(endTimeMin);
        }
        if (!BaseUtil.isNull(endTimeMax)) {
            endTime = formatTime(endTimeMax);
        }
        checkTimeMaxMin(endTime, startTime,
            DataBaseConstant.VehicleAvailabilityResource.TRSP_OP_PLAN_DATE_TRM_STRT_TIME,
            DataBaseConstant.VehicleAvailabilityResource.TRSP_OP_PLAN_DATE_TRM_END_TIME);

    }

    /**
     * 時間フォーマット.<BR>
     *
     * @param time 時間
     * @return 時間
     */
    private String formatTime(String time) {
        if (!BaseUtil.isNull(time) && time.length() == 4) {
            time += "00";
        }
        return time;
    }
}
