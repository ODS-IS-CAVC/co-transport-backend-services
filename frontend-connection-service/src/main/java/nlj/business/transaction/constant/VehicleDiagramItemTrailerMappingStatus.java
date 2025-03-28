package nlj.business.transaction.constant;

/**
 * <PRE>
 * 車両図形アイテムトレーラーマッピングステータス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public enum VehicleDiagramItemTrailerMappingStatus {
    MATCHING_OK(2, 2),
    MATCHING_NG(1, 1),
    PROPOSED_120(110, 4),
    PROPOSED_110(120, 3),
    CONTRACTED_140(140, 5),
    TRANSPORT_CONFIRMED_160(160, 6),
    PAYMENT_150(150, 7),
    COMPLETED_161(161, 8),
    CANCEL_999(999, 9);

    private final int source;
    private final int target;

    VehicleDiagramItemTrailerMappingStatus(int source, int target) {
        this.source = source;
        this.target = target;
    }

    public int getSource() {
        return source;
    }

    public int getTarget() {
        return target;
    }

    public static Integer getMappedTrailerStatusValue(int source) {
        for (VehicleDiagramItemTrailerMappingStatus mapping : values()) {
            if (mapping.getSource() == source) {
                return mapping.getTarget();
            }
        }
        return null; // Return null if not found
    }
}
