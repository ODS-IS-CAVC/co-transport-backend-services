package nlj.business.carrier.link.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nlj.business.carrier.link.constant.DataBaseConstant;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * <PRE>
 * 車両図表明細操作トラッキングエンティティ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Entity
@Table(name = DataBaseConstant.DiagramItemTracking.TABLE_OPERATION)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDiagramItemOperationTracking implements Serializable {

    @Serial
    private static final long serialVersionUID = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DataBaseConstant.DiagramItemTracking.ID)
    private Long id;

    @Column(name = DataBaseConstant.DiagramItemTracking.VEHICLE_DIAGRAM_ITEM_ID)
    private Long vehicleDiagramItemId;

    @Column(name = DataBaseConstant.DiagramItemTracking.OPERATION_DATE)
    private LocalDate operationDate;

    @Column(name = DataBaseConstant.DiagramItemTracking.OPERATION_TIME)
    private LocalTime operationTime;

    @Column(name = DataBaseConstant.DiagramItemTracking.STATUS)
    private Integer status;

    @Column(name = DataBaseConstant.DiagramItemTracking.LABEL)
    private String label;

    @Column(name = DataBaseConstant.DiagramItemTracking.MESSAGE)
    private String message;

    @CreatedBy
    @Column(name = DataBaseConstant.DiagramItemTracking.CREATED_USER, nullable = false, length = 50, updatable = false)
    private String createdUser = "Admin";

    @CreatedDate
    @Column(name = DataBaseConstant.DiagramItemTracking.CREATED_DATE, updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @LastModifiedBy
    @Column(name = DataBaseConstant.DiagramItemTracking.UPDATED_USER, length = 50)
    private String updatedUser = "Admin";

    @LastModifiedDate
    @Column(name = DataBaseConstant.DiagramItemTracking.UPDATED_DATE)
    private LocalDateTime updatedDate = LocalDateTime.now();
}
