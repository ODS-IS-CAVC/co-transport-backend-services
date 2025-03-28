package nlj.business.carrier.link.service;

import nlj.business.carrier.link.dto.commonBody.request.CommonRequestDTO;
import nlj.business.carrier.link.dto.commonBody.response.CommonResponseDTO;

/**
 * <PRE>
 * 車両情報サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleInfoService {

    /**
     * 車両タイプで車両情報を検索.<BR>
     *
     * @param vehicleType 車両タイプ
     * @return 車両情報DTO
     */
    CommonResponseDTO searchVehicleByVehicleType(String vehicleType);

    /**
     * 車両情報を登録または更新.<BR>
     *
     * @param commonRequestDTO 共通リクエストDTO
     */
    void registerOrUpdateVehicleInfo(CommonRequestDTO commonRequestDTO);

    /**
     * 車両情報を削除.<BR>
     *
     * @param commonRequestDTO 共通リクエストDTO
     */
    void delete(CommonRequestDTO commonRequestDTO);

    void deleteVehicle(String giai);
}
