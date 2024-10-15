package sg.com.officecleanings.workwise.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.service.AiService;

import java.io.IOException;
import java.util.*;


@RestController
public class AiController {

    private final OpenAiChatModel aiModel;
    private final AiService aiService;

    @Autowired
    public AiController(OpenAiChatModel aiModel, AiService aiService) {
        this.aiModel = aiModel;
        this.aiService = aiService;
    }

    @PostMapping("/ai/generate")
    public Map<String, List<Map<String, Object>>> generate() {

        // Call the createEmployeeAssignments method
        StringBuilder prompt = aiService.createEmployeeAssignments();

        // Call the GPT-4o API to get the best candidate(s)
        String apiResponse = aiModel.call(prompt.toString());
        // Process the API response to create a structured output
        return parseApiResponse(apiResponse);
    }

    private Map<String, List<Map<String, Object>>> parseApiResponse(String apiResponse) {
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        List<Map<String, Object>> assignments = new ArrayList<>();

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
                    Map<String, Object> assignment = new LinkedHashMap<>(); // Use LinkedHashMap to maintain order
                    JsonNode jobIdNode = assignmentNode.path("job_id").get(0);
                    if (jobIdNode != null && !jobIdNode.isNull()) {
                        assignment.put("job_id", jobIdNode.asInt());
                    }

                    List<Integer> employeeIds = new ArrayList<>();
                    for (JsonNode idNode : assignmentNode.path("employees_id")) {
                        if (idNode != null && !idNode.isNull()) {
                            employeeIds.add(idNode.asInt());
                        }
                    }
                    assignment.put("employees_id", employeeIds);

                    assignment.put("reasoning", assignmentNode.path("reasoning").asText());

                    assignments.add(assignment);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        result.put("assignments", assignments);
        return result;
    }

}
