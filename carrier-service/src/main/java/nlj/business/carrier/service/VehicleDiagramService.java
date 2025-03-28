package nlj.business.carrier.service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import nlj.business.carrier.dto.vehicleDiagram.request.VehicleDiagramDTO;
import nlj.business.carrier.dto.vehicleDiagram.response.VehicleDiagramListResponse;
import nlj.business.carrier.dto.vehicleDiagram.response.VehicleDiagramResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramHead.request.VehicleDiagramHeadDTO;
import nlj.business.carrier.dto.vehicleDiagramHead.response.ResDTO;
import nlj.business.carrier.dto.vehicleDiagramHead.response.VehicleDiagramStatusResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemByAbilityResponseDTO;
import org.springframework.data.domain.Pageable;

/**
 * <PRE>
 * 車両情報サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleDiagramService {

    /**
     * 車両ダイアグラムを追加する。
     *
     * @param vehicleDiagramDTO     車両ダイアグラムのDTO
     * @param vehicleDiagramHeadDTO 車両ダイアグラムヘッドのDTO
     * @return 追加結果
     */
    ResDTO addVehicleDiagram(VehicleDiagramDTO vehicleDiagramDTO, VehicleDiagramHeadDTO vehicleDiagramHeadDTO);

    /**
     * 車両ダイアグラムを更新する。
     *
     * @param vehicleDiagramId      車両ダイアグラムのID
     * @param vehicleDiagramHeadDTO 車両ダイアグラムヘッドのDTO
     * @return 更新結果
     */
    VehicleDiagramStatusResponseDTO updateVehicleDiagram(Long vehicleDiagramId,
        VehicleDiagramHeadDTO vehicleDiagramHeadDTO);

    /**
     * 登録された車両ダイアグラムのリストを取得する。
     *
     * @return 車両ダイアグラムのレスポンスDTOのリスト
     */
    List<VehicleDiagramResponseDTO> getRegisterVehicleDiagrams();

    /**
     * 指定されたヘッドIDに基づいて車両ダイアグラムを取得する。
     *
     * @param headId 車両ダイアグラムヘッドのID
     * @return 車両ダイアグラムのレスポンスDTOのリスト
     */
    List<VehicleDiagramResponseDTO> getVehicleDiagramsByHeadId(Long headId);

    /**
     * 指定されたヘッドIDに基づいて車両ダイアグラムを削除する。
     *
     * @param headId 車両ダイアグラムヘッドのID
     */
    void deleteVehicleDiagramsByHeadId(Long headId);

    /**
     * 車両ダイアグラムをIDで取得する。
     *
     * @param vehicleDiagramId 車両ダイアグラムのID
     * @param haveTrailer      トレーラーの有無
     * @return 車両ダイアグラムのレスポンスDTO
     */
    VehicleDiagramResponseDTO getVehicleDiagramById(Long vehicleDiagramId, boolean haveTrailer);

    /**
     * 車両ダイアグラムのリストを取得する。
     *
     * @param pageable     ページ情報
     * @param trailer      トレーラーの有無
     * @param tripName     旅行名
     * @param startDate    開始日
     * @param endDate      終了日
     * @param deparuteFrom 出発地
     * @param arrivalTo    到着地
     * @return 車両ダイアグラムのリスト
     */
    VehicleDiagramListResponse getVehicleDiagrams(Pageable pageable, boolean trailer, String tripName, String startDate,
        String endDate, String deparuteFrom, String arrivalTo);

    /**
     * 能力に基づいて車両ダイアグラムを取得する。
     *
     * @param abilityId          能力ID
     * @param startDate          開始日
     * @param endDate            終了日
     * @param tripName           旅行名
     * @param statusList         ステータスリスト
     * @param pageable           ページ情報
     * @param httpServletRequest HTTPリクエスト
     * @return 車両ダイアグラムのレスポンスDTO
     */
    VehicleDiagramItemByAbilityResponseDTO getVehicleDiagramByAbility(Long abilityId, String startDate, String endDate,
        String tripName, String statusList, Integer temperatureRange, Pageable pageable,
        HttpServletRequest httpServletRequest);
}
