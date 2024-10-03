package sg.com.officecleanings.workwise;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.model.Property;
import sg.com.officecleanings.workwise.model.Client;
import sg.com.officecleanings.workwise.service.AiService;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest // This will load the Spring context for testing
public class AiServiceTest {

    @Autowired
    private AiService aiService; // Spring will inject the AiService instance

    @Test
    public void testCreateComplexPrompt() {
        List<Job> jobs = createDummyJobs();
        Date testDate = Date.valueOf("2024-10-01");

        String prompt = aiService.createComplexPrompt(jobs, testDate);
        assertNotNull(prompt); // Ensure that the prompt is not null
        System.out.println(prompt); // Print the prompt for verification
    }

    private List<Job> createDummyJobs() {
        Client client1 = new Client();
        client1.setName("Client A");

        Property property1 = new Property();
        property1.setAddress("123 Example St.");

        Job job1 = new Job();
        job1.setJobId(1);
        job1.setClient(client1);
        job1.setProperty(property1);
        job1.setDate(Date.valueOf("2024-10-01"));
        job1.setStartTime(Time.valueOf("09:00:00"));
        job1.setActualDuration(2);

        return List.of(job1); // Return a list of jobs
    }
}
