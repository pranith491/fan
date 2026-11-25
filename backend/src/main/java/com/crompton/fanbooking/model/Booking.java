package com.crompton.fanbooking.model;
import jakarta.persistence.*;import java.time.LocalDateTime;
@Entity @Table(name="bookings")
public class Booking {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=100) private String customerName;
 @Column(nullable=false,length=10) private String phone;
 @Column(nullable=false,length=190) private String email;
 @Column(nullable=false) private Integer quantity;
 @Column(nullable=false,length=500) private String address;
 @Column(nullable=false,length=50) private String product="Crompton Table Fan";
 @Column(nullable=false,length=30) private String status="REQUESTED";
 private LocalDateTime createdAt=LocalDateTime.now();
 public Long getId(){return id;} public String getCustomerName(){return customerName;} public void setCustomerName(String v){customerName=v;}
 public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
 public String getEmail(){return email;} public void setEmail(String v){email=v;}
 public Integer getQuantity(){return quantity;} public void setQuantity(Integer v){quantity=v;}
 public String getAddress(){return address;} public void setAddress(String v){address=v;}
 public String getProduct(){return product;} public String getStatus(){return status;}
}