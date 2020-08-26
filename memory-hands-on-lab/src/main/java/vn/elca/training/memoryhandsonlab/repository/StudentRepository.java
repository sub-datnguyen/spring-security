package vn.elca.training.memoryhandsonlab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import vn.elca.training.memoryhandsonlab.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, QuerydslPredicateExecutor<Student>, StudentCustomRepository {
}
