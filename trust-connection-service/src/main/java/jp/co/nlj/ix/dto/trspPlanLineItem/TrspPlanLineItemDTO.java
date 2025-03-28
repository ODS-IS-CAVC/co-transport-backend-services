package jp.co.nlj.ix.dto.trspPlanLineItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import jp.co.nlj.ix.aop.proxy.ValidateField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <PRE>
 * 運送計画明細DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Getter
@Setter
@NoArgsConstructor
public class TrspPlanLineItemDTO {

    @JsonProperty("cid")
    private String cid;

    @JsonProperty("trsp_isr")
    @ValidateField(notNull = true)
    private TrspIsrDTO trspIsr = new TrspIsrDTO();

    @JsonProperty("trsp_srvc")
    private TrspSrvcDTO trspSrvc = new TrspSrvcDTO();

    @JsonProperty("trsp_vehicle_trms")
    private TrspVehicleTrmsDTO trspVehicleTrms = new TrspVehicleTrmsDTO();

    @JsonProperty("del_info")
    private DelInfoDTO delInfo = new DelInfoDTO();

    @JsonProperty("cns")
    @ValidateField(notNull = true)
    private CnsDTO cns = new CnsDTO();

    @JsonProperty("cns_line_item")
    private List<CnsLineItemDTO> cnsLineItem = new ArrayList<>();

    @JsonProperty("cnsg_prty")
    private CnsgPrtyDTO cnsgPrty = new CnsgPrtyDTO();

    @JsonProperty("trsp_rqr_prty")
    private TrspRqrPrtyDTO trspRqrPrty = new TrspRqrPrtyDTO();

    @JsonProperty("cnee_prty")
    private CneePrtyDTO cneePrty = new CneePrtyDTO();

    @JsonProperty("logs_srvc_prv")
    private LogsSrvcPrvDTO logsSrvcPrv = new LogsSrvcPrvDTO();

    @JsonProperty("road_carr")
    private RoadCarrDTO roadCarr = new RoadCarrDTO();

    @JsonProperty("fret_clim_to_prty")
    private FretClimToPrtyDTO fretClimToPrty = new FretClimToPrtyDTO();

    @JsonProperty("ship_from_prty")
    private List<ShipFromPrtyDTO> shipFromPrty = new ArrayList<>();

    @JsonProperty("ship_to_prty")
    private List<ShipToPrtyDTO> shipToPrty = new ArrayList<>();

}
