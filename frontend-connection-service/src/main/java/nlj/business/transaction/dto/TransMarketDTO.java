package nlj.business.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;
import nlj.business.transaction.constant.DataBaseConstant;

/**
 * <PRE>
 * 市場DTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransMarketDTO {

    @JsonProperty(DataBaseConstant.TransMarket.MONTH)
    private String month;

    @JsonProperty(DataBaseConstant.TransMarket.DAY)
    private String day;

    @JsonProperty(DataBaseConstant.TransMarket.TOTAL_TRANS_NUMBER)
    private Integer totalTransNumber;

    @JsonProperty(DataBaseConstant.TransMarket.TOTAL_TRAILER_NUMBER)
    private Integer totalTrailerNumber;

    @JsonProperty(DataBaseConstant.TransMarket.TOTAL_AVAILABLE_TRAILER_NUMBER)
    private Integer totalAvailableTrailerNumber;

    @JsonProperty(DataBaseConstant.TransMarket.LOW_PRICE)
    private BigDecimal lowPrice;

    @JsonProperty(DataBaseConstant.TransMarket.HIGH_PRICE)
    private BigDecimal highPrice;

    @JsonProperty(DataBaseConstant.TransMarket.MEDIAN_PRICE)
    private BigDecimal medianPrice;

    @JsonProperty(DataBaseConstant.TransMarket.TOTAL_PROPOSAL_NUMBER)
    private Integer totalProposalNumber;

    @JsonProperty(DataBaseConstant.TransMarket.TOTAL_TRAILER_PROPOSAL_NUMBER)
    private Integer totalTrailerProposalNumber;

    @JsonProperty(DataBaseConstant.TransMarket.LOW_PROPOSAL_PRICE)
    private BigDecimal lowProposalPrice;

    @JsonProperty(DataBaseConstant.TransMarket.HIGH_PROPOSAL_PRICE)
    private BigDecimal highProposalPrice;

    @JsonProperty(DataBaseConstant.TransMarket.AVERAGE_PROPOSAL_PRICE)
    private BigDecimal averageProposalPrice;

    @JsonProperty(DataBaseConstant.TransMarket.TOTAL_REQUEST_NUMBER)
    private Integer totalRequestNumber;

    @JsonProperty(DataBaseConstant.TransMarket.TOTAL_TRAILER_REQUEST_NUMBER)
    private Integer totalTrailerRequestNumber;

    @JsonProperty(DataBaseConstant.TransMarket.LOW_REQUEST_PRICE)
    private BigDecimal lowRequestPrice;

    @JsonProperty(DataBaseConstant.TransMarket.HIGH_REQUEST_PRICE)
    private BigDecimal highRequestPrice;

    @JsonProperty(DataBaseConstant.TransMarket.AVERAGE_REQUEST_PRICE)
    private BigDecimal averageRequestPrice;

}
