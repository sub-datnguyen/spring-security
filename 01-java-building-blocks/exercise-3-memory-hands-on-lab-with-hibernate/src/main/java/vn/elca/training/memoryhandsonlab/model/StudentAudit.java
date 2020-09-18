package vn.elca.training.memoryhandsonlab.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "STUDENT_AUDIT")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "STUDENT_ID")
    private Long studentId;
    private String name;
    @Column(name = "PASSPORT_NUMBER")
    private String passportNumber;
    @Column(name = "CREATED_DATE")
    private Date createdDate;
    private String address;
    @Column(name = "PREVIOUS_CHANGE_ID")
    private Long previousChangeId;
}
