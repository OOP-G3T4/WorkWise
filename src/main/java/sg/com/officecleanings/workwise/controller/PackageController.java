package sg.com.officecleanings.workwise.controller;

import sg.com.officecleanings.workwise.model.SelectedPackage;
import sg.com.officecleanings.workwise.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/package")
public class PackageController {

    @Autowired
    private PackageService packageService;

    @GetMapping
    public List<SelectedPackage> getAllPackages() {
        return packageService.getAllPackages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SelectedPackage> getPackageById(@PathVariable String id) {
        Optional<SelectedPackage> packageEntity = packageService.getPackageById(id);
        return packageEntity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public SelectedPackage createPackage(@RequestBody SelectedPackage packageEntity) {
        return packageService.savePackage(packageEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SelectedPackage> updatePackage(@PathVariable String id,
            @RequestBody SelectedPackage packageDetails) {
        Optional<SelectedPackage> packageEntity = packageService.getPackageById(id);
        if (packageEntity.isPresent()) {
            packageDetails.setPackageId(id);
            return ResponseEntity.ok(packageService.savePackage(packageDetails));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePackage(@PathVariable String id) {
        try {
            Optional<SelectedPackage> packageOptional = packageService.getPackageById(id);
            if (packageOptional.isEmpty()) {
                String errorMessage = "Package with ID " + id + " not found.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
            packageService.deletePackage(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
