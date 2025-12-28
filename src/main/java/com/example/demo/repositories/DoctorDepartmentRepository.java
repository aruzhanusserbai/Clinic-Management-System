package com.example.demo.repositories;

import com.example.demo.entities.DoctorDepartment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface DoctorDepartmentRepository extends JpaRepository<DoctorDepartment, Long> {
    List<DoctorDepartment> findAllByDoctor_Id(Long doctorId);
}
