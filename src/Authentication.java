import org.jasypt.util.password.StrongPasswordEncryptor;
import java.util.HashMap;
import java.util.Map;

class Authentication {
    private static HashMap<String,String> userList = new HashMap<>();
    static boolean isValidUser(String userName,String password){
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        addNewUser("sai",passwordEncryptor.encryptPassword("sai"));
        addNewUser("lalith",passwordEncryptor.encryptPassword("lalith"));
        for(Map.Entry<String,String> user : userList.entrySet()){
            if(user.getKey().equals(userName) && passwordEncryptor.checkPassword(password,user.getValue())){
                return true;
            }
        }
        return false;
    }
    private static void addNewUser(String userName,String password){
        userList.put(userName,password);
    }
}
