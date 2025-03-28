package jp.co.nlj.ix.mapper;

import jp.co.nlj.ix.domain.CnsLineItemByDate;
import jp.co.nlj.ix.dto.CnsLineItemByDateSnapshot;
import org.mapstruct.Mapper;

/**
 * <PRE>
 * コンソールラインアイテムマッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring")
public interface CnsLineItemByDateMapper {

    CnsLineItemByDateSnapshot mapToSnapshot(CnsLineItemByDate cnsLineItem);
}
