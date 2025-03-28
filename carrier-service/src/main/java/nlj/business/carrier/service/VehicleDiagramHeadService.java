package nlj.business.carrier.service;

import java.util.List;
import nlj.business.carrier.dto.vehicleDiagramHead.request.VehicleDiagramHeadDTO;
import nlj.business.carrier.dto.vehicleDiagramHead.response.VehicleDiagramHeadResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramHead.response.VehicleDiagramStatusResponseDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * <PRE>
 * 車両図ヘッドサービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleDiagramHeadService {

    /**
     * 車両ダイアグラムヘッドを登録する。
     *
     * @param vehicleDiagramHeadDTO 車両ダイアグラムヘッドのDTO
     * @return 登録された車両ダイアグラムヘッドのレスポンスDTO
     */
    VehicleDiagramStatusResponseDTO registerVehicleDiagramHead(VehicleDiagramHeadDTO vehicleDiagramHeadDTO);

    /**
     * 登録された車両ダイアグラムヘッドのリストを取得する。
     *
     * @return 車両ダイアグラムヘッドのレスポンスDTOのリスト
     */
    List<VehicleDiagramHeadResponseDTO> getRegisterVehicleDiagramHeads();

    /**
     * IDによって車両ダイアグラムヘッドを取得する。
     *
     * @param id 車両ダイアグラムヘッドのID
     * @return 車両ダイアグラムヘッドのレスポンスDTO
     */
    VehicleDiagramHeadResponseDTO getVehicleDiagramHeadById(Long id);

    /**
     * データをインポートする。
     *
     * @param file インポートするファイル
     */
    void importData(MultipartFile file);
} 