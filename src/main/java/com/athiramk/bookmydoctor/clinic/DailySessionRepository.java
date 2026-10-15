package com.athiramk.bookmydoctor.clinic;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailySessionRepository extends JpaRepository<DailySession, Long> {

    Optional<DailySession> findBySessionDate(LocalDate sessionDate);

    boolean existsBySessionDate(LocalDate sessionDate);

    Optional<DailySession> findFirstBySessionDateGreaterThanEqualOrderBySessionDateAsc(LocalDate fromDate);

    List<DailySession> findByStatusInOrderBySessionDateAsc(Collection<SessionStatus> statuses);
}
