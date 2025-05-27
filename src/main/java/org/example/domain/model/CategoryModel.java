package org.example.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "categories")
public class CategoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String name;

    @Column(name = "create_at", nullable = false, updatable = false)
    @CreationTimestamp
    LocalDate createdAt;
    
    @Column(name = "update_at", nullable = false)
    @UpdateTimestamp
    LocalDate updatedAt;

    @OneToMany(mappedBy = "categoryModel", cascade = CascadeType.REMOVE)
    List<ProductModel> productModels = new ArrayList<>();
}
