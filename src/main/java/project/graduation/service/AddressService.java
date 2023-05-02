package project.graduation.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.graduation.dto.AddressDto;
import project.graduation.repository.AddressRepository;

@Transactional(readOnly = true)
@Slf4j
@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Page<AddressDto> getBuildingsInfo(Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        return addressRepository.findAll(pageable)
                .map(AddressDto::new);
    }

}
