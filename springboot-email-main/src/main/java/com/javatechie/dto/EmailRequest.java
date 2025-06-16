package com.javatechie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    private String toEMail;
    private String subject;
    private String messageBody;
    private String attachment;
    private String[] toEmails;
}
