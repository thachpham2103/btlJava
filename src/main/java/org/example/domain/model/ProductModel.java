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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "products")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String name;

    @Column(nullable = false)
    Double price;

    @Column(nullable = false)
    Long remainingQuantity;

    @Column(nullable = false)
    Long soldQuantity;

    @Column(name = "create_at", nullable = false)
    @CreationTimestamp
    LocalDate createdAt;

    @ManyToOne
    @JoinColumn
    CategoryModel categoryModel;

    @OneToMany(mappedBy = "productModel", cascade = CascadeType.REMOVE)
    List<OrderDetailModel> orderDetailModels  = new ArrayList<>();

}
