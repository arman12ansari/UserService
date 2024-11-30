package dev.arman.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class ExceptionDto {
    private String message;
    private String details;
}
