package com.athiramk.bookmydoctor.clinic;

import com.athiramk.bookmydoctor.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "token_bookings")
public class TokenBooking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_booking_id_generator")
    @SequenceGenerator(name = "token_booking_id_generator", sequenceName = "token_booking_id_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private DailySession session;

    @Column(name = "token_number", nullable = false)
    private Integer tokenNumber;

    @Column(name = "booking_reference", nullable = false, unique = true)
    private String bookingReference;

    @Column(name = "cancellation_token", nullable = false, unique = true)
    private String cancellationToken;

    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private Integer age;

    @Column(name = "visit_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VisitType visitType;

    @Column
    private String symptoms;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenStatus status;

    @Column(name = "is_walk_in", nullable = false)
    private Boolean isWalkIn = false;

    @Column(name = "booked_at", nullable = false)
    private Instant bookedAt;

    @Column(name = "cancelled_at")
    private Instant cancelledAt;

    public TokenBooking() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DailySession getSession() {
        return session;
    }

    public void setSession(DailySession session) {
        this.session = session;
    }

    public Integer getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(Integer tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public String getCancellationToken() {
        return cancellationToken;
    }

    public void setCancellationToken(String cancellationToken) {
        this.cancellationToken = cancellationToken;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public VisitType getVisitType() {
        return visitType;
    }

    public void setVisitType(VisitType visitType) {
        this.visitType = visitType;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public TokenStatus getStatus() {
        return status;
    }

    public void setStatus(TokenStatus status) {
        this.status = status;
    }

    public Boolean getIsWalkIn() {
        return isWalkIn;
    }

    public void setIsWalkIn(Boolean walkIn) {
        isWalkIn = walkIn;
    }

    public Instant getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(Instant bookedAt) {
        this.bookedAt = bookedAt;
    }

    public Instant getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(Instant cancelledAt) {
        this.cancelledAt = cancelledAt;
    }
}
