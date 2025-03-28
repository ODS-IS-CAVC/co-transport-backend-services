package nlj.business.transaction.dto.trip.transportPlans.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.transaction.dto.trip.transportPlans.ConsigneePartyDTO;
import nlj.business.transaction.dto.trip.transportPlans.ConsignmentDTO;
import nlj.business.transaction.dto.trip.transportPlans.ConsignmentLineItemDTO;
import nlj.business.transaction.dto.trip.transportPlans.ConsignorPartyDTO;
import nlj.business.transaction.dto.trip.transportPlans.DeliveryInfoDTO;
import nlj.business.transaction.dto.trip.transportPlans.FreightClaimToPartyDTO;
import nlj.business.transaction.dto.trip.transportPlans.LogisticsServiceProviderDTO;
import nlj.business.transaction.dto.trip.transportPlans.RoadCarrierDTO;
import nlj.business.transaction.dto.trip.transportPlans.ShipFromPartyDTO;
import nlj.business.transaction.dto.trip.transportPlans.ShipToPartyDTO;
import nlj.business.transaction.dto.trip.transportPlans.TransportRequesterPartyDTO;
import nlj.business.transaction.dto.trip.transportPlans.TrspIsrDTO;
import nlj.business.transaction.dto.trip.transportPlans.TrspSrvcDTO;
import nlj.business.transaction.dto.trip.transportPlans.TrspVehicleTermsDTO;

/**
 * <PRE>
 * 運送業者プラン通知リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspPlansNotifyRequestDTO {

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
}
