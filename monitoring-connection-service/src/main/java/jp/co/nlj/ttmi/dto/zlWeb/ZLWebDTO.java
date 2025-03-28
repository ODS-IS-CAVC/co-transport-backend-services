package jp.co.nlj.ttmi.dto.zlWeb;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import jp.co.nlj.ttmi.dto.zlWeb.item.ZLWebItemDTO;
import lombok.Data;

/**
 * <PRE>
 * ZLWebDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class ZLWebDTO {

    private String managementNumber;
    private String shipperOperatorCode;
    private String shipperOperatorName;
    private String shipperManagerName;
    private String carrierOperatorCode;
    private String carrierOperatorName;
    private String carrierManagerName;
    private Long departureFromCode;
    private String departureFromName;
    private String departureFromPostalCode;
    private String departureFromLatitude;
    private String departureFromLongitude;
    private Long arrivalToCode;
    private String arrivalToName;
    private String arrivalToPostalCode;
    private String arrivalToLatitude;
    private String arrivalToLongitude;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate dropOffDate;
    private String dropOffTimeFrom;
    private String dropOffTimeTo;
    private BigDecimal trailerNumber;
    private String tripName;
    private BigDecimal totalLength;
    private BigDecimal totalWidth;
    private BigDecimal totalHeight;
    private BigDecimal weight;
    private String vehicleNumber;
    private Integer vehicleType;
    private Long plUnit;
    private List<ZLWebItemDTO> zlWebItemDTOList;
    private String shipmentDateFrom;
    private String shipmentDateTo;
}
