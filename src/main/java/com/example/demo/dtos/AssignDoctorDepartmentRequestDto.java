package com.example.demo.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignDoctorDepartmentRequestDto {
    private Long doctorId;
    private Long departmentId;
}
