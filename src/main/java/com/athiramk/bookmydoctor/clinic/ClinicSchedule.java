package com.athiramk.bookmydoctor.clinic;

import com.athiramk.bookmydoctor.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalTime;

@Entity
@Table(name = "clinic_schedules")
public class ClinicSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clinic_schedule_id_generator")
    @SequenceGenerator(name = "clinic_schedule_id_generator", sequenceName = "clinic_schedule_id_seq")
    private Long id;

    @Column(name = "doctor_name", nullable = false)
    private String doctorName;

    @Column(nullable = false)
    private String specialization;

    @Column(name = "clinic_address", nullable = false)
    private String clinicAddress;

    @Column(name = "max_tokens_per_day", nullable = false)
    private Integer maxTokensPerDay;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "active_days", nullable = false)
    private String activeDays;

    @Column(name = "booking_open_time", nullable = false)
    private LocalTime bookingOpenTime;

    @Column(name = "cancellation_cutoff_time", nullable = false)
    private LocalTime cancellationCutoffTime;

    public ClinicSchedule() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public Integer getMaxTokensPerDay() {
        return maxTokensPerDay;
    }

    public void setMaxTokensPerDay(Integer maxTokensPerDay) {
        this.maxTokensPerDay = maxTokensPerDay;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getActiveDays() {
        return activeDays;
    }

    public void setActiveDays(String activeDays) {
        this.activeDays = activeDays;
    }

    public LocalTime getBookingOpenTime() {
        return bookingOpenTime;
    }

    public void setBookingOpenTime(LocalTime bookingOpenTime) {
        this.bookingOpenTime = bookingOpenTime;
    }

    public LocalTime getCancellationCutoffTime() {
        return cancellationCutoffTime;
    }

    public void setCancellationCutoffTime(LocalTime cancellationCutoffTime) {
        this.cancellationCutoffTime = cancellationCutoffTime;
    }
}
