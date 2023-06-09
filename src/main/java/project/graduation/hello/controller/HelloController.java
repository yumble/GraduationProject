package project.graduation.hello.controller;

import org.springframework.web.bind.annotation.PostMapping;
import project.graduation.config.resultform.ResultException;
import project.graduation.config.resultform.ResultResponse;
import project.graduation.config.resultform.ResultResponseStatus;
import project.graduation.hello.dto.HelloDto;
import project.graduation.hello.entity.Hello;
import project.graduation.hello.repository.HelloRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/hello")
@RequiredArgsConstructor
public class HelloController {

    private final HelloRepository helloRepository;
    @GetMapping
    public Map<String, Object> hello() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", "hello");
        map.put("timestamp", System.currentTimeMillis());
        map.put("date", new Date());
        map.put("null", null);
        return map;
    }
    @GetMapping("/test")
    public ResultResponse<Map<String, Object>> test() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", "hello");
        map.put("timestamp", System.currentTimeMillis());
        map.put("date", new Date());
        map.put("null", null);
        return new ResultResponse<>(map, null);
    }
    @GetMapping("/test2")
    public ResultResponse<List<Hello>> test2() {
        Map<String, Object> map = new LinkedHashMap<>();

        List<Hello> byId = helloRepository.findAll();
        return new ResultResponse<>(null, byId);
    }

    @GetMapping("/test3")
    public ResultResponse<List<HelloDto>> test3() {
        Map<String, Object> map = new LinkedHashMap<>();

        List<HelloDto> helloDtos = helloRepository.findAll()
                .stream()
                .map(HelloDto::new)
                .collect(Collectors.toList());
        return new ResultResponse<>(null, helloDtos);
    }

    @GetMapping("/error-test")
    public ResultResponse<String> errorTest() {
        if(true) {
            throw new ResultException(ResultResponseStatus.NOT_FOUND);
        }

        Map<String, Object> map = new LinkedHashMap<>();
        return new ResultResponse<>("aaa", null);
    }
    @PostMapping
    public ResultResponse<String> postTest(){
        return new ResultResponse<>("success", null);
    }
}
