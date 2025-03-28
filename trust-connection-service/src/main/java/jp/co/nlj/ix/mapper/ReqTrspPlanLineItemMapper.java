package jp.co.nlj.ix.mapper;

import jp.co.nlj.ix.constant.MapperConstants;
import jp.co.nlj.ix.domain.ReqTrspPlanLineItem;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqCnsDTO;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqDelInfoDTO;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqTrspIsrDTO;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqTrspRqrPrtyDTO;
import jp.co.nlj.ix.dto.reqTrspPlanLineItem.ReqTrspSrvcDTO;
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
public interface ReqTrspPlanLineItemMapper {

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
    ReqTrspPlanLineItem toEntity(ReqTrspIsrDTO trspIsrDTO, ReqTrspSrvcDTO trspSrvcDTO,
        TrspVehicleTrmsDTO trspVehicleTrmsDTO, ReqDelInfoDTO delInfoDTO, ReqCnsDTO cnsDTO,
        CnsgPrtyDTO cnsgPrtyDTO, ReqTrspRqrPrtyDTO trspRqrPrtyDTO, CneePrtyDTO cneePrtyDTO,
        LogsSrvcPrvDTO logsSrvcPrvDTO, RoadCarrDTO roadCarrDTO,
        FretClimToPrtyDTO fretClimToPrtyDTO);

