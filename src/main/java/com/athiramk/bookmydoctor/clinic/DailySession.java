package com.athiramk.bookmydoctor.clinic;

import com.athiramk.bookmydoctor.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "daily_sessions")
public class DailySession extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "daily_session_id_generator")
    @SequenceGenerator(name = "daily_session_id_generator", sequenceName = "daily_session_id_seq")
    private Long id;

    @Column(name = "session_date", nullable = false, unique = true)
    private LocalDate sessionDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SessionStatus status;

    @Column(name = "current_serving_token_number", nullable = false)
    private Integer currentServingTokenNumber;

    @Column(name = "booking_opens_at", nullable = false)
    private LocalDateTime bookingOpensAt;

    @Column(name = "booking_closes_at", nullable = false)
    private LocalDateTime bookingClosesAt;

    @Column(name = "cancellation_closes_at", nullable = false)
    private LocalDateTime cancellationClosesAt;

    @Column(name = "max_tokens", nullable = false)
    private Integer maxTokens;

    @Column
    private String notes;

    public DailySession() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDate sessionDate) {
        this.sessionDate = sessionDate;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public void setStatus(SessionStatus status) {
        this.status = status;
    }

    public Integer getCurrentServingTokenNumber() {
        return currentServingTokenNumber;
    }

    public void setCurrentServingTokenNumber(Integer currentServingTokenNumber) {
        this.currentServingTokenNumber = currentServingTokenNumber;
    }

    public LocalDateTime getBookingOpensAt() {
        return bookingOpensAt;
    }

    public void setBookingOpensAt(LocalDateTime bookingOpensAt) {
        this.bookingOpensAt = bookingOpensAt;
    }

    public LocalDateTime getBookingClosesAt() {
        return bookingClosesAt;
    }

    public void setBookingClosesAt(LocalDateTime bookingClosesAt) {
        this.bookingClosesAt = bookingClosesAt;
    }

    public LocalDateTime getCancellationClosesAt() {
        return cancellationClosesAt;
    }

    public void setCancellationClosesAt(LocalDateTime cancellationClosesAt) {
        this.cancellationClosesAt = cancellationClosesAt;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isBookingWindowOpen(LocalDateTime now) {
        return status == SessionStatus.OPEN && !now.isBefore(bookingOpensAt) && !now.isAfter(bookingClosesAt);
    }

    public boolean isCancellationWindowOpen(LocalDateTime now) {
        return (status == SessionStatus.OPEN || status == SessionStatus.IN_PROGRESS)
                && !now.isAfter(cancellationClosesAt);
    }
}
