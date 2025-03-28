package nlj.business.shipper.dto.request;

import lombok.Data;
import nlj.business.shipper.aop.proxy.ValidateField;
import nlj.business.shipper.constant.ParamConstant;
import org.springframework.web.multipart.MultipartFile;

/**
 * <PRE>
 * 輸送計画ファイルリクエストDTO。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Data
public class TransportPlanFileRequestDTO {

    @ValidateField(notNull = true, fileType = ParamConstant.TransportPlanBulk.FILE_TYPE, maxFileSize = ParamConstant.TransportPlanBulk.MAX_FILE_SIZE)
    private MultipartFile file;
}
