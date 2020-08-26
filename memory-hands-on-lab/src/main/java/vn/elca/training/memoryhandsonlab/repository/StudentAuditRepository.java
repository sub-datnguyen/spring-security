package vn.elca.training.memoryhandsonlab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.elca.training.memoryhandsonlab.model.StudentAudit;

import java.util.List;

@Repository
public interface StudentAuditRepository extends JpaRepository<StudentAudit, Long> {
    List<StudentAudit> findAllByStudentId(Long studentId);
}
