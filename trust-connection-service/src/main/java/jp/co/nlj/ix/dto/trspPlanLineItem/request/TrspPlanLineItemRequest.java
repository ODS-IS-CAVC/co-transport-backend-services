package jp.co.nlj.ix.dto.trspPlanLineItem.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import jp.co.nlj.ix.dto.trspPlanLineItem.CneePrtyDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.CnsDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.CnsLineItemDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.CnsgPrtyDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.DelInfoDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.FretClimToPrtyDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.LogsSrvcPrvDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.RoadCarrDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.ShipFromPrtyDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.ShipToPrtyDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.TrspIsrDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.TrspRqrPrtyDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.TrspSrvcDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.TrspVehicleTrmsDTO;
import lombok.Data;


/**
 * <PRE>
 * 輸送計画品目登録リクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrspPlanLineItemRequest {

    @JsonProperty("trsp_isr")
    @ValidateField(notNull = true)
    private TrspIsrDTO trspIsr;

    @JsonProperty("trsp_srvc")
    private TrspSrvcDTO trspSrvc;

    @JsonProperty("trsp_vehicle_trms")
    private TrspVehicleTrmsDTO trspVehicleTrms;

    @JsonProperty("del_info")
    private DelInfoDTO delInfo;

    @JsonProperty("cns")
    @ValidateField(notNull = true)
    private CnsDTO cns;

    @JsonProperty("cns_line_item")
    private List<CnsLineItemDTO> cnsLineItem;

    @JsonProperty("cnsg_prty")
    private CnsgPrtyDTO cnsgPrty;

    @JsonProperty("trsp_rqr_prty")
    private TrspRqrPrtyDTO trspRqrPrty;

    @JsonProperty("cnee_prty")
    private CneePrtyDTO cneePrty;

    @JsonProperty("logs_srvc_prv")
    private LogsSrvcPrvDTO logsSrvcPrv;

    @JsonProperty("road_carr")
    private RoadCarrDTO roadCarr;

    @JsonProperty("fret_clim_to_prty")
    private FretClimToPrtyDTO fretClimToPrty;

    @JsonProperty("ship_from_prty")
    private List<ShipFromPrtyDTO> shipFromPrty;

    @JsonProperty("ship_to_prty")
    private List<ShipToPrtyDTO> shipToPrty;
}
