package nlj.business.transaction.dto.matching;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;
import java.sql.Time;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <PRE>
 * マッチング能力販売ヘッダレスポンス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@AllArgsConstructor
public class TransMatchingAbilitySaleHeadResponse {

    @JsonProperty("service_no")
    private String serviceNo;

    @JsonProperty("service_name")
    private String serviceName;

    @JsonProperty("service_strt_date")
    private Date serviceStrtDate;

    @JsonProperty("service_strt_time")
    private Time serviceStrtTime;

    @JsonProperty("service_end_time")
    private Time serviceEndTime;

    @JsonProperty("departure_from")
    private String departureFrom;

    @JsonProperty("arrival_to")
    private String arrivalTo;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("truck")
    private HeadResponse headResponse;

    @JsonProperty("trailer_1")
    private TransMatchingTrailerResponse trailer1;

    @JsonProperty("trailer_2")
    private TransMatchingTrailerResponse trailer2;
}
