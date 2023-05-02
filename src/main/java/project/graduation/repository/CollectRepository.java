package project.graduation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.graduation.entity.Collect;

import java.util.List;
import java.util.UUID;

public interface CollectRepository extends JpaRepository<Collect, UUID>, CollectRepositoryCustom {

}