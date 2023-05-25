package project.graduation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.graduation.dto.CollectDetailDto;
import project.graduation.dto.CollectListDto;

import java.util.UUID;

public interface CollectRepositoryCustom {
    Page<CollectListDto> findAllByAddressId(String addressId, Integer floor, Pageable pageable);
    CollectDetailDto findByCollectId(UUID collectId);
}
