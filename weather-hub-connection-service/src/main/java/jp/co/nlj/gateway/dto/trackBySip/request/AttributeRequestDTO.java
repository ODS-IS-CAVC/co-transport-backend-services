package jp.co.nlj.gateway.dto.trackBySip.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.gateway.dto.trackBySip.DataChannelTrspSrvcDTO;
import jp.co.nlj.gateway.dto.trackBySip.LogsSrvcPrv;
import jp.co.nlj.gateway.dto.trackBySip.ShipFromPrty;
import jp.co.nlj.gateway.dto.trackBySip.ShipToPrty;
import jp.co.nlj.gateway.dto.trackBySip.TrspIsr;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <PRE>
 * AttributeRequestDTOクラスは、属性リクエストDTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
public class AttributeRequestDTO {

    @JsonProperty("is_create")
    private boolean isCreate = true;

    @JsonProperty("mh_reservation_id")
    private String mhReservationId;

    @JsonProperty("trsp_isr")
    private TrspIsr trspIsr;

    @JsonProperty("trsp_srvc")
    private DataChannelTrspSrvcDTO trspSrvc;

    @JsonProperty("logs_srvc_prv")
    private LogsSrvcPrv logsSrvcPrv;

    @JsonProperty("ship_from_prty")
    private ShipFromPrty shipFromPrty;

    @JsonProperty("ship_to_prty")
    private ShipToPrty shipToPrty;
}
