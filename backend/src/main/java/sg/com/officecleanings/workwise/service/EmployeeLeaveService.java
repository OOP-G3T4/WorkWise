package sg.com.officecleanings.workwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sg.com.officecleanings.workwise.model.EmployeeLeave;
import sg.com.officecleanings.workwise.repository.EmployeeLeaveRepository;


import java.io.IOException;
import java.util.Optional;
import java.util.List;



@Service
public class EmployeeLeaveService {

    @Autowired
    private EmployeeLeaveRepository employeeLeaveRepository;

    public EmployeeLeave saveEmployeeLeave(EmployeeLeave employeeLeave, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            employeeLeave.setMcProofImg(file.getBytes());
            employeeLeave.setMcProofUploaded(true);
        } else{
            if (employeeLeave.getLeaveType() == EmployeeLeave.LeaveType.MC) {
                employeeLeave.setMcProofUploaded(false);
            }
        }
        return employeeLeaveRepository.save(employeeLeave);
    }

    public EmployeeLeave uploadMcProof(int id, MultipartFile file) throws IOException {
        Optional<EmployeeLeave> optionalEmployeeLeave = employeeLeaveRepository.findById(id);
        if (optionalEmployeeLeave.isPresent()) {
            EmployeeLeave employeeLeave = optionalEmployeeLeave.get();
            employeeLeave.setMcProofImg(file.getBytes());
            employeeLeave.setMcProofUploaded(true);
            return employeeLeaveRepository.save(employeeLeave);
        }
        return null;
    }

    public EmployeeLeave approveLeave(int id) {
        Optional<EmployeeLeave> optionalEmployeeLeave = employeeLeaveRepository.findById(id);
        if (optionalEmployeeLeave.isPresent()) {
            EmployeeLeave employeeLeave = optionalEmployeeLeave.get();
            employeeLeave.setStatus(EmployeeLeave.LeaveStatus.APPROVED);
            return employeeLeaveRepository.save(employeeLeave);
        }
        return null;
    }

    public EmployeeLeave rejectLeave(int id) {
        Optional<EmployeeLeave> optionalEmployeeLeave = employeeLeaveRepository.findById(id);
        if (optionalEmployeeLeave.isPresent()) {
            EmployeeLeave employeeLeave = optionalEmployeeLeave.get();
            employeeLeave.setStatus(EmployeeLeave.LeaveStatus.REJECTED);
            return employeeLeaveRepository.save(employeeLeave);
        }
        return null;
    }

    public EmployeeLeave getLeaveById(int id) {
        return employeeLeaveRepository.findById(id).orElse(null);
    }

    public byte[] getMcProofImage(int employeeLeaveId) {
        Optional<EmployeeLeave> employeeLeave = employeeLeaveRepository.findById(employeeLeaveId);
        return employeeLeave.map(EmployeeLeave::getMcProofImg).orElse(null);
    }

    public List<EmployeeLeave> getAllEmployeeLeaves() {
        return employeeLeaveRepository.findAll();
    }

    public List<EmployeeLeave> getEmployeeLeavesByEmployeeId(int employeeId) {
        return employeeLeaveRepository.findByEmployeeEmployeeId(employeeId);
    }
}