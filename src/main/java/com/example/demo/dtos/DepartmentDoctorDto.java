package com.example.demo.dtos;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDoctorDto {
    public Long doctorId;
    public String doctorFullName;

    public String departmentName;
    public Long departmentId;

}
