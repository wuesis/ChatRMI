package Models;

import java.io.Serializable;
import java.time.LocalDate;

public class MessageInformation implements Serializable {

    public String userNickName, message, ipAddress, messageDate;

    public MessageInformation(String userNickName, String ipAddress, String message){
        this.userNickName = userNickName;
        this.ipAddress = ipAddress;
        this.message = message;
        this.messageDate = String.valueOf(LocalDate.now());
    }
}
