package com.dealerapp.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "send_date", nullable = false)
    private Date sendDate;
    @Column(name = "delivery_date")
    private Date deliveryDate;
    @ManyToOne
    @JoinColumn(name="client_id", nullable = false)
    private Client client;
    @ManyToOne
    @JoinColumn(name="configuration_id", nullable = false)
    private Configuration configuration;

    public Order() {
    }

    public Order(long id, Date sendDate, Date deliveryDate, Client client, Configuration configuration) {
        this.id = id;
        this.sendDate = sendDate;
        this.deliveryDate = deliveryDate;
        this.client = client;
        this.configuration = configuration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
