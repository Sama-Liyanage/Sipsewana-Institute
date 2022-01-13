package business.custom.Impl;

import business.custom.CourseBO;
import dao.Custom.CourseDAO;
import dao.DAOFactory;
import dto.CourseDTO;
import entity.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseBOImpl implements CourseBO {

    private CourseDAO courseDAO = (CourseDAO) DAOFactory.getDaoFactory().getSuperDAO(DAOFactory.DAOType.COURSE);


    @Override
    public boolean saveCourse(CourseDTO courseDTO) throws Exception {
        return courseDAO.save(new Course(courseDTO.getCourseId(),courseDTO.getCourseName(),
                courseDTO.getDuration(),courseDTO.getCourseFee()));
    }

    @Override
    public boolean updateCourse(CourseDTO courseDTO) throws Exception {
        return courseDAO.update(
                new Course(
                        courseDTO.getCourseId(),
                        courseDTO.getCourseName(),
                        courseDTO.getDuration(),
                        courseDTO.getCourseFee()
                )
        );
    }

    @Override
    public boolean deleteCourse(CourseDTO courseDTO) throws Exception {
        return courseDAO.delete(
                new Course(
                        courseDTO.getCourseId(),
                        courseDTO.getCourseName(),
                        courseDTO.getDuration(),
                        courseDTO.getCourseFee()
                )
        );
    }

    @Override
    public CourseDTO searchCourse(CourseDTO courseDTO) throws Exception {
        return courseDTOs(courseDAO.search(courses(courseDTO)));
    }

    @Override
    public List<CourseDTO> getAll() throws Exception {
        List<CourseDTO> courseDTOS=new ArrayList<>();
        List<Course> all = courseDAO.getAll();

        for (Course course : all) {
            courseDTOS.add(new CourseDTO(
                    course.getC_id(),course.getC_name(),
                    course.getDuration(),course.getFee()));
        }
        return courseDTOS;
    }


    private CourseDTO courseDTOs(Course course){
        return new CourseDTO(
                course.getC_id(),
                course.getC_name(),
                course.getDuration(),
                course.getFee()
        );
    }
    private Course courses(CourseDTO courseDTO) {
        return new Course(
                courseDTO.getCourseId(),
                courseDTO.getCourseName(),
                courseDTO.getDuration(),
                courseDTO.getCourseFee()
        );
    }
}
