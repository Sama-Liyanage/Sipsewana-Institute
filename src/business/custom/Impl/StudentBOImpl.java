package business.custom.Impl;

import business.custom.StudentBO;
import dao.Custom.CourseDAO;
import dao.Custom.StudentDAO;
import dao.DAOFactory;

import dto.StudentDTO;
import entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {

    private StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getSuperDAO(DAOFactory.DAOType.STUDENT);


    @Override
    public boolean saveStudent(StudentDTO s1) throws Exception {
        return studentDAO.save(new Student(s1.getStudentId(),s1.getStudentName(),s1.getStudentAddress(),
                s1.getStudentDOB(),s1.getStudentContact(),s1.getStudentGender()));
    }

    @Override
    public boolean updateStudent(StudentDTO studentDTO) throws Exception {
        return studentDAO.update(
                new Student(
                        studentDTO.getStudentId(),
                        studentDTO.getStudentName(),
                        studentDTO.getStudentAddress(),
                        studentDTO.getStudentDOB(),
                        studentDTO.getStudentContact(),
                        studentDTO.getStudentGender()
                )
        );
    }

    @Override
    public boolean deleteStudent(StudentDTO studentDTO) throws Exception {
        return studentDAO.delete(
                new Student(
                        studentDTO.getStudentId(),
                        studentDTO.getStudentName(),
                        studentDTO.getStudentAddress(),
                        studentDTO.getStudentContact(),
                        studentDTO.getStudentDOB(),
                        studentDTO.getStudentGender()
                )
        );
    }

    @Override
    public StudentDTO searchStudent(StudentDTO studentDTO) throws Exception {
        return searchingStudentDTOs(studentDAO.search(searchingStudent(studentDTO)));

    }

    @Override
    public List<StudentDTO> getAllStudent() throws Exception {
        List<StudentDTO> studentDTOS = new ArrayList<>();
        List<Student> courseList = studentDAO.getAll();
        for (Student student : courseList){
            studentDTOS.add(
                    new StudentDTO(
                            student.getS_id(),
                            student.getS_name(),
                            student.getS_address(),
                            student.getS_contact_no(),
                            student.getS_DOB(),
                            student.getS_gender()
                    )
            );
        }
        return studentDTOS;
    }


    private Student searchingStudent(StudentDTO studentDTO) {
        return new Student(
                studentDTO.getStudentId(),
                studentDTO.getStudentName(),
                studentDTO.getStudentAddress(),
                studentDTO.getStudentContact(),
                studentDTO.getStudentDOB(),
                studentDTO.getStudentGender()
        );
    }

    private StudentDTO searchingStudentDTOs(Student student) {
        return new StudentDTO(
                student.getS_id(),
                student.getS_name(),
                student.getS_address(),
                student.getS_contact_no(),
                student.getS_DOB(),
                student.getS_gender()
        );
    }
}
