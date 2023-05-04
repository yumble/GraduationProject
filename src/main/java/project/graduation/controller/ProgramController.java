package project.graduation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.graduation.dto.ProgramDto;
import project.graduation.entity.Program;
import project.graduation.repository.ProgramRepository;

@Slf4j
@RequestMapping("/programs")
@RestController
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramRepository programRepository;
    @PostMapping
    public void saveProgram(@RequestBody ProgramDto programDto){
        Program program = new Program(programDto);
        programRepository.save(program);
    }
}