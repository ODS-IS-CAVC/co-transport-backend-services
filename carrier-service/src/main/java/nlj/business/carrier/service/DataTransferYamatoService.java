package nlj.business.carrier.service;

import nlj.business.carrier.domain.VehicleDiagramItem;

/**
 * <PRE>
 * ヤマトデータ転送サービス。
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface DataTransferYamatoService {

    /**
     * ヤマトデータ転送を行う。
     *
     * @param vehicleDiagramId 車両ダイアグラムID
     */
    void transferDataToYamato(Long vehicleDiagramId);

    /**
     * ヤマトデータ転送を行う。
     *
     * @param vehicleDiagramItem 車両ダイアグラムアイテム
     */
    void transferDataDateTime(VehicleDiagramItem vehicleDiagramItem);
}
