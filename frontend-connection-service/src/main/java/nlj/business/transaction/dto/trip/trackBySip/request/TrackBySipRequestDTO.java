package nlj.business.transaction.dto.trip.trackBySip.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import nlj.business.transaction.aop.proxy.ValidateField;
import nlj.business.transaction.dto.trip.trackBySip.LogsSrvcPrv;
import nlj.business.transaction.dto.trip.trackBySip.ShipFromPrty;
import nlj.business.transaction.dto.trip.trackBySip.ShipToPrty;
import nlj.business.transaction.dto.trip.trackBySip.TrspIsr;
import nlj.business.transaction.dto.trip.trackBySip.TrspSrvc;

/**
 * <PRE>
 * トラックバイSIPリクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrackBySipRequestDTO {

    @JsonProperty("is_post")
    private boolean isPost = true;

    @JsonProperty("trsp_isr")
    @ValidateField
    private TrspIsr trspIsr;

    @JsonProperty("trsp_srvc")
    @ValidateField
    private TrspSrvc trspSrvc;

    @JsonProperty("logs_srvc_prv")
    @ValidateField
    private LogsSrvcPrv logsSrvcPrv;

    @JsonProperty("ship_from_prty")
    @ValidateField
    private ShipFromPrty shipFromPrty;

    @JsonProperty("ship_to_prty")
    @ValidateField
    private ShipToPrty shipToPrty;
}
