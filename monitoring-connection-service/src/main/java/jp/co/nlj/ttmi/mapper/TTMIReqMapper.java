package jp.co.nlj.ttmi.mapper;

import jp.co.nlj.ttmi.dto.trackBySip.LogsSrvcPrvDTO;
import jp.co.nlj.ttmi.dto.trackBySip.ShipFromPrtyDTO;
import jp.co.nlj.ttmi.dto.trackBySip.ShipToPrtyDTO;
import jp.co.nlj.ttmi.dto.trackBySip.TrspIsrDTO;
import jp.co.nlj.ttmi.dto.trackBySip.TrspSrvcDTO;
import jp.co.nlj.ttmi.dto.trackBySip.request.TTMITrackBySipRequestDTO;
import jp.co.nlj.ttmi.dto.trackBySip.request.TrackBySipReqDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;

/**
 * <PRE>
 * TTMIReqMapper。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Primary
@Mapper(componentModel = "spring")
public interface TTMIReqMapper {

    @Mapping(source = "trspIsr", target = "trspIsr")
    @Mapping(source = "trspSrvc", target = "trspSrvc")
    @Mapping(source = "logsSrvcPrv", target = "logsSrvcPrv")
    @Mapping(source = "shipFromPrty", target = "shipFromPrty")
    @Mapping(source = "shipToPrty", target = "shipToPrty")
    TTMITrackBySipRequestDTO toTTMIRequest(TrackBySipReqDTO source);

    // Inner class mappings
    @Mapping(source = "trspInstructionId", target = "trspInstructionId")
    TrspIsrDTO trspIsrToTTMI(TrspIsrDTO trspIsr);

    @Mapping(source = "carLicensePltNumId", target = "carLicensePltNumId")
    @Mapping(source = "avbDateCllDate", target = "avbDateCllDate")
    @Mapping(source = "avbFromTimeOfCllTime", target = "avbFromTimeOfCllTime")
    @Mapping(source = "apedArrToDate", target = "apedArrToDate")
    @Mapping(source = "apedArrToTimePrfmDttm", target = "apedArrToTimePrfmDttm")
    TrspSrvcDTO trspSrvcToTTMI(TrspSrvcDTO trspSrvc);

    @Mapping(source = "logsSrvcPrvPrtyNameTxt", target = "logsSrvcPrvPrtyNameTxt")
    LogsSrvcPrvDTO logsSrvcPrvToTTMI(LogsSrvcPrvDTO logsSrvcPrv);

    @Mapping(source = "shipFromPrtyBrncOffId", target = "shipFromPrtyBrncOffId")
    ShipFromPrtyDTO shipFromPrtyToTTMI(ShipFromPrtyDTO shipFromPrty);

    @Mapping(source = "shipToPrtyBrncOffId", target = "shipToPrtyBrncOffId")
    ShipToPrtyDTO shipToPrtyToTTMI(ShipToPrtyDTO shipToPrty);
}
