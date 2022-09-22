package com.oniraanu.basicsignuplogin.email;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.oniraanu.basicsignuplogin.dto.request.MessageRequest;
import com.oniraanu.basicsignuplogin.dto.response.MailResponse;

import java.util.concurrent.CompletableFuture;

public interface EmailService {
    CompletableFuture <MailResponse> sendSimpleMessage(MessageRequest messageRequest) throws UnirestException;
}
