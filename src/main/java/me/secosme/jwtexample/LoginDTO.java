package me.secosme.jwtexample;

import java.io.Serializable;
import lombok.Data;

@Data
public class LoginDTO implements Serializable { 
    private static final long serialVersionUID = 1L;
    private String user;
    private String password;
}
