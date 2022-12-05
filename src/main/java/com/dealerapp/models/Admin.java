package com.dealerapp.models;

import com.dealerapp.models.enums.UserRole;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admins")
@DiscriminatorValue("ADMIN")
public class Admin extends User{
    public Admin() {
        setRole(UserRole.ADMIN);
    }

    public Admin(long id, String login, String password) {
        super(id, login, password);
        setRole(UserRole.ADMIN);
    }
}
