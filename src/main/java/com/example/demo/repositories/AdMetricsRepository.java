package com.example.demo.repositories;

import com.example.demo.tables.AdDataTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for accessing and managing AdDataTable entities in the database.
 */
@Repository
public interface AdMetricsRepository extends JpaRepository<AdDataTable, Integer> {

    /**
     * Retrieves a list of AdDataTable entities where the requestDate matches the current date.
     *
     * @return List of AdDataTable entities with requestDate equal to the current date.
     */
    @Query("SELECT a FROM AdDataTable a WHERE cast(a.requestDate as date) = current_date")
    List<AdDataTable> findAllByRequestDateToday();
}
