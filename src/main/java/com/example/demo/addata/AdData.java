package com.example.demo.addata;

import lombok.*;
import org.springframework.stereotype.Component;

/**
 * AdData is a data class representing advertising metrics for a specific page.
 * It encapsulates information such as page name, views, visits, and expenses,
 * providing a structured way to store and manipulate advertising performance data.
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
@Getter
@Setter
public class AdData {

    private String pageName;
    private long views;
    private long visits;
    private long expenses;

}
