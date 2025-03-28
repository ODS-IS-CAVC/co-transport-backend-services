package nlj.business.transaction.mapper;


import java.util.List;
import nlj.business.transaction.dto.matching.TransMatchingAbilityResponse;
import nlj.business.transaction.dto.matching.TransMatchingAbilitySaleHeadResponse;
import nlj.business.transaction.dto.matching.TransMatchingAbilitySaleResponse;
import nlj.business.transaction.dto.matching.TransMatchingHeadResponse;
import nlj.business.transaction.dto.matching.TransMatchingIdDTOResponse;
import nlj.business.transaction.dto.matching.TrspPlanIdDTOResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * <PRE>
 * 運送計画応答マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface TransMatchingHeadResponseMapper {

    @Mapping(target = "createdAt", source = "transMatchingDTO.createdDate")
    @Mapping(target = "serviceNo", source = "transMatchingDTO.serviceNo1")
    @Mapping(target = "serviceName", source = "transMatchingDTO.serviceName1")
    @Mapping(target = "serviceStrtDate", source = "transMatchingDTO.serviceStrtDate1")
    @Mapping(target = "serviceEndDate", source = "transMatchingDTO.serviceEndDate1")
    @Mapping(target = "serviceStrtTime", source = "transMatchingDTO.serviceStrtTime1")
    @Mapping(target = "serviceEndTime", source = "transMatchingDTO.serviceEndTime1")
    @Mapping(target = "departureFrom", source = "transMatchingDTO.departureFrom1")
    @Mapping(target = "arrivalTo", source = "transMatchingDTO.arrivalTo1")
    @Mapping(target = "trailer1.orderId", source = "transMatchingDTO.orderId1")
    @Mapping(target = "trailer2.orderId", source = "transMatchingDTO.orderId2")
    @Mapping(target = "trailer1.matchingId", source = "transMatchingDTO.matchingId1")
    @Mapping(target = "trailer2.matchingId", source = "transMatchingDTO.matchingId2")
    @Mapping(target = "trailer1.createdAt", source = "transMatchingDTO.createdAt1")
    @Mapping(target = "trailer2.createdAt", source = "transMatchingDTO.createdAt2")
    @Mapping(target = "trailer1.matchingType", source = "transMatchingDTO.matchingType1")
    @Mapping(target = "trailer2.matchingType", source = "transMatchingDTO.matchingType2")
    @Mapping(target = "trailer1.trailerLicensePltNumId", source = "transMatchingDTO.trailerLicensePltNumId1")
    @Mapping(target = "trailer1.price", source = "transMatchingDTO.price1")
    @Mapping(target = "trailer1.cutOffTime", source = "transMatchingDTO.cutOffTime1")
    @Mapping(target = "trailer1.matchingStatus", source = "transMatchingDTO.matchingStatus1")
    @Mapping(target = "trailer1.orderStatus", source = "transMatchingDTO.orderStatus1")
    @Mapping(target = "trailer1.totalCount", source = "transMatchingDTO.totalCount1")
    @Mapping(target = "trailer2.trailerLicensePltNumId", source = "transMatchingDTO.trailerLicensePltNumId2")
    @Mapping(target = "trailer2.price", source = "transMatchingDTO.price2")
    @Mapping(target = "trailer2.cutOffTime", source = "transMatchingDTO.cutOffTime2")
    @Mapping(target = "trailer2.matchingStatus", source = "transMatchingDTO.matchingStatus2")
    @Mapping(target = "trailer2.orderStatus", source = "transMatchingDTO.orderStatus2")
    @Mapping(target = "trailer2.totalCount", source = "transMatchingDTO.totalCount2")
    @Mapping(target = "trailer1.transType", source = "transMatchingDTO.transType1")
    @Mapping(target = "trailer2.transType", source = "transMatchingDTO.transType2")

    @Mapping(target = "trailer1.vehicleAvbResourceId", source = "transMatchingDTO.vehicleAvbResourceId1")
    @Mapping(target = "trailer2.vehicleAvbResourceId", source = "transMatchingDTO.vehicleAvbResourceId2")
    @Mapping(target = "trailer1.tripName", source = "transMatchingDTO.tripName1")
    @Mapping(target = "trailer2.tripName", source = "transMatchingDTO.tripName2")
    @Mapping(target = "trailer1.vehicleName", source = "transMatchingDTO.vehicleName1")
    @Mapping(target = "trailer2.vehicleName", source = "transMatchingDTO.vehicleName2")
    @Mapping(target = "trailer1.temperatureRange", source = "transMatchingDTO.temperatureRange1")
    @Mapping(target = "trailer2.temperatureRange", source = "transMatchingDTO.temperatureRange2")
    @Mapping(target = "trailer1.vehicleDiagramItemId", source = "transMatchingDTO.vehicleDiagramItemId1")
    @Mapping(target = "trailer2.vehicleDiagramItemId", source = "transMatchingDTO.vehicleDiagramItemId2")
    @Mapping(target = "trailer1.shipperOperatorId", source = "transMatchingDTO.shipperOperatorId1")
    @Mapping(target = "trailer1.carrierOperatorId", source = "transMatchingDTO.carrierOperatorId1")
    @Mapping(target = "trailer1.carrier2OperatorId", source = "transMatchingDTO.carrier2OperatorId1")
    @Mapping(target = "trailer2.shipperOperatorId", source = "transMatchingDTO.shipperOperatorId2")
    @Mapping(target = "trailer2.carrierOperatorId", source = "transMatchingDTO.carrierOperatorId2")
    @Mapping(target = "trailer2.carrier2OperatorId", source = "transMatchingDTO.carrier2OperatorId2")
    @Mapping(target = "trailer1.carrier2OperatorName", source = "transMatchingDTO.carrier2OperatorName1")
    @Mapping(target = "trailer2.carrier2OperatorName", source = "transMatchingDTO.carrier2OperatorName2")
    @Mapping(target = "trailer1.displayOrder", source = "transMatchingDTO.displayOrder1")
    @Mapping(target = "trailer2.displayOrder", source = "transMatchingDTO.displayOrder2")
    @Mapping(target = "trailer1.departureTimeMax", source = "transMatchingDTO.departureTimeMax1")
    @Mapping(target = "trailer2.departureTimeMax", source = "transMatchingDTO.departureTimeMax2")
    @Mapping(target = "trailer1.departureTimeMin", source = "transMatchingDTO.departureTimeMin1")
    @Mapping(target = "trailer2.departureTimeMin", source = "transMatchingDTO.departureTimeMin2")
    @Mapping(target = "trailer1.cutOffFee", source = "transMatchingDTO.cutOffFee1")
    @Mapping(target = "trailer2.cutOffFee", source = "transMatchingDTO.cutOffFee2")
    @Mapping(target = "trailer1.serviceStrtDate", source = "transMatchingDTO.serviceStrtDate1")
    @Mapping(target = "trailer2.serviceStrtDate", source = "transMatchingDTO.serviceStrtDate2")
    @Mapping(target = "trailer1.serviceStrtTime", source = "transMatchingDTO.serviceStrtTime1")
    @Mapping(target = "trailer2.serviceStrtTime", source = "transMatchingDTO.serviceStrtTime2")
    @Mapping(target = "trailer1.serviceEndTime", source = "transMatchingDTO.serviceEndTime1")
    @Mapping(target = "trailer2.serviceEndTime", source = "transMatchingDTO.serviceEndTime2")
    @Mapping(target = "trailer1.serviceEndDate", source = "transMatchingDTO.serviceEndDate1")
    @Mapping(target = "trailer2.serviceEndDate", source = "transMatchingDTO.serviceEndDate2")
    @Mapping(target = "trailer1.isEmergency", source = "transMatchingDTO.isEmergency1")
    @Mapping(target = "trailer2.isEmergency", source = "transMatchingDTO.isEmergency2")

    @Mapping(target = "trailer1.vehicleAvbResourceItemId", source = "transMatchingDTO.vehicleAvbResourceItemId1")
    @Mapping(target = "trailer2.vehicleAvbResourceItemId", source = "transMatchingDTO.vehicleAvbResourceItemId2")
    @Mapping(target = "trailer1.cnsLineItemByDateId", source = "transMatchingDTO.cnsLineItemByDateId1")
    @Mapping(target = "trailer2.cnsLineItemByDateId", source = "transMatchingDTO.cnsLineItemByDateId2")
    TransMatchingHeadResponse toMapper(TransMatchingAbilityResponse transMatchingDTO);

    List<TransMatchingHeadResponse> toMapperList(List<TransMatchingAbilityResponse> transMatchingDTOList);

    @Mapping(target = "createdAt", source = "transMatchingDTO.createdDate")
    @Mapping(target = "trailer1.createdAt", source = "transMatchingDTO.createdAt1")
    @Mapping(target = "trailer2.createdAt", source = "transMatchingDTO.createdAt2")
    @Mapping(target = "serviceNo", source = "transMatchingDTO.serviceNo1")
    @Mapping(target = "serviceName", source = "transMatchingDTO.serviceName1")
    @Mapping(target = "serviceStrtDate", source = "transMatchingDTO.serviceStrtDate1")
    @Mapping(target = "serviceStrtTime", source = "transMatchingDTO.serviceStrtTime1")
    @Mapping(target = "serviceEndTime", source = "transMatchingDTO.serviceEndTime1")
    @Mapping(target = "departureFrom", source = "transMatchingDTO.departureFrom1")
    @Mapping(target = "arrivalTo", source = "transMatchingDTO.arrivalTo1")
    @Mapping(target = "trailer1.trailerLicensePltNumId", source = "transMatchingDTO.trailerLicensePltNumId1")
    @Mapping(target = "trailer1.price", source = "transMatchingDTO.price1")
    @Mapping(target = "trailer1.cutOffTime", source = "transMatchingDTO.cutOffTime1")
    @Mapping(target = "trailer2.trailerLicensePltNumId", source = "transMatchingDTO.trailerLicensePltNumId2")
    @Mapping(target = "trailer2.price", source = "transMatchingDTO.price2")
    @Mapping(target = "trailer2.cutOffTime", source = "transMatchingDTO.cutOffTime2")
    @Mapping(target = "trailer1.vehicleAvbResourceId", source = "transMatchingDTO.vehicleAvbResourceId1")
    @Mapping(target = "trailer2.vehicleAvbResourceId", source = "transMatchingDTO.vehicleAvbResourceId2")
    @Mapping(target = "trailer1.tripName", source = "transMatchingDTO.tripName1")
    @Mapping(target = "trailer2.tripName", source = "transMatchingDTO.tripName2")
    @Mapping(target = "trailer1.temperatureRange", source = "transMatchingDTO.temperatureRange1")
    @Mapping(target = "trailer2.temperatureRange", source = "transMatchingDTO.temperatureRange2")
    @Mapping(target = "trailer1.vehicleName", source = "transMatchingDTO.vehicleName1")
    @Mapping(target = "trailer2.vehicleName", source = "transMatchingDTO.vehicleName2")
    @Mapping(target = "trailer1.vehicleDiagramItemId", source = "transMatchingDTO.vehicleDiagramItemId1")
    @Mapping(target = "trailer2.vehicleDiagramItemId", source = "transMatchingDTO.vehicleDiagramItemId2")
    @Mapping(target = "trailer1.displayOrder", source = "transMatchingDTO.displayOrder1")
    @Mapping(target = "trailer2.displayOrder", source = "transMatchingDTO.displayOrder2")
    @Mapping(target = "trailer1.departureTimeMax", source = "transMatchingDTO.departureTimeMax1")
    @Mapping(target = "trailer2.departureTimeMax", source = "transMatchingDTO.departureTimeMax2")
    @Mapping(target = "trailer1.departureTimeMin", source = "transMatchingDTO.departureTimeMin1")
    @Mapping(target = "trailer2.departureTimeMin", source = "transMatchingDTO.departureTimeMin2")
    @Mapping(target = "trailer1.cutOffFee", source = "transMatchingDTO.cutOffFee1")
    @Mapping(target = "trailer2.cutOffFee", source = "transMatchingDTO.cutOffFee2")
    @Mapping(target = "trailer1.serviceStrtDate", source = "transMatchingDTO.serviceStrtDate1")
    @Mapping(target = "trailer2.serviceStrtDate", source = "transMatchingDTO.serviceStrtDate2")
    @Mapping(target = "trailer1.serviceStrtTime", source = "transMatchingDTO.serviceStrtTime1")
    @Mapping(target = "trailer2.serviceStrtTime", source = "transMatchingDTO.serviceStrtTime2")
    @Mapping(target = "trailer1.serviceEndTime", source = "transMatchingDTO.serviceEndTime1")
    @Mapping(target = "trailer2.serviceEndTime", source = "transMatchingDTO.serviceEndTime2")
    @Mapping(target = "trailer1.serviceEndDate", source = "transMatchingDTO.serviceEndDate1")
    @Mapping(target = "trailer2.serviceEndDate", source = "transMatchingDTO.serviceEndDate2")
    TransMatchingAbilitySaleHeadResponse toSaleMapper(TransMatchingAbilitySaleResponse transMatchingDTO);

    List<TransMatchingAbilitySaleHeadResponse> toSaleMapperList(
        List<TransMatchingAbilitySaleResponse> transMatchingDTOList);

    @Mapping(target = "trailer.trailerLicensePltNumId", source = "transMatchingIdDTOResponse.trailerLicensePltNumId")
    @Mapping(target = "trailer.createdAt", source = "transMatchingIdDTOResponse.createdAt")
    @Mapping(target = "trailer.serviceStrtTime", source = "transMatchingIdDTOResponse.serviceStrtTime")
    @Mapping(target = "trailer.serviceEndTime", source = "transMatchingIdDTOResponse.serviceEndTime")
    TrspPlanIdDTOResponse toMapperTrspPlanId(TransMatchingIdDTOResponse transMatchingIdDTOResponse);

    List<TrspPlanIdDTOResponse> toMapperTrspPlanIdList(List<TransMatchingIdDTOResponse> transMatchingDTOList);
}
