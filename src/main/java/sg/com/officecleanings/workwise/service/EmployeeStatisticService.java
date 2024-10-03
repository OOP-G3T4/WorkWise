package sg.com.officecleanings.workwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.com.officecleanings.workwise.model.EmployeeStatistic;
import sg.com.officecleanings.workwise.repository.EmployeeStatisticRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeStatisticService {

    @Autowired
    private EmployeeStatisticRepository employeeStatisticRepository;

    public List<EmployeeStatistic> getAllEmployeeStatistics() {
        return employeeStatisticRepository.findAll();
    }

    public Optional<EmployeeStatistic> getEmployeeStatisticById(int id) {
        return employeeStatisticRepository.findById(id);
    }

    public EmployeeStatistic saveEmployeeStatistic(EmployeeStatistic employeeStatistic) {
        return employeeStatisticRepository.save(employeeStatistic);
    }

    public void deleteEmployeeStatistic(int id) {
        employeeStatisticRepository.deleteById(id);
    }
}
