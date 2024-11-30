package dev.arman.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class LoginRequestDto {
    private String email;
    private String password;
}
