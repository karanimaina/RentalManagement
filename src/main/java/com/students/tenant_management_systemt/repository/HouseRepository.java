package com.students.tenant_management_systemt.repository;

import com.students.tenant_management_systemt.models.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {

    List<House>findByAvailableTrue();
    List<House>findByLocationIgnoreCase(String location);
    List<House>findHouseByPricePerDayBetween(Double min, Double max);
}
