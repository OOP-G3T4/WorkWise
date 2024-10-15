package sg.com.officecleanings.workwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.com.officecleanings.workwise.model.EmployeeEvent;
import sg.com.officecleanings.workwise.repository.EmployeeEventRepository;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;


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

    public List<EmployeeEvent> getEmployeeEventsByWeek(LocalDate date) {
        LocalDate startOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        Date startDate = Date.valueOf(startOfWeek);
        Date endDate = Date.valueOf(endOfWeek);

        // Fetch events that fall within the specified date range
        return employeeEventRepository.findByEventDateBetweenOrderByEventDateAsc(startDate, endDate);
    }


    public EmployeeEvent saveEmployeeEvent(EmployeeEvent employeeEvent) {
        return employeeEventRepository.save(employeeEvent);
    }

    public void deleteEmployeeEvent(int id) {
        employeeEventRepository.deleteById(id);
    }
}