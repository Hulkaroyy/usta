package uz.handihub.productms.domain;

import jakarta.persistence.*;
import lombok.Data;
import uz.handihub.productms.domain.enumeration.Category;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Float price;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "product")
    private List<Content> contents;
}
