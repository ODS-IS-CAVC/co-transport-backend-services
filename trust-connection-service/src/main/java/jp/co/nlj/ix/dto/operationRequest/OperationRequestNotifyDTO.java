package jp.co.nlj.ix.dto.operationRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.dto.operationPlans.request.RoadCarrNotifyDTO;
import lombok.Data;


/**
 * <PRE>
 * 運送計画通知DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class OperationRequestNotifyDTO {

    @JsonProperty("trsp_isr")
    private TrspIsrNotifyDTO trspIsr;

    @JsonProperty("trsp_srvc")
    private TrspSrvcNotifyDTO trspSrvc;

    @JsonProperty("trsp_vehicle_trms")
    private TrspVehicleTrmsNotifyDTO trspVehicleTrms;

    @JsonProperty("del_info")
    private DelInfoNotifyDTO delInfo;

    @JsonProperty("cns")
    private CnsNotifyDTO cns;

    @JsonProperty("cns_line_item")
    private CnsLineItemNotifyDTO cnsLineItem;

    @JsonProperty("cnsg_prty")
    private CnsgPrtyNotifyDTO cnsgPrty;

    @JsonProperty("trsp_rqr_prty")
    private TrspRqrPrtyNotifyDTO trspRqrPrty;

    @JsonProperty("cnee_prty")
    private CneePrtyNotifyDTO cneePrty;

    @JsonProperty("logs_srvc_prv")
    private LogsSrvcPrvNotifyDTO logsSrvcPrv;

    @JsonProperty("road_carr")
    private RoadCarrNotifyDTO roadCarr;

    @JsonProperty("fret_clim_to_prty")
    private FretClimToPrtyNotifyDTO fretClimToPrty;

    @JsonProperty("ship_from_prty")
    private ShipFromPrtyNotifyDTO shipFromPrty;

    @JsonProperty("ship_to_prty")
    private ShipToPrtyNotifyDTO shipToPrty;
    @JsonProperty("shipper_cid")
    private String shipperCid;
    @JsonProperty("from_cid")
    private String fromCid;
    @JsonProperty("to_cid")
    private String toCid;
    @JsonProperty("propose_id")
    private String proposeId;
}
