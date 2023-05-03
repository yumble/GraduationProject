package project.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.graduation.entity.Collect;
import project.graduation.entity.Program;

import java.util.Optional;
import java.util.UUID;

public interface ProgramRepository extends JpaRepository<Program, UUID> {
    Program findByRoutingKey(String routingKey);
    Optional<Program> findByPriority(Integer priority);
}