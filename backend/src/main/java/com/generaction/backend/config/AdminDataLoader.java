package com.generaction.backend.config;

import com.generaction.backend.entity.Admin;
import com.generaction.backend.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminDataLoader implements CommandLineRunner {

    private final AdminRepository adminRepository;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${app.admin.nombre}")
    private String adminNombre;

    @Override
    public void run(String... args) {
        if (adminRepository.findByEmail(adminEmail).isEmpty()) {
            Admin admin = new Admin();
            admin.setEmail(adminEmail);
            admin.setPasswordHash(adminPassword);
            admin.setNombre(adminNombre);
            admin.setActivo(true);
            admin.setRol("ADMIN");
            adminRepository.save(admin);
        }
    }
}
