package nlj.business.transaction.dto.trspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 運送サービスDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrspSrvcDTO {

    @JsonProperty("service_no")
    private String serviceNo; // 便・ダイヤ番号

    @JsonProperty("service_name")
    private String serviceName; // 便・ダイヤ名称

    @JsonProperty("service_strt_date")
    private String serviceStrtDate; // 便の運行日

    @JsonProperty("service_strt_time")
    private String serviceStrtTime; // 便の運行時刻

    @JsonProperty("service_end_date")
    private String serviceEndDate; // 便の運行終了日

    @JsonProperty("service_end_time")
    private String serviceEndTime; // 便の運行終了時刻

}
