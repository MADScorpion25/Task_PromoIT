package com.dealerapp.models;

import com.dealerapp.models.enums.BodyType;
import com.dealerapp.models.enums.CarClass;
import com.dealerapp.models.enums.DriveType;
import com.dealerapp.models.enums.TransmissionType;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "configurations")
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "configuration_name", nullable = false, unique = true)
    private String configurationName;
    @Column(nullable = false)
    private short power;
    @Column(nullable = false)
    private int price;
    @Column(name = "body_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BodyType bodyType;
    @Column(name = "car_class", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CarClass carClass;
    @Column(name = "drive_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private DriveType driveType;
    @Column(name = "transmission_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TransmissionType transmissionType;
    @Column(length = 65535, columnDefinition="TEXT", nullable = false)
    private String description;
    @Column(name = "img_path")
    private String imgPath;
    @ManyToOne
    @JoinColumn(name="car_id")
    private Car car;
    @OneToMany(mappedBy="configuration")
    private Set<Order> orders;
    @Transient MultipartFile file;

    public Configuration() {
    }

    public Configuration(long id, String configurationName, short power, int price, BodyType bodyType, CarClass carClass, DriveType driveType, TransmissionType transmissionType, String description, String imgPath, Car car, Set<Order> orders) {
        this.id = id;
        this.configurationName = configurationName;
        this.power = power;
        this.price = price;
        this.bodyType = bodyType;
        this.carClass = carClass;
        this.driveType = driveType;
        this.transmissionType = transmissionType;
        this.description = description;
        this.imgPath = imgPath;
        this.car = car;
        this.orders = orders;
    }

    public Configuration(long id, String configurationName, short power, int price, BodyType bodyType, CarClass carClass, DriveType driveType, TransmissionType transmissionType, String description, String imgPath, Car car, Set<Order> orders, MultipartFile file) {
        this.id = id;
        this.configurationName = configurationName;
        this.power = power;
        this.price = price;
        this.bodyType = bodyType;
        this.carClass = carClass;
        this.driveType = driveType;
        this.transmissionType = transmissionType;
        this.description = description;
        this.imgPath = imgPath;
        this.car = car;
        this.orders = orders;
        this.file = file;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConfigurationName() {
        return configurationName;
    }

    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }

    public short getPower() {
        return power;
    }

    public void setPower(short power) {
        this.power = power;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public CarClass getCarClass() {
        return carClass;
    }

    public void setCarClass(CarClass carClass) {
        this.carClass = carClass;
    }

    public DriveType getDriveType() {
        return driveType;
    }

    public void setDriveType(DriveType driveType) {
        this.driveType = driveType;
    }

    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(TransmissionType transmissionType) {
        this.transmissionType = transmissionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
