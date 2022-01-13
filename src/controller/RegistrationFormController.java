package controller;


import business.BOFactory;
import business.custom.AddRegistrationBO;
import business.custom.CourseBO;
import business.custom.RegistrationBO;
import business.custom.StudentBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dto.CourseDTO;
import dto.RegistrationDTO;
import dto.StudentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import view.TM.CourseTM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class RegistrationFormController {
    public JFXTextField txtStName;
    public RadioButton rbtnMale;
    public RadioButton rbtnFemale;
    public JFXDatePicker dpDate;
    public JFXDatePicker dpStDOB;
    public JFXTextField txtStAddress;
    public JFXComboBox cmbCourseID;
    public Label lblCourseID;
    public JFXTextField txtCourseDuration;
    public JFXTextField txtCourseFee;
    public JFXTextField txtStMobileNumber;
    public TableView<CourseTM> tblRegistration;
    public TableColumn<CourseTM,String> colCourseID;
    public TableColumn <CourseTM,String> colCourseName;
    public TableColumn <CourseTM,String> colCourseDuration;
    public TableColumn <CourseTM,Double> colCourseFee;
    public JFXButton btnAdd;
    public JFXButton btnRemove;
    public JFXTextField txtTotal;
    public JFXButton btnReg;
    public Label lblRegID;
    public JFXTextField lblStID;

    CourseBO courseBO = (CourseBO) BOFactory.getBoFactory().getSuperBO(BOFactory.BOType.COURSE);
    AddRegistrationBO addRegistrationBO = (AddRegistrationBO) BOFactory.getBoFactory().getSuperBO(BOFactory.BOType.ADDREGISTRATION);
    RegistrationBO registrationBO= (RegistrationBO) BOFactory.getBoFactory().getSuperBO(BOFactory.BOType.REGISTRATION);
    StudentBO studentBO= (StudentBO) BOFactory.getBoFactory().getSuperBO(BOFactory.BOType.STUDENT);

    ObservableList<CourseTM> courseTMObservableList = FXCollections.observableArrayList();
    List<CourseDTO> courseDTOList = null;
    CourseDTO addCourses = null;

    public void initialize(){
        dpDate.setValue(LocalDate.now());
        loadAllCourse();
        SetTbl();
        genderToggleChange();
        courseFeesTot();
        setRegIdNStudentId();
        lblCourseID.setText(null);

    }

    private void courseFeesTot(){
        courseTMObservableList.addListener((ListChangeListener.Change<? extends CourseTM> courseTot) -> {
            double fees = 0;
            for (CourseTM courseTM : courseTMObservableList){
                fees += Double.parseDouble(String.valueOf(courseTM.getCoFee()));
            }
            txtTotal.setText(String.valueOf(fees));
        });

    }

    private void loadAllCourse(){
        try {
            List<CourseDTO> courseDTOList0 = courseBO.getAll();
            this.courseDTOList = courseDTOList0;
            for (CourseDTO courseDTO : courseDTOList0){
                cmbCourseID.getItems().add(courseDTO.getCourseName());
            }
        }catch (Exception e){
        }
    }
    private void genderToggleChange(){
        ToggleGroup tgGender = new ToggleGroup();
        rbtnMale.setToggleGroup(tgGender);
        rbtnFemale.setToggleGroup(tgGender);
    }

    private void courseTextClear(){
        lblCourseID.setText(null);
        txtCourseDuration.setText(null);
        txtCourseFee.setText(null);
    }

    private void SetTbl(){
        colCourseID.setCellValueFactory(new PropertyValueFactory<>("coId"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("coName"));
        colCourseDuration.setCellValueFactory(new PropertyValueFactory<>("coDuration"));
        colCourseFee.setCellValueFactory(new PropertyValueFactory<>("coFee"));
    }

    private StudentDTO selectStudentDTO(){
        StudentDTO selectStDTO = new StudentDTO();
        selectStDTO.setStudentId(lblStID.getText());
        selectStDTO.setStudentName(txtStName.getText());
        selectStDTO.setStudentAddress(txtStAddress.getText());
        selectStDTO.setStudentContact(txtStMobileNumber.getText());
        selectStDTO.setStudentDOB(String.valueOf(dpStDOB.getValue()));

        if (rbtnMale.isSelected()){
            selectStDTO.setStudentGender("Male");
        }
        if (rbtnFemale.isSelected()){
            selectStDTO.setStudentGender("Female");
        }
        return selectStDTO;
    }

    public void btnRegOnAction(ActionEvent actionEvent) {
        try {


            List<CourseDTO> selectCourseDTOList = new ArrayList<>();
            for (CourseTM courseTM : courseTMObservableList) {
                selectCourseDTOList.add(
                        new CourseDTO(
                                courseTM.getCoId(),
                                courseTM.getCoName(),
                                courseTM.getCoDuration(),
                                courseTM.getCoFee()
                        )
                );
            }


            List<RegistrationDTO> selectRegDTOList = new ArrayList<>();
            for (CourseDTO courseDTO : selectCourseDTOList) {
                selectRegDTOList.add(
                        new RegistrationDTO(
                                lblRegID.getText(),
                                String.valueOf(LocalDate.now()),
                                courseDTO.getCourseFee(),
                                selectStudentDTO(),
                                courseDTO
                        )
                );
            }


            boolean savedAddRegistration = addRegistrationBO.addRegistration(
                    selectStudentDTO(),
                    selectRegDTOList
            );

            if (savedAddRegistration){
                setRegIdNStudentId();
                clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Successful");
                alert.setHeaderText(null);
                alert.setContentText("Student registered successfully");
                alert.showAndWait();
            }else {

            }

        }catch (Exception e){

        }

    }

    private void clear(){
        txtStName.clear();
        txtStAddress.clear();
        dpStDOB.getEditor().clear();
        txtTotal.clear();
        txtStMobileNumber.clear();
        courseTMObservableList.clear();
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {

     try{
         CourseTM selectedItem = tblRegistration.getSelectionModel().getSelectedItem();
         courseTMObservableList.remove(selectedItem);
         double d = Double.parseDouble(txtCourseFee.getText());
         txtCourseFee.setText(String.valueOf(d-selectedItem.getCoFee()));
         tblRegistration.setItems(courseTMObservableList);
     }catch (Exception e){

     }

    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        try{
            if (addCourses !=null){
                CourseTM courseTM = new CourseTM(
                        addCourses.getCourseId(),
                        addCourses.getCourseName(),
                        addCourses.getDuration(),
                        addCourses.getCourseFee()
                );
                courseTMObservableList.add(courseTM);
                tblRegistration.setItems(courseTMObservableList);
                courseDTOList.remove(addCourses);
                cmbCourseID.getItems().clear();
                for (CourseDTO courseDTO : courseDTOList){
                    cmbCourseID.getItems().add(courseDTO.getCourseName());
                }
                courseTextClear();
                addCourses = null;
            }
        }catch (Exception e){

        }
    }

    public void cmbCourseIDOnAction(ActionEvent actionEvent) {
                try {
            String coursesName = String.valueOf(cmbCourseID.getSelectionModel().getSelectedItem());

            CourseDTO selectCourses = null;
            for (CourseDTO courseDTO1 : courseDTOList){
                if (courseDTO1.getCourseName().compareToIgnoreCase(coursesName) == 0) {
                    selectCourses = courseDTO1;
                    this.addCourses = selectCourses;
                }
            }
            if (selectCourses != null) {
                lblCourseID.setText(selectCourses.getCourseId());
                txtCourseDuration.setText(selectCourses.getDuration());
                txtCourseFee.setText(String.valueOf(selectCourses.getCourseFee()));
            }
        }catch (Exception e){

        }
    }

    private void setRegIdNStudentId(){
        lblRegID.setText(RegistrationId());
        lblStID.setText(studentId());
    }

    public String studentId() {
        String lastStudentId=null;
        try {

            List<StudentDTO> allStudent = studentBO.getAllStudent();

            for (StudentDTO studentDTO : allStudent) {
                lastStudentId=studentDTO.getStudentId();
            }

            if (lastStudentId!=null){
                int tempId = Integer.
                        parseInt(lastStudentId.split("-")[1]);
                tempId=tempId+1;
                if (tempId<=9){
                    return "S-00"+tempId;
                }else if(tempId<=99){
                    return "S-0"+tempId;
                }else{
                    return "S-"+tempId;
                }

            }else{
                return "S-001";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public String RegistrationId() {
            String lastRegistrationId=null;
        try {
            List<RegistrationDTO> allRegistrations = registrationBO.getAllRegistrationIds();
            for (RegistrationDTO allRegistrationId : allRegistrations) {
                lastRegistrationId=allRegistrationId.getRegId();
            }

            if (lastRegistrationId!=null){
            int tempId = Integer.
                    parseInt(lastRegistrationId.split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "R-00"+tempId;
            }else if(tempId<=99){
                return "R-0"+tempId;
            }else{
                return "R-"+tempId;
            }

        }else{
            return "R-001";
        }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public void txtIDonAction(KeyEvent keyEvent) {
        if(keyEvent.getCode().toString().equals("ENTER"))
        {
            StudentDTO s1 = new StudentDTO();
            s1.setStudentId(lblStID.getText());

            try {
                StudentDTO studentDTO = studentBO.searchStudent(s1);

                if (studentDTO != null) {
                    txtStName.setText(studentDTO.getStudentName());
                    txtStAddress.setText(studentDTO.getStudentAddress());
                    dpStDOB.setValue(LocalDate.parse(studentDTO.getStudentDOB()));
                    txtStMobileNumber.setText(studentDTO.getStudentContact());
                    if (studentDTO.getStudentGender().equalsIgnoreCase("male")) {
                        rbtnMale.fire();
                    } else {
                        rbtnFemale.fire();
                    }
                }


            } catch (Exception e) {
                setRegIdNStudentId();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Student not exist");
                alert.setHeaderText(null);
                alert.setContentText("Student does not exist register first !");
                alert.showAndWait();

            }
        }
    }
}
