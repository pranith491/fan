package com.crompton.fanbooking.controller;
import com.crompton.fanbooking.model.*;import com.crompton.fanbooking.repository.*;
import jakarta.servlet.http.HttpSession;import jakarta.validation.Valid;import jakarta.validation.constraints.*;
import org.springframework.http.*;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api")
public class ApiController {
 private final CustomerRepository customers;private final BookingRepository bookings;private final MessageRepository messages;private final PasswordEncoder encoder;
 public ApiController(CustomerRepository c,BookingRepository b,MessageRepository m,PasswordEncoder e){customers=c;bookings=b;messages=m;encoder=e;}
 record RegisterRequest(@NotBlank @Size(max=100) String name,@NotBlank @Email @Size(max=190) String email,@NotBlank @Size(min=8,max=72) String password){}
 record LoginRequest(@NotBlank @Email String email,@NotBlank String password){}
 record BookingRequest(@NotBlank @Size(max=100) String customerName,@NotBlank @Pattern(regexp="[0-9]{10}") String phone,@NotBlank @Email @Size(max=190) String email,@Min(1) @Max(10) Integer quantity,@NotBlank @Size(max=500) String address){}
 record MessageRequest(@NotBlank @Size(max=100) String name,@NotBlank @Email @Size(max=190) String email,@NotBlank @Size(max=2000) String message){}
 @PostMapping("/auth/register") public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest r){
  if(customers.existsByEmail(r.email().toLowerCase()))return ResponseEntity.status(409).body(Map.of("message","This email is already registered."));
  Customer c=new Customer();c.setName(r.name().trim());c.setEmail(r.email().toLowerCase().trim());c.setPasswordHash(encoder.encode(r.password()));customers.save(c);
  return ResponseEntity.status(201).body(Map.of("message","Account created successfully. Please login."));
 }
 @PostMapping("/auth/login") public ResponseEntity<?> login(@Valid @RequestBody LoginRequest r,HttpSession session){
  Customer c=customers.findByEmail(r.email().toLowerCase().trim()).orElse(null);
  if(c==null||!encoder.matches(r.password(),c.getPasswordHash()))return ResponseEntity.status(401).body(Map.of("message","Invalid email or password."));
  session.setAttribute("customerId",c.getId());session.setAttribute("customerName",c.getName());return ResponseEntity.ok(Map.of("message","Login successful. Welcome "+c.getName()+"."));
 }
 @PostMapping("/auth/logout") public Map<String,String> logout(HttpSession session){session.invalidate();return Map.of("message","You have been logged out.");}
 @PostMapping("/bookings") public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequest r){
  Booking b=new Booking();b.setCustomerName(r.customerName().trim());b.setPhone(r.phone());b.setEmail(r.email().toLowerCase().trim());b.setQuantity(r.quantity());b.setAddress(r.address().trim());Booking saved=bookings.save(b);
  return ResponseEntity.status(201).body(Map.of("id",saved.getId(),"message","Booking request saved."));
 }
 @PostMapping("/messages") public ResponseEntity<?> createMessage(@Valid @RequestBody MessageRequest r){
  CustomerMessage m=new CustomerMessage();m.setName(r.name().trim());m.setEmail(r.email().toLowerCase().trim());m.setMessage(r.message().trim());messages.save(m);
  return ResponseEntity.status(201).body(Map.of("message","Message received successfully."));
 }
}