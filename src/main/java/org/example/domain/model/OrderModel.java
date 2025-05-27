package org.example.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, updatable = false, unique = true)
    @CreationTimestamp
    LocalDate orderAt;

    @ManyToOne
    @JoinColumn
    UserModel userModel;

    @ManyToOne
    @JoinColumn
    ShipmentModel shipmentModel;

    @OneToMany(mappedBy = "orderModel", cascade = CascadeType.REMOVE)
    List<OrderDetailModel> orderDetailModelList = new ArrayList<>();

    @OneToMany(mappedBy = "orderModel", cascade = CascadeType.REMOVE)
    List<PaymentModel> paymentModelList = new ArrayList<>();
}
