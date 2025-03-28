package jp.co.nlj.ix.dto.operationRequest.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ix.dto.operationPlans.request.RoadCarrNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.CneePrtyNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.CnsLineItemNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.CnsNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.CnsgPrtyNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.DelInfoNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.FretClimToPrtyNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.LogsSrvcPrvNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.ShipFromPrtyNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.ShipToPrtyNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.TrspIsrNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.TrspRqrPrtyNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.TrspSrvcNotifyDTO;
import jp.co.nlj.ix.dto.operationRequest.TrspVehicleTrmsNotifyDTO;
import lombok.Data;


/**
 * <PRE>
 * 運送計画通知リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class OperationRequestNotifyRequest {

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
}
