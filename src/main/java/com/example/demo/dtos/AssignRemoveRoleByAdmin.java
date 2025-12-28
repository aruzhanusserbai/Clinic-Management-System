package com.example.demo.dtos;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AssignRemoveRoleByAdmin {
    private Long userId;
    private Long roleId;
}
