package spring.angular.social.service;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.machinelearning.MachineLearningAsyncClient;

import java.util.Collections;

@Service
public class EmailService {

    @Autowired
    private AmazonSimpleEmailService amazonSimpleEmailService;


    public void sendEmail() {
        String fromEmail = "lkolli1@evoketechnologies.com";
        String toEmail = "spadala@evoketechnologies.com";
        String subject = "This is a test email";
        String body1 = "This is the body of the email.";


        Content contentSubject = new Content();
        contentSubject.setData(subject);
        Content contentBody = new Content();
        contentBody.setData(body1);
        Body body = new Body();
        body.setText(contentBody);

        Message message = new Message(contentSubject, body);

        SendEmailRequest sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setSource(fromEmail);
        sendEmailRequest.setDestination(new Destination(Collections.singletonList(toEmail)));
        sendEmailRequest.setMessage(message);

        amazonSimpleEmailService.sendEmail(sendEmailRequest);
    }

}
