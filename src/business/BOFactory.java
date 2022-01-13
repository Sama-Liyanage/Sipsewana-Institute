package business;


import business.custom.Impl.AddRegistrationBOImpl;
import business.custom.Impl.StudentBOImpl;
import business.custom.Impl.CourseBOImpl;
import business.custom.Impl.RegistrationBOImpl;

public class BOFactory {
    public enum BOType{
        COURSE,STUDENT,REGISTRATION,ADDREGISTRATION
    }

    private static BOFactory boFactory;

    private BOFactory(){
    }

    public static BOFactory getBoFactory(){
        return (boFactory !=null)
                ? boFactory : (boFactory=new BOFactory());
    }
    public SuperBO getSuperBO(BOType boType){
        switch (boType){
            case COURSE:
                return new CourseBOImpl();
            case STUDENT:
                return new StudentBOImpl();
            case REGISTRATION:
                return new RegistrationBOImpl();
            case ADDREGISTRATION:
                return new AddRegistrationBOImpl();
            default:
                return null;
        }
    }
}
