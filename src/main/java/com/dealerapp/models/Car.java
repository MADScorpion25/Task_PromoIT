package com.dealerapp.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "brand_name", nullable = false)
    private String brandName;
    @Column(name = "model_name", nullable = false, unique = true)
    private String modelName;
    @Column(name = "production_year", nullable = false)
    private short productionYear;
    @OneToMany(mappedBy="car", cascade = CascadeType.ALL)
    private Set<Configuration> configurations;

    public Car() {
    }

    public Car(long id, String brandName, String modelName, short productionYear, Set<Configuration> configurations) {
        this.id = id;
        this.brandName = brandName;
        this.modelName = modelName;
        this.productionYear = productionYear;
        this.configurations = configurations;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public short getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(short productionYear) {
        this.productionYear = productionYear;
    }

    public Set<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(Set<Configuration> configurations) {
        this.configurations = configurations;
    }
}
