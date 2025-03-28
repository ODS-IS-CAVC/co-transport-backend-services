package nlj.business.carrier.link.dto.trspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.aop.proxy.ValidateField;

/**
 * <PRE>
 * 運送計画明細削除DTO.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class TrspPlanLineItemDeleteDTO {

    @JsonProperty("trsp_isr")
    @ValidateField(notNull = true)
    private TrspIsrDeleteDTO trspIsr;

    @JsonProperty("trsp_srvc")
    private TrspSrvcDeleteDTO trspSrvc;

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
