package jp.co.nlj.ttmi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jp.co.nlj.ttmi.constant.DataBaseConstant;
import lombok.Data;

/**
 * <PRE>
 * 注文明細区分エンティティ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
@Entity
@Table(name = DataBaseConstant.IOrderDetailDiv.TABLE)
public class IOrderDetailDiv {

    @Id
    @Column(name = DataBaseConstant.IOrderDetailDiv.ORDER_DETAIL_DIV_KEY, length = 20, nullable = false)
    private String orderDetailDivKey;

    @Column(name = DataBaseConstant.IOrderDetailDiv.MANAGEMENT_NUMBER, length = 20, nullable = false)
    private String managementNumber;

    @Column(name = DataBaseConstant.IOrderDetailDiv.DETAIL_NO, precision = 6, scale = 0, nullable = false)
    private Integer detailNo;

    @Column(name = DataBaseConstant.IOrderDetailDiv.DETAIL_SERIAL_NUMBER, precision = 6, scale = 0, nullable = false)
    private Integer detailSerialNumber;

    @Column(name = DataBaseConstant.IOrderDetailDiv.SHIPMENT_TASK_KEY, length = 20)
    private String shipmentTaskKey;

    @Column(name = DataBaseConstant.IOrderDetailDiv.FINAL_LOADING_DATETIME)
    private LocalDateTime finalLoadingDatetime;

    @Column(name = DataBaseConstant.IOrderDetailDiv.TAG_PRINT_FLG, length = 1)
    private String tagPrintFlg;

    @Column(name = DataBaseConstant.IOrderDetailDiv.LOGISTICS_COMPANY_CODE, length = 12, nullable = false)
    private String logisticsCompanyCode;

    @Column(name = DataBaseConstant.IOrderDetailDiv.UNIT, precision = 11, scale = 3, nullable = false)
    private BigDecimal unit;

    @Column(name = DataBaseConstant.IOrderDetailDiv.FINAL_PRINT_DATETIME)
    private LocalDateTime finalPrintDatetime;

    @Column(name = DataBaseConstant.IOrderDetailDiv.REALLOCATE_FLG, length = 1)
    private String reallocateFlg;

    @Column(name = DataBaseConstant.IOrderDetailDiv.TAG_ADD_FLG, length = 1)
    private String tagAddFlg;

    @Column(name = DataBaseConstant.IOrderDetailDiv.DELETE_FLG, length = 1, nullable = false)
    private String deleteFlg;

    @Column(name = DataBaseConstant.IOrderDetailDiv.PROCESS_ID, length = 15, nullable = false)
    private String processId;

    @Column(name = DataBaseConstant.IOrderDetailDiv.CREATE_USER)
    private String createUser;

    @Column(name = DataBaseConstant.IOrderDetailDiv.CREATE_DT)
    private LocalDateTime createDt;

    @Column(name = DataBaseConstant.IOrderDetailDiv.UPD_USER)
    private String updUser;

    @Column(name = DataBaseConstant.IOrderDetailDiv.UPD_DT)
    private LocalDateTime updDt;
}
