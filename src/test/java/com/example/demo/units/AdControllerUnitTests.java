package com.example.demo.units;

import com.example.demo.api.AdController;
import com.example.demo.repositories.AdMetricsRepository;
import com.example.demo.tables.AdDataTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdControllerUnitTests {

    @InjectMocks
    private AdController adController;

    @Mock
    private AdMetricsRepository adMetricsRepository;

    /**
     * Test successful retrieval of page data.
     * <p>
     * This test verifies that the {@code getPageData()} method in the controller
     * correctly returns page statistics for today.
     */
    @Test
    void testGetPageData_Success() {
        AdDataTable adData1 = AdDataTable.builder().id(1).pageName("Page 1").views(100).visits(50).expenses(10).requestDate(LocalDateTime.now()).build();
        AdDataTable adData2 = AdDataTable.builder().id(2).pageName("Page 2").views(200).visits(70).expenses(20).requestDate(LocalDateTime.now()).build();
        List<AdDataTable> adDataList = Arrays.asList(adData1, adData2);

        when(adMetricsRepository.findAllByRequestDateToday()).thenReturn(adDataList);

        List<AdDataTable> returnedAdDataList = adController.getPageData().getBody();

        Assertions.assertNotNull(returnedAdDataList);
        Assertions.assertEquals(adDataList.size(), returnedAdDataList.size());
        Assertions.assertEquals(adDataList.get(0).getPageName(), returnedAdDataList.get(0).getPageName());
        Assertions.assertEquals(adDataList.get(1).getViews(), returnedAdDataList.get(1).getViews());
    }

    /**
     * Test successful posting of new page data.
     * <p>
     * This test verifies that the {@code postPageData()} method in the controller
     * correctly saves new page statistics.
     */
    @Test
    void testPostPageData_Success() {
        AdDataTable newAdData = AdDataTable.builder()
                .pageName("New Page")
                .views(150)
                .visits(70)
                .expenses(15)
                .requestDate(LocalDateTime.now())
                .build();

        when(adMetricsRepository.save(newAdData)).thenReturn(newAdData);

        ResponseEntity<AdDataTable> responseEntity = adController.postPageData(newAdData);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(newAdData.getPageName(), responseEntity.getBody().getPageName());
        Assertions.assertEquals(newAdData.getViews(), responseEntity.getBody().getViews());
    }

    /**
     * Test successful updating of existing page data.
     * <p>
     * This test verifies that the {@code updatePageData()} method in the controller
     * correctly updates existing page statistics.
     */
    @Test
    void testUpdatePageData_Success() {
        AdDataTable existingAdData = AdDataTable.builder()
                .id(1)
                .pageName("Existing Page")
                .views(200)
                .visits(80)
                .expenses(20)
                .requestDate(LocalDateTime.now())
                .build();

        when(adMetricsRepository.findById(existingAdData.getId())).thenReturn(Optional.of(existingAdData));
        when(adMetricsRepository.save(existingAdData)).thenReturn(existingAdData);

        AdDataTable updatedAdData = AdDataTable.builder()
                .id(existingAdData.getId())
                .pageName("Updated Page")
                .views(300)
                .visits(100)
                .expenses(30)
                .requestDate(existingAdData.getRequestDate())
                .build();

        ResponseEntity<AdDataTable> responseEntity = adController.updatePageData(existingAdData.getId(), updatedAdData);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(updatedAdData.getPageName(), responseEntity.getBody().getPageName());
        Assertions.assertEquals(updatedAdData.getViews(), responseEntity.getBody().getViews());
    }


    @Test
    void testDeletePageData_Success() {
        int existingId = 1;

        when(adMetricsRepository.existsById(existingId)).thenReturn(true);
        doNothing().when(adMetricsRepository).deleteById(existingId);

        ResponseEntity<Void> responseEntity = adController.deletePageData(existingId);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
}
