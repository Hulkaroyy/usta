package uz.handihub.productms.domain;

import jakarta.persistence.*;
import lombok.Data;
import uz.handihub.productms.domain.enumeration.OrderStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "total_amount", nullable = false)
    private Float totalAmount;

    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Cart> carts;

}
