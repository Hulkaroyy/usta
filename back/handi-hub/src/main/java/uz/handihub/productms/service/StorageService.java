package uz.handihub.productms.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface StorageService {

    Optional<Resource> store(MultipartFile file);

    Optional<Resource> fetch(String path);

}
