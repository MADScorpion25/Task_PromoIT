package com.dealerapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="client_id", nullable = false)
    private Client client;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name="configuration_id", nullable = false)
    private Configuration configuration;
    @Column(name = "is_notified")
    private boolean isNotified;

    public Order() {
    }

    public Order(long id, Date sendDate, Date deliveryDate, Client client, Configuration configuration) {
        this.id = id;
        this.sendDate = sendDate;
        this.deliveryDate = deliveryDate;
        this.client = client;
        this.configuration = configuration;
        isNotified = false;
    }

    public Order(long id, Date sendDate, Date deliveryDate, Client client, Configuration configuration, boolean isNotified) {
        this.id = id;
        this.sendDate = sendDate;
        this.deliveryDate = deliveryDate;
        this.client = client;
        this.configuration = configuration;
        this.isNotified = isNotified;
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

    @JsonIgnore
    public Client getClient() {
        return client;
    }

    @JsonIgnore
    public void setClient(Client client) {
        this.client = client;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public boolean isNotified() {
        return isNotified;
    }

    public void setNotified(boolean notified) {
        isNotified = notified;
    }
}
