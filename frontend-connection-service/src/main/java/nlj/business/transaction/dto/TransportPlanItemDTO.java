package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.Data;

/**
 * <PRE>
 * 輸送計画項目 DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransportPlanItemDTO {

    private Long id;

    @JsonProperty("trailer_number")
    private Integer trailerNumber;

    private LocalDate day;

    @JsonProperty("collection_time_from")
    private LocalTime collectionTimeFrom;

    @JsonProperty("collection_time_to")
    private LocalTime collectionTimeTo;

    @JsonProperty("transport_plan")
    private TransportPlanDTO transportPlanDTO;

    @JsonProperty("transport_plan_trailer")
    private List<TransportPlanTrailerDTO> transportPlanTrailerDTOS;
}
