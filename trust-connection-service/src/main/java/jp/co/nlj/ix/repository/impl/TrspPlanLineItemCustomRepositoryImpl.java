package jp.co.nlj.ix.repository.impl;

import com.next.logistic.authorization.UserContext;
import com.next.logistic.exception.NextWebException;
import com.next.logistic.exception.model.NextAPIError;
import com.next.logistic.model.SoaResponsePool;
import com.next.logistic.util.BaseUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
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
import java.util.stream.Collectors;
import jp.co.nlj.ix.constant.DataBaseConstant;
import jp.co.nlj.ix.constant.MapperConstants.Validate;
import jp.co.nlj.ix.constant.ParamConstant;
import jp.co.nlj.ix.domain.CnsLineItem;
import jp.co.nlj.ix.domain.CnsLineItemByDate;
import jp.co.nlj.ix.domain.TrspPlanLineItem;
import jp.co.nlj.ix.dto.trspPlanLineItem.request.TrspPlanLineItemSearchRequest;
import jp.co.nlj.ix.repository.TrspPlanLineItemCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 輸送計画明細項目カスタムリポジトリ実装。<BR>
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
     * 輸送計画明細項目を検索する。
     *
     * @param request 検索リクエスト
     * @return 輸送計画明細項目のリスト
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
        boolean allParamRequestSubQueryNull = true;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrspPlanLineItem> query = cb.createQuery(TrspPlanLineItem.class);
        Root<TrspPlanLineItem> root = query.from(TrspPlanLineItem.class);

        Join<TrspPlanLineItem, CnsLineItem> cnsLineItemJoin = root.join("cnsLineItem",
            JoinType.LEFT); // Replace "cnsLineItems" with the actual field name in TrspPlanLineItem for the relationship

        Subquery<CnsLineItem> cnsLineItemSubquery = query.subquery(CnsLineItem.class);
        Root<CnsLineItem> cnsLineItemRoot = cnsLineItemSubquery.from(CnsLineItem.class);
        Root<CnsLineItemByDate> cnsLineItemByDateRoot = cnsLineItemSubquery.from(CnsLineItemByDate.class);

        Predicate joinCondition = cb.equal(
            cnsLineItemByDateRoot.get("cnsLineItemId"),
            cnsLineItemRoot.get("id")
        );

        joinCondition = cb.and(
            joinCondition,
            cb.equal(cnsLineItemByDateRoot.get("transType"), 1),
            cnsLineItemByDateRoot.get("status").in(0, 1, 2)
        );

        if (!BaseUtil.isNull(request.getTrspOpStrtAreaLineOneTxt())) {
            allParamRequestSubQueryNull = false;
            Long departureFromValue = Long.parseLong(request.getTrspOpStrtAreaLineOneTxt());
            joinCondition = cb.and(joinCondition,
                cb.equal(cnsLineItemByDateRoot.get("departureFrom"), departureFromValue));
        }
        if (!BaseUtil.isNull(request.getTrspOpEndAreaLineOneTxt())) {
            allParamRequestSubQueryNull = false;
            Long arrivalToValue = Long.parseLong(request.getTrspOpEndAreaLineOneTxt());
            joinCondition = cb.and(joinCondition, cb.equal(cnsLineItemByDateRoot.get("arrivalTo"), arrivalToValue));
        }
        if (!BaseUtil.isNull(request.getMaxTrspOpDateTrmStrtDate())) {
            allParamRequestSubQueryNull = false;
            LocalDate maxDate = LocalDate.parse(request.getMaxTrspOpDateTrmStrtDate(), inputDateFormatter);
            joinCondition = cb.and(joinCondition,
                cb.lessThanOrEqualTo(cnsLineItemByDateRoot.get("collectionDate"), maxDate));
        }
        if (!BaseUtil.isNull(request.getMinTrspOpDateTrmStrtDate())) {
            allParamRequestSubQueryNull = false;
            LocalDate minDate = LocalDate.parse(request.getMinTrspOpDateTrmStrtDate(), inputDateFormatter);
            joinCondition = cb.and(joinCondition,
                cb.greaterThanOrEqualTo(cnsLineItemByDateRoot.get("collectionDate"), minDate));
        }
        if (!BaseUtil.isNull(request.getMinTrspOpPlanDateTrmStrtTime())) {
            allParamRequestSubQueryNull = false;
            request.setMinTrspOpPlanDateTrmStrtTime(formatTime(request.getMinTrspOpPlanDateTrmStrtTime()));
            LocalTime minTime = LocalTime.parse(request.getMinTrspOpPlanDateTrmStrtTime(), inputTimeFormatter);
            joinCondition = cb.and(joinCondition,
                cb.greaterThanOrEqualTo(cnsLineItemByDateRoot.get("collectionTimeFrom"), minTime));
        }
        if (!BaseUtil.isNull(request.getMaxTrspOpPlanDateTrmStrtTime())) {
            allParamRequestSubQueryNull = false;
            request.setMaxTrspOpPlanDateTrmStrtTime(formatTime(request.getMaxTrspOpPlanDateTrmStrtTime()));
            LocalTime maxTime = LocalTime.parse(request.getMaxTrspOpPlanDateTrmStrtTime(), inputTimeFormatter);
            joinCondition = cb.and(joinCondition,
                cb.lessThanOrEqualTo(cnsLineItemByDateRoot.get("collectionTimeTo"), maxTime));
        }

        if (request.getAdvancedConditions() != null) {
            if (request.getAdvancedConditions().getFreightRateMin() != null) {
                joinCondition = cb.and(joinCondition, cb.greaterThanOrEqualTo(cnsLineItemByDateRoot.get("pricePerUnit"),
                    request.getAdvancedConditions().getFreightRateMin()));
            }

            if (request.getAdvancedConditions().getFreightRateMax() != null) {
                joinCondition = cb.and(joinCondition, cb.lessThanOrEqualTo(cnsLineItemByDateRoot.get("pricePerUnit"),
                    request.getAdvancedConditions().getFreightRateMax()));
            }

            if (request.getAdvancedConditions().getDayWeek() != null && !request.getAdvancedConditions().getDayWeek()
                .isEmpty()) {
                Expression<Integer> isoDayOfWeek = cb.function("DATE_PART", Integer.class,
                    cb.literal("ISODOW"), cnsLineItemByDateRoot.get("collectionDate"));
                Predicate dayOfWeekPredicate = isoDayOfWeek.in(request.getAdvancedConditions().getDayWeek());

                joinCondition = cb.and(joinCondition, dayOfWeekPredicate);
            }
            if (request.getAdvancedConditions().getTemperatureRange() != null &&
                !request.getAdvancedConditions().getTemperatureRange().isEmpty()) {
                String tempRangeString = request.getAdvancedConditions().getTemperatureRange().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")); // Convert list to comma-separated string

                Predicate temperatureRangePredicate = cb.like(
                    cnsLineItemByDateRoot.get("temperatureRange"),
                    cb.literal("%" + tempRangeString + "%") // Use wildcard for LIKE
                );

                joinCondition = cb.and(joinCondition, temperatureRangePredicate);

            }
        }

        // Predicates for main query
        List<Predicate> mainQueryPredicates = new ArrayList<>();
        mainQueryPredicates.add(cb.conjunction()); // Adds 1=1 as the default condition

        if (!BaseUtil.isNull(request.getOperatorId())) {
            mainQueryPredicates.add(cb.equal(root.get("operatorId"), request.getOperatorId()));
        }

        cnsLineItemSubquery.select(cnsLineItemRoot).where(cb.and(joinCondition));
        mainQueryPredicates.add(cb.in(cnsLineItemJoin).value(cnsLineItemSubquery));

        query.select(root).where(mainQueryPredicates.toArray(new Predicate[0]));

        query.orderBy(cb.desc(cnsLineItemByDateRoot.get("createdDate")));
        query.orderBy(cb.desc(root.get("createdDate")));
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * 日付の最大値と最小値をチェックする。
     *
     * @param max    最大日付
     * @param min    最小日付
     * @param field1 フィールド名1
     * @param field2 フィールド名2
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
     * 時間の最大値と最小値をチェックする。
     *
     * @param max    最大時間
     * @param min    最小時間
     * @param field1 フィールド名1
     * @param field2 フィールド名2
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
     * 開始時間と終了時間を比較する。
     *
     * @param startTimeMax 開始時間の最大値
     * @param startTimeMin 開始時間の最小値
     * @param endTimeMax   終了時間の最大値
     * @param endTimeMin   終了時間の最小値
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
     * 時間をフォーマットする。
     *
     * @param time フォーマットする時間
     * @return フォーマットされた時間
     */
    private String formatTime(String time) {
        if (!BaseUtil.isNull(time) && time.length() == 4) {
            time += "00";
        }
        return time;
    }
}
