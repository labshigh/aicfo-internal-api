package com.labshigh.aicfo.internal.api.common.utils;


import com.labshigh.aicfo.core.models.ResponseModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Log4j2
@Component
public class MailUtils {

  @Value("${ncloud.mail-storage.access-key}")
  private String accessKey;
  @Value("${ncloud.mail-storage.secret-key}")
  private String secretKey;
  @Value("${ncloud.mail-storage.end-point}")
  private String mailEndPoint;

  @Value("${ncloud.mail-storage.port}")
  private String port;

  @Value("${ncloud.mail-storage.sender}")
  private String sender;


  String body = "<b></b>";

  //이메일 전송하기
  public ResponseModel awsSESSend(String subject, String content, String receiver) throws Exception {
    ResponseModel result = new ResponseModel();

    Properties props = System.getProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.port", port);
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.auth", "true");

    // Create a Session object to represent a mail session with the specified properties.
    Session session = Session.getDefaultInstance(props);

    // Create a message with the specified information.
    MimeMessage msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress(sender,"aicfo"));
    msg.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
    msg.setSubject(subject);
    msg.setText(content,"utf-8","html");

    // Add a configuration set header. Comment or delete the
    // next line if you are not using a configuration set
//        msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

    // Create a transport.
    Transport transport = session.getTransport();

    // Send the message.
    try
    {
      System.out.println("Sending...");

      // Connect to Amazon SES using the SMTP username and password you specified above.
      transport.connect(mailEndPoint, accessKey, secretKey);

      // Send the email.
      transport.sendMessage(msg, msg.getAllRecipients());
      System.out.println("Email sent!");
    }
    catch (Exception ex) {
      result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      result.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
      result.error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      result.error.setErrorMessage(ex.getLocalizedMessage());
    }
    finally
    {
      // Close and terminate the connection.
      transport.close();
    }
    return result;
  }

}

