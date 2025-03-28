package nlj.business.shipper.service;

import java.io.IOException;
import nlj.business.shipper.dto.cargoInfo.CargoInfoResponseDTO;
import nlj.business.shipper.dto.cargoInfo.request.CargoInfoRequest;
import nlj.business.shipper.dto.cargoInfo.response.CargoInfoSearchResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <PRE>
 * 荷物情報サービスインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
public interface CargoInfoService {

    CargoInfoResponseDTO createCargoInfo(CargoInfoRequest request, Long importId);

    void updateCargoInfo(CargoInfoRequest request, Long id);

    CargoInfoResponseDTO getDetailCargoInfo(Long id);

    CargoInfoSearchResponse search(String status, Pageable pageable);

    void importData(MultipartFile file);

    void deleteCargoInfo(Long id);

    byte[] downloadFile(String fileName) throws IOException;

    void uploadTemplateFile(MultipartFile file);
}
