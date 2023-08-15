package com.auth.loginviaotp.rest;

import com.auth.loginviaotp.entity.User;
import com.auth.loginviaotp.repository.UserRepository;
import com.auth.loginviaotp.service.JwtTokenService;
import com.auth.loginviaotp.service.OtpCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private OtpCacheService otpCacheService;

    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private UserRepository userRepository;



    @PostMapping("/generate-otp")
    public ResponseEntity<String> generateOTP(@RequestBody String phoneNumber) {
        // Call dummy vendor API here to generate OTP

        // For demonstration, let's generate a random OTP
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpCacheService.storeOtp(phoneNumber, otp);

        // Store OTP in cache (e.g., using Caffeine Cache)
        // You'll need to configure cache manager and cache annotations

        return ResponseEntity.ok("OTP generated: " + otp);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestMap) {
        String phoneNumber = requestMap.get("phoneNumber");
        String otp = requestMap.get("otp");
        String cachedOtp = otpCacheService.retrieveOtp(phoneNumber);
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if(userOptional.isPresent()) {
            //otp validation
            if (cachedOtp != null && cachedOtp.equals(otp)) {
                String jwtToken = jwtTokenService.generateJwtToken(phoneNumber);
                otpCacheService.clearOtpCache(phoneNumber);
                return ResponseEntity.ok("Logged in successfully! JWT: " + jwtToken);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
    }
}

