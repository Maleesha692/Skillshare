package com.skillshare.skillsharebackend.service;

import com.skillshare.skillsharebackend.repository.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VerificationService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Transactional
    public void deleteCode(String email) {
        verificationCodeRepository.deleteByEmail(email); // or entityManager.remove(...)
    }
}

