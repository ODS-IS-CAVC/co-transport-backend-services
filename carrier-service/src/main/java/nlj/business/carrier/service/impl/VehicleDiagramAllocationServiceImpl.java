package nlj.business.carrier.service.impl;

import com.next.logistic.authorization.User;
import com.next.logistic.authorization.UserContext;
import com.next.logistic.util.BaseUtil;
import com.next.logistic.util.NextWebUtil;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import nlj.business.carrier.constant.MessageConstant;
import nlj.business.carrier.domain.VehicleDiagramAllocation;
import nlj.business.carrier.domain.VehicleDiagramItemTrailer;
import nlj.business.carrier.dto.vehicleDiagramAllocation.request.VehicleDiagramAllocationRequestDTO;
import nlj.business.carrier.dto.vehicleDiagramAllocation.response.VehicleDiagramAllocationResponseDTO;
import nlj.business.carrier.mapper.VehicleDiagramAllocationMapper;
import nlj.business.carrier.repository.VehicleDiagramAllocationRepository;
import nlj.business.carrier.service.VehicleDiagramAllocationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <PRE>
 * 車両ダイアグラム割当サービス実装クラス。
 * </PRE>
 *
 * @author Next Logistics Japan
 */
@Service
@RequiredArgsConstructor
public class VehicleDiagramAllocationServiceImpl implements VehicleDiagramAllocationService {

    private final VehicleDiagramAllocationRepository vehicleDiagramAllocationRepository;
    private final VehicleDiagramAllocationMapper vehicleDiagramAllocationMapper;
    private final EntityManager entityManager;

    @Resource(name = "userContext")
    private final UserContext userContext;

    @Override
    @Transactional
    public void saveVehicleDiagramAllocation(Long vehicleDiagramId,
        List<VehicleDiagramAllocationRequestDTO> vehicleDiagramAllocationRequestDTO) {

        User user = userContext.getUser();
        if (BaseUtil.isNull(user.getCompanyId())) {
            NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST, MessageConstant.System.SYSTEM_ERR_001);
        }
        List<VehicleDiagramAllocation> existedVehicleDiagramAllocation =
            vehicleDiagramAllocationRepository.findAllByVehicleDiagramId(vehicleDiagramId);

