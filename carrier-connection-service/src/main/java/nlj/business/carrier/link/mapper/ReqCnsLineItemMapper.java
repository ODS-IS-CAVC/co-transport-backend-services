package nlj.business.carrier.link.mapper;

import nlj.business.carrier.link.constant.MapperConstants;
import nlj.business.carrier.link.domain.ReqCnsLineItem;
import nlj.business.carrier.link.dto.reqTrspPlanLineItem.ReqCnsLineItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * 要求からエンティティへのマップ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Component
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class, BaseMapper.class})
public interface ReqCnsLineItemMapper {

    @Mapping(source = "lineItemNumId", target = "lineItemNumId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "sevOrdNumId", target = "sevOrdNumId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "cnsgCrgItemNumId", target = "cnsgCrgItemNumId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "buyAssiItemCd", target = "buyAssiItemCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "sellAssiItemCd", target = "sellAssiItemCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "wrhsAssiItemCd", target = "wrhsAssiItemCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "itemNameTxt", target = "itemNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "godsIdcsInOtsPckeNameTxt", target = "godsIdcsInOtsPckeNameTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "clsOfKpngTmpCd", target = "clsOfKpngTmpCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "numOfIstdQuan", target = "numOfIstdQuan")
    @Mapping(source = "sevNumUntCd", target = "sevNumUntCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "istdPckeWeigMeas", target = "istdPckeWeigMeas")
    @Mapping(source = "sevWeigUntCd", target = "sevWeigUntCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "istdPckeVolMeas", target = "istdPckeVolMeas")
    @Mapping(source = "sevVolUntCd", target = "sevVolUntCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "istdQuanMeas", target = "istdQuanMeas")
    @Mapping(source = "cnteNumUntCd", target = "cnteNumUntCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "dcpvTrpnPckgTxt", target = "dcpvTrpnPckgTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "pckeFrmCd", target = "pckeFrmCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "pckeFrmNameCd", target = "pckeFrmNameCd", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "crgHndTrmsSpclIsrsTxt", target = "crgHndTrmsSpclIsrsTxt", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "glbRetbAsseId", target = "glbRetbAsseId", qualifiedByName = MapperConstants.TRIM_VALUE)
    @Mapping(source = "totlRtiQuanQuan", target = "totlRtiQuanQuan")
    @Mapping(source = "sellCtrlNumId", target = "sellCtrlNumId", qualifiedByName = MapperConstants.TRIM_VALUE)
    ReqCnsLineItem toEntity(ReqCnsLineItemDTO cnsLineItemDTO);

    @Mapping(source = "lineItemNumId", target = "lineItemNumId")
    @Mapping(source = "sevOrdNumId", target = "sevOrdNumId")
    @Mapping(source = "cnsgCrgItemNumId", target = "cnsgCrgItemNumId")
    @Mapping(source = "buyAssiItemCd", target = "buyAssiItemCd")
    @Mapping(source = "sellAssiItemCd", target = "sellAssiItemCd")
    @Mapping(source = "wrhsAssiItemCd", target = "wrhsAssiItemCd")
    @Mapping(source = "itemNameTxt", target = "itemNameTxt")
    @Mapping(source = "godsIdcsInOtsPckeNameTxt", target = "godsIdcsInOtsPckeNameTxt")
    @Mapping(source = "clsOfKpngTmpCd", target = "clsOfKpngTmpCd")
    @Mapping(source = "numOfIstdQuan", target = "numOfIstdQuan")
    @Mapping(source = "sevNumUntCd", target = "sevNumUntCd")
    @Mapping(source = "istdPckeWeigMeas", target = "istdPckeWeigMeas")
    @Mapping(source = "sevWeigUntCd", target = "sevWeigUntCd")
    @Mapping(source = "istdPckeVolMeas", target = "istdPckeVolMeas")
    @Mapping(source = "sevVolUntCd", target = "sevVolUntCd")
    @Mapping(source = "istdQuanMeas", target = "istdQuanMeas")
    @Mapping(source = "cnteNumUntCd", target = "cnteNumUntCd")
    @Mapping(source = "dcpvTrpnPckgTxt", target = "dcpvTrpnPckgTxt")
    @Mapping(source = "pckeFrmCd", target = "pckeFrmCd")
    @Mapping(source = "pckeFrmNameCd", target = "pckeFrmNameCd")
    @Mapping(source = "crgHndTrmsSpclIsrsTxt", target = "crgHndTrmsSpclIsrsTxt")
    @Mapping(source = "glbRetbAsseId", target = "glbRetbAsseId")
    @Mapping(source = "totlRtiQuanQuan", target = "totlRtiQuanQuan")
    @Mapping(source = "sellCtrlNumId", target = "sellCtrlNumId")
    ReqCnsLineItemDTO toDTO(ReqCnsLineItem cnsLineItem);

}
