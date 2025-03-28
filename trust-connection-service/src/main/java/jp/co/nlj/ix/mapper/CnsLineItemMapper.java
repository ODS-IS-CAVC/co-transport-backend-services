package jp.co.nlj.ix.mapper;

import jp.co.nlj.ix.domain.CnsLineItem;
import jp.co.nlj.ix.dto.trspPlanLineItem.CnsLineItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

/**
 * <PRE>
 * コンソールラインアイテムマッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Component
@Mapper(componentModel = "spring")
public interface CnsLineItemMapper {

    @Mapping(source = "lineItemNumId", target = "lineItemNumId")
    @Mapping(source = "sevOrdNumId", target = "sevOrdNumId")
    @Mapping(source = "cnsgCrgItemNumId", target = "cnsgCrgItemNumId")
    @Mapping(source = "buyAssiItemCd", target = "buyAssiItemCd")
    @Mapping(source = "sellAssiItemCd", target = "sellAssiItemCd")
    @Mapping(source = "wrhsAssiItemCd", target = "wrhsAssiItemCd")
    @Mapping(source = "itemNameTxt", target = "itemNameTxt")
    @Mapping(source = "godsIdcsInOtsPckeNameTxt", target = "godsIdcsInOtsPckeNameTxt")
    @Mapping(source = "numOfIstdUntlQuan", target = "numOfIstdUntlQuan")
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
    @Mapping(source = "chrgOfPckeCtrlNumUntAmnt", target = "chrgOfPckeCtrlNumUntAmnt")
    CnsLineItem toEntity(CnsLineItemDTO cnsLineItemDTO);

    @Mapping(source = "lineItemNumId", target = "lineItemNumId")
    @Mapping(source = "sevOrdNumId", target = "sevOrdNumId")
    @Mapping(source = "cnsgCrgItemNumId", target = "cnsgCrgItemNumId")
    @Mapping(source = "buyAssiItemCd", target = "buyAssiItemCd")
    @Mapping(source = "sellAssiItemCd", target = "sellAssiItemCd")
    @Mapping(source = "wrhsAssiItemCd", target = "wrhsAssiItemCd")
    @Mapping(source = "itemNameTxt", target = "itemNameTxt")
    @Mapping(source = "godsIdcsInOtsPckeNameTxt", target = "godsIdcsInOtsPckeNameTxt")
    @Mapping(source = "numOfIstdUntlQuan", target = "numOfIstdUntlQuan")
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
    @Mapping(source = "chrgOfPckeCtrlNumUntAmnt", target = "chrgOfPckeCtrlNumUntAmnt")
    CnsLineItemDTO toDTO(CnsLineItem cnsLineItem);

}