        if (existedVehicleDiagramAllocation != null) {
            // Create maps for lookup
            Map<Long, VehicleDiagramAllocation> existingMap = existedVehicleDiagramAllocation.stream()
                .collect(Collectors.toMap(VehicleDiagramAllocation::getId, Function.identity()));

            Map<Long, VehicleDiagramAllocationRequestDTO> newMap = vehicleDiagramAllocationRequestDTO.stream()
                .filter(dto -> dto.getId() != null)
                .collect(Collectors.toMap(VehicleDiagramAllocationRequestDTO::getId, Function.identity()));

            // Handle updates and deletes
            existedVehicleDiagramAllocation.forEach(existing -> {
                if (newMap.containsKey(existing.getId())) {
                    // Update existing allocation
                    existing.setVehicleInfoId(newMap.get(existing.getId()).getVehicleInfoId());
                    existing.setVehicleType(newMap.get(existing.getId()).getVehicleType());
                    existing.setDisplayOrder(newMap.get(existing.getId()).getDisplayOrder());
                    existing.setAssignType(newMap.get(existing.getId()).getAssignType());
                    vehicleDiagramAllocationRepository.save(existing);
                    List<VehicleDiagramItemTrailer> vehicleDiagramItemTrailers = existing.getVehicleDiagramItemTrailers();
                    // update display_order in vehicle_avb_resource_item
                    if (!vehicleDiagramItemTrailers.isEmpty()) {
                        String sqlUpdateVehicleAvbResourceItem =
                            "UPDATE vehicle_avb_resource_item SET display_order = :displayOrder WHERE "
                                + "vehicle_diagram_item_trailer_id IN (:vehicle_diagram_item_trailer_ids)";
                        entityManager.createNativeQuery(sqlUpdateVehicleAvbResourceItem)
                            .setParameter("displayOrder", existing.getDisplayOrder())
                            .setParameter("vehicle_diagram_item_trailer_ids", vehicleDiagramItemTrailers.stream()
                                .map(VehicleDiagramItemTrailer::getId)
                                .toList())
                            .executeUpdate();
                    }
                } else {
                    // Delete allocation that's not in new list
                    checkVehicleDiagramItemTrailerInTransOrder(existing.getVehicleDiagramItemTrailers());
                    vehicleDiagramAllocationRepository.delete(existing);
                    if (!existing.getVehicleDiagramItemTrailers().isEmpty()) {
                        existing.getVehicleDiagramItemTrailers().forEach(vehicleDiagramItemTrailer -> {
                            String sqlUpdateVehicleAvbResourceItem = "DELETE FROM vehicle_avb_resource_item WHERE "
                                + "vehicle_diagram_item_trailer_id = :vehicle_diagram_item_trailer_id";
                            entityManager.createNativeQuery(sqlUpdateVehicleAvbResourceItem)
                                .setParameter("vehicle_diagram_item_trailer_id", vehicleDiagramItemTrailer.getId())
                                .executeUpdate();
                            String sqlUpdateTransMatching = "DELETE FROM t_trans_matching WHERE "
                                + "vehicle_diagram_item_trailer_id = :vehicle_diagram_item_trailer_id";
                            entityManager.createNativeQuery(sqlUpdateTransMatching)
                                .setParameter("vehicle_diagram_item_trailer_id", vehicleDiagramItemTrailer.getId())
                                .executeUpdate();
                        });
                    }
                }
            });

            // Handle new insertions
            vehicleDiagramAllocationRequestDTO.stream()
                .filter(dto -> dto.getId() == null || !existingMap.containsKey(dto.getId()))
                .forEach(newDto -> {
                    VehicleDiagramAllocation newAllocation =
                        vehicleDiagramAllocationMapper.toVehicleDiagramAllocation(newDto);
                    newAllocation.setVehicleDiagramId(vehicleDiagramId);
                    vehicleDiagramAllocationRepository.save(newAllocation);
                });
        } else {
            // If no existing allocations, just insert all new ones
            vehicleDiagramAllocationRequestDTO.forEach(newDto -> {
                VehicleDiagramAllocation newAllocation =
                    vehicleDiagramAllocationMapper.toVehicleDiagramAllocation(newDto);
                newAllocation.setVehicleDiagramId(vehicleDiagramId);
                vehicleDiagramAllocationRepository.save(newAllocation);
            });
        }

        // update display order in vehicle_avb_resource_item

    }

    private void checkVehicleDiagramItemTrailerInTransOrder(
        List<VehicleDiagramItemTrailer> vehicleDiagramItemTrailers) {
        vehicleDiagramItemTrailers.forEach(vehicleDiagramItemTrailer -> {
            String sql = "SELECT id FROM t_trans_order WHERE vehicle_diagram_item_trailer_id = :trailerId";

            List<Object[]> results = entityManager.createNativeQuery(sql)
                .setParameter("trailerId", vehicleDiagramItemTrailer.getId())
                .getResultList();

            if (!results.isEmpty()) {
                NextWebUtil.throwCustomException(HttpStatus.BAD_REQUEST,
                    MessageConstant.System.VEHICLE_DIAGRAM_ITEM_TRAILER_HAVE_IN_TRANS_ORDER);
            }
        });
    }

    @Override
    public List<VehicleDiagramAllocationResponseDTO> getVehicleDiagramAllocation(Long vehicleDiagramId) {
        VehicleDiagramAllocation vehicleDiagramAllocation =
            vehicleDiagramAllocationRepository.findByVehicleDiagramId(vehicleDiagramId);

        if (Objects.isNull(vehicleDiagramAllocation)) {
            return List.of();
        }

        List<VehicleDiagramAllocation> vehicleDiagramAllocations =
            vehicleDiagramAllocationRepository.findAllByVehicleDiagramId(vehicleDiagramId);

        return vehicleDiagramAllocations.stream()
            .map(vehicleDiagramAllocationMapper::toVehicleDiagramAllocationResponseDTO).toList();
    }
}
