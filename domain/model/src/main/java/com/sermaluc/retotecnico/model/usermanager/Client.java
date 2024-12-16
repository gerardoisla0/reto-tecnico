package com.sermaluc.retotecnico.model.usermanager;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder(toBuilder = true)
public class Client {
    protected String id;
    protected String name;
    protected String email;
    protected String password;
    protected Date created;
    protected Date modified;
    protected Date lastLogin;
    protected String token;
    protected boolean active;
    protected Phones[] phones;
}