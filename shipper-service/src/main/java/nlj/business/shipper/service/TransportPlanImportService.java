package nlj.business.shipper.service;

import nlj.business.shipper.dto.TransportPlanImportDTO;
import nlj.business.shipper.dto.request.TransportPlanFileRequestDTO;

/**
 * <PRE>
 * 輸送計画インポートサービスインターフェース。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface TransportPlanImportService {

    TransportPlanImportDTO createTransportPlanBulk(TransportPlanFileRequestDTO transportPlanFileRequestDTO);
} 