package jp.co.nlj.ix.dto.transportPlans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * <PRE>
 * 運送計画通知応答DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspPlansNotifyResDTO {

    @JsonProperty("trsp_isr")
    private TrspIsrDTO trspIsr;

    @JsonProperty("trsp_srvc")
    private TrspSrvcDTO trspSrvc;

    @JsonProperty("trsp_vehicle_trms")
    private TrspVehicleTermsDTO trspVehicleTerms;

    @JsonProperty("del_info")
    private DeliveryInfoDTO delInfo;

    @JsonProperty("cns")
    private ConsignmentDTO cns;

    @JsonProperty("cns_line_item")
    private ConsignmentLineItemDTO cnsLineItem;

    @JsonProperty("cnsg_prty")
    private ConsignorPartyDTO cnsgPrty;

    @JsonProperty("trsp_rqr_prty")
    private TransportRequesterPartyDTO trspRqrPrty;

    @JsonProperty("cnee_prty")
    private ConsigneePartyDTO cneePrty;

    @JsonProperty("logs_srvc_prv")
    private LogisticsServiceProviderDTO logsSrvcPrv;

    @JsonProperty("road_carr")
    private RoadCarrierDTO roadCarr;

    @JsonProperty("fret_clim_to_prty")
    private FreightClaimToPartyDTO fretClimToPrty;

    @JsonProperty("ship_from_prty")
    private ShipFromPartyDTO shipFromPrty;

    @JsonProperty("ship_to_prty")
    private ShipToPartyDTO shipToPrty;

    @JsonProperty("shipper_cid")
    private String shipperCid;

    @JsonProperty("carrier_cid")
    private String carrierCid;
}
