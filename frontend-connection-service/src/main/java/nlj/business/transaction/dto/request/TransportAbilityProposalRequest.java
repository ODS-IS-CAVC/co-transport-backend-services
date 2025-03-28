package nlj.business.transaction.dto.request;

import jakarta.validation.constraints.Digits;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nlj.business.transaction.aop.proxy.ValidateField;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportAbilityProposalRequest extends CommonPagingSearch {

    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0, notEqualField = "arrId")
    private String depId;

    @ValidateField(notNull = true, precision = Integer.MAX_VALUE, scale = 0)
    private String arrId;

    @ValidateField(dateFormat = "yyyyMMdd")
    private String collectionDate;

    @ValidateField(timeFormat = "HH:mm:ss")
    private String collectTimeFrom;

    @ValidateField(timeFormat = "HH:mm:ss")
    private String collectTimeTo;

    @Digits(integer = 10, fraction = 0) // 10 digits, 0 decimal places
    private BigDecimal pricePerUnit;
}
