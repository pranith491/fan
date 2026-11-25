package com.crompton.fanbooking.model;
import jakarta.persistence.*;import java.time.LocalDateTime;
@Entity @Table(name="customer_messages")
public class CustomerMessage {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=100) private String name;
 @Column(nullable=false,length=190) private String email;
 @Column(nullable=false,length=2000) private String message;
 private LocalDateTime createdAt=LocalDateTime.now();
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public String getMessage(){return message;} public void setMessage(String v){message=v;}
}