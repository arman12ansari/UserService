package dev.arman.userservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */
@Entity
@Getter
@Setter
public class Role extends BaseModel {
    private String name;
}
