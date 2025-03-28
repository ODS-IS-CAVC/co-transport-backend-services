package nlj.business.transaction.dto.trspPlanLineItem.ix;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
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
//    @ValidateField(notNull = true)
    private TrspIsrDTO trspIsr;

    @JsonProperty("trsp_srvc")
    private TrspSrvcDTO trspSrvc;

    @JsonProperty("trsp_vehicle_trms")
    private TrspVehicleTrmsDTO trspVehicleTrms;

    @JsonProperty("del_info")
    private DelInfoDTO delInfo;

    @JsonProperty("cns")
//    @ValidateField(notNull = true)
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
