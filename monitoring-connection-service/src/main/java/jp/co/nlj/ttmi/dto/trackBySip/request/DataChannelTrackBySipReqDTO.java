package jp.co.nlj.ttmi.dto.trackBySip.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.ttmi.dto.trackBySip.DataChannelTrspSrvcDTO;
import jp.co.nlj.ttmi.dto.trackBySip.LogsSrvcPrvDTO;
import jp.co.nlj.ttmi.dto.trackBySip.ShipFromPrtyDTO;
import jp.co.nlj.ttmi.dto.trackBySip.ShipToPrtyDTO;
import jp.co.nlj.ttmi.dto.trackBySip.TrspIsrDTO;
import lombok.Data;

/**
 * <PRE>
 * データチャンネルSipリクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class DataChannelTrackBySipReqDTO {

    @JsonProperty("is_create")
    private boolean isCreate = true;

    @JsonProperty("mh_reservation_id")
    private String mhReservationId;

    @JsonProperty("trsp_isr")
    private TrspIsrDTO trspIsr;

    @JsonProperty("trsp_srvc")
    private DataChannelTrspSrvcDTO trspSrvc;

    @JsonProperty("logs_srvc_prv")
    private LogsSrvcPrvDTO logsSrvcPrv;

    @JsonProperty("ship_from_prty")
    private ShipFromPrtyDTO shipFromPrty;

    @JsonProperty("ship_to_prty")
    private ShipToPrtyDTO shipToPrty;
}
