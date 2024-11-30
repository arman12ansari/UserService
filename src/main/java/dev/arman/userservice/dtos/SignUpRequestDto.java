package dev.arman.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */
@Setter
@Getter
public class SignUpRequestDto {
    private String name;
    private String email;
    private String password;
}
