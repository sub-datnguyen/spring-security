package vn.elca.training.memoryhandsonlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.elca.training.memoryhandsonlab.model.Student;
import vn.elca.training.memoryhandsonlab.model.StudentAudit;
import vn.elca.training.memoryhandsonlab.repository.JpaUtil;
import vn.elca.training.memoryhandsonlab.repository.StudentAuditRepository;
import vn.elca.training.memoryhandsonlab.repository.StudentRepository;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(rollbackFor = Throwable.class)
public class JobService implements InitializingBean {
    private StudentService studentService;
    private StudentRepository studentRepository;
    private JpaUtil jpaUtil;
    private StudentAuditRepository studentAuditRepository;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        List<Pair<Long, Student>> studentsByIdJsonData = parseStudentsFromFile();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
    
        Map<Long, Student> dbStudents = studentRepository.findAllById(studentsByIdJsonData
                                                        .stream()
                                                            .map(Pair::getLeft)
                                                            .collect(Collectors.toList()))
                                                        .stream()
                                                        .collect(Collectors.toMap(Student::getId, student -> student));
        log.info(" === Found {} students from DB", dbStudents.size());
    
        int curLineNumber = 0;
        AtomicInteger nbUpdatedStudents = new AtomicInteger(0);
        AtomicInteger nbNewStudents = new AtomicInteger(0);
        List<StudentAudit> studentAudits = new LinkedList<>();
        for (Pair<Long, Student> jsonRow : studentsByIdJsonData) {
            try {
                Student savedStudent = studentService.updateInfo(jsonRow, dbStudents, nbUpdatedStudents, nbNewStudents,
                        curLineNumber++);
                saveAuditInfo(studentAudits, savedStudent);
            } catch (Exception e) {
                log.error("Error happens when processing data of line {}.", curLineNumber);
            }
        }
        
        log.info(" === inserted {} audit items into DB", studentAudits.size());
        stopWatch.stop();
        buildSummaryLog(stopWatch.getTime(), nbUpdatedStudents, nbNewStudents);
    }
    
    private void saveAuditInfo(List<StudentAudit> studentAudits, Student savedStudent) {
        List<StudentAudit> audits = studentAuditRepository.findAllByStudentId(savedStudent.getId());
        audits.sort(Comparator.comparing(StudentAudit::getCreatedDate).reversed());
        Long prevItemId = Optional.ofNullable(audits.size() > 0 ? audits.iterator().next() : null)
                .map(StudentAudit::getId)
                .orElse(null);
        
        StudentAudit studentAudit = new StudentAudit(null, savedStudent.getId(), savedStudent.getName(),
                savedStudent.getPassportNumber(), new Date(), savedStudent.getAddress().getName(), prevItemId);
        studentAudits.add(studentAuditRepository.save(studentAudit));
        log.info(" === Saved new audit for student ID = {}", savedStudent.getId());
    }
    
    private void buildSummaryLog(Long periodInMilis,AtomicInteger nbUpdatedStudents, AtomicInteger nbNewStudents) {
        log.info("===========================================================");
        log.info("== Finished in {} seconds", periodInMilis / 1000);
        log.info("== Updated {} students", nbUpdatedStudents.get());
        log.info("== Created {} students", nbNewStudents.get());
        log.info("===========================================================");
    }
    
    private List<Pair<Long, Student>> parseStudentsFromFile() throws IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        return IOUtils.readLines(getClass().getResourceAsStream("/STUDENT_DATA.txt"), Charset.forName("UTF-8"))
                .stream().map(line -> {
                    try {
                        return jsonMapper.readValue(line, Student.class);
                    } catch (JsonProcessingException e) {
                        return null;
                    }
                }).map(x -> Pair.of(x.getId(), x)).collect(Collectors.toList());
    }
}
