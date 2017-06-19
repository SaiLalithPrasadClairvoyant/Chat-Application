import org.jasypt.util.password.StrongPasswordEncryptor;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by Sai Lalith Pathi on 09-Jun-17.
 */
class Authentication {
    private HashMap<String,String> userList = new HashMap<>();
    boolean isValidUser(String userName,String password){
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        addNewUser("sai",passwordEncryptor.encryptPassword("sai"));
        addNewUser("lalith","lalith");
        for(Map.Entry<String,String> user : userList.entrySet()){
            if(user.getKey().equals(userName) && passwordEncryptor.checkPassword(password,user.getValue())){
                return true;
            }
        }
        return false;
    }
    private void addNewUser(String userName,String password){
        userList.put(userName,password);
    }
}
