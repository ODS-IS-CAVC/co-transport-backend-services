package nlj.business.carrier.repository;

import nlj.business.carrier.domain.VehicleInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <PRE>
 * 車両情報リポジトリ。<BR>
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Repository
public interface VehicleInfoRepository extends JpaRepository<VehicleInfo, Long>, VehicleInfoCustomRepository {

    /**
     * 運行者IDで車両情報を検索する。
     *
     * @param operatorId 運行者ID
     * @param pageable   ページング情報
     * @return 車両情報のページ
     */
    Page<VehicleInfo> findAllByOperatorId(String operatorId, Pageable pageable);

    /**
     * 車両IDと運行者IDで車両情報を検索する。
     *
     * @param id         車両ID
     * @param operatorId 運行者ID
     * @return 車両情報
     */
    VehicleInfo findByIdAndOperatorId(Long id, String operatorId);

    /**
     * 車両IDで車両情報を検索する。
     *
     * @param id 車両ID
     * @return 車両情報
     */
    VehicleInfo findVehicleInfoById(Long id);

    /**
     * 登録エリアコード、登録文字、登録グループ番号、登録番号1、ガイアで車両情報が存在するかを検索する。
     *
     * @param registrationAreaCode    登録エリアコード
     * @param registrationCharacter   登録文字
     * @param registrationGroupNumber 登録グループ番号
     * @param registrationNumber1     登録番号1
     * @param giai                    ガイア
     * @return 車両情報が存在するか
     */
    Boolean existsVehicleInfoByRegistrationAreaCodeAndRegistrationCharacterAndRegistrationGroupNumberAndRegistrationNumber1OrGiai(
        String registrationAreaCode, String registrationCharacter, String registrationGroupNumber,
        String registrationNumber1, String giai);
}
