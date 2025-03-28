package jp.co.nlj.ix.dto.trspPlanLineItem.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * <PRE>
 * キャリアオペレータープラン登録応答DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class CarrierOperatorPlansInsertResponse {

    @JsonProperty("propose_id")
    private String proposeId;
}
