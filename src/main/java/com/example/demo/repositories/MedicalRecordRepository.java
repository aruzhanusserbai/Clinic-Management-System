package com.example.demo.repositories;

import com.example.demo.entities.Appointment;
import com.example.demo.entities.MedicalRecord;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findAllByDoctor_Id(Long doctorId);

    List<MedicalRecord> findAllByPatient_Id(Long patientId);
}
