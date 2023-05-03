package project.graduation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.graduation.config.resultform.ResultException;
import project.graduation.config.resultform.ResultResponse;
import project.graduation.dto.AddressDto;
import project.graduation.dto.CollectDto;
import project.graduation.dto.GPSDto;
import project.graduation.service.CollectService;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static project.graduation.config.resultform.ResultResponseStatus.REQUEST_ERROR;

@Slf4j
@RequestMapping("/programs")
@RestController
@RequiredArgsConstructor
public class ProgramController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public ResultResponse<String> program(@RequestParam String fileId) throws IOException, InterruptedException {

        List<String> cmd = new ArrayList<>();
        cmd.add("./ply2pcd");
        cmd.add(fileId);

        ProcessBuilder builder = new ProcessBuilder(cmd);
        builder.redirectErrorStream(true);
        builder.directory(new File("/root/pcl_ply2pcd/build"));
        Process process = builder.start();

        // 프로세스의 출력을 읽는 코드
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;

        // 프로세스의 출력을 콘솔에 출력하는 코드
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }

        // 프로세스의 종료를 기다리는 코드
        int exitCode = process.waitFor();
        System.out.println("Exit code: " + exitCode);

        rabbitTemplate.convertAndSend("typers.filter", "typers.filter.ply2pcd", Map.of("fileId", fileId));

        return new ResultResponse<>(fileId, null);
    }

    @RabbitListener(queues = "typers.filter.ply2pcd")
    public void receiveMessage(Map<String, Object> map) throws IOException, InterruptedException {
        program2((String) map.get("fileId"));
    }
    @RabbitListener(queues = "typers.filter.outlier")
    public void receiveMessage2(Map<String, Object> map) throws IOException, InterruptedException {
        System.out.println("map.get(\"fileId\") = " + map.get("fileId"));
    }

    public ResultResponse<String> program2(String fileId) throws IOException, InterruptedException {

        List<String> cmd = new ArrayList<>();
        cmd.add("./filter_test");
        cmd.add(fileId);

        ProcessBuilder builder = new ProcessBuilder(cmd);
        builder.redirectErrorStream(true);
        builder.directory(new File("/root/ply_filter_test/build"));
        Process process = builder.start();

        // 프로세스의 출력을 읽는 코드
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;

        // 프로세스의 출력을 콘솔에 출력하는 코드
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }

        // 프로세스의 종료를 기다리는 코드
        int exitCode = process.waitFor();
        System.out.println("Exit code: " + exitCode);

        rabbitTemplate.convertAndSend("typers.filter", "typers.filter.outlier", Map.of("fileId", fileId));

        return new ResultResponse<>(fileId, null);
    }
}