package com.athiramk.bookmydoctor.clinic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.athiramk.bookmydoctor.BaseIT;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

class ClinicRepositoryTests extends BaseIT {

    private final ClinicScheduleRepository clinicScheduleRepository;
    private final DailySessionRepository dailySessionRepository;
    private final TokenBookingRepository tokenBookingRepository;

    @Autowired
    ClinicRepositoryTests(
            ClinicScheduleRepository clinicScheduleRepository,
            DailySessionRepository dailySessionRepository,
            TokenBookingRepository tokenBookingRepository) {
        this.clinicScheduleRepository = clinicScheduleRepository;
        this.dailySessionRepository = dailySessionRepository;
        this.tokenBookingRepository = tokenBookingRepository;
    }

    @Test
    void shouldFindDefaultClinicSchedule() {
        var scheduleOpt = clinicScheduleRepository.findFirstByOrderByIdAsc();
        assertThat(scheduleOpt).isPresent();
        var schedule = scheduleOpt.get();
        assertThat(schedule.getDoctorName()).isEqualTo("Dr. Mathew");
        assertThat(schedule.getMaxTokensPerDay()).isEqualTo(8);
        assertThat(schedule.getActiveDays()).contains("MONDAY", "WEDNESDAY", "FRIDAY");
    }

    @Test
    void shouldCreateSessionAndManageTokenBookings() {
        LocalDate sessionDate = LocalDate.now().plusDays(10);
        DailySession session = new DailySession();
        session.setSessionDate(sessionDate);
        session.setStatus(SessionStatus.OPEN);
        session.setCurrentServingTokenNumber(0);
        session.setMaxTokens(8);
        session.setBookingOpensAt(LocalDateTime.now().minusHours(1));
        session.setBookingClosesAt(LocalDateTime.now().plusHours(24));
        session.setCancellationClosesAt(LocalDateTime.now().plusHours(22));

        DailySession savedSession = dailySessionRepository.save(session);
        assertThat(savedSession.getId()).isNotNull();
        assertThat(dailySessionRepository.existsBySessionDate(sessionDate)).isTrue();

        // Create first booking
        TokenBooking booking1 = new TokenBooking();
        booking1.setSession(savedSession);
        booking1.setTokenNumber(1);
        booking1.setBookingReference("BMD-" + UUID.randomUUID().toString().substring(0, 8));
        booking1.setCancellationToken(UUID.randomUUID().toString());
        booking1.setPatientName("Suresh Kumar");
        booking1.setPhone("+919847012345");
        booking1.setAge(58);
        booking1.setVisitType(VisitType.NEW_PATIENT);
        booking1.setSymptoms("Migraine and dizziness");
        booking1.setStatus(TokenStatus.BOOKED);
        booking1.setIsWalkIn(false);
        booking1.setBookedAt(Instant.now());

        TokenBooking savedBooking = tokenBookingRepository.save(booking1);
        assertThat(savedBooking.getId()).isNotNull();

        // Anti-hoarding test: same phone number cannot book another active token in the same session
        TokenBooking hoardingBooking = new TokenBooking();
        hoardingBooking.setSession(savedSession);
        hoardingBooking.setTokenNumber(2);
        hoardingBooking.setBookingReference(
                "BMD-" + UUID.randomUUID().toString().substring(0, 8));
        hoardingBooking.setCancellationToken(UUID.randomUUID().toString());
        hoardingBooking.setPatientName("Suresh Kumar 2");
        hoardingBooking.setPhone("+919847012345"); // same phone
        hoardingBooking.setAge(58);
        hoardingBooking.setVisitType(VisitType.FOLLOW_UP);
        hoardingBooking.setStatus(TokenStatus.BOOKED);
        hoardingBooking.setIsWalkIn(false);
        hoardingBooking.setBookedAt(Instant.now());

        assertThatThrownBy(() -> tokenBookingRepository.saveAndFlush(hoardingBooking))
                .isInstanceOf(DataIntegrityViolationException.class);

        // Verification of queries
        var foundByCancelToken = tokenBookingRepository.findByCancellationToken(savedBooking.getCancellationToken());
        assertThat(foundByCancelToken).isPresent();
        assertThat(foundByCancelToken.get().getPatientName()).isEqualTo("Suresh Kumar");

        long activeCount = tokenBookingRepository.countActiveBookingsBySessionId(savedSession.getId());
        assertThat(activeCount).isEqualTo(1);
    }
}
