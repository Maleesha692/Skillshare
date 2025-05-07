package com.skillshare.skillsharebackend.service;

import com.skillshare.skillsharebackend.model.User;
import com.skillshare.skillsharebackend.model.VerificationCode;
import com.skillshare.skillsharebackend.repository.UserRepository;
import com.skillshare.skillsharebackend.repository.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ForgotPasswordService {

    @Autowired
    private VerificationCodeRepository codeRepo;
    @Autowired
    private VerificationService verificationService;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private EmailService emailService;

    public void sendCode(String email) throws Exception {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isEmpty()) throw new Exception("Email not registered");

        String code = String.valueOf((int)(Math.random() * 900000) + 100000); // 6-digit code
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(1);

        VerificationCode verification = new VerificationCode();
        verification.setEmail(email);
        verification.setCode(code);
        verification.setExpiry(expiry);

        verificationService.deleteCode(email); // clear old
        codeRepo.save(verification);

        emailService.sendVerificationEmail(email, "Your Verification Code", "Your code is: " + code);
    }

    public boolean verifyCode(String email, String code) {
        Optional<VerificationCode> optional = codeRepo.findByEmailAndCode(email, code);
        if (optional.isEmpty()) return false;

        VerificationCode vc = optional.get();
        if (vc.getExpiry().isBefore(LocalDateTime.now())) {
            codeRepo.delete(vc);
            return false;
        }

        return true;
    }

    public void resetPassword(String email, String newPassword) throws Exception {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isEmpty()) throw new Exception("User not found");

        User user = optionalUser.get();
        user.setPassword(newPassword); // hash in production
        userRepo.save(user);
        verificationService.deleteCode(email); // instead of codeRepo.deleteByEmail(email)

    }
}
