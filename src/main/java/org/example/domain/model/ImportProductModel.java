package org.example.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
@Table(name = "import_products")
public class ImportProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "import_date", nullable = false)
    LocalDate importDate;

    @ManyToOne
    @JoinColumn()
    UserModel userModel;

    @ManyToOne
    @JoinColumn()
    SupplierModel supplierModel;

    @OneToMany(mappedBy = "importProductModel", cascade = CascadeType.ALL)
    List<ImportDetailModel> importDetails = new ArrayList<>();
}