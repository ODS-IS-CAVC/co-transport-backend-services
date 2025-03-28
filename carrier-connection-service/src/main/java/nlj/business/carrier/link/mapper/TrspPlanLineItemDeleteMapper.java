package nlj.business.carrier.link.mapper;

import nlj.business.carrier.link.constant.MapperConstants;
import nlj.business.carrier.link.domain.TrspPlanLineItem;
import nlj.business.carrier.link.dto.trspPlanLineItem.CneePrtyDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.CnsDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.CnsgPrtyDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.DelInfoDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.FretClimToPrtyDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.LogsSrvcPrvDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.RoadCarrDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.TrspIsrDeleteDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.TrspRqrPrtyDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.TrspSrvcDeleteDTO;
import nlj.business.carrier.link.dto.trspPlanLineItem.TrspVehicleTrmsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * 輸送計画行マッパー.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Component
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface TrspPlanLineItemDeleteMapper {

    @Mapping(source = "trspIsrDTO.trspInstructionId", target = "trspInstructionId")
    @Mapping(source = "trspIsrDTO.trspInstructionDateSubmDttm", target = "trspInstructionDateSubmDttm", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "trspIsrDTO.invNumId", target = "invNumId")
    @Mapping(source = "trspIsrDTO.cmnInvNumId", target = "cmnInvNumId")
    @Mapping(source = "trspIsrDTO.mixLoadNumId", target = "mixLoadNumId")
    @Mapping(source = "trspSrvcDTO.serviceNo", target = "serviceNo")
    @Mapping(source = "trspSrvcDTO.serviceName", target = "serviceName")
    @Mapping(source = "trspSrvcDTO.serviceStrtDate", target = "serviceStrtDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "trspSrvcDTO.serviceStrtTime", target = "serviceStrtTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(source = "trspSrvcDTO.serviceEndDate", target = "serviceEndDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "trspSrvcDTO.serviceEndTime", target = "serviceEndTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(source = "trspSrvcDTO.freightRate", target = "freightRate")
    @Mapping(source = "trspSrvcDTO.trspMeansTypCd", target = "trspMeansTypCd")
    @Mapping(source = "trspSrvcDTO.trspSrvcTypCd", target = "trspSrvcTypCd")
    @Mapping(source = "trspSrvcDTO.roadCarrSrvcTypCd", target = "roadCarrSrvcTypCd")
    @Mapping(source = "trspSrvcDTO.trspRootPrioCd", target = "trspRootPrioCd")
    @Mapping(source = "trspSrvcDTO.carClsPrioCd", target = "carClsPrioCd")
    @Mapping(source = "trspSrvcDTO.clsOfCargInSrvcRqrmCd", target = "clsOfCargInSrvcRqrmCd")
    @Mapping(source = "trspSrvcDTO.clsOfPkgUpSrvcRqrmCd", target = "clsOfPkgUpSrvcRqrmCd")
    @Mapping(source = "trspSrvcDTO.pyrClsSrvcRqrmCd", target = "pyrClsSrvcRqrmCd")
    @Mapping(source = "trspSrvcDTO.trmsOfMixLoadCndCd", target = "trmsOfMixLoadCndCd")
    @Mapping(source = "trspSrvcDTO.dsedCllFromDate", target = "dsedCllFromDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "trspSrvcDTO.dsedCllToDate", target = "dsedCllToDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "trspSrvcDTO.dsedCllFromTime", target = "dsedCllFromTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(source = "trspSrvcDTO.dsedCllToTime", target = "dsedCllToTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(source = "trspSrvcDTO.dsedCllTimeTrmsSrvcRqrmCd", target = "dsedCllTimeTrmsSrvcRqrmCd")
    @Mapping(source = "trspSrvcDTO.apedArrFromDate", target = "apedArrFromDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "trspSrvcDTO.apedArrToDate", target = "apedArrToDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "trspSrvcDTO.apedArrFromTimePrfmDttm", target = "apedArrFromTimePrfmDttm", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(source = "trspSrvcDTO.apedArrToTimePrfmDttm", target = "apedArrToTimePrfmDttm", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(source = "trspSrvcDTO.apedArrTimeTrmsSrvcRqrmCd", target = "apedArrTimeTrmsSrvcRqrmCd")
    @Mapping(source = "trspSrvcDTO.trmsOfMixLoadTxt", target = "trmsOfMixLoadTxt")
    @Mapping(source = "trspSrvcDTO.trspSrvcNoteOneTxt", target = "trspSrvcNoteOneTxt")
    @Mapping(source = "trspSrvcDTO.trspSrvcNoteTwoTxt", target = "trspSrvcNoteTwoTxt")
    @Mapping(source = "trspVehicleTrmsDTO.carClsOfSizeCd", target = "carClsOfSizeCd")
    @Mapping(source = "trspVehicleTrmsDTO.carClsOfShpCd", target = "carClsOfShpCd")
    @Mapping(source = "trspVehicleTrmsDTO.carClsOfTlgLftrExstCd", target = "carClsOfTlgLftrExstCd")
    @Mapping(source = "trspVehicleTrmsDTO.carClsOfWingBodyExstCd", target = "carClsOfWingBodyExstCd")
    @Mapping(source = "trspVehicleTrmsDTO.carClsOfRfgExstCd", target = "carClsOfRfgExstCd")
    @Mapping(source = "trspVehicleTrmsDTO.trmsOfLwrTmpMeas", target = "trmsOfLwrTmpMeas")
    @Mapping(source = "trspVehicleTrmsDTO.trmsOfUppTmpMeas", target = "trmsOfUppTmpMeas")
    @Mapping(source = "trspVehicleTrmsDTO.carClsOfCrnExstCd", target = "carClsOfCrnExstCd")
    @Mapping(source = "trspVehicleTrmsDTO.carRmkAboutEqpmTxt", target = "carRmkAboutEqpmTxt")
    @Mapping(source = "delInfoDTO.delNoteId", target = "delNoteId")
    @Mapping(source = "delInfoDTO.shpmNumId", target = "shpmNumId")
    @Mapping(source = "delInfoDTO.rcedOrdNumId", target = "rcedOrdNumId")
    @Mapping(source = "cnsDTO.istdTotlPcksQuan", target = "istdTotlPcksQuan")
    @Mapping(source = "cnsDTO.numUntCd", target = "numUntCd")
    @Mapping(source = "cnsDTO.istdTotlWeigMeas", target = "istdTotlWeigMeas")
    @Mapping(source = "cnsDTO.weigUntCd", target = "weigUntCd")
    @Mapping(source = "cnsDTO.istdTotlVolMeas", target = "istdTotlVolMeas")
    @Mapping(source = "cnsDTO.volUntCd", target = "volUntCd")
    @Mapping(source = "cnsDTO.istdTotlUntlQuan", target = "istdTotlUntlQuan")
    @Mapping(source = "cnsgPrtyDTO.cnsgPrtyHeadOffId", target = "cnsgPrtyHeadOffId")
    @Mapping(source = "cnsgPrtyDTO.cnsgPrtyBrncOffId", target = "cnsgPrtyBrncOffId")
    @Mapping(source = "cnsgPrtyDTO.cnsgPrtyNameTxt", target = "cnsgPrtyNameTxt")
    @Mapping(source = "cnsgPrtyDTO.cnsgSctSpedOrgId", target = "cnsgSctSpedOrgId")
    @Mapping(source = "cnsgPrtyDTO.cnsgSctSpedOrgNameTxt", target = "cnsgSctSpedOrgNameTxt")
    @Mapping(source = "cnsgPrtyDTO.cnsgTelCmmCmpNumTxt", target = "cnsgTelCmmCmpNumTxt")
    @Mapping(source = "cnsgPrtyDTO.cnsgPstlAdrsLineOneTxt", target = "cnsgPstlAdrsLineOneTxt")
    @Mapping(source = "cnsgPrtyDTO.cnsgPstcCd", target = "cnsgPstcCd")
    @Mapping(source = "trspRqrPrtyDTO.trspRqrPrtyHeadOffId", target = "trspRqrPrtyHeadOffId")
    @Mapping(source = "trspRqrPrtyDTO.trspRqrPrtyBrncOffId", target = "trspRqrPrtyBrncOffId")
    @Mapping(source = "trspRqrPrtyDTO.trspRqrPrtyNameTxt", target = "trspRqrPrtyNameTxt")
    @Mapping(source = "trspRqrPrtyDTO.trspRqrSctSpedOrgId", target = "trspRqrSctSpedOrgId")
    @Mapping(source = "trspRqrPrtyDTO.trspRqrSctSpedOrgNameTxt", target = "trspRqrSctSpedOrgNameTxt")
    @Mapping(source = "trspRqrPrtyDTO.trspRqrSctTelCmmCmpNumTxt", target = "trspRqrSctTelCmmCmpNumTxt")
    @Mapping(source = "trspRqrPrtyDTO.trspRqrPstlAdrsLineOneTxt", target = "trspRqrPstlAdrsLineOneTxt")
    @Mapping(source = "trspRqrPrtyDTO.trspRqrPstcCd", target = "trspRqrPstcCd")
    @Mapping(source = "cneePrtyDTO.cneePrtyHeadOffId", target = "cneePrtyHeadOffId")
    @Mapping(source = "cneePrtyDTO.cneePrtyBrncOffId", target = "cneePrtyBrncOffId")
    @Mapping(source = "cneePrtyDTO.cneePrtyNameTxt", target = "cneePrtyNameTxt")
    @Mapping(source = "cneePrtyDTO.cneeSctId", target = "cneeSctId")
    @Mapping(source = "cneePrtyDTO.cneeSctNameTxt", target = "cneeSctNameTxt")
    @Mapping(source = "cneePrtyDTO.cneePrimCntPersNameTxt", target = "cneePrimCntPersNameTxt")
    @Mapping(source = "cneePrtyDTO.cneeTelCmmCmpNumTxt", target = "cneeTelCmmCmpNumTxt")
    @Mapping(source = "cneePrtyDTO.cneePstlAdrsLineOneTxt", target = "cneePstlAdrsLineOneTxt")
    @Mapping(source = "cneePrtyDTO.cneePstcCd", target = "cneePstcCd")
    @Mapping(source = "logsSrvcPrvDTO.logsSrvcPrvPrtyHeadOffId", target = "logsSrvcPrvPrtyHeadOffId")
    @Mapping(source = "logsSrvcPrvDTO.logsSrvcPrvPrtyBrncOffId", target = "logsSrvcPrvPrtyBrncOffId")
    @Mapping(source = "logsSrvcPrvDTO.logsSrvcPrvPrtyNameTxt", target = "logsSrvcPrvPrtyNameTxt")
    @Mapping(source = "logsSrvcPrvDTO.logsSrvcPrvSctSpedOrgId", target = "logsSrvcPrvSctSpedOrgId")
    @Mapping(source = "logsSrvcPrvDTO.logsSrvcPrvSctSpedOrgNameTxt", target = "logsSrvcPrvSctSpedOrgNameTxt")
    @Mapping(source = "logsSrvcPrvDTO.logsSrvcPrvSctPrimCntPersNameTxt", target = "logsSrvcPrvSctPrimCntPersNameTxt")
    @Mapping(source = "logsSrvcPrvDTO.logsSrvcPrvSctTelCmmCmpNumTxt", target = "logsSrvcPrvSctTelCmmCmpNumTxt")
    @Mapping(source = "roadCarrDTO.trspCliPrtyHeadOffId", target = "trspCliPrtyHeadOffId")
    @Mapping(source = "roadCarrDTO.trspCliPrtyBrncOffId", target = "trspCliPrtyBrncOffId")
    @Mapping(source = "roadCarrDTO.trspCliPrtyNameTxt", target = "trspCliPrtyNameTxt")
    @Mapping(source = "roadCarrDTO.roadCarrDepaSpedOrgId", target = "roadCarrDepaSpedOrgId")
    @Mapping(source = "roadCarrDTO.roadCarrDepaSpedOrgNameTxt", target = "roadCarrDepaSpedOrgNameTxt")
    @Mapping(source = "roadCarrDTO.trspCliTelCmmCmpNumTxt", target = "trspCliTelCmmCmpNumTxt")
    @Mapping(source = "roadCarrDTO.roadCarrArrSpedOrgId", target = "roadCarrArrSpedOrgId")
    @Mapping(source = "roadCarrDTO.roadCarrArrSpedOrgNameTxt", target = "roadCarrArrSpedOrgNameTxt")
    @Mapping(source = "fretClimToPrtyDTO.fretClimToPrtyHeadOffId", target = "fretClimToPrtyHeadOffId")
    @Mapping(source = "fretClimToPrtyDTO.fretClimToPrtyBrncOffId", target = "fretClimToPrtyBrncOffId")
    @Mapping(source = "fretClimToPrtyDTO.fretClimToPrtyNameTxt", target = "fretClimToPrtyNameTxt")
    @Mapping(source = "fretClimToPrtyDTO.fretClimToSctSpedOrgId", target = "fretClimToSctSpedOrgId")
    @Mapping(source = "fretClimToPrtyDTO.fretClimToSctSpedOrgNameTxt", target = "fretClimToSctSpedOrgNameTxt")
    TrspPlanLineItem toEntity(TrspIsrDeleteDTO trspIsrDTO, TrspSrvcDeleteDTO trspSrvcDTO,
        TrspVehicleTrmsDTO trspVehicleTrmsDTO, DelInfoDTO delInfoDTO, CnsDTO cnsDTO,
        CnsgPrtyDTO cnsgPrtyDTO, TrspRqrPrtyDTO trspRqrPrtyDTO, CneePrtyDTO cneePrtyDTO, LogsSrvcPrvDTO logsSrvcPrvDTO,
        RoadCarrDTO roadCarrDTO,
        FretClimToPrtyDTO fretClimToPrtyDTO);

}
