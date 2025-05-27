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
@Table(name = "export_products")
public class ExportProductModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "export_date", nullable = false)
    LocalDate exportDate;

    @ManyToOne
    @JoinColumn()
    UserModel userModel;

    @ManyToOne
    @JoinColumn()
    CustomerModel customerModel;

    @OneToMany(mappedBy = "exportProductModel", cascade = CascadeType.ALL)
    List<ExportDetailModel> exportDetailModel = new ArrayList<>();

}
