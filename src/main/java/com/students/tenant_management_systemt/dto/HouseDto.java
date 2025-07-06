package com.students.tenant_management_systemt.dto;

public record HouseDto(
        String name,
        String location,
        String description,
        Double pricePerDay,
        String imageUrl
) {}