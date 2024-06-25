package com.example.demo.api;

import com.example.demo.addata.AdData;
import com.example.demo.repositories.AdMetricsRepository;
import com.example.demo.tables.AdDataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * AdController handles HTTP requests related to advertisement metrics data.
 * It provides endpoints for retrieving, creating, updating, and deleting
 * advertisement metrics information. The class integrates with a repository
 * to perform CRUD operations on AdData objects, ensuring interaction with
 * persistent storage. Additionally, it manages CORS configuration to
 * allow cross-origin requests from a specific domain, enhancing the
 * accessibility and security of API interactions. AdController serves as
 * the main controller for advertisement metrics data, facilitating
 * communication between clients and the application's backend services.
 */
@RestController
public class AdController {

    @Autowired
    AdData adData;

    @Autowired
    private AdMetricsRepository adMetricsRepository;

    /**
     * Get page statistics for today.
     * <p>
     * This method retrieves the ad data records with today's date.
     * If the records are found, the first three records are returned.
     * If no records are found, a 404 Not Found response is returned.
     *
     * @return ResponseEntity with a list of AdDataTable or 404 Not Found.
     */
    @GetMapping("/api/v1/page-stats-today")
    public ResponseEntity<List<AdDataTable>> getPageData() {
        List<AdDataTable> adDataList = adMetricsRepository.findAllByRequestDateToday();

        if (!adDataList.isEmpty()) {
            List<AdDataTable> adDataTables = adDataList.stream().limit(3).toList();

            return ResponseEntity.ok(adDataTables);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Post new page statistics.
     * <p>
     * This method saves a new ad data record to the database.
     * If the record is successfully saved, it returns the saved record.
     * If there is an error, a 400 Bad Request response is returned.
     *
     * @param newAdData the new AdDataTable to be saved.
     * @return ResponseEntity with the saved AdDataTable or 400 Bad Request.
     */
    @PostMapping("/api/v1/page-stats")
    public ResponseEntity<AdDataTable> postPageData(@RequestBody AdDataTable newAdData) {

        AdDataTable savedAdData;

        try{
             savedAdData = adMetricsRepository.save(newAdData);
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedAdData);
    }

    /**
     * Update existing page statistics.
     * <p>
     * This method updates an existing ad data record by ID.
     * If the record exists, it updates the record and returns the updated record.
     * If the record does not exist, a 404 Not Found response is returned.
     *
     * @param id the ID of the AdDataTable to be updated.
     * @param updatedAdData the updated AdDataTable data.
     * @return ResponseEntity with the updated AdDataTable or 404 Not Found.
     */
    @PutMapping("api/v1/page-stats/{id}")
    public ResponseEntity<AdDataTable> updatePageData(@PathVariable Integer id, @RequestBody AdDataTable updatedAdData) {
        Optional<AdDataTable> existingAdDataOptional = adMetricsRepository.findById(id);

        if (existingAdDataOptional.isPresent()) {
            AdDataTable existingAdData = existingAdDataOptional.get();

            existingAdData.setPageName(updatedAdData.getPageName());
            existingAdData.setViews(updatedAdData.getViews());
            existingAdData.setVisits(updatedAdData.getVisits());
            existingAdData.setExpenses(updatedAdData.getExpenses());
            AdDataTable savedAdData = adMetricsRepository.save(existingAdData);
            return ResponseEntity.ok(savedAdData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete page statistics by ID.
     * <p>
     * This method deletes an ad data record by ID.
     * If the record exists, it deletes the record and returns a 204 No Content response.
     * If the record does not exist, a 404 Not Found response is returned.
     *
     * @param id the ID of the AdDataTable to be deleted.
     * @return ResponseEntity with 204 No Content or 404 Not Found.
     */
    @DeleteMapping("api/v1/page-stats/{id}")
    public ResponseEntity<Void> deletePageData(@PathVariable Integer id) {
        if (adMetricsRepository.existsById(id)) {
            adMetricsRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
