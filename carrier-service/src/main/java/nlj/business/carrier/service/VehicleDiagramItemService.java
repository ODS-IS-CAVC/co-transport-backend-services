package nlj.business.carrier.service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import nlj.business.carrier.domain.VehicleDiagram;
import nlj.business.carrier.domain.VehicleDiagramItem;
import nlj.business.carrier.domain.VehicleDiagramItemTrailer;
import nlj.business.carrier.dto.semiDynamicInfo.response.ValidateSemiDynamicInfoResponseDTO;
import nlj.business.carrier.dto.transferStatus.ItemTrailerUpdateStatusRequestDTO;
import nlj.business.carrier.dto.transferStatus.ItemTrailerUpdateStatusResponse;
import nlj.business.carrier.dto.vehicleDiagramItem.request.DiagramItemUpdateStatusRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.UpdateDiagramItemTimeRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.UpdateEmergencyRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.UpdatePrivateDiagramItemRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.UpdateSipTrackIdRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.UpdateTrackStatusRequest;
import nlj.business.carrier.dto.vehicleDiagramItem.request.VehicleDiagramItemDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.DiagramItemDepartureArrivalTimeDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemByAbilityDetailsDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramItemTrailer.response.VehicleDiagramItemTrailerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * <PRE>
 * Vehicle Diagram Item Service。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleDiagramItemService {

    /**
     * 車両ダイアグラムアイテムを登録します。
     *
     * @param vehicleDiagramItems 車両ダイアグラムアイテムのリスト
     */
    void registerVehicleDiagramItems(VehicleDiagram vehicleDiagram, List<VehicleDiagramItemDTO> vehicleDiagramItems);

    /**
     * 車両ダイアグラムアイテムを取得します。
     *
     * @param vehicleDiagramId 車両ダイアグラムID
     * @return 車両ダイアグラムアイテムのリスト
     */
    List<VehicleDiagramItemResponseDTO> getVehicleDiagramItems(Long vehicleDiagramId);

    /**
     * 指定されたダイアグラムIDに基づいてアイテムを取得します。
     *
     * @param diagramId ダイアグラムID
     * @return 車両ダイアグラムアイテムのリスト
     */
    List<VehicleDiagramItemResponseDTO> getItemsByDiagramId(Long diagramId);

    /**
     * 指定されたダイアグラムIDのリストに基づいてアイテムを削除します。
     *
     * @param diagramIds ダイアグラムIDのリスト
     */
    void deleteByDiagramIds(List<Long> diagramIds);

    /**
     * 車両ダイアグラムアイテムを検証します。
     */
    void validateVehicleDiagramItems();

    /**
     * 指定されたダイアグラムIDとオペレーターIDに基づいてすべてのアイテムを取得します。
     *
     * @param diagramId  ダイアグラムID
     * @param operatorId オペレーターID
     * @param pageable   ページ情報
     * @return ページネーションされた車両ダイアグラムアイテム
     */
    Page<VehicleDiagramItem> findAllByVehicleDiagramIdAndOperatorId(Long diagramId, String operatorId,
        Pageable pageable);

    /**
     * IDに基づいて車両ダイアグラムアイテムを取得します。
     *
     * @param id 車両ダイアグラムアイテムのID
     * @return 車両ダイアグラムアイテム
     */
    VehicleDiagramItem findById(Long id);

    /**
     * 車両ダイアグラムアイテムの詳細を取得します。
     *
     * @param id                 車両ダイアグラムアイテムのID
     * @param binName            ビン名
     * @param transportDate      輸送日
     * @param httpServletRequest HTTPリクエスト
     * @return 車両ダイアグラムアイテムの詳細
     */
    VehicleDiagramItemByAbilityDetailsDTO getDetail(Long id, String binName, String transportDate,
        HttpServletRequest httpServletRequest);

    /**
     * 車両ダイアグラムアイテムを更新します。
     *
     * @param id  車両ダイアグラムアイテムのID
     * @param dto 車両ダイアグラムアイテムのDTO
     * @return 更新結果
     */
    ValidateSemiDynamicInfoResponseDTO updateVehicleItem(Long id, VehicleDiagramItemByAbilityDetailsDTO dto);

    /**
     * 車両ダイアグラムアイテムを削除します。
     *
     * @param id 車両ダイアグラムアイテムのID
     */
    void deleteVehicleItem(Long id);

    /**
     * アイテムトレーラーのステータスを更新します。
     *
     * @param dto アイテムトレーラーの更新リクエストDTO
     * @return 更新結果
     */
    ItemTrailerUpdateStatusResponse updateItemTrailerStatus(ItemTrailerUpdateStatusRequestDTO dto);

    /**
     * SIPトラックIDを更新します。
     *
     * @param request SIPトラックIDの更新リクエスト
     * @return 更新結果
     */
    String updateSipTrackId(UpdateSipTrackIdRequest request);

    /**
     * プライベートダイアグラムアイテムを更新します。
     *
     * @param id                 車両ダイアグラムアイテムのID
     * @param request            更新リクエスト
     * @param httpServletRequest HTTPリクエスト
     */
    void updatePrivateDiagramItem(Long id, UpdatePrivateDiagramItemRequest request,
        HttpServletRequest httpServletRequest);

    /**
     * 車両ダイアグラムアイテムトレーラーの出発到着時間を取得します。
     *
     * @param diagramItemTrailerId 車両ダイアグラムアイテムトレーラーのID
     * @return 出発到着時間DTO
     */
    DiagramItemDepartureArrivalTimeDTO getDepartureArrivalTime(Long diagramItemTrailerId);

    /**
     * 車両ダイアグラムアイテムIDに基づいて出発到着時間を取得します。
     *
     * @param diagramItemTrailerId 車両ダイアグラムアイテムトレーラーのID
     * @return 出発到着時間DTO
     */
    DiagramItemDepartureArrivalTimeDTO getDepartureArrivalTimeByDiagramItemId(Long diagramItemTrailerId);

    /**
     * 車両ダイアグラムアイテムのステータスを更新します。
     *
     * @param diagramItemId 車両ダイアグラムアイテムのID
     * @param type          ステータスタイプ
     * @param request       更新リクエスト
     * @return 更新結果
     */
    String updateVehicleStatus(Long diagramItemId, Long type, UpdateTrackStatusRequest request);

    /**
     * 車両ダイアグラムアイテムのステータスを更新するジョブを実行します。
     */
    void updateStatusVehicleDiagramItemsJob();

    /**
     * 車両ダイアグラムアイテムを更新します。
     *
     * @param vehicleDiagram     車両ダイアグラム
     * @param vehicleDiagramItem 車両ダイアグラムアイテム
     */
    void updateVehicleDiagramItem(VehicleDiagram vehicleDiagram, VehicleDiagramItem vehicleDiagramItem);

    /**
     * 車両ダイアグラムアイテムを追加します。
     *
     * @param vehicleDiagram        車両ダイアグラム
     * @param vehicleDiagramItemDTO 車両ダイアグラムアイテムのDTO
     */
    void addVehicleDiagramItem(VehicleDiagram vehicleDiagram, VehicleDiagramItemDTO vehicleDiagramItemDTO);

    /**
     * 車両ダイアグラムアイテムの時間を更新します。
     *
     * @param diagramItemId 車両ダイアグラムアイテムのID
     * @param request       更新リクエスト
     */
    void updateDiagramItemTime(Long diagramItemId, UpdateDiagramItemTimeRequest request);

    /**
     * 緊急車両ダイアグラムアイテムを更新します。
     *
     * @param id      車両ダイアグラムアイテムのID
     * @param request 更新リクエスト
     */
    void updateEmergencyDiagramItem(Long id, UpdateEmergencyRequest request);

    /**
     * モビリティハブを呼び出します。
     *
     * @param vehicleDiagramItem 車両ダイアグラムアイテム
     * @param dto                車両ダイアグラムアイテムの詳細DTO
     */
    void callMobilityHub(VehicleDiagramItem vehicleDiagramItem, VehicleDiagramItemByAbilityDetailsDTO dto);

    /**
     * 車両ダイアグラムアイテムのステータスを更新します。
     *
     * @param request 更新リクエスト
     */
    void updateDiagramItemStatus(DiagramItemUpdateStatusRequest request);

    /**
     * トレーラーのステータスと合計カウントを設定します。
     *
     * @param trailer 車両ダイアグラムアイテムトレーラー
     * @param dto     車両ダイアグラムアイテムトレーラーDTO
     * @return 更新されたトレーラーDTO
     */
    VehicleDiagramItemTrailerDTO setOrderStatusAndTotalCountForTrailer(VehicleDiagramItemTrailer trailer,
        VehicleDiagramItemTrailerDTO dto);
}
