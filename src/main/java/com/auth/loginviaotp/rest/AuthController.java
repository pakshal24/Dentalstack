package com.auth.loginviaotp.rest;

import com.auth.loginviaotp.entity.User;
import com.auth.loginviaotp.repository.UserRepository;
import com.auth.loginviaotp.service.JwtTokenService;
import com.auth.loginviaotp.service.OtpCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> generateOTP(@RequestBody Map<String, String> requestMap) {

        String otp = String.format("%06d", new Random().nextInt(999999)); //Random Otp Generation
        otpCacheService.storeOtp(requestMap.get("phoneNumber"), otp);

        return ResponseEntity.ok("OTP generated: " + otp);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestMap) {
        String phoneNumber = requestMap.get("phoneNumber");
        String otp = requestMap.get("otp");
        String cachedOtp = otpCacheService.retrieveOtp(phoneNumber);
        System.out.println(cachedOtp);
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        if(userOptional.isPresent()) {
            //otp validation
            if (cachedOtp != null && cachedOtp.equals(otp)) {
                String jwtToken = jwtTokenService.generateJwtToken(phoneNumber);
                otpCacheService.clearOtpCache(phoneNumber); //Clearing Cache after login
//                System.out.println(otpCacheService.retrieveOtp(phoneNumber));
                return ResponseEntity.ok("Logged in successfully! JWT: " + jwtToken);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
    }
}

