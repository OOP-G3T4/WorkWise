package sg.com.officecleanings.workwise.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.com.officecleanings.workwise.dto.JobAssignmentDTO;
import sg.com.officecleanings.workwise.service.AiService;
import sg.com.officecleanings.workwise.service.CheckerService;
import sg.com.officecleanings.workwise.service.JobEmployeeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
public class AiController {

    private final OpenAiChatModel aiModel;
    private final AiService aiService;
    private final JobEmployeeService jobEmployeeService;

    private final CheckerService checkerService;

    @Autowired
    public AiController(OpenAiChatModel aiModel, AiService aiService, JobEmployeeService jobEmployeeService, CheckerService checkerService) {
        this.aiModel = aiModel;
        this.aiService = aiService;
        this.jobEmployeeService = jobEmployeeService;
        this.checkerService = checkerService;
    }

    @PostMapping("/ai/generate")
    public ResponseEntity<String> generate() {
        int maxAttempts = 10;
        int attempt = 0;
        boolean success = false;
        String apiResponse = null;
        List<JobAssignmentDTO> assignments = new ArrayList<>();
        Map.Entry<Boolean, String> validationResult = null;

        while (attempt < maxAttempts && !success) {
            try {
                // Call the createEmployeeAssignments method
                StringBuilder prompt = aiService.createEmployeeAssignments();

                // Call the GPT-4o API to get the best candidate(s)
                apiResponse = aiModel.call(prompt.toString());

                // Process the API response to create a structured output
                assignments = parseApiResponse(apiResponse);
                validationResult = checkerService.validateBatchJobAssignments(assignments);

                if (validationResult.getKey()) {
                    jobEmployeeService.saveAssignments(assignments);
                    success = true;
                } else {
                    attempt++;
                }
            } catch (Exception e) {

                attempt++;
            }
        }

        if (success) {
            return new ResponseEntity<>("Schedule saved successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Schedule not saved. " + validationResult.getValue(), HttpStatus.BAD_REQUEST);
        }
    }


    private List<JobAssignmentDTO> parseApiResponse(String apiResponse) {
        List<JobAssignmentDTO> assignments = new ArrayList<>();

        // Find the first '{' and the last '}'
        int startIndex = apiResponse.indexOf('{');
        int endIndex = apiResponse.lastIndexOf('}');

        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            String jsonContent = apiResponse.substring(startIndex, endIndex + 1);

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode rootNode = objectMapper.readTree(jsonContent);
                JsonNode assignmentsNode = rootNode.path("assignments");

                for (JsonNode assignmentNode : assignmentsNode) {
                    int jobId = assignmentNode.path("job_id").asInt(); // Extract the first element of the array
                    List<Integer> employeeIds = new ArrayList<>();
                    for (JsonNode idNode : assignmentNode.path("employees_id")) {
                        employeeIds.add(idNode.asInt());
                    }
                    String reasoning = assignmentNode.path("reasoning").asText();

                    JobAssignmentDTO assignment = new JobAssignmentDTO(jobId, employeeIds, reasoning);
                    assignments.add(assignment);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return assignments;
    }


}
