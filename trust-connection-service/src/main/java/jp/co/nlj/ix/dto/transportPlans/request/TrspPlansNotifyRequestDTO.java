package jp.co.nlj.ix.dto.transportPlans.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.dto.transportPlans.ConsigneePartyDTO;
import jp.co.nlj.ix.dto.transportPlans.ConsignmentDTO;
import jp.co.nlj.ix.dto.transportPlans.ConsignmentLineItemDTO;
import jp.co.nlj.ix.dto.transportPlans.ConsignorPartyDTO;
import jp.co.nlj.ix.dto.transportPlans.DeliveryInfoDTO;
import jp.co.nlj.ix.dto.transportPlans.FreightClaimToPartyDTO;
import jp.co.nlj.ix.dto.transportPlans.LogisticsServiceProviderDTO;
import jp.co.nlj.ix.dto.transportPlans.RoadCarrierDTO;
import jp.co.nlj.ix.dto.transportPlans.ShipFromPartyDTO;
import jp.co.nlj.ix.dto.transportPlans.ShipToPartyDTO;
import jp.co.nlj.ix.dto.transportPlans.TransportRequesterPartyDTO;
import jp.co.nlj.ix.dto.transportPlans.TrspIsrDTO;
import jp.co.nlj.ix.dto.transportPlans.TrspSrvcDTO;
import jp.co.nlj.ix.dto.transportPlans.TrspVehicleTermsDTO;
import lombok.Data;

/**
 * <PRE>
 * 輸送計画通知リクエストDTO。<BR>
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
