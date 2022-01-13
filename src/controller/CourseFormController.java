package controller;

import business.BOFactory;
import business.custom.CourseBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CourseDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import view.TM.CourseTM;

import java.util.List;

public class CourseFormController {
    public TextField txtCourseID;
    public JFXTextField txtCourseName;
    public JFXTextField txtCourseDuration;
    public JFXTextField txtCourseFee;
    public JFXButton btnSaveCourse;
    public JFXButton btnUpdateCourse;
    public TableView<CourseTM> tblCourse;
    public TableColumn<CourseTM , String> colCourseID;
    public TableColumn <CourseTM , String> colCourseName;
    public TableColumn <CourseTM , String> colCourseDuration;
    public TableColumn <CourseTM , Double> colCourseFee;
    public JFXButton btnDeleteCourse;

    CourseBO courseBO = (CourseBO) BOFactory.getBoFactory().getSuperBO(BOFactory.BOType.COURSE);
    ObservableList<CourseTM> courseTMObservableList = FXCollections.observableArrayList();

    public void initialize(){
        loadTable();
        setDataToTable();
    }

    public void setDataToTable(){
        colCourseID.setCellValueFactory(new PropertyValueFactory<>("coId"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("coName"));
        colCourseDuration.setCellValueFactory(new PropertyValueFactory<>("coDuration"));
        colCourseFee.setCellValueFactory(new PropertyValueFactory<>("coFee"));
        tblCourse.setItems(courseTMObservableList);
    }


    public void loadTable(){
        try{
            courseTMObservableList.clear();
            List<CourseDTO> courseDTOList = courseBO.getAll();
            for (CourseDTO courseDTO : courseDTOList){
                courseTMObservableList.add(
                        new CourseTM(
                                courseDTO.getCourseId(),
                                courseDTO.getCourseName(),
                                courseDTO.getDuration(),
                                courseDTO.getCourseFee()
                        )
                );
            }
            tblCourse.refresh();
        }  catch (Exception e){
        }
    }

    public void btnSaveCourseOnAction(ActionEvent actionEvent) {
        try{
            boolean saved = courseBO.saveCourse(
                    new CourseDTO(
                            txtCourseID.getText(),
                            txtCourseName.getText(),
                            txtCourseDuration.getText(),
                            Double.parseDouble(txtCourseFee.getText())
                    )
            );
            if (saved){
                txtCourseID.clear();
                txtCourseName.clear();
                txtCourseName.clear();
                txtCourseFee.clear();
                txtCourseDuration.clear();
                loadTable();
            }else {
                System.out.println("no");
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void btnUpdateCourseOnAction(ActionEvent actionEvent) {
        try{
            if(courseBO.updateCourse(
                    new CourseDTO(
                            txtCourseID.getText(),
                            txtCourseName.getText(),
                            txtCourseDuration.getText(),
                            Double.parseDouble(txtCourseFee.getText())
                    )
            )){
                txtCourseID.clear();
                txtCourseName.clear();
                txtCourseName.clear();
                txtCourseFee.clear();
                txtCourseDuration.clear();
                loadTable();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Course update Failed");
                alert.setHeaderText(null);
                alert.setContentText("Please Check the Values & Try again !");
                alert.showAndWait();
            }
        }catch (Exception e){
        }
    }

    public void btnDeleteCourseOnAction(ActionEvent actionEvent) {
        if (tblCourse.getSelectionModel().getSelectedItem() != null) {
            CourseTM courseTM = tblCourse.getSelectionModel().getSelectedItem();
            try {
                boolean deleteCourse = courseBO.deleteCourse(
                        new CourseDTO(
                                courseTM.getCoId(),
                                courseTM.getCoName(),
                                courseTM.getCoDuration(),
                                courseTM.getCoFee()
                        )
                );
                if (deleteCourse) {
                    courseTMObservableList.remove(courseTM);
                    tblCourse.refresh();
                } else {
//                    courseTMObservableList.remove(courseTM);
//                    tblCourse.refresh();
                }
            } catch (Exception e) {
            }
        }
    }
}
