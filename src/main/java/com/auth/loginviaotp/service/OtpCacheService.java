package com.auth.loginviaotp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class OtpCacheService {

    private final Cache otpCache;

    public OtpCacheService(CacheManager cacheManager) {
        this.otpCache = cacheManager.getCache("otpCache");
    }

    @Cacheable(cacheNames = "otpCache", key = "#phoneNumber")
    public String retrieveOtp(String phoneNumber) {
        Cache.ValueWrapper valueWrapper = otpCache.get(phoneNumber);
        if (valueWrapper != null) {
            return (String) valueWrapper.get();
        }
        return null; // OTP not found in cache
    }

    public void storeOtp(String phoneNumber, String otp) {
        otpCache.put(phoneNumber, otp);
    }

    public void clearOtpCache(String phoneNumber) {
        otpCache.evict(phoneNumber);
    }
}
