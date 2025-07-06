package com.students.tenant_management_systemt.dto;

import lombok.Builder;

@Builder
public record UniversalResponse (
        int status,
        String message,
        Object data
){
}
