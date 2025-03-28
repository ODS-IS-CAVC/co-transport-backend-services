package nlj.business.transaction.domain.trans;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.transaction.constant.DataBaseConstant;
import nlj.business.transaction.domain.AbstractAuditingEntity;

/**
 * <PRE>
 * 市場エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.TransMarket.TABLE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransMarket extends AbstractAuditingEntity<Long> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = DataBaseConstant.TransMarket.MONTH)
    private String month;

    @Column(name = DataBaseConstant.TransMarket.DAY)
    private String day;

    @Column(name = DataBaseConstant.TransMarket.TOTAL_TRANS_NUMBER)
    private Integer totalTransNumber;

    @Column(name = DataBaseConstant.TransMarket.TOTAL_TRAILER_NUMBER)
    private Integer totalTrailerNumber;

    @Column(name = DataBaseConstant.TransMarket.TOTAL_AVAILABLE_TRAILER_NUMBER)
    private Integer totalAvailableTrailerNumber;

    @Column(name = DataBaseConstant.TransMarket.LOW_PRICE, precision = 9, scale = 0)
    private BigDecimal lowPrice;

    @Column(name = DataBaseConstant.TransMarket.HIGH_PRICE, precision = 9, scale = 0)
    private BigDecimal highPrice;

    @Column(name = DataBaseConstant.TransMarket.MEDIAN_PRICE, precision = 9, scale = 0)
    private BigDecimal medianPrice;

    @Column(name = DataBaseConstant.TransMarket.TOTAL_PROPOSAL_NUMBER)
    private Integer totalProposalNumber;

    @Column(name = DataBaseConstant.TransMarket.TOTAL_TRAILER_PROPOSAL_NUMBER)
    private Integer totalTrailerProposalNumber;

    @Column(name = DataBaseConstant.TransMarket.LOW_PROPOSAL_PRICE, precision = 9, scale = 0)
    private BigDecimal lowProposalPrice;

    @Column(name = DataBaseConstant.TransMarket.HIGH_PROPOSAL_PRICE, precision = 9, scale = 0)
    private BigDecimal highProposalPrice;

    @Column(name = DataBaseConstant.TransMarket.AVERAGE_PROPOSAL_PRICE, precision = 9, scale = 0)
    private BigDecimal averageProposalPrice;

    @Column(name = DataBaseConstant.TransMarket.TOTAL_REQUEST_NUMBER)
    private Integer totalRequestNumber;

    @Column(name = DataBaseConstant.TransMarket.TOTAL_TRAILER_REQUEST_NUMBER)
    private Integer totalTrailerRequestNumber;

    @Column(name = DataBaseConstant.TransMarket.LOW_REQUEST_PRICE, precision = 9, scale = 0)
    private BigDecimal lowRequestPrice;

    @Column(name = DataBaseConstant.TransMarket.HIGH_REQUEST_PRICE, precision = 9, scale = 0)
    private BigDecimal highRequestPrice;

    @Column(name = DataBaseConstant.TransMarket.AVERAGE_REQUEST_PRICE, precision = 9, scale = 0)
    private BigDecimal averageRequestPrice;
}
