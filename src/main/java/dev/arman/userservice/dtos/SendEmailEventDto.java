package dev.arman.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class SendEmailEventDto {
    private String to;
    private String from;
    private String subject;
    private String body;
}
