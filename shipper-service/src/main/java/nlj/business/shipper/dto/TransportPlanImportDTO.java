package nlj.business.shipper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.shipper.aop.proxy.ValidateField;

/**
 * <PRE>
 * 輸送計画インポートDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransportPlanImportDTO {

    @JsonProperty("file_path")
    @ValidateField(notNull = true, maxLength = 500)
    private String filePath;

    @JsonProperty("number_success")
    private Integer numberSuccess;

    @JsonProperty("number_failure")
    private Integer numberFailure;

    @JsonProperty("status")
    private Integer status;
}