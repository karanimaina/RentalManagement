package com.students.tenant_management_systemt.models;

import jakarta.persistence.Entity;
import lombok.*;

import javax.swing.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TB_HOUSE")
@Builder
public class House extends BaseEntity{
    private String name;
    private String location;
    private String description;
    private double pricePerDay;
    private String imageUrl;
    private boolean available;
}
