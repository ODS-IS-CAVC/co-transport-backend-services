package jp.co.nlj.ix.mapper;

import jp.co.nlj.ix.constant.MapperConstants;
import jp.co.nlj.ix.domain.ReqTrspPlanLineItem;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqCnsDTO;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqDelInfoDTO;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqTrspIsrDeleteDTO;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqTrspRqrPrtyDTO;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqTrspSrvcDeleteDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.CneePrtyDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.CnsgPrtyDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.FretClimToPrtyDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.LogsSrvcPrvDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.RoadCarrDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.TrspVehicleTrmsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * 要求からエンティティへのマップパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Component
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class, BaseMapper.class})
public interface ReqTrspPlanLineItemDeleteMapper {

    @Mapping(source = "trspIsrDTO.trspInstructionId", target = "trspInstructionId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspIsrDTO.trspInstructionDateSubmDttm", target = "trspInstructionDateSubmDttm", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "trspIsrDTO.invNumId", target = "invNumId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspIsrDTO.cmnInvNumId", target = "cmnInvNumId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspSrvcDTO.freightRate", target = "freightRate")
    @Mapping(source = "trspSrvcDTO.brtRsvNecCd", target = "brtRsvNecCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspSrvcDTO.brtRsvStasCd", target = "brtRsvStasCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspSrvcDTO.brtRsvNumId", target = "brtRsvNumId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspSrvcDTO.trspMeansTypCd", target = "trspMeansTypCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspSrvcDTO.trspSrvcTypCd", target = "trspSrvcTypCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspSrvcDTO.roadCarrSrvcTypCd", target = "roadCarrSrvcTypCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspSrvcDTO.trspRootPrioCd", target = "trspRootPrioCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspSrvcDTO.carClsPrioCd", target = "carClsPrioCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspSrvcDTO.clsOfCargInSrvcRqrmCd", target = "clsOfCargInSrvcRqrmCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspSrvcDTO.clsOfPkgUpSrvcRqrmCd", target = "clsOfPkgUpSrvcRqrmCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspSrvcDTO.pyrClsSrvcRqrmCd", target = "pyrClsSrvcRqrmCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspSrvcDTO.trmsOfMixLoadCndCd", target = "trmsOfMixLoadCndCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspSrvcDTO.dsedCllFromDate", target = "dsedCllFromDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "trspSrvcDTO.dsedCllToDate", target = "dsedCllToDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "trspSrvcDTO.dsedCllFromTime", target = "dsedCllFromTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(source = "trspSrvcDTO.dsedCllToTime", target = "dsedCllToTime", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(source = "trspSrvcDTO.dsedCllTimeTrmsSrvcRqrmCd", target = "dsedCllTimeTrmsSrvcRqrmCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspSrvcDTO.apedArrFromDate", target = "apedArrFromDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "trspSrvcDTO.apedArrToDate", target = "apedArrToDate", qualifiedByName = MapperConstants.STRING_TO_LOCAL_DATE)
    @Mapping(source = "trspSrvcDTO.apedArrFromTimePrfmDttm", target = "apedArrFromTimePrfmDttm", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(source = "trspSrvcDTO.apedArrToTimePrfmDttm", target = "apedArrToTimePrfmDttm", qualifiedByName = MapperConstants.STRING_TO_LOCAL_TIME)
    @Mapping(source = "trspSrvcDTO.apedArrTimeTrmsSrvcRqrmCd", target = "apedArrTimeTrmsSrvcRqrmCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspSrvcDTO.trmsOfMixLoadTxt", target = "trmsOfMixLoadTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspSrvcDTO.trspSrvcNoteOneTxt", target = "trspSrvcNoteOneTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspSrvcDTO.trspSrvcNoteTwoTxt", target = "trspSrvcNoteTwoTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspVehicleTrmsDTO.carClsOfSizeCd", target = "carClsOfSizeCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspVehicleTrmsDTO.carClsOfShpCd", target = "carClsOfShpCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspVehicleTrmsDTO.carClsOfTlgLftrExstCd", target = "carClsOfTlgLftrExstCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspVehicleTrmsDTO.carClsOfWingBodyExstCd", target = "carClsOfWingBodyExstCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspVehicleTrmsDTO.carClsOfRfgExstCd", target = "carClsOfRfgExstCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspVehicleTrmsDTO.trmsOfLwrTmpMeas", target = "trmsOfLwrTmpMeas")
    @Mapping(source = "trspVehicleTrmsDTO.trmsOfUppTmpMeas", target = "trmsOfUppTmpMeas")
    @Mapping(source = "trspVehicleTrmsDTO.carClsOfCrnExstCd", target = "carClsOfCrnExstCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspVehicleTrmsDTO.carRmkAboutEqpmTxt", target = "carRmkAboutEqpmTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "delInfoDTO.delNoteId", target = "delNoteId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "delInfoDTO.delCtrlNumId", target = "delCtrlNumId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "delInfoDTO.shpmNumId", target = "shpmNumId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cnsDTO.istdTotlPcksQuan", target = "istdTotlPcksQuan")
    @Mapping(source = "cnsDTO.numUntCd", target = "numUntCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cnsDTO.istdTotlWeigMeas", target = "istdTotlWeigMeas")
    @Mapping(source = "cnsDTO.weigUntCd", target = "weigUntCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cnsDTO.istdTotlVolMeas", target = "istdTotlVolMeas")
    @Mapping(source = "cnsDTO.volUntCd", target = "volUntCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cnsgPrtyDTO.cnsgPrtyHeadOffId", target = "cnsgPrtyHeadOffId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cnsgPrtyDTO.cnsgPrtyBrncOffId", target = "cnsgPrtyBrncOffId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cnsgPrtyDTO.cnsgPrtyNameTxt", target = "cnsgPrtyNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cnsgPrtyDTO.cnsgSctSpedOrgId", target = "cnsgSctSpedOrgId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cnsgPrtyDTO.cnsgSctSpedOrgNameTxt", target = "cnsgSctSpedOrgNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cnsgPrtyDTO.cnsgTelCmmCmpNumTxt", target = "cnsgTelCmmCmpNumTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cnsgPrtyDTO.cnsgPstlAdrsLineOneTxt", target = "cnsgPstlAdrsLineOneTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cnsgPrtyDTO.cnsgPstcCd", target = "cnsgPstcCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspRqrPrtyDTO.trspRqrPrtyHeadOffId", target = "trspRqrPrtyHeadOffId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspRqrPrtyDTO.trspRqrPrtyBrncOffId", target = "trspRqrPrtyBrncOffId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspRqrPrtyDTO.trspRqrPrtyNameTxt", target = "trspRqrPrtyNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspRqrPrtyDTO.trspRqrSctSpedOrgId", target = "trspRqrSctSpedOrgId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspRqrPrtyDTO.trspRqrSctSpedOrgNameTxt", target = "trspRqrSctSpedOrgNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspRqrPrtyDTO.trspRqrSctTelCmmCmpNumTxt", target = "trspRqrSctTelCmmCmpNumTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspRqrPrtyDTO.trspRqrPstlAdrsLineOneTxt", target = "trspRqrPstlAdrsLineOneTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "trspRqrPrtyDTO.trspRqrPstcCd", target = "trspRqrPstcCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cneePrtyDTO.cneePrtyHeadOffId", target = "cneePrtyHeadOffId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cneePrtyDTO.cneePrtyBrncOffId", target = "cneePrtyBrncOffId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cneePrtyDTO.cneePrtyNameTxt", target = "cneePrtyNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cneePrtyDTO.cneeSctId", target = "cneeSctId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cneePrtyDTO.cneeSctNameTxt", target = "cneeSctNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cneePrtyDTO.cneePrimCntPersNameTxt", target = "cneePrimCntPersNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cneePrtyDTO.cneeTelCmmCmpNumTxt", target = "cneeTelCmmCmpNumTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cneePrtyDTO.cneePstlAdrsLineOneTxt", target = "cneePstlAdrsLineOneTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cneePrtyDTO.cneePstcCd", target = "cneePstcCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "logsSrvcPrvDTO.logsSrvcPrvPrtyHeadOffId", target = "logsSrvcPrvPrtyHeadOffId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "logsSrvcPrvDTO.logsSrvcPrvPrtyBrncOffId", target = "logsSrvcPrvPrtyBrncOffId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "logsSrvcPrvDTO.logsSrvcPrvPrtyNameTxt", target = "logsSrvcPrvPrtyNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "logsSrvcPrvDTO.logsSrvcPrvSctSpedOrgId", target = "logsSrvcPrvSctSpedOrgId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "logsSrvcPrvDTO.logsSrvcPrvSctSpedOrgNameTxt", target = "logsSrvcPrvSctSpedOrgNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "logsSrvcPrvDTO.logsSrvcPrvSctPrimCntPersNameTxt", target = "logsSrvcPrvSctPrimCntPersNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "logsSrvcPrvDTO.logsSrvcPrvSctTelCmmCmpNumTxt", target = "logsSrvcPrvSctTelCmmCmpNumTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "roadCarrDTO.trspCliPrtyHeadOffId", target = "trspCliPrtyHeadOffId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "roadCarrDTO.trspCliPrtyBrncOffId", target = "trspCliPrtyBrncOffId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "roadCarrDTO.trspCliPrtyNameTxt", target = "trspCliPrtyNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "roadCarrDTO.roadCarrDepaSpedOrgId", target = "roadCarrDepaSpedOrgId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "roadCarrDTO.roadCarrDepaSpedOrgNameTxt", target = "roadCarrDepaSpedOrgNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "roadCarrDTO.trspCliTelCmmCmpNumTxt", target = "trspCliTelCmmCmpNumTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "roadCarrDTO.roadCarrArrSpedOrgId", target = "roadCarrArrSpedOrgId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "roadCarrDTO.roadCarrArrSpedOrgNameTxt", target = "roadCarrArrSpedOrgNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "fretClimToPrtyDTO.fretClimToPrtyHeadOffId", target = "fretClimToPrtyHeadOffId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "fretClimToPrtyDTO.fretClimToPrtyBrncOffId", target = "fretClimToPrtyBrncOffId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "fretClimToPrtyDTO.fretClimToPrtyNameTxt", target = "fretClimToPrtyNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "fretClimToPrtyDTO.fretClimToSctSpedOrgId", target = "fretClimToSctSpedOrgId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "fretClimToPrtyDTO.fretClimToSctSpedOrgNameTxt", target = "fretClimToSctSpedOrgNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    ReqTrspPlanLineItem toEntity(ReqTrspIsrDeleteDTO trspIsrDTO, ReqTrspSrvcDeleteDTO trspSrvcDTO,
        TrspVehicleTrmsDTO trspVehicleTrmsDTO, ReqDelInfoDTO delInfoDTO, ReqCnsDTO cnsDTO,
        CnsgPrtyDTO cnsgPrtyDTO, ReqTrspRqrPrtyDTO trspRqrPrtyDTO, CneePrtyDTO cneePrtyDTO,
        LogsSrvcPrvDTO logsSrvcPrvDTO, RoadCarrDTO roadCarrDTO,
        FretClimToPrtyDTO fretClimToPrtyDTO);
}
