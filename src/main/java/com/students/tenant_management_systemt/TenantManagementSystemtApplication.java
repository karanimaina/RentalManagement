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
public class TenantManagementSystemtApplication {
   private  final HouseRepository houseRepository;
    public static void main(String[] args) {
        SpringApplication.run(TenantManagementSystemtApplication.class, args);
    }
    @PostConstruct
    public void initData() {
        List<House> houses = new ArrayList<>();
        houses.add(House
                .builder()
                .name( "Modern Apartment")
                .location("Downtown")
                .description("Spacious 2-bedroom apartment with city view")
                .
        75.0,
                "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267"));
        houses.add( House( "Cozy Studio", "Westlands",
                "Perfect for single professionals", 45.0,
                "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688"));
        houses.add(new House(idCounter.getAndIncrement(), "Family House", "Karen",
                "3-bedroom house with garden", 120.0,
                "https://images.unsplash.com/photo-1480074568708-e7b720bb3f09"));
        houses.add(new House(idCounter.getAndIncrement(), "Luxury Penthouse", "Kilimani",
                "High-end penthouse with premium amenities", 200.0,
                "https://images.unsplash.com/photo-1493809842364-78817add7ffb"));
    }
}
