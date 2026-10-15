package com.athiramk.bookmydoctor.clinic;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenBookingRepository extends JpaRepository<TokenBooking, Long> {

    Optional<TokenBooking> findByCancellationToken(String cancellationToken);

    Optional<TokenBooking> findByBookingReference(String bookingReference);

    List<TokenBooking> findBySessionIdOrderByTokenNumberAsc(Long sessionId);

    List<TokenBooking> findBySessionIdAndStatusOrderByTokenNumberAsc(Long sessionId, TokenStatus status);

    Optional<TokenBooking> findBySessionIdAndTokenNumberAndStatusNot(
            Long sessionId, Integer tokenNumber, TokenStatus status);

    Optional<TokenBooking> findBySessionIdAndPhoneAndStatusNot(Long sessionId, String phone, TokenStatus status);

    @Query("SELECT MAX(b.tokenNumber) FROM TokenBooking b WHERE b.session.id = :sessionId AND b.isWalkIn = true")
    Optional<Integer> findMaxWalkInTokenNumber(@Param("sessionId") Long sessionId);

    @Query("SELECT COUNT(b) FROM TokenBooking b WHERE b.session.id = :sessionId AND b.status != 'CANCELLED'")
    long countActiveBookingsBySessionId(@Param("sessionId") Long sessionId);
}
