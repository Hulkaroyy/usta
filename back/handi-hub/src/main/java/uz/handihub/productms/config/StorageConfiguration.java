package uz.handihub.productms.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uz.handihub.productms.domain.enumeration.MimeType;
import uz.handihub.productms.properties.StorageProperties;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class StorageConfiguration {

    private final StorageProperties properties;
    private final Map<String, String> supportedMimeTypes;

    public StorageConfiguration(StorageProperties properties) {
        this.properties = properties;
        this.supportedMimeTypes = initializeSupportedMimeTypes();
    }

    @PostConstruct
    public void initDirectory() {
        log.info("Start creating storage directory...");

        File storageDirectory = new File(properties.getLocation());

        if (!storageDirectory.exists()) {
            boolean isDirectoryCreated = storageDirectory.mkdirs();

            log.info("Is directory created? Result: {}", isDirectoryCreated);
        } else {

            log.info("Storage directory is already created. Path: {}", storageDirectory.getAbsolutePath());
        }
    }

    public String getStorageLocation() {
        return properties.getLocation();
    }

    public Map<String, String> getSupportedMimeTypes() {
        return supportedMimeTypes;
    }

    private Map<String, String> initializeSupportedMimeTypes() {

        Map<String, String> map = new HashMap<>();
        Arrays.stream(MimeType.values())
                .forEach(mimeType -> map.put(mimeType.getType().getType(), mimeType.getExtension()));

        return map;
    }
}
