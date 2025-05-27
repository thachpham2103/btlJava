package org.example.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.processing.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Table(name = "suppliers")

public class SupplierModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String address;

    @Column(nullable = false, unique = true)
    String phoneNumber;

    @Column(nullable = false)
    String email;

    @OneToMany(mappedBy = "supplierModel", cascade = CascadeType.REMOVE)
    List<ImportProductModel> importProduct = new ArrayList<>();

}