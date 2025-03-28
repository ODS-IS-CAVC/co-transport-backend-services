package nlj.business.carrier.controller;

import com.next.logistic.component.ResponseBuilderComponent;
import com.next.logistic.model.NextResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.APIConstant.CommonDownloadAndUpload;
import nlj.business.carrier.service.VehicleInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <PRE>
 * 共通アップロード・ダウンロードコントローラ.<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@RestController
@RequestMapping(CommonDownloadAndUpload.PREFIX)
@RequiredArgsConstructor
public class CommonUploadAndDownloadController {

    private final ResponseBuilderComponent builderComponent;
    private final VehicleInfoService vehicleInfoService;

    @GetMapping(value = CommonDownloadAndUpload.DOWNLOAD)
    public NextResponseEntity<byte[]> downloadFIle(@RequestParam("fileName") String fileName,
        HttpServletRequest httpServletRequest) throws IOException {
        return new NextResponseEntity<>(
            builderComponent.buildResponse(vehicleInfoService.downloadFile(fileName), httpServletRequest));
    }

    @PostMapping(value = CommonDownloadAndUpload.UPLOAD_TEMPLATE)
    public NextResponseEntity<Void> uploadTemplateFile(@RequestParam(value = "file") MultipartFile file,
        HttpServletRequest httpServletRequest) {
        vehicleInfoService.uploadTemplateFile(file);
        return new NextResponseEntity<>(
            builderComponent.buildResponse(null, httpServletRequest));
    }
}
