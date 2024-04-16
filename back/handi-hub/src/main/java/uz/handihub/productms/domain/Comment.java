package uz.handihub.productms.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "message", nullable = false, length = 1024)
    private String message;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

}
