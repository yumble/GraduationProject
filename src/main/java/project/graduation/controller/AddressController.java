package project.graduation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import project.graduation.config.resultform.ResultResponse;
import project.graduation.dto.AddressDto;
import project.graduation.dto.FloorDto;
import project.graduation.entity.Address;
import project.graduation.service.AddressService;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/buildings")
@RestController
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    public ResultResponse<List<AddressDto>> getBuildingsInfo(@RequestParam(defaultValue = "1") Integer page,
                                                         @RequestParam(defaultValue = "10") Integer size) {
        Page<AddressDto> buildingList = addressService.getBuildingsInfo(page, size);
        return new ResultResponse<>(null, buildingList.getContent(),
                Map.of("totalCount", buildingList.getTotalElements(),
                        "totalPage", buildingList.getTotalPages()
        ));
    }
    @GetMapping("/{addressId}")
    public ResultResponse<FloorDto> getBuildingDetail(@PathVariable(value = "addressId") String addressId) {
        return new ResultResponse<>( addressService.getBuildingInfo(addressId), null);
    }

}
