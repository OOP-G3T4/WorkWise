package sg.com.officecleanings.workwise.controller;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import sg.com.officecleanings.workwise.model.Job;
import sg.com.officecleanings.workwise.service.AiService;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AiController {

    private final OpenAiChatModel aiModel;
    private final AiService aiService;

    @Autowired
    public AiController(OpenAiChatModel aiModel, AiService aiService) {
        this.aiModel = aiModel;
        this.aiService = aiService;
    }

//    @GetMapping("/ai/generate")
//    public Map<String, String> generate(@RequestParam(value = "message", defaultValue = "Return: 'Error with AI input'") String message,
//                                        @RequestParam(value = "context", defaultValue = "General") String context,
//                                        @RequestParam(value = "instructions", defaultValue = "Be creative") String instructions) {
//        String complexPrompt = aiService.createComplexPrompt(message, context, instructions);
//        return Map.of("generation", aiModel.call(complexPrompt));
//    }

    @PostMapping("/ai/generate")
    public Map<String, String> generate(@RequestBody List<Job> jobs) {
        // Extract date from the first job in the list (assuming all jobs have the same date)
        java.util.Date utilDate = jobs.get(0).getDate();
        Date sqlDate = new Date(utilDate.getTime()); // Convert java.util.Date to java.sql.Date

        // Create the complex prompt using AiService
        String complexPrompt = aiService.createComplexPrompt(jobs, sqlDate);

        // Call the GPT-4 API to get the best candidate(s)
        String availableEmployees = aiModel.call(complexPrompt);

        return Map.of("availableEmployees", availableEmployees);
    }

}