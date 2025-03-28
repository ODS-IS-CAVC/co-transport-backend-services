package jp.co.nlj.ttmi.dto.trackBySip.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ttmi.dto.trackBySip.LogsSrvcPrvDTO;
import jp.co.nlj.ttmi.dto.trackBySip.ShipFromPrtyDTO;
import jp.co.nlj.ttmi.dto.trackBySip.ShipToPrtyDTO;
import jp.co.nlj.ttmi.dto.trackBySip.TrspIsrDTO;
import jp.co.nlj.ttmi.dto.trackBySip.TrspSrvcDTO;
import lombok.Data;

/**
 * <PRE>
 * SipリクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TrackBySipReqDTO {

    @JsonProperty("is_post")
    private boolean isPost = true;

    @JsonProperty("trsp_isr")
    private TrspIsrDTO trspIsr;

    @JsonProperty("trsp_srvc")
    private TrspSrvcDTO trspSrvc;

    @JsonProperty("logs_srvc_prv")
    private LogsSrvcPrvDTO logsSrvcPrv;

    @JsonProperty("ship_from_prty")
    private ShipFromPrtyDTO shipFromPrty;

    @JsonProperty("ship_to_prty")
    private ShipToPrtyDTO shipToPrty;
}
