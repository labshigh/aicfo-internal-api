package com.labshigh.aicfo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

///**
// * Created by nogah on 2023. 2. 10
// */
public class MailTokenTests {

    public static void main(String[] args) throws Exception {

        long curTimeStamp = new Date().getTime();
        long timeStamp = Timestamp.valueOf(LocalDateTime.now().plusMinutes(1440)).getTime();
        Date date=new Date(curTimeStamp);
        Date date2=new Date(timeStamp);

        System.out.println(curTimeStamp);
        System.out.println(timeStamp);
        if (curTimeStamp > timeStamp) {
            System.out.println(timeStamp);
        }

    }
}
// snippet-end:[ses.java2.sendmessage.complete]