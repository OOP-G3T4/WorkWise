package sg.com.officecleanings.workwise.service;

import sg.com.officecleanings.workwise.model.SelectedPackage;
import sg.com.officecleanings.workwise.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    public List<SelectedPackage> getAllPackages() {
        return packageRepository.findAll();
    }

    public Optional<SelectedPackage> getPackageById(String id) {
        return packageRepository.findById(id);
    }

    public SelectedPackage savePackage(SelectedPackage packageEntity) {
        return packageRepository.save(packageEntity);
    }

    public void deletePackage(String id) {
        packageRepository.deleteById(id);
    }
}
