package com.example.hexagonal.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.hexagonal.domain.models.Price;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class PriceTest {

    @Test
    void priceCreationTest() {
        Integer brandId = 1;
        LocalDateTime startDate = LocalDateTime.of(2022, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2022, 12, 31, 23, 59);
        Integer priceList = 1;
        Integer productId = 35455;
        Integer priority = 1;
        Double priceValue = 35.50;
        String currency = "EUR";

        Price price = new Price(brandId, startDate, endDate, priceList, productId, priority, priceValue, currency);

        assertEquals(brandId, price.brandId());
        assertEquals(startDate, price.startDate());
        assertEquals(endDate, price.endDate());
        assertEquals(priceList, price.priceList());
        assertEquals(productId, price.productId());
        assertEquals(priority, price.priority());
        assertEquals(priceValue, price.price());
        assertEquals(currency, price.currency());
    }
}
