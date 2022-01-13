package entity;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.util.Date;

@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Registration implements SuperEntity {
    @Id
    @Column(name = "r_rid",nullable = false)
    private String r_id;
    private String date;
    private double fee;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "student_Id" ,referencedColumnName = "s_id",nullable = false)
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_Id" ,referencedColumnName = "c_id",nullable = false)
    private Course course;

    public Registration() {
    }

    public Registration(String r_id, String date, double fee, Student student, Course course) {
        this.setR_id(r_id);
        this.setDate(date);
        this.setFee(fee);
        this.setStudent(student);
        this.setCourse(course);
    }


    public String getR_id() {
        return r_id;
    }

    public void setR_id(String r_id) {
        this.r_id = r_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
