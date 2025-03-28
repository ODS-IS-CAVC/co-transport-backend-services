package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

/**
 * <PRE>
 * 輸送計画トレーラーDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransportPlanTrailerDTO {

    private Long id;

    @JsonProperty("trailer_order_number")
    private Integer trailerOrderNumber;

    private LocalDate day;

    @JsonProperty("collection_time_from")
    private LocalTime collectionTimeFrom;

    @JsonProperty("collection_time_to")
    private LocalTime collectionTimeTo;
}