    @Mapping(source = "trspInstructionId", target = "trspInstructionId")
    @Mapping(source = "trspInstructionDateSubmDttm", target = "trspInstructionDateSubmDttm", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(source = "invNumId", target = "invNumId")
    @Mapping(source = "cmnInvNumId", target = "cmnInvNumId")
    ReqTrspIsrDTO toTrspIsrDTO(ReqTrspPlanLineItem reqTrspPlanLineItem);

    @Mapping(source = "freightRate", target = "freightRate")
    @Mapping(source = "brtRsvNecCd", target = "brtRsvNecCd")
    @Mapping(source = "brtRsvStasCd", target = "brtRsvStasCd")
    @Mapping(source = "brtRsvNumId", target = "brtRsvNumId")
    @Mapping(source = "trspMeansTypCd", target = "trspMeansTypCd")
    @Mapping(source = "trspSrvcTypCd", target = "trspSrvcTypCd")
    @Mapping(source = "roadCarrSrvcTypCd", target = "roadCarrSrvcTypCd")
    @Mapping(source = "trspRootPrioCd", target = "trspRootPrioCd")
    @Mapping(source = "carClsPrioCd", target = "carClsPrioCd")
    @Mapping(source = "clsOfCargInSrvcRqrmCd", target = "clsOfCargInSrvcRqrmCd")
    @Mapping(source = "clsOfPkgUpSrvcRqrmCd", target = "clsOfPkgUpSrvcRqrmCd")
    @Mapping(source = "pyrClsSrvcRqrmCd", target = "pyrClsSrvcRqrmCd")
    @Mapping(source = "trmsOfMixLoadCndCd", target = "trmsOfMixLoadCndCd")
    @Mapping(source = "dsedCllFromDate", target = "dsedCllFromDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(source = "dsedCllToDate", target = "dsedCllToDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(source = "dsedCllFromTime", target = "dsedCllFromTime", qualifiedByName = MapperConstants.TIME_TO_STRING)
    @Mapping(source = "dsedCllToTime", target = "dsedCllToTime", qualifiedByName = MapperConstants.TIME_TO_STRING)
    @Mapping(source = "dsedCllTimeTrmsSrvcRqrmCd", target = "dsedCllTimeTrmsSrvcRqrmCd")
    @Mapping(source = "apedArrFromDate", target = "apedArrFromDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(source = "apedArrToDate", target = "apedArrToDate", qualifiedByName = MapperConstants.DATE_TO_STRING)
    @Mapping(source = "apedArrFromTimePrfmDttm", target = "apedArrFromTimePrfmDttm", qualifiedByName = MapperConstants.TIME_TO_STRING)
    @Mapping(source = "apedArrToTimePrfmDttm", target = "apedArrToTimePrfmDttm", qualifiedByName = MapperConstants.TIME_TO_STRING)
    @Mapping(source = "apedArrTimeTrmsSrvcRqrmCd", target = "apedArrTimeTrmsSrvcRqrmCd")
    @Mapping(source = "trmsOfMixLoadTxt", target = "trmsOfMixLoadTxt")
    @Mapping(source = "trspSrvcNoteOneTxt", target = "trspSrvcNoteOneTxt")
    @Mapping(source = "trspSrvcNoteTwoTxt", target = "trspSrvcNoteTwoTxt")
    ReqTrspSrvcDTO toTrspSrvcDTO(ReqTrspPlanLineItem reqTrspPlanLineItem);

    @Mapping(source = "carClsOfSizeCd", target = "carClsOfSizeCd")
    @Mapping(source = "carClsOfShpCd", target = "carClsOfShpCd")
    @Mapping(source = "carClsOfTlgLftrExstCd", target = "carClsOfTlgLftrExstCd")
    @Mapping(source = "carClsOfWingBodyExstCd", target = "carClsOfWingBodyExstCd")
    @Mapping(source = "carClsOfRfgExstCd", target = "carClsOfRfgExstCd")
    @Mapping(source = "trmsOfLwrTmpMeas", target = "trmsOfLwrTmpMeas")
    @Mapping(source = "trmsOfUppTmpMeas", target = "trmsOfUppTmpMeas")
    @Mapping(source = "carClsOfCrnExstCd", target = "carClsOfCrnExstCd")
    @Mapping(source = "carRmkAboutEqpmTxt", target = "carRmkAboutEqpmTxt")
    TrspVehicleTrmsDTO toTrspVehicleTrmsDTO(ReqTrspPlanLineItem reqTrspPlanLineItem);

    @Mapping(source = "delNoteId", target = "delNoteId")
    @Mapping(source = "delCtrlNumId", target = "delCtrlNumId")
    @Mapping(source = "shpmNumId", target = "shpmNumId")
    ReqDelInfoDTO toDelInfoDTO(ReqTrspPlanLineItem reqTrspPlanLineItem);

    @Mapping(source = "istdTotlPcksQuan", target = "istdTotlPcksQuan")
    @Mapping(source = "numUntCd", target = "numUntCd")
    @Mapping(source = "istdTotlWeigMeas", target = "istdTotlWeigMeas")
    @Mapping(source = "weigUntCd", target = "weigUntCd")
    @Mapping(source = "istdTotlVolMeas", target = "istdTotlVolMeas")
    @Mapping(source = "volUntCd", target = "volUntCd")
    ReqCnsDTO toCnsDTO(ReqTrspPlanLineItem reqTrspPlanLineItem);

    @Mapping(source = "cnsgPrtyHeadOffId", target = "cnsgPrtyHeadOffId")
    @Mapping(source = "cnsgPrtyBrncOffId", target = "cnsgPrtyBrncOffId")
    @Mapping(source = "cnsgPrtyNameTxt", target = "cnsgPrtyNameTxt")
    @Mapping(source = "cnsgSctSpedOrgId", target = "cnsgSctSpedOrgId")
    @Mapping(source = "cnsgSctSpedOrgNameTxt", target = "cnsgSctSpedOrgNameTxt")
    @Mapping(source = "cnsgTelCmmCmpNumTxt", target = "cnsgTelCmmCmpNumTxt")
    @Mapping(source = "cnsgPstlAdrsLineOneTxt", target = "cnsgPstlAdrsLineOneTxt")
    @Mapping(source = "cnsgPstcCd", target = "cnsgPstcCd")
    CnsgPrtyDTO toCnsgPrtyDTO(ReqTrspPlanLineItem reqTrspPlanLineItem);

    @Mapping(source = "trspRqrPrtyHeadOffId", target = "trspRqrPrtyHeadOffId")
    @Mapping(source = "trspRqrPrtyBrncOffId", target = "trspRqrPrtyBrncOffId")
    @Mapping(source = "trspRqrPrtyNameTxt", target = "trspRqrPrtyNameTxt")
    @Mapping(source = "trspRqrSctSpedOrgId", target = "trspRqrSctSpedOrgId")
    @Mapping(source = "trspRqrSctSpedOrgNameTxt", target = "trspRqrSctSpedOrgNameTxt")
    @Mapping(source = "trspRqrSctTelCmmCmpNumTxt", target = "trspRqrSctTelCmmCmpNumTxt")
    @Mapping(source = "trspRqrPstlAdrsLineOneTxt", target = "trspRqrPstlAdrsLineOneTxt")
    @Mapping(source = "trspRqrPstcCd", target = "trspRqrPstcCd")
    ReqTrspRqrPrtyDTO toTrspRqrPrtyDTO(ReqTrspPlanLineItem reqTrspPlanLineItem);

    @Mapping(source = "cneePrtyHeadOffId", target = "cneePrtyHeadOffId")
    @Mapping(source = "cneePrtyBrncOffId", target = "cneePrtyBrncOffId")
    @Mapping(source = "cneePrtyNameTxt", target = "cneePrtyNameTxt")
    @Mapping(source = "cneeSctId", target = "cneeSctId")
    @Mapping(source = "cneeSctNameTxt", target = "cneeSctNameTxt")
    @Mapping(source = "cneePrimCntPersNameTxt", target = "cneePrimCntPersNameTxt")
    @Mapping(source = "cneeTelCmmCmpNumTxt", target = "cneeTelCmmCmpNumTxt")
    @Mapping(source = "cneePstlAdrsLineOneTxt", target = "cneePstlAdrsLineOneTxt")
    @Mapping(source = "cneePstcCd", target = "cneePstcCd")
    CneePrtyDTO toCneePrtyDTO(ReqTrspPlanLineItem reqTrspPlanLineItem);

    @Mapping(source = "logsSrvcPrvPrtyHeadOffId", target = "logsSrvcPrvPrtyHeadOffId")
    @Mapping(source = "logsSrvcPrvPrtyBrncOffId", target = "logsSrvcPrvPrtyBrncOffId")
    @Mapping(source = "logsSrvcPrvPrtyNameTxt", target = "logsSrvcPrvPrtyNameTxt")
    @Mapping(source = "logsSrvcPrvSctSpedOrgId", target = "logsSrvcPrvSctSpedOrgId")
    @Mapping(source = "logsSrvcPrvSctSpedOrgNameTxt", target = "logsSrvcPrvSctSpedOrgNameTxt")
    @Mapping(source = "logsSrvcPrvSctPrimCntPersNameTxt", target = "logsSrvcPrvSctPrimCntPersNameTxt")
    @Mapping(source = "logsSrvcPrvSctTelCmmCmpNumTxt", target = "logsSrvcPrvSctTelCmmCmpNumTxt")
    LogsSrvcPrvDTO toLogsSrvcPrvDTO(ReqTrspPlanLineItem reqTrspPlanLineItem);

    @Mapping(source = "trspCliPrtyHeadOffId", target = "trspCliPrtyHeadOffId")
    @Mapping(source = "trspCliPrtyBrncOffId", target = "trspCliPrtyBrncOffId")
    @Mapping(source = "trspCliPrtyNameTxt", target = "trspCliPrtyNameTxt")
    @Mapping(source = "roadCarrDepaSpedOrgId", target = "roadCarrDepaSpedOrgId")
    @Mapping(source = "roadCarrDepaSpedOrgNameTxt", target = "roadCarrDepaSpedOrgNameTxt")
    @Mapping(source = "trspCliTelCmmCmpNumTxt", target = "trspCliTelCmmCmpNumTxt")
    @Mapping(source = "roadCarrArrSpedOrgId", target = "roadCarrArrSpedOrgId")
    @Mapping(source = "roadCarrArrSpedOrgNameTxt", target = "roadCarrArrSpedOrgNameTxt")
    RoadCarrDTO toRoadCarrDTO(ReqTrspPlanLineItem reqTrspPlanLineItem);

    @Mapping(source = "fretClimToPrtyHeadOffId", target = "fretClimToPrtyHeadOffId")
    @Mapping(source = "fretClimToPrtyBrncOffId", target = "fretClimToPrtyBrncOffId")
    @Mapping(source = "fretClimToPrtyNameTxt", target = "fretClimToPrtyNameTxt")
    @Mapping(source = "fretClimToSctSpedOrgId", target = "fretClimToSctSpedOrgId")
    @Mapping(source = "fretClimToSctSpedOrgNameTxt", target = "fretClimToSctSpedOrgNameTxt")
    FretClimToPrtyDTO toFretClimToPrtyDTO(ReqTrspPlanLineItem reqTrspPlanLineItem);

}
