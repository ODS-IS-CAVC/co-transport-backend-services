package nlj.business.shipper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.shipper.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送会社法人DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ShipperCorporateDTO {

    @JsonProperty("corporate_code")
    @ValidateField(maxLength = 13)
    private String corporateCode;

    @JsonProperty("corporate_name")
    @ValidateField(maxLength = 320)
    private String corporateName;

    @JsonProperty("postal_code")
    @ValidateField(maxLength = 7)
    private String postalCode;

    @JsonProperty("address_1")
    @ValidateField(maxLength = 320)
    private String address1;

    @JsonProperty("address_2")
    @ValidateField(maxLength = 320)
    private String address2;

    @JsonProperty("phone_number")
    @ValidateField(maxLength = 20)
    private String phoneNumber;

    @JsonProperty("manager_name")
    @ValidateField(maxLength = 100)
    private String managerName;

    @JsonProperty("image_logo")
    @ValidateField(maxLength = 500)
    private String imageLogo;

    @JsonProperty("others")
    @ValidateField(maxLength = 500)
    private String others;

    @JsonProperty("status")
    private Integer status;
}