package sg.com.officecleanings.workwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.com.officecleanings.workwise.model.EmployeeEvent;
import sg.com.officecleanings.workwise.repository.EmployeeEventRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeEventService {

    @Autowired
    private EmployeeEventRepository employeeEventRepository;

    public List<EmployeeEvent> getAllEmployeeEvents() {
        return employeeEventRepository.findAll();
    }

    public Optional<EmployeeEvent> getEmployeeEventById(int id) {
        return employeeEventRepository.findById(id);
    }

    public EmployeeEvent saveEmployeeEvent(EmployeeEvent employeeEvent) {
        return employeeEventRepository.save(employeeEvent);
    }

    public void deleteEmployeeEvent(int id) {
        employeeEventRepository.deleteById(id);
    }
}