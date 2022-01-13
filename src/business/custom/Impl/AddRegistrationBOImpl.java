package business.custom.Impl;

import business.custom.AddRegistrationBO;
import dto.CourseDTO;
import dto.RegistrationDTO;
import dto.StudentDTO;
import entity.Course;
import entity.Registration;
import entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.FactoryConfiguration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddRegistrationBOImpl implements AddRegistrationBO {

    private SessionFactory sessionFactory;

    public AddRegistrationBOImpl(){
        this.sessionFactory = FactoryConfiguration.getSessionFactory();
    }


    @Override
    public boolean addRegistration(StudentDTO studentDTO, List<RegistrationDTO> registrationDTOList) throws Exception {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Student student=new Student(studentDTO.getStudentId(),studentDTO.getStudentName(),
                    studentDTO.getStudentAddress(),studentDTO.getStudentDOB(),
                    studentDTO.getStudentContact(),studentDTO.getStudentGender());

            List<Registration> registrationList=new ArrayList<>();

            for (RegistrationDTO registrationDTO : registrationDTOList) {
                registrationList.add(changeReg(registrationDTO));
            }

            for (Registration stuReg : registrationList) {
                student.getRegistrations().add(stuReg);
            }

            for (Registration registration : registrationList) {
                registration.setStudent(student);
            }

            for (Registration registration : registrationList) {
                Serializable addReg = session.save(registration);

                if (addReg==null){
                    session.getTransaction().rollback();

                    return false;
                }

            }
            session.getTransaction().commit();
            return true;

        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

    private Course ChangeCourses(CourseDTO courseDTO){
        return new Course(
                courseDTO.getCourseId(),
                courseDTO.getCourseName(),
                courseDTO.getDuration(),
                courseDTO.getCourseFee()
        );
    }
    private Registration changeReg(RegistrationDTO registrationDTO){
        return new Registration(
                registrationDTO.getRegId(),
                registrationDTO.getRegDate(),
                registrationDTO.getRegFee(),
                null,
                ChangeCourses(registrationDTO.getCourseDTO()));
    }
}
