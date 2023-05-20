package project.graduation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.graduation.dto.FileGroupDto;


public interface FileGroupRepositoryCustom {
    Page<FileGroupDto> findAllByAddress(String addressId, Pageable pageable);
}
