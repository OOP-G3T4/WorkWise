package sg.com.officecleanings.workwise.service;

import sg.com.officecleanings.workwise.model.Admin;
import sg.com.officecleanings.workwise.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(int id) {
        return adminRepository.findById(id);
    }

    public Admin saveAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    public void deleteAdmin(int id) {
        adminRepository.deleteById(id);
    }

    public boolean verifyPassword(int adminId, String rawPassword) {
        Optional<Admin> admin = adminRepository.findById(adminId);
        return admin.isPresent() && passwordEncoder.matches(rawPassword, admin.get().getPassword());
    }
}

