package vn.elca.training.memoryhandsonlab.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.StatelessSession;
import org.hibernate.dialect.Sybase11Dialect;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import vn.elca.training.memoryhandsonlab.model.Student;
import vn.elca.training.memoryhandsonlab.model.StudentAudit;
import vn.elca.training.memoryhandsonlab.repository.JpaUtil;
import vn.elca.training.memoryhandsonlab.repository.StudentAuditRepository;
import vn.elca.training.memoryhandsonlab.repository.StudentRepository;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(rollbackFor = Throwable.class)
public class StudentService {
    private StudentRepository studentRepository;
    private StudentAuditRepository studentAuditRepository;
    private JpaUtil jpaUtil;
    
    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    public Student updateInfo(Pair<Long, Student> jsonRow, Map<Long, Student> dbStudents,
                              AtomicInteger nbUpdatedStudents, AtomicInteger nbNewStudents,
                              int curLineNumber) {
        Student savedStudent = null;
        if (dbStudents.containsKey(jsonRow.getKey())) {
            Student dbStudent = dbStudents.get(jsonRow.getKey());
            dbStudent.updateDataFromJson(jsonRow.getValue());
            savedStudent = studentRepository.save(dbStudent);
            log.info(" === Updated info of student with ID {} for line {}", dbStudent.getId(), curLineNumber);
            nbUpdatedStudents.incrementAndGet();
            
        } else {
            Student newStudent = new Student();
            newStudent.updateDataFromJson(jsonRow.getValue());
            log.info(" === New student with ID {} added for line {}.", newStudent.getId(), curLineNumber);
            savedStudent = studentRepository.save(newStudent);
            nbNewStudents.incrementAndGet();
        }
        return savedStudent;
    }
}



