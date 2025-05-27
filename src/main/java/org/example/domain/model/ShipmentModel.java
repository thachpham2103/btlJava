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
@Table(name = "shipments")
public class ShipmentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    ShipmentStatus shipmentStatus;

    @Column(nullable = false)
    String shipmentVehicle;

    @Column(name = "delivered_at", nullable = false, updatable = false)
    @CreationTimestamp
    LocalDate deliveredAt;

    @OneToMany(mappedBy = "shipmentModel", cascade = CascadeType.REMOVE)
    List<OrderModel> orderModels = new ArrayList<>();
}
