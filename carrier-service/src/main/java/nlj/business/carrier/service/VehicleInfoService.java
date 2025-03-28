package nlj.business.carrier.service;

import java.io.IOException;
import java.util.List;
import nlj.business.carrier.dto.vehicleInfo.request.VehicleInfoRegisterRequest;
import nlj.business.carrier.dto.vehicleInfo.response.VehicleInfoListResponse;
import nlj.business.carrier.dto.vehicleInfo.response.VehicleInfoResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * <PRE>
 * 車両情報サービス。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
public interface VehicleInfoService {

    /**
     * 車両情報を登録する。
     *
     * @param request 車両情報登録リクエスト
     * @param files   アップロードするファイルのリスト
     * @return 登録結果
     */
    VehicleInfoResponse register(VehicleInfoRegisterRequest request, List<MultipartFile> files);

    /**
     * 車両情報を更新する。
     *
     * @param request 車両情報登録リクエスト
     * @param id      更新する車両情報のID
     * @param files   アップロードするファイルのリスト
     */
    void update(VehicleInfoRegisterRequest request, Long id, List<MultipartFile> files);

    /**
     * すべての車両情報を取得する。
     *
     * @param type     車両の種類
     * @param range    範囲
     * @param pageable ページ情報
     * @return 車両情報のリスト
     */
    VehicleInfoListResponse findAll(String type, String range, Pageable pageable);

    /**
     * 指定されたIDの車両情報の詳細を取得する。
     *
     * @param id 車両情報のID
     * @return 車両情報の詳細
     */
    VehicleInfoResponse getDetail(Long id);

    /**
     * 車両情報をインポートする。
     *
     * @param file インポートするファイル
     */
    void importData(MultipartFile file);

    /**
     * 指定されたIDの車両情報を削除する。
     *
     * @param id 車両情報のID
     */
    void deleteVehicleInfo(Long id);

    /**
     * 指定されたファイル名のファイルをダウンロードする。
     *
     * @param fileName ダウンロードするファイル名
     * @return ファイルのバイト配列
     * @throws IOException 入出力例外
     */
    byte[] downloadFile(String fileName) throws IOException;

    /**
     * テンプレートファイルをアップロードする。
     *
     * @param file アップロードするファイル
     */
    void uploadTemplateFile(MultipartFile file);

}
