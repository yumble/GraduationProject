package dorosee.initial.hello.repository;

import dorosee.initial.hello.entity.Hello;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelloRepository extends JpaRepository<Hello, Integer> {
}
