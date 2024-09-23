package com.example.stock_management.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "warehouses")
@ToString
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public String name;

    public Double latitude;

    @Column
    public Double longitude;

    @Column
    private Integer minCapacity;

    @Column
    private Integer maxCapacity;

    // Path or URL to the image
    @Column
    private String imagePath;
}
