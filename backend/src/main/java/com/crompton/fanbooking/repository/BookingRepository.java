package com.crompton.fanbooking.repository;
import com.crompton.fanbooking.model.Booking;import org.springframework.data.jpa.repository.JpaRepository;
public interface BookingRepository extends JpaRepository<Booking,Long>{}