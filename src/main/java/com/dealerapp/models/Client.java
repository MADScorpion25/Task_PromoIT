package com.dealerapp.models;

import com.dealerapp.models.enums.UserRole;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clients")
@DiscriminatorValue("CLIENT")
public class Client extends User{
    @OneToMany(mappedBy="client")
    private Set<Order> orders;

    public Client() {
        setRole(UserRole.CLIENT);
    }

    public Client(long id, String login, String password, Set<Order> orders) {
        super(id, login, password);
        this.orders = orders;
        setRole(UserRole.CLIENT);
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
