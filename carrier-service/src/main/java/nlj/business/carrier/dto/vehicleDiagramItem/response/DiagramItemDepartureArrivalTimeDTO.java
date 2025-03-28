package nlj.business.carrier.dto.vehicleDiagramItem.response;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * 車両図面アイテム出発到着時間DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagramItemDepartureArrivalTimeDTO {

    private LocalDate operationDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private String carrierMail;
}
