package vn.elca.training.memoryhandsonlab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javassist.bytecode.MethodParametersAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"address"})
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "PASSPORT_NUMBER")
    private String passportNumber;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID")
    @JsonIgnore
    private Address address;
    
    @Transient
    private String addressText;
    
    public void updateDataFromJson(Student jsonStudent) {
        setName(jsonStudent.getName());
        setPassportNumber(jsonStudent.getPassportNumber());
        if (getAddress() != null) {
            getAddress().setName(jsonStudent.getAddressText());
        } else {
            Address address = new Address();
            address.setName(jsonStudent.getAddressText());
            setAddress(address);
        }
    }
}