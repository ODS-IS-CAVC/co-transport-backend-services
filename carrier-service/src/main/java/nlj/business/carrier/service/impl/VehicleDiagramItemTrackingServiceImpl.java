package nlj.business.carrier.service.impl;

import com.next.logistic.util.NextWebUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.MessageConstant;
import nlj.business.carrier.domain.VehicleDiagram;
import nlj.business.carrier.domain.VehicleDiagramAllocation;
import nlj.business.carrier.domain.VehicleDiagramItem;
import nlj.business.carrier.domain.VehicleDiagramItemOperationTracking;
import nlj.business.carrier.domain.VehicleDiagramItemTracking;
import nlj.business.carrier.domain.VehicleDiagramItemTrailer;
import nlj.business.carrier.domain.VehicleDiagramMobilityHub;
import nlj.business.carrier.domain.VehicleInfo;
import nlj.business.carrier.dto.vehicleDiagramAllocation.response.VehicleDiagramAllocationResDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemTrackingDetailsResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramItem.response.VehicleDiagramItemTrackingResponseDTO;
import nlj.business.carrier.dto.vehicleDiagramItemTrailer.response.VehicleDiagramItemTrailerDTO;
import nlj.business.carrier.dto.vehicleDiagramMobilityHub.response.VehicleDiagramMobilityHubResponseDTO;
import nlj.business.carrier.mapper.VehicleDiagramItemMapper;
import nlj.business.carrier.mapper.VehicleDiagramItemOperationTrackingMapper;
import nlj.business.carrier.mapper.VehicleDiagramItemTrackingMapper;
import nlj.business.carrier.mapper.VehicleDiagramItemTrailerMapper;
import nlj.business.carrier.mapper.VehicleDiagramMobilityHubMapper;
import nlj.business.carrier.mapper.VehicleInfoMapper;
import nlj.business.carrier.repository.VehicleDiagramAllocationRepository;
import nlj.business.carrier.repository.VehicleDiagramItemOperationTrackingRepository;
import nlj.business.carrier.repository.VehicleDiagramItemRepository;
import nlj.business.carrier.repository.VehicleDiagramItemTrackingRepository;
import nlj.business.carrier.repository.VehicleDiagramItemTrailerRepository;
import nlj.business.carrier.repository.VehicleDiagramMobilityHubRepository;
import nlj.business.carrier.repository.VehicleInfoRepository;
import nlj.business.carrier.service.VehicleDiagramItemTrackingService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 車両ダイアグラム明細追跡サービスの実装クラス
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class VehicleDiagramItemTrackingServiceImpl implements VehicleDiagramItemTrackingService {

    private final VehicleDiagramItemTrackingRepository vehicleDiagramItemTrackingRepository;
    private final VehicleDiagramItemOperationTrackingRepository vehicleDiagramItemOperationTrackingRepository;
    private final VehicleDiagramItemRepository vehicleDiagramItemRepository;
    private final VehicleDiagramItemTrackingMapper vehicleDiagramItemTrackingMapper;
    private final VehicleDiagramItemOperationTrackingMapper vehicleDiagramItemOperationTrackingMapper;
    private final VehicleDiagramItemMapper vehicleDiagramItemMapper;
    private final VehicleDiagramAllocationRepository vehicleDiagramAllocationRepository;
    private final VehicleDiagramItemTrailerRepository vehicleDiagramItemTrailerRepository;
    private final VehicleDiagramMobilityHubMapper vehicleDiagramMobilityHubMapper;
    private final VehicleDiagramMobilityHubRepository vehicleDiagramMobilityHubRepository;
    private final VehicleDiagramItemTrailerMapper vehicleDiagramItemTrailerMapper;
    private final VehicleInfoRepository vehicleInfoRepository;
    private final VehicleInfoMapper vehicleInfoMapper;

    /**
     * 車両ダイアグラム明細の追跡情報を取得する。
     *
     * @param itemId             車両ダイアグラム明細のID
     * @param httpServletRequest HTTPリクエスト
     * @return 車両ダイアグラム明細追跡レスポンスDTO
     */
    @Override
    @Transactional(readOnly = true)
    public VehicleDiagramItemTrackingResponseDTO getVehicleDiagramItemTracking(Long itemId,
        HttpServletRequest httpServletRequest) {
        List<VehicleDiagramItemTracking> vehicleDiagramItemTracking = vehicleDiagramItemTrackingRepository.findAllByVehicleDiagramItemId(
            itemId);
        if (Objects.isNull(vehicleDiagramItemTracking)) {
            NextWebUtil.throwCustomException(HttpStatus.NOT_FOUND, MessageConstant.System.NOT_FOUND,
                "vehicle_diagram_item_id");
        }
        VehicleDiagramItem vehicleDiagramItem = vehicleDiagramItemRepository.findVehicleDiagramItemById(itemId);
        if (Objects.isNull(vehicleDiagramItem)) {
            NextWebUtil.throwCustomException(HttpStatus.NOT_FOUND, MessageConstant.System.NOT_FOUND,
                "vehicle_diagram_item_id");
        }
        VehicleDiagram vehicleDiagram = vehicleDiagramItem.getVehicleDiagram();

        List<VehicleDiagramItemTrackingDetailsResponseDTO> vehicleDiagramItemTrackingResponseDTO =
            vehicleDiagramItemTracking.stream()
                .map(vehicleDiagramItemTrackingMapper::toVehicleDiagramItemTrackingResponseDTO)
                .sorted(Comparator.comparing(VehicleDiagramItemTrackingDetailsResponseDTO::getOperationDate)
                    .thenComparing(VehicleDiagramItemTrackingDetailsResponseDTO::getOperationTime))
                .toList();
        VehicleDiagramItemTrackingResponseDTO responseDTO = vehicleDiagramItemMapper.toVehicleDiagramItemTrackingDTO(
            vehicleDiagramItem);
        if (vehicleDiagram != null) {
            responseDTO.setArrivalTo(vehicleDiagram.getArrivalTo());
            responseDTO.setDepartureFrom(vehicleDiagram.getDepartureFrom());
        }

        responseDTO.setItemTrackingDetails(vehicleDiagramItemTrackingResponseDTO);

        List<VehicleDiagramAllocation> allocations = vehicleDiagramAllocationRepository.findAllByVehicleDiagramId(
            vehicleDiagramItem.getVehicleDiagram().getId());
        if (!allocations.isEmpty()) {
            List<VehicleDiagramAllocationResDTO> allocationDTOs = allocations.stream()
                .map(this::mapToAllocationResDTO)
                .toList();
            responseDTO.setVehicleDiagramAllocations(allocationDTOs);
        }

        List<VehicleDiagramItemTrailer> trailers = vehicleDiagramItemTrailerRepository.findAllByVehicleDiagramItemId(
            itemId);
        if (!trailers.isEmpty()) {
            List<VehicleDiagramItemTrailerDTO> trailerDTOList = trailers.stream()
                .map(vehicleDiagramItemTrailerMapper::toVehicleDiagramItemTrailerDto)
                .toList();
            responseDTO.setVehicleDiagramItemTrailers(trailerDTOList);
        }
        List<VehicleDiagramMobilityHub> vehicleDiagramMobilityHubs = vehicleDiagramMobilityHubRepository.findAllByVehicleDiagramItemId(
            itemId);
        if (!vehicleDiagramMobilityHubs.isEmpty()) {
            List<VehicleDiagramMobilityHubResponseDTO> vehicleDiagramMobilityHubResponseDTOList = vehicleDiagramMobilityHubs.stream()
                .map(vehicleDiagramMobilityHubMapper::toVehicleDiagramMobilityHubResponseDTO).toList();
            responseDTO.setVehicleDiagramMobilityHub(vehicleDiagramMobilityHubResponseDTOList);
        }

        return responseDTO;
    }

    /**
     * 車両ダイアグラム明細の操作追跡情報を取得する。
     *
     * @param itemId             車両ダイアグラム明細のID
     * @param httpServletRequest HTTPリクエスト
     * @return 車両ダイアグラム明細追跡レスポンスDTO
     */
    @Override
    @Transactional(readOnly = true)
    public VehicleDiagramItemTrackingResponseDTO getOperationTracking(Long itemId,
        HttpServletRequest httpServletRequest) {
        List<VehicleDiagramItemOperationTracking> vehicleDiagramItemTracking = vehicleDiagramItemOperationTrackingRepository.findAllByVehicleDiagramItemId(
            itemId);
        if (Objects.isNull(vehicleDiagramItemTracking)) {
            NextWebUtil.throwCustomException(HttpStatus.NOT_FOUND, MessageConstant.System.NOT_FOUND,
                "vehicle_diagram_item_id");
        }
        VehicleDiagramItem vehicleDiagramItem = vehicleDiagramItemRepository.findVehicleDiagramItemById(itemId);
        if (Objects.isNull(vehicleDiagramItem)) {
            NextWebUtil.throwCustomException(HttpStatus.NOT_FOUND, MessageConstant.System.NOT_FOUND,
                "vehicle_diagram_item_id");
        }
        VehicleDiagram vehicleDiagram = vehicleDiagramItem.getVehicleDiagram();

        List<VehicleDiagramItemTrackingDetailsResponseDTO> vehicleDiagramItemTrackingResponseDTO =
            vehicleDiagramItemTracking.stream()
                .map(vehicleDiagramItemOperationTrackingMapper::toVehicleDiagramItemOperationTrackingResponseDTO)
                .sorted(Comparator.comparing(VehicleDiagramItemTrackingDetailsResponseDTO::getOperationDate)
                    .thenComparing(VehicleDiagramItemTrackingDetailsResponseDTO::getOperationTime))
                .toList();
        VehicleDiagramItemTrackingResponseDTO responseDTO = vehicleDiagramItemMapper.toVehicleDiagramItemTrackingDTO(
            vehicleDiagramItem);
        if (vehicleDiagram != null) {
            responseDTO.setArrivalTo(vehicleDiagram.getArrivalTo());
            responseDTO.setDepartureFrom(vehicleDiagram.getDepartureFrom());
        }

        responseDTO.setItemTrackingDetails(vehicleDiagramItemTrackingResponseDTO);

        List<VehicleDiagramAllocation> allocations = vehicleDiagramAllocationRepository.findAllByVehicleDiagramId(
            vehicleDiagramItem.getVehicleDiagram().getId());
        if (!allocations.isEmpty()) {
            List<VehicleDiagramAllocationResDTO> allocationDTOs = allocations.stream()
                .map(this::mapToAllocationResDTO)
                .toList();
            responseDTO.setVehicleDiagramAllocations(allocationDTOs);
        }

        List<VehicleDiagramItemTrailer> trailers = vehicleDiagramItemTrailerRepository.findAllByVehicleDiagramItemId(
            itemId);
        if (!trailers.isEmpty()) {
            List<VehicleDiagramItemTrailerDTO> trailerDTOList = trailers.stream()
                .map(vehicleDiagramItemTrailerMapper::toVehicleDiagramItemTrailerDto)
                .toList();
            responseDTO.setVehicleDiagramItemTrailers(trailerDTOList);
        }
        List<VehicleDiagramMobilityHub> vehicleDiagramMobilityHubs = vehicleDiagramMobilityHubRepository.findAllByVehicleDiagramItemId(
            itemId);
        if (!vehicleDiagramMobilityHubs.isEmpty()) {
            List<VehicleDiagramMobilityHubResponseDTO> vehicleDiagramMobilityHubResponseDTOList = vehicleDiagramMobilityHubs.stream()
                .map(vehicleDiagramMobilityHubMapper::toVehicleDiagramMobilityHubResponseDTO).toList();
            responseDTO.setVehicleDiagramMobilityHub(vehicleDiagramMobilityHubResponseDTOList);
        }

        return responseDTO;
    }

    /**
     * 車両ダイアグラムの割り当てをレスポンスDTOにマッピングする。
     *
     * @param allocation 車両ダイアグラムの割り当て
     * @return 車両ダイアグラム割り当てレスポンスDTO
     */
    private VehicleDiagramAllocationResDTO mapToAllocationResDTO(VehicleDiagramAllocation allocation) {
        VehicleDiagramAllocationResDTO allocationResDTO = new VehicleDiagramAllocationResDTO();
        Optional<VehicleInfo> vehicleInfoOptional = vehicleInfoRepository.findById(allocation.getVehicleInfoId());
        vehicleInfoOptional.ifPresent(
            vehicleInfo -> allocationResDTO.setVehicleInfoResponseDTO(vehicleInfoMapper.toResponseDto(vehicleInfo)));
        allocationResDTO.setId(allocation.getId());
        allocationResDTO.setVehicleType(allocation.getVehicleType());
        allocationResDTO.setDisplayOrder(allocation.getDisplayOrder());
        allocationResDTO.setAssignType(allocation.getAssignType());
        return allocationResDTO;
    }
}
