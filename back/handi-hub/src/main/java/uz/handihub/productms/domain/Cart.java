package uz.handihub.productms.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "count")
    private Integer count;

    @ManyToOne
    private Product product;
}
