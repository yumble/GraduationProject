package project.graduation.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.graduation.config.resultform.ResultException;
import project.graduation.config.resultform.ResultResponseStatus;
import project.graduation.entity.*;
import project.graduation.repository.ProgramRepository;

import java.io.*;
import java.util.Map;
import java.util.Optional;

@Transactional(readOnly = true)
@Slf4j
@Service
@AllArgsConstructor
public class ProgramService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final ProgramRepository programRepository;

    @RabbitListener(queues = "typers-filter-queue")
    public void receiveMessage(Map<String, Object> map, Message message) throws IOException, InterruptedException {
        program(message.getMessageProperties().getReceivedRoutingKey(), (String) map.get("fileId"));
    }

    @Transactional
    public void program(String routingKey, String fileId) throws IOException, InterruptedException {

        Program program = programRepository.findByRoutingKey(routingKey);

        Thread programThread = new Thread(()-> {
            try{
                ProcessBuilder builder = new ProcessBuilder(program.getCommandPath(), fileId); //"./ply2pcd"
                builder.redirectErrorStream(true);
                builder.directory(new File(program.getArguments())); //"/root/pcl_ply2pcd/build" arguments -> directory
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

                Optional<Program> nextProgram = programRepository.findByPriority(program.getPriority() + 1);
                //arguments는 next Routing Key
                nextProgram.ifPresent(value -> rabbitTemplate.convertAndSend("typers.filter", value.getRoutingKey(), Map.of("fileId", fileId)));

            } catch (IOException | InterruptedException e) {
                throw new ResultException(ResultResponseStatus.NOT_FOUND);
            }
        });
        programThread.start();
        //type -> Routing key
        // argumentS -> 뒤에 붙는 파일이름 or directory
    }
}
