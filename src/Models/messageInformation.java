package Models;

import sun.util.calendar.BaseCalendar;

import java.sql.Date;
import java.time.LocalDate;

public class messageInformation {

    public String userNickName, message, ipAddress, messageDate;

    public messageInformation(String userNickName, String ipAddress, String message){
        this.userNickName = userNickName;
        this.ipAddress = ipAddress;
        this.message = message;
        this.messageDate = String.valueOf(LocalDate.now());
    }
}
