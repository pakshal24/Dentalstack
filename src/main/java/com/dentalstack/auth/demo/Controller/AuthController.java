package com.dentalstack.auth.demo.Controller;

import com.dentalstack.auth.demo.Repository.UserRepository;
import com.dentalstack.auth.demo.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    HashMap<String, String> hm = new HashMap<>();
    @PostMapping("/generate-otp")
    public ResponseEntity<String> generateOTP(@RequestBody String phoneNumber) {


        String otp = String.format("%06d", new Random().nextInt(999999));

        hm.put(phoneNumber, otp);

        // Store OTP in cache (e.g., using Caffeine Cache)
        // You'll need to configure cache manager and cache annotations

        return ResponseEntity.ok("OTP generated: " + otp);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestMap) {
        // Validate OTP from cache
        // Perform rate limiting logic (e.g., using Spring's RateLimiter)

        Optional<UserEntity> userOptional = userRepository.findByPhoneNumber(requestMap.get("phoneNumber"));
        if (userOptional.isPresent()) {
            // Validate OTP

            if(hm.get(requestMap.get("phoneNumber"))==requestMap.get("otp")) {
                return ResponseEntity.ok("Otp Verified! JWT: <your_token>");
            }

            // Generate JWT token using Spring Security


            return ResponseEntity.ok("Logged in successfully! JWT: <your_token>");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    }


}

