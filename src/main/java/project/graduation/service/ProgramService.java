package project.graduation.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.graduation.config.resultform.ResultException;
import project.graduation.dto.MQDto;
import project.graduation.entity.*;
import project.graduation.repository.CollectRepository;
import project.graduation.repository.ProgramRepository;

import java.io.*;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static project.graduation.config.resultform.ResultResponseStatus.UPLOAD_ERROR;
import static project.graduation.controller.RabbitMQConfig.*;

@Transactional(readOnly = true)
@Slf4j
@Service
@AllArgsConstructor
public class ProgramService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final ProgramRepository programRepository;
    private final CollectRepository collectRepository;
    private final GeneralFileService generalFileService;

    @Transactional
    @RabbitListener(queues = QUEUE_NAME)
    public void receiveMessage(Map<String, String> map) {
        try {
            excuteProcess(map.get("routingKey"), map.get("fileId"));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void excuteProcess(String routingKey, String fileId) throws InterruptedException {
        Program program = programRepository.findByRoutingKey(routingKey);
        if (!routingKey.equals(KEY_COMPLETE)) {
            Thread thread = new Thread(() -> {
                try {
                    ProcessBuilder builder = new ProcessBuilder(program.getCommandPath(), fileId);
                    builder.redirectErrorStream(true);
                    builder.directory(new File(program.getDir()));
                    Process process = builder.start();

                    //프로세스의 출력을 읽는 코드
                    //printProcess(process);
                    // 프로세스의 종료를 기다리는 코드
                    waitProcess(process);
                } catch (Exception e) {
                    throw new ResultException(UPLOAD_ERROR);
                }
            });

            thread.start();
            thread.join();
        }

        Optional<Collect> optionalCollect = collectRepository.findByFileId(UUID.fromString(fileId));
        optionalCollect.ifPresent(collect -> {
            collect.modifyByProgram(program);
            if(routingKey.equals(KEY_COMPLETE)) {
                Long totalPoints = updateTotalPoints(collect.getGeneralFile());
                if(totalPoints!=null){
                    collect.updateTotalPoints(totalPoints);
                }

            }
        });

        sendMessage(program.getPriority(), fileId);
    }

    private void printProcess(Process process) throws IOException {
        // 프로세스의 출력을 읽는 코드
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;

        // 프로세스의 출력을 콘솔에 출력하는 코드
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
    }

    private void waitProcess(Process process) throws InterruptedException {
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new ResultException(UPLOAD_ERROR);
        }
    }

    public void sendMessage(Integer priority, String fileId) {
        Optional<Program> nextProgram = programRepository.findByPriority(priority + 1);
        nextProgram.ifPresent(value
                -> rabbitTemplate.convertAndSend(EXCHANGE_NAME, value.getRoutingKey(), new MQDto(value.getRoutingKey(), fileId)));
    }
    public Long updateTotalPoints(GeneralFile generalFile) {
        Long totalPoints = null;
        String filePathStr = generalFileService.getFilePathStr(generalFile);
        try (BufferedReader br = new BufferedReader(new FileReader(filePathStr))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("POINTS")) {
                    totalPoints = Long.parseLong(line.split(" ")[1]);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalPoints;
    }
}
