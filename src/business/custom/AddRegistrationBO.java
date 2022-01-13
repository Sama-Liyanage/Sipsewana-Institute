package business.custom;


import business.SuperBO;
import dto.RegistrationDTO;
import dto.StudentDTO;

import java.util.List;

public interface AddRegistrationBO extends SuperBO {
    boolean addRegistration(StudentDTO studentDTO , List<RegistrationDTO> registrationDTOList) throws Exception;
}
