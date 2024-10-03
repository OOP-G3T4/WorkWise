package sg.com.officecleanings.workwise.controller;

import sg.com.officecleanings.workwise.model.Property;
import sg.com.officecleanings.workwise.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable int id) {
        Optional<Property> property = propertyService.getPropertyById(id);
        return property.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Property createProperty(@RequestBody Property property) {
        return propertyService.saveProperty(property);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable int id, @RequestBody Property propertyDetails) {
        Optional<Property> property = propertyService.getPropertyById(id);
        if (property.isPresent()) {
            propertyDetails.setPropertyId(id);
            return ResponseEntity.ok(propertyService.saveProperty(propertyDetails));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProperty(@PathVariable int id) {
        try {
            Optional<Property> propertyOptional = propertyService.getPropertyById(id);
            if (propertyOptional.isEmpty()) {
                String errorMessage = "Property with ID " + id + " not found.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
            propertyService.deleteProperty(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
