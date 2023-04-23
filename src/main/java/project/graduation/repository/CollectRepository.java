package project.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.graduation.entity.Collect;

import java.util.UUID;

public interface CollectRepository extends JpaRepository<Collect, UUID> {
}