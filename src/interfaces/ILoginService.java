package interfaces;

import entity.user.User;

public interface ILoginService {
    
    User validateLogin(String nric, String password);
    
    boolean checkNRIC(String nric);
    
    String strongPassword(String password);
    
}
