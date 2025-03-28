package jp.co.nlj.ix.dto.reqTrspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import jp.co.nlj.ix.dto.trspPlanLineItem.CneePrtyDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.CnsgPrtyDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.FretClimToPrtyDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.LogsSrvcPrvDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.RoadCarrDTO;
import jp.co.nlj.ix.dto.trspPlanLineItem.TrspVehicleTrmsDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 要求運送計画明細DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class ReqTrspPlanLineItemDTO {

    @JsonProperty("trsp_isr")
    @ValidateField(notNull = true)
    private ReqTrspIsrDTO trspIsr;

    @JsonProperty("trsp_srvc")
    private ReqTrspSrvcDTO trspSrvc;

    @JsonProperty("trsp_vehicle_trms")
    private TrspVehicleTrmsDTO trspVehicleTrms;

    @JsonProperty("del_info")
    private ReqDelInfoDTO delInfo;

    @JsonProperty("cns")
    @ValidateField(notNull = true)
    private ReqCnsDTO cns;

    @JsonProperty("cns_line_item")
    private List<ReqCnsLineItemDTO> cnsLineItem;

    @JsonProperty("cnsg_prty")
    private CnsgPrtyDTO cnsgPrty;

    @JsonProperty("trsp_rqr_prty")
    private ReqTrspRqrPrtyDTO trspRqrPrty;

    @JsonProperty("cnee_prty")
    private CneePrtyDTO cneePrty;

    @JsonProperty("logs_srvc_prv")
    private LogsSrvcPrvDTO logsSrvcPrv;

    @JsonProperty("road_carr")
    private RoadCarrDTO roadCarr;

    @JsonProperty("fret_clim_to_prty")
    private FretClimToPrtyDTO fretClimToPrty;

    @JsonProperty("ship_from_prty")
    private ReqShipFromPrtyDTO shipFromPrty;

    @JsonProperty("ship_to_prty")
    private ReqShipToPrtyDTO shipToPrty;

}
