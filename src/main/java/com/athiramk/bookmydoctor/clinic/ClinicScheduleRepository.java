package com.athiramk.bookmydoctor.clinic;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicScheduleRepository extends JpaRepository<ClinicSchedule, Long> {

    Optional<ClinicSchedule> findFirstByOrderByIdAsc();
}
