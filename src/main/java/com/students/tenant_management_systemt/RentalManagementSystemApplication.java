package com.students.tenant_management_systemt;

import com.students.tenant_management_systemt.models.House;
import com.students.tenant_management_systemt.repository.HouseRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class RentalManagementSystemApplication {
   private  final HouseRepository houseRepository;
    public  static void main(String[] args) {
        SpringApplication.run(RentalManagementSystemApplication.class, args);
    }
    @PostConstruct
    public void initData() {
        List<House> houses = new ArrayList<>();
        houses.add(House
                .builder()
                .name( "Modern Apartment")
                .location("Westlands")
                .description("3-bedroom house with garden")
                .pricePerDay(45.0)
                .imageUrl("https://images.unsplash.com/photo-1522708323590-d24dbb6b0267")
                .build());

        houses.add(House
                .builder()
                .name( "Cozy Studio")
                .location("Downtown")
                .description("Perfect for single professionals")
                .pricePerDay(45.0)
                .imageUrl("https://images.unsplash.com/photo-1502672260266-1c1ef2d93688")
                .build());

        houses.add(House
                .builder()
                .name( "Family House")
                .location("Karen")
                .description("Perfect for single professionals")
                .pricePerDay(120.0)
                .imageUrl("https://images.unsplash.com/photo-1502672260266-1c1ef2d93688")
                .build());
        houses.add(House
                .builder()
                .name("Luxury Penthouse")
                .location("Kilimani")
                .description("Perfect for single professionals")
                .pricePerDay(200.0)
                .imageUrl("https://images.unsplash.com/photo-1493809842364-78817add7ffb")
                .build());
       houseRepository.saveAll(houses);
    }
}
