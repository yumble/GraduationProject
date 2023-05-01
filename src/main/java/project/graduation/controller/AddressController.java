package project.graduation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import project.graduation.config.resultform.ResultResponse;
import project.graduation.dto.AddressDto;
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
    public ResultResponse<List<AddressDto>> getBuildings() {
        Page<AddressDto> buildingList = addressService.getBuildings();
        return new ResultResponse<>(null, buildingList.getContent(),
                Map.of("totalCount", buildingList.getTotalElements(),
                        "totalPage", buildingList.getTotalPages()
        ));
    }

}
