package project.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import project.graduation.entity.GeneralFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GeneralFileRepository extends JpaRepository<GeneralFile, UUID> {
}
