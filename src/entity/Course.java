package entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Course implements SuperEntity {
    @Id
    private String c_id;
    private String c_name;
    private String duration;
    private double fee;

    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    private List<Registration> registrations=new ArrayList<>();

    public Course() {
    }

    public Course(String c_id, String c_name, String duration, double fee) {
        this.setC_id(c_id);
        this.setC_name(c_name);
        this.setDuration(duration);
        this.setFee(fee);
    }

    public Course(String c_id) {
        this.setC_id(c_id);
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Double.compare(course.getFee(), getFee()) == 0 &&
                Objects.equals(getC_id(), course.getC_id()) &&
                Objects.equals(getC_name(), course.getC_name()) &&
                Objects.equals(getDuration(), course.getDuration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getC_id(), getC_name(), getDuration(), getFee());
    }

    @Override
    public String toString() {
        return "Course{" +
                "c_id='" + c_id + '\'' +
                ", c_name='" + c_name + '\'' +
                ", duration='" + duration + '\'' +
                ", fee=" + fee +
                '}';
    }
}
