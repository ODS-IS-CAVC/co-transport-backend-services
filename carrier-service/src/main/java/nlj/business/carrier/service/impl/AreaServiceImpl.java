package nlj.business.carrier.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.dto.areaLocation.Prefecture;
import nlj.business.carrier.dto.areaLocation.response.AreaLocationResponseDTO;
import nlj.business.carrier.repository.AreaMasterRepository;
import nlj.business.carrier.repository.LocationMasterRepository;
import nlj.business.carrier.service.AreaService;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * 輸送計画アイテムサービス実装クラス
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

    private final AreaMasterRepository areaMasterRepository;
    private final LocationMasterRepository locationMasterRepository;

    /**
     * 全てのエリアとその都道府県を取得する。
     *
     * @return エリアと都道府県のリスト
     */
    @Override
    public List<AreaLocationResponseDTO> getAllAreaLocation() {
        return areaMasterRepository.findAll().stream()
            .filter(Objects::nonNull)
            .map(area -> {
                AreaLocationResponseDTO dto = new AreaLocationResponseDTO();
                dto.setId(area.getCode());
                dto.setName(area.getName());

                List<Prefecture> prefectures = locationMasterRepository
                    .findLocationMastersByAreaId(area.getId())
                    .stream()
                    .filter(Objects::nonNull)
                    .map(location -> {
                        Prefecture prefecture = new Prefecture();
                        prefecture.setId(location.getCode());
                        prefecture.setName(location.getName());
                        prefecture.setCode(location.getPostalCode());
                        return prefecture;
                    })
                    .toList();

                dto.setPrefectures(prefectures);
                return dto;
            })
            .toList();
    }
}
