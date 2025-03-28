package nlj.business.transaction.constant;

/**
 * <PRE>
 * 転送計画アイテムマッピングステータス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public enum TransportPlanItemMappingStatus {
    MATCHING_OK(2, 2),
    MATCHING_NG(1, 1),
    PROPOSED_120(120, 4),
    PROPOSED_110(110, 3),
    CONTRACTED_140(140, 5),
    TRANSPORT_CONFIRMED_160(160, 6),
    PAYMENT_150(150, 8),
    COMPLETED_161(161, 9),
    CANCEL_999(998, 10);

    private final int source;
    private final int target;

    TransportPlanItemMappingStatus(int source, int target) {
        this.source = source;
        this.target = target;
    }

    public int getSource() {
        return source;
    }

    public int getTarget() {
        return target;
    }

    public static Integer getMappedTransportStatusValue(int source) {
        for (TransportPlanItemMappingStatus mapping : values()) {
            if (mapping.getSource() == source) {
                return mapping.getTarget();
            }
        }
        return null; // Return null if not found
    }
}
