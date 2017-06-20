package ChatApplication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;



import org.jasypt.util.password.StrongPasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Authentication {
    private static Logger logger = LoggerFactory.getLogger(WriteThread.class);

    private Authentication() {
        throw new IllegalStateException("Utility class");
    }

    static boolean isValidUser(String userName, String password) {
        try (InputStream inputStream = new FileInputStream("User.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
            if (passwordEncryptor.checkPassword(password, properties.getProperty(userName))) {
                return true;
            }
        } catch (Exception e) {
            logger.info("Exception at Authentication",e);
        }
        return false;
    }

    private static void addNewUser(String userName, String password) {
        try(OutputStream outputStream = new FileOutputStream("User.properties")){
            StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
            Properties properties = new Properties();
            properties.setProperty(userName,passwordEncryptor.encryptPassword(password));
            properties.store(outputStream,null);
        }catch(Exception e){
            logger.info("Exception while addind new User",e);
        }
    }
}
