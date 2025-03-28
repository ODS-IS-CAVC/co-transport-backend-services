package nlj.business.transaction.repository;

import java.util.List;
import nlj.business.transaction.domain.CnsLineItemByDate;
import nlj.business.transaction.domain.VehicleAvbResourceItem;
import nlj.business.transaction.dto.matching.TransMatchingAbilityDetailResponse;
import nlj.business.transaction.dto.matching.TransMatchingAbilitySaleHeadResponse;
import nlj.business.transaction.dto.matching.TransMatchingDTOResponse;
import nlj.business.transaction.dto.matching.TransMatchingHeadResponse;
import nlj.business.transaction.dto.matching.TrspPlanIdDTOResponse;
import org.springframework.data.domain.Page;

/**
 * <PRE>
 * 配送マッチング。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TransMatchingCustomRepository {

    Page<TransMatchingDTOResponse> getTransPlanMatching(String companyId, String freeWord,
        List<Integer> temperatureRange, int offset, int limit);

    List<TrspPlanIdDTOResponse> getTransPlanMatchingById(Long cnsLineItemByDateId);

    Page<TransMatchingHeadResponse> getTransMatchingByTrailer(String transType, String companyId, String freeWord,
        List<Integer> temperatureRange, int offset, int limit, Integer isEmergency);

    TransMatchingAbilityDetailResponse getTransMatchingByTrailerId(Long vehicleAvbResourceId, String transType);

    Page<TransMatchingHeadResponse> getTransactionByTrailer(String transType, List<String> advanceStatus,
        String companyId, String status, String freeWord, List<Integer> temperatureRange, int page, int limit,
        Integer isEmergency);

    List<CnsLineItemByDate> shippers(VehicleAvbResourceItem vehicleAvbResourceItem, Integer transType,
        List<Integer> statuses, List<Long> cnsLineItemByDateIds);

    List<VehicleAvbResourceItem> carriers(CnsLineItemByDate cnsLineItemByDate, Integer transType,
        List<Integer> statuses);

    Page<TransMatchingHeadResponse> getTransCarrierMatchingByTrailer(Long orderId, int page, int limit);

    Page<TransMatchingAbilitySaleHeadResponse> getTransportAbilitySale(String transType, List<Integer> temperatureRange,
        String companyId, int page, int limit);
}