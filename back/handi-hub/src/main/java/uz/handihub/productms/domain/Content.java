package uz.handihub.productms.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.http.MediaType;
import uz.handihub.productms.domain.converter.MediaTypeConverter;

import java.util.UUID;

@Data
@Entity
@Table(name = "content")
public class Content {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "photo_url", nullable = false, unique = true)
    private String photoUrl;

    @Convert(converter = MediaTypeConverter.class)
    @Column(name = "type", nullable = false)
    private MediaType type;

    @Column(name = "is_main")
    private Boolean isMain;

    @ManyToOne
    private Product product;
}
