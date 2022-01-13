package business.custom.Impl;

import business.custom.RegistrationBO;
import dao.Custom.ResgistrationDAO;
import dao.DAOFactory;
import dto.CourseDTO;
import dto.RegistrationDTO;
import dto.StudentDTO;
import entity.Course;
import entity.Registration;
import entity.Student;

import java.util.ArrayList;
import java.util.List;

public class RegistrationBOImpl implements RegistrationBO {

    private ResgistrationDAO registrationDAO = (ResgistrationDAO) DAOFactory.getDaoFactory().getSuperDAO(DAOFactory.DAOType.REGISTRATION);


    @Override
    public boolean saveReg(RegistrationDTO registrationDTO) throws Exception {
        Student student = changeStudentDT0(registrationDTO.getStudentDTO());
        Course course = changeCourseDT0(registrationDTO.getCourseDTO());

        return registrationDAO.save(new Registration(
                registrationDTO.getRegId(),
                registrationDTO.getRegDate(),
                registrationDTO.getRegFee(),
                student,
                course
        ));
    }

    @Override
    public boolean updateReg(RegistrationDTO registrationDTO) throws Exception {
        Student student = changeStudentDT0(registrationDTO.getStudentDTO());
        Course course = changeCourseDT0(registrationDTO.getCourseDTO());

        return registrationDAO.update(
                new Registration(
                        registrationDTO.getRegId(),
                        registrationDTO.getRegDate(),
                        registrationDTO.getRegFee(),
                        student,
                        course
                )
        );
    }

    @Override
    public boolean deleteReg(RegistrationDTO registrationDTO) throws Exception {
        Student student = changeStudentDT0(registrationDTO.getStudentDTO());
        Course course = changeCourseDT0(registrationDTO.getCourseDTO());
        return registrationDAO.delete(
                new Registration(
                        registrationDTO.getRegId(),
                        registrationDTO.getRegDate(),
                        registrationDTO.getRegFee(),
                        student,
                        course
                )
        );
    }

    @Override
    public RegistrationDTO searchReg(RegistrationDTO registrationDTO) throws Exception {
        return null;
    }

    @Override
    public List<RegistrationDTO> getAllReg(RegistrationDTO registrationDTO) throws Exception {
        List<RegistrationDTO> registrationDTOList = new ArrayList<>();
        List<Registration> registrationList = registrationDAO.getAll();
        for (Registration registration : registrationList){
            registrationDTOList.add(
                    new RegistrationDTO(
                            registration.getR_id(),
                            registration.getDate(),
                            registration.getFee(),
                            registrationDTO.getStudentDTO(),
                            registrationDTO.getCourseDTO()
                    )
            );
        }
        return registrationDTOList;
    }

    @Override
    public List<RegistrationDTO> getAllRegistration(String studentId) throws Exception {
        List<RegistrationDTO> registrationDTOList = new ArrayList<>();
        List<Registration> registrationList = registrationDAO.getAllRegistration(studentId);
        for (Registration registration : registrationList){
            registrationDTOList.add(getRegisDTO(registration));
        }
        return registrationDTOList;
    }

    @Override
    public List<RegistrationDTO> getAllRegistrationIds() throws Exception {
        List<RegistrationDTO> registrationDTOList = new ArrayList<>();
        List<Registration> registrationList = registrationDAO.getAll();
        for (Registration registration : registrationList){
            registrationDTOList.add(
                    new RegistrationDTO(
                            registration.getR_id(),
                            registration.getDate(),
                            registration.getFee(),
                            getAllStud(registration.getStudent()),
                            getAllCou(registration.getCourse())
                    )
            );
        }
        return registrationDTOList;
    }

    private RegistrationDTO getRegisDTO(Registration registration){
        return new RegistrationDTO(
                registration.getR_id(),
                registration.getDate(),
                registration.getFee(),
                getAllStud(registration.getStudent()),
                getAllCou(registration.getCourse())
        );
    }

    private StudentDTO getAllStud(Student student){
        return new StudentDTO(
                student.getS_id(),
                student.getS_name(),
                student.getS_address(),
                student.getS_contact_no(),
                student.getS_DOB(),
                student.getS_gender()
        );
    }
    private CourseDTO getAllCou(Course course){
        return new CourseDTO(
                course.getC_id(),
                course.getC_name(),
                course.getDuration(),
                course.getFee()
        );
    }

    public Student changeStudentDT0(StudentDTO  studentDTO){
        return new Student(
                studentDTO.getStudentId(),
                studentDTO.getStudentName(),
                studentDTO.getStudentAddress(),
                studentDTO.getStudentDOB(),
                studentDTO.getStudentContact(),
                studentDTO.getStudentGender());
    }

    public Course changeCourseDT0(CourseDTO courseDTO){
        return new Course(courseDTO.getCourseId(),
                courseDTO.getCourseName(),
                courseDTO.getDuration(),
                courseDTO.getCourseFee()
               );
    }
}
