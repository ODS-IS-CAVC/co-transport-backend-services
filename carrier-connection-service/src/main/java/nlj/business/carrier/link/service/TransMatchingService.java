package nlj.business.carrier.link.service;

import java.util.List;
import nlj.business.carrier.link.domain.VehicleAvbResourceItem;
import nlj.business.carrier.link.dto.matching.TransMatchingDTO;
import nlj.business.carrier.link.dto.matching.request.CnsLineItemByDateRequest;
import nlj.business.carrier.link.dto.matching.request.MatchingIdRequest;
import nlj.business.carrier.link.dto.matching.request.ShipperOrderIdSaleRequest;

/**
 * <PRE>
 * 輸送計画マッチングサービスインターフェース.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TransMatchingService {

    /**
     * キャリアとのマッチング.<BR>
     *
     * @param shipper シッパー
     * @return マッチングDTOリスト
     */
    List<TransMatchingDTO> matchingWithCarrier(CnsLineItemByDateRequest shipper);

    /**
     * 輸送計画によるマッチング.<BR>
     *
     * @param shipper シッパー
     * @return マッチングDTOリスト
     */
    List<TransMatchingDTO> matchingWithCarrierByTransportPlan(CnsLineItemByDateRequest shipper);

    /**
     * シッパーとのマッチング.<BR>
     *
     * @param id シッパーID
     * @return マッチングDTOリスト
     */
    List<TransMatchingDTO> matchingWithShipper(Long id);

    /**
     * 車両図によるマッチング.<BR>
     *
     * @param id 車両図ID
     * @return マッチングDTOリスト
     */
    List<TransMatchingDTO> matchingWithShipperByVehicleDiagramId(Long id);

    /**
     * キャリア注文ID売上を登録.<BR>
     *
     * @param cnsLineItemByDateRequest キャリア注文ID売上リクエスト
     */
    void insertCarrierOrderIdSale(CnsLineItemByDateRequest cnsLineItemByDateRequest);

    /**
     * キャリア注文IDキャンセル.<BR>
     *
     * @param cnsLineItemByDateRequest キャリア注文IDキャンセルリクエスト
     */
    void carrierOrderIdCancel(CnsLineItemByDateRequest cnsLineItemByDateRequest);

    /**
     * キャリアとのマッチング.<BR>
     *
     * @param request マッチングIDリクエスト
     * @return マッチングDTOリスト
     */
    List<TransMatchingDTO> carrierMatchingWithCarrier(MatchingIdRequest request);

    /**
     * 要求輸送計画明細マッチング.<BR>
     *
     * @param reqCnsLineItemId 要求輸送計画明細ID
     * @return マッチングDTOリスト
     */
    List<TransMatchingDTO> reqCnsLineItemMatching(Long reqCnsLineItemId);

    /**
     * 車両可動資源マッチング.<BR>
     *
     * @param vehicleAvbResourceId 車両可動資源ID
     * @return マッチングDTOリスト
     */
    List<TransMatchingDTO> vehicleAvbResourceMatching(Long vehicleAvbResourceId);

    /**
     * キャリア注文ID売上を登録.<BR>
     *
     * @param cnsLineItemId キャリア注文ID
     * @return マッチングDTOリスト
     */
    List<TransMatchingDTO> cnsLineItemMatching(Long cnsLineItemId);

    /**
     * シッパー注文ID売上を登録.<BR>
     *
     * @param shipperOrderIdSaleRequest シッパー注文ID売上リクエスト
     * @return マッチングDTOリスト
     */
    List<VehicleAvbResourceItem> insertShipperOrderIdSale(ShipperOrderIdSaleRequest shipperOrderIdSaleRequest);
}
