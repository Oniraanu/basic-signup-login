package com.oniraanu.basicsignuplogin.email;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.oniraanu.basicsignuplogin.dto.request.MessageRequest;
import com.oniraanu.basicsignuplogin.dto.response.MailResponse;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service("mailgun_sender")
public class EmailSender implements EmailService{

    private final String DOMAIN = System.getenv("DOMAIN");
    private final String PRIVATE_KEY = System.getenv("PRIVATE_KEY");

    @Override
    public CompletableFuture <MailResponse> sendSimpleMessage(MessageRequest messageRequest) throws UnirestException {
        HttpResponse<String> request = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN + "/messages")
                .basicAuth("api", PRIVATE_KEY)
                .queryString("from", messageRequest.getSender())
                .queryString("to", messageRequest.getReceiver())
                .queryString("subject", messageRequest.getSubject())
                .queryString("body", messageRequest.getBody())
                .asString();
        MailResponse mailResponse = request.getStatus() == 200 ? new MailResponse(true) : new MailResponse(false);
        return CompletableFuture.completedFuture(mailResponse);
    }
}
