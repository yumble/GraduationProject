package project.graduation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.graduation.dto.CollectResponseDto;

import java.util.UUID;

public interface CollectRepositoryCustom {
    Page<CollectResponseDto> findAllByAddressId(String addressId, Pageable pageable);
    CollectResponseDto findByCollectId(UUID collectId);
}
