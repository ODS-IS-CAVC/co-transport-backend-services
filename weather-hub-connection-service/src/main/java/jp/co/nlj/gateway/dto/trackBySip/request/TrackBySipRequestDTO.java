package jp.co.nlj.gateway.dto.trackBySip.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jp.co.nlj.gateway.aop.proxy.ValidateField;
import jp.co.nlj.gateway.dto.trackBySip.LogsSrvcPrv;
import jp.co.nlj.gateway.dto.trackBySip.ShipFromPrty;
import jp.co.nlj.gateway.dto.trackBySip.ShipToPrty;
import jp.co.nlj.gateway.dto.trackBySip.TrspIsr;
import jp.co.nlj.gateway.dto.trackBySip.TrspSrvc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <PRE>
 * TrackBySipRequestDTOクラスは、トラックバイSIPリクエストDTOを定義するためのクラスです。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false)
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
