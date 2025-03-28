package nlj.business.transaction.mapper;

import nlj.business.transaction.dto.TransactionCarrierDTO;
import nlj.business.transaction.dto.TransactionDetailResponseDTO;
import org.mapstruct.Mapper;

/**
 * <PRE>
 * 運送業者取引応答マッパー。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Mapper(componentModel = "spring", uses = DateTimeMapper.class)
public interface TransactionDetailResponseMapper {

    TransactionDetailResponseDTO mapToTransactionDetailResponse(TransactionCarrierDTO transactionCarrierDTO);
}
