package controller;

import business.BOFactory;
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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import view.TM.StudentTM;

import java.time.LocalDate;
import java.util.List;

public class StudentFormController {
    public JFXTextField txtStId;
    public JFXTextField txtStName;
    public JFXTextField txtStAddress;
    public JFXDatePicker txtStDob;
    public RadioButton rbtnMale;
    public RadioButton rbtnFemale;
    public JFXTextField txtStMobile;
    public JFXButton btnUpdateStudent;
    public TableView<StudentTM> tblStudent;
    public TableColumn<StudentTM,String> colRegNo;
    public TableColumn <StudentTM,String> colCourseName;
    public TableColumn <StudentTM,String> colStNo;
    public TableColumn <StudentTM,String> colStName;
    public TableColumn <StudentTM,String> colStAddress;
    public TableColumn <StudentTM,String> colStContact;
    public TableColumn <StudentTM,String>colStDOB;
    public TableColumn <StudentTM,String> colStGender;


    CourseBO courseBO = (CourseBO) BOFactory.getBoFactory().getSuperBO(BOFactory.BOType.COURSE);
    StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getSuperBO(BOFactory.BOType.STUDENT);
    RegistrationBO registrationBO = (RegistrationBO) BOFactory.getBoFactory().getSuperBO(BOFactory.BOType.REGISTRATION);

    ObservableList<StudentTM> studentTMObservableList = FXCollections.observableArrayList();
    List<CourseDTO> courseDTOList = null;

    public void initialize(){
        colRegNo.setCellValueFactory(new PropertyValueFactory<>("regisID"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("couName"));
        colStNo.setCellValueFactory(new PropertyValueFactory<>("stuID"));
        colStName.setCellValueFactory(new PropertyValueFactory<>("stuName"));
        colStAddress.setCellValueFactory(new PropertyValueFactory<>("stuAddress"));
        colStContact.setCellValueFactory(new PropertyValueFactory<>("stuContact"));
        colStDOB.setCellValueFactory(new PropertyValueFactory<>("stuDOB"));
        colStGender.setCellValueFactory(new PropertyValueFactory<>("stuGender"));
        tblStudent.setItems(studentTMObservableList);
        loadStudents();
        genderToggleChange();
    }

    private void genderToggleChange(){
        ToggleGroup tgGender = new ToggleGroup();
        rbtnMale.setToggleGroup(tgGender);
        rbtnFemale.setToggleGroup(tgGender);
    }

    private void loadStudents(){
        try {

        }catch (Exception e){

        }
    }



    private StudentTM searchStudentTMs(RegistrationDTO registrationDTO,CourseDTO courseDTOs){
        StudentTM studentTMss = new StudentTM();

        studentTMss.setRegisID(registrationDTO.getRegId());
        studentTMss.setCouName(courseDTOs.getCourseName());
        studentTMss.setStuID(registrationDTO.getStudentDTO().getStudentId());
        studentTMss.setStuName(registrationDTO.getStudentDTO().getStudentName());
        studentTMss.setStuAddress(registrationDTO.getStudentDTO().getStudentAddress());
        studentTMss.setStuContact(registrationDTO.getStudentDTO().getStudentContact());
        studentTMss.setStuDOB(registrationDTO.getStudentDTO().getStudentDOB());
        studentTMss.setStuGender(registrationDTO.getStudentDTO().getStudentGender());
        return studentTMss;
    }

    private StudentDTO selectStudentDTO(){
        StudentDTO selectStDTO = new StudentDTO();
        selectStDTO.setStudentId(txtStId.getText());
        selectStDTO.setStudentName(txtStName.getText());
        selectStDTO.setStudentAddress(txtStAddress.getText());
        selectStDTO.setStudentContact(txtStMobile.getText());
        selectStDTO.setStudentDOB(String.valueOf(txtStDob.getValue()));
        System.out.println(txtStDob.getValue());


        if (rbtnMale.isSelected()){
            selectStDTO.setStudentGender("Male");
        }
        if (rbtnFemale.isSelected()){
            selectStDTO.setStudentGender("Female");
        }
        return selectStDTO;
    }

    public void btnUpdateStudentOnAction(ActionEvent actionEvent) {
        studentTMObservableList.clear();
        try {
            boolean b = studentBO.updateStudent(selectStudentDTO());

            if (b){
                StudentDTO s1=new StudentDTO();
                s1.setStudentId(txtStId.getText());
                StudentDTO studentDTO = studentBO.searchStudent(s1);
                if (studentDTO!=null){

                    List<RegistrationDTO> allRegistration = registrationBO.getAllRegistration(studentDTO.getStudentId());

                    for (RegistrationDTO registrationDTO : allRegistration) {

                        StudentTM studentTM = searchStudentTMs(registrationDTO, registrationDTO.getCourseDTO());
                        studentTMObservableList.add(studentTM);
                    }

                }
            }else{

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void txtStIdOnAction(KeyEvent e)  {
        if(e.getCode().toString().equals("ENTER"))
        {
            try {
                studentTMObservableList.clear();
                txtStName.clear();
                txtStAddress.clear();
                //txtStDob.getEditor().clear();
                txtStMobile.clear();
                StudentDTO s1 = new StudentDTO();
                s1.setStudentId(txtStId.getText());


                StudentDTO studentDTO = studentBO.searchStudent(s1);

                if (studentDTO != null) {
                    txtStName.setText(studentDTO.getStudentName());
                    txtStAddress.setText(studentDTO.getStudentAddress());
                    txtStDob.setValue(LocalDate.parse(studentDTO.getStudentDOB()));
                    txtStMobile.setText(studentDTO.getStudentContact());
                    if (studentDTO.getStudentGender().equalsIgnoreCase("male")) {
                        rbtnMale.fire();
                    } else {
                        rbtnFemale.fire();
                    }

                    List<RegistrationDTO> allRegistration = registrationBO.getAllRegistration(studentDTO.getStudentId());

                    for (RegistrationDTO registrationDTO : allRegistration) {

                        StudentTM studentTM = searchStudentTMs(registrationDTO, registrationDTO.getCourseDTO());
                        studentTMObservableList.add(studentTM);
                    }

                }
            }catch (Exception exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Student not exist");
                alert.setHeaderText(null);
                alert.setContentText("please enter valid Id and Try again !");
            }
        }
    }
}
