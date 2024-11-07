package sg.com.officecleanings.workwise.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sg.com.officecleanings.workwise.model.EmployeeLeave;
import sg.com.officecleanings.workwise.service.EmployeeLeaveService;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "${cors.allowedOrigins}")
@RequestMapping("/api/employee-leave")
public class EmployeeLeaveController {

    @Autowired
    private EmployeeLeaveService employeeLeaveService;

    @PostMapping("/apply")
    public ResponseEntity<EmployeeLeave> applyForLeave(
            @RequestParam("employeeLeave") String employeeLeaveJson,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        EmployeeLeave employeeLeave = objectMapper.readValue(employeeLeaveJson, EmployeeLeave.class);
        EmployeeLeave savedEmployeeLeave = employeeLeaveService.saveEmployeeLeave(employeeLeave, file);
        return ResponseEntity.ok(savedEmployeeLeave);
    }

    @PostMapping("/{id}/upload-mc")
    public ResponseEntity<EmployeeLeave> uploadMcProof(
            @PathVariable int id,
            @RequestParam("file") MultipartFile file) throws IOException {
        EmployeeLeave updatedEmployeeLeave = employeeLeaveService.uploadMcProof(id, file);
        return ResponseEntity.ok(updatedEmployeeLeave);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<EmployeeLeave> approveLeave(@PathVariable int id) {
        EmployeeLeave approvedEmployeeLeave = employeeLeaveService.approveLeave(id);
        return ResponseEntity.ok(approvedEmployeeLeave);
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<EmployeeLeave> rejectLeave(@PathVariable int id) {
        EmployeeLeave rejectedEmployeeLeave = employeeLeaveService.rejectLeave(id);
        return ResponseEntity.ok(rejectedEmployeeLeave);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeLeave> getLeaveById(@PathVariable int id) {
        EmployeeLeave employeeLeave = employeeLeaveService.getLeaveById(id);
        return ResponseEntity.ok(employeeLeave);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getMcProofImage(@PathVariable int id) {
        byte[] image = employeeLeaveService.getMcProofImage(id);
        if (image == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeLeave>> getAllEmployeeLeaves() {
        List<EmployeeLeave> employeeLeaves = employeeLeaveService.getAllEmployeeLeaves();
        return ResponseEntity.ok(employeeLeaves);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeLeave>> getEmployeeLeavesByEmployeeId(@PathVariable int employeeId) {
        List<EmployeeLeave> employeeLeaves = employeeLeaveService.getEmployeeLeavesByEmployeeId(employeeId);
        return ResponseEntity.ok(employeeLeaves);
    }
}