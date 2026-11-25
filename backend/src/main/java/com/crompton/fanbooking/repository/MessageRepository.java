package com.crompton.fanbooking.repository;
import com.crompton.fanbooking.model.CustomerMessage;import org.springframework.data.jpa.repository.JpaRepository;
public interface MessageRepository extends JpaRepository<CustomerMessage,Long>{}