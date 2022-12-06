package com.dealerapp.repo;

import com.dealerapp.models.Car;
import com.dealerapp.models.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
    List<Configuration> findByCarIsNull();
    Optional<Configuration> findByConfigurationName(String name);
    boolean existsByConfigurationName(String name);
}
