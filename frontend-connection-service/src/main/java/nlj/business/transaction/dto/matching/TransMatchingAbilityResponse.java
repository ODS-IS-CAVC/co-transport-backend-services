package nlj.business.transaction.dto.matching;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <PRE>
 * マッチング能力レスポンス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransMatchingAbilityResponse {

    @JsonProperty("temperature_range_1")
    private Integer[] temperatureRange1;

    @JsonProperty("temperature_range_2")
    private Integer[] temperatureRange2;

    @JsonProperty("vehicle_diagram_item_id_1")
    private Long vehicleDiagramItemId1;

    @JsonProperty("vehicle_diagram_item_id_2")
    private Long vehicleDiagramItemId2;

    @JsonProperty("trans_type_1")
    private Integer transType1;

    @JsonProperty("trans_type_2")
    private Integer transType2;

    @JsonProperty("order_id_1")
    private Long orderId1;

    @JsonProperty("order_id_2")
    private Long orderId2;

    @JsonProperty("created_at_1")
    private String createdAt1;

    @JsonProperty("created_at_2")
    private String createdAt2;

    @JsonProperty("matching_id_1")
    private Long matchingId1;

    @JsonProperty("matching_id_2")
    private Long matchingId2;

    @JsonProperty("car_info_id_1")
    private Long carInfoId1;

    @JsonProperty("service_no_1")
    private String serviceNo1;

    @JsonProperty("service_name_1")
    private String serviceName1;

    @JsonProperty("service_strt_date_1")
    private Date serviceStrtDate1;

    @JsonProperty("service_strt_time_1")
    private Time serviceStrtTime1;

    @JsonProperty("service_end_time_1")
    private Time serviceEndTime1;

    @JsonProperty("trailer_license_plt_num_id_1")
    private String trailerLicensePltNumId1;

    @JsonProperty("price_1")
    private BigDecimal price1;

    @JsonProperty("departure_from_1")
    private String departureFrom1;

    @JsonProperty("arrival_to_1")
    private String arrivalTo1;

    @JsonProperty("cut_off_time_1")
    private BigDecimal cutOffTime1;

    @JsonProperty("matching_type_1")
    private Integer matchingType1;

    @JsonProperty("matching_status_1")
    private Integer matchingStatus1;

    @JsonProperty("order_status_1")
    private Integer orderStatus1;

    @JsonProperty("total_count_1")
    private Long totalCount1;

    @JsonProperty("car_info_id_2")
    private Long carInfoId2;

    @JsonProperty("service_no_2")
    private String serviceNo2;

    @JsonProperty("service_name_2")
    private String serviceName2;

    @JsonProperty("trailer_license_plt_num_id_2")
    private String trailerLicensePltNumId2;

    @JsonProperty("price_2")
    private BigDecimal price2;

    @JsonProperty("cut_off_time_2")
    private BigDecimal cutOffTime2;

    @JsonProperty("matching_type_2")
    private Integer matchingType2;

    @JsonProperty("matching_status_2")
    private Integer matchingStatus2;

    @JsonProperty("order_status_2")
    private Integer orderStatus2;

    @JsonProperty("total_count_2")
    private Long totalCount2;

    @JsonProperty("vehicle_avb_resource_id_1")
    private Long vehicleAvbResourceId1;

    @JsonProperty("vehicle_avb_resource_id_2")
    private Long vehicleAvbResourceId2;

    @JsonProperty("trip_name_1")
    private String tripName1;

    @JsonProperty("trip_name_2")
    private String tripName2;

    @JsonProperty("vehicle_name_1")
    private String vehicleName1;

    @JsonProperty("vehicle_name_2")
    private String vehicleName2;

    @JsonProperty("shipper_operator_id_1")
    private String shipperOperatorId1;

    @JsonProperty("carrier_operator_id_1")
    private String carrierOperatorId1;

    @JsonProperty("carrier2_operator_id_1")
    private String carrier2OperatorId1;

    @JsonProperty("shipper_operator_id_2")
    private String shipperOperatorId2;

    @JsonProperty("carrier_operator_id_2")
    private String carrierOperatorId2;

    @JsonProperty("carrier2_operator_id_2")
    private String carrier2OperatorId2;

    @JsonProperty("carrier2_operator_name_1")
    private String carrier2OperatorName1;

    @JsonProperty("carrier2_operator_name_2")
    private String carrier2OperatorName2;

    @JsonProperty("display_order_1")
    private Integer displayOrder1;

    @JsonProperty("display_order_2")
    private Integer displayOrder2;

    @JsonProperty("departure_time_max_1")
    private Time departureTimeMax1;

    @JsonProperty("departure_time_max_2")
    private Time departureTimeMax2;

    @JsonProperty("departure_time_min_1")
    private Time departureTimeMin1;

    @JsonProperty("departure_time_min_2")
    private Time departureTimeMin2;

    @JsonProperty("cut_off_fee_1")
    private BigDecimal cutOffFee1;

    @JsonProperty("cut_off_fee_2")
    private BigDecimal cutOffFee2;

    @JsonProperty("service_strt_date_2")
    private Date serviceStrtDate2;

    @JsonProperty("service_strt_time_2")
    private Time serviceStrtTime2;

    @JsonProperty("service_end_time_2")
    private Time serviceEndTime2;

    @JsonProperty("service_end_date_1")
    private Date serviceEndDate1;

    @JsonProperty("service_end_date_2")
    private Date serviceEndDate2;

    @JsonProperty("is_emergency_1")
    private Integer isEmergency1;

    @JsonProperty("is_emergency_2")
    private Integer isEmergency2;

    @JsonProperty("created_date")
    private String createdDate;

    @JsonProperty("vehicle_avb_resource_item_id_1")
    private Long vehicleAvbResourceItemId1;

    @JsonProperty("vehicle_avb_resource_item_id_2")
    private Long vehicleAvbResourceItemId2;

    @JsonProperty("cns_line_item_by_date_id_1")
    private Long cnsLineItemByDateId1;

    @JsonProperty("cns_line_item_by_date_id_2")
    private Long cnsLineItemByDateId2;
}
