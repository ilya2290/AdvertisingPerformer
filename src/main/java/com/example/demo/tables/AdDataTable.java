package com.example.demo.tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Represents a table entity storing advertising metrics.
 * Each instance of AdDataTable corresponds to a record in the 'ad_metrics' table.
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ad_metrics")
public class AdDataTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String pageName;

    @Column(nullable = false)
    private long views;

    @Column(nullable = false)
    private long visits;

    @Column(nullable = false)
    private long expenses;

    @Column(nullable = false)
    private LocalDateTime requestDate;

    /**
     * Automatically sets the current date and time before persisting the entity.
     * This method is annotated with @PrePersist to ensure the requestDate field
     * is populated with the creation timestamp.
     */
    @PrePersist
    protected void onCreate() {
        requestDate = LocalDateTime.now();
    }
}
