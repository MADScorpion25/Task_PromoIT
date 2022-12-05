package com.dealerapp.repo;

import com.dealerapp.models.Car;
import com.dealerapp.models.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
    List<Configuration> findByCarIsNull();
    Configuration findByConfigurationName(String name);
    boolean existsByConfigurationName(String name);
}
