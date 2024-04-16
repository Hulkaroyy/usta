package uz.handihub.productms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.handihub.productms.domain.Content;

import java.util.UUID;

@Repository
public interface ContentRepository extends JpaRepository<Content, UUID> {
}
