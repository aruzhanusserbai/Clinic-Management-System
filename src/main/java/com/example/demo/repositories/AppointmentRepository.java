package com.example.demo.repositories;

import com.example.demo.entities.Appointment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByDoctor_Id(Long doctorId);

    List<Appointment> findAllByPatient_Id(Long patientId);
}
