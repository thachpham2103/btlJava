package org.example.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "customers")
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, updatable = false, unique = true)
    String name;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String address;

    @Column(nullable = false, unique = true)
    String phoneNumber;

    @OneToMany(mappedBy = "customerModel", cascade = CascadeType.REMOVE)
    List<ExportProductModel> exportProductModels = new ArrayList<>();

}
