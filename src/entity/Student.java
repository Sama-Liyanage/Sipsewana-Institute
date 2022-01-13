package entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Student implements SuperEntity {
    @Id
    private String s_id;
    private String s_name;
    private String s_address;
    private String s_DOB;
    private String s_contact_no;
    private String s_gender;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Registration> registrations = new ArrayList<>();

    public Student() {
    }

    public Student(String s_id, String s_name, String s_address, String s_DOB, String s_contact_no, String s_gender) {
        this.s_id = s_id;
        this.s_name = s_name;
        this.s_address = s_address;
        this.s_DOB = s_DOB;
        this.s_contact_no = s_contact_no;
        this.s_gender = s_gender;
    }

    public Student(String s_id) {
        this.setS_id(s_id);
    }




    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_address() {
        return s_address;
    }

    public void setS_address(String s_address) {
        this.s_address = s_address;
    }

    public String getS_DOB() {
        return s_DOB;
    }

    public void setS_DOB(String s_DOB) {
        this.s_DOB = s_DOB;
    }

    public String getS_contact_no() {
        return s_contact_no;
    }

    public void setS_contact_no(String s_contact_no) {
        this.s_contact_no = s_contact_no;
    }

    public String getS_gender() {
        return s_gender;
    }

    public void setS_gender(String s_gender) {
        this.s_gender = s_gender;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }
}