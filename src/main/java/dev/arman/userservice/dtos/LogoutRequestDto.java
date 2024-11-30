package dev.arman.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class LogoutRequestDto {
    private String token;
}
