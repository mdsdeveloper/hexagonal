package com.example.hexagonal.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.hexagonal.application.exceptions.PriceNotFoundException;
import com.example.hexagonal.application.usecases.RetrievePriceUseCaseImpl;
import com.example.hexagonal.domain.models.Price;
import com.example.hexagonal.domain.ports.in.RetrievePriceUseCase;
import com.example.hexagonal.domain.ports.out.PriceRepositoryPort;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RetrievePriceUseCaseImplTest {

    private RetrievePriceUseCase retrievePriceUseCase;
    private PriceRepositoryPort priceRepositoryPort;

    @BeforeEach
    void setUp() {
        priceRepositoryPort = mock(PriceRepositoryPort.class);
        retrievePriceUseCase = new RetrievePriceUseCaseImpl(priceRepositoryPort);
    }

    @Test
    void getPriceBeforeStartDateTest() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 13, 12, 0);
        Integer productId = 35455;
        Integer brandId = 1;

        Throwable exception = assertThrows(PriceNotFoundException.class,
            () -> retrievePriceUseCase.getPrice(applicationDate, productId, brandId));

        assertEquals("Price not found for this product 35455, this brand 1 between this date 2020-06-13T12:00",
            exception.getMessage());
    }

    @Test
    void getPriceWithinPricePeriodTest() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 10, 15, 30);
        Integer productId = 35455;
        Integer brandId = 1;
        Price expectedPrice = new Price(1, LocalDateTime.now(), LocalDateTime.now(), 3, productId, 1, 30.50, "EUR");
        List<Price> prices = Arrays.asList(
            new Price(1, LocalDateTime.of(2020, 6, 15, 0, 0), LocalDateTime.of(2020, 6, 15, 11, 0), 3, productId, 1, 30.50,
                "EUR"),
            new Price(1, LocalDateTime.now(), LocalDateTime.now(), 4, productId, 1, 38.95, "EUR"));

        when(priceRepositoryPort.findByProductIdAndBrandId(productId, brandId)).thenReturn(prices);

        Price actualPrice = retrievePriceUseCase.getPrice(applicationDate, productId, brandId);

        assertEquals(expectedPrice.price(), actualPrice.price());
    }

    @Test
    void getPriceAfterEndDateTest() {
        LocalDateTime applicationDate = LocalDateTime.of(2021, 1, 1, 0, 0);
        Integer productId = 35455;
        Integer brandId = 1;

        Throwable exception = assertThrows(PriceNotFoundException.class,
            () -> retrievePriceUseCase.getPrice(applicationDate, productId, brandId));

        assertEquals("Price not found for this product 35455, this brand 1 between this date 2021-01-01T00:00",
            exception.getMessage());
    }

    @Test
    void getPriceEmptyPriceListTest() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 10, 15, 30);
        Integer productId = 99999;
        Integer brandId = 1;

        when(priceRepositoryPort.findByProductIdAndBrandId(productId, brandId)).thenReturn(Collections.emptyList());

        Throwable exception = assertThrows(PriceNotFoundException.class,
            () -> retrievePriceUseCase.getPrice(applicationDate, productId, brandId));

        assertEquals("Price not found for this product 99999, this brand 1 between this date 2020-06-15T10:15:30",
            exception.getMessage());
    }

    @Test
    void getPriceSinglePriceTest() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 17, 0);
        Integer productId = 35455;
        Integer brandId = 1;

        Price expectedPrice = new Price(1, LocalDateTime.now(), LocalDateTime.now(), 2, productId, 1, 25.45, "EUR");

        List<Price> prices = List.of(
            new Price(1, LocalDateTime.of(2020, 6, 14, 15, 0), LocalDateTime.of(2020, 6, 14, 18, 30), 2, productId, 1, 25.45,
                "EUR")
        );

        when(priceRepositoryPort.findByProductIdAndBrandId(productId, brandId)).thenReturn(prices);

        Price actualPrice = retrievePriceUseCase.getPrice(applicationDate, productId, brandId);

        assertEquals(expectedPrice.price(), actualPrice.price());
    }

    @Test
    void getPriceNoMatchingByBrandPriceTest() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 14, 0);
        Integer productId = 35455;
        Integer brandId = 1;
        List<Price> prices = List.of(
            new Price(2, LocalDateTime.of(2020, 6, 14, 15, 0), LocalDateTime.of(2020, 6, 14, 18, 30), 2, productId, 1, 25.45,
                "EUR"));

        when(priceRepositoryPort.findByProductIdAndBrandId(productId, brandId)).thenReturn(prices);

        Throwable exception = assertThrows(PriceNotFoundException.class,
            () -> retrievePriceUseCase.getPrice(applicationDate, productId, brandId));

        assertEquals("Price not found for this product 35455, this brand 1 between this date 2020-06-14T14:00",
            exception.getMessage());
    }

    @Test
    void getPriceMatchingByPriorityPriceTest() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 9, 0); // Dentro del período de varios precios
        Integer productId = 35455;
        Integer brandId = 1;
        Price expectedPrice = new Price(1, LocalDateTime.now(), LocalDateTime.now(), 3, productId, 1, 30.50, "EUR");

        List<Price> prices = List.of(
            new Price(1, LocalDateTime.of(2020, 6, 14, 15, 0), LocalDateTime.of(2020, 6, 15, 18, 30), 2, productId, 1, 25.45, "EUR"),
            new Price(1, LocalDateTime.of(2020, 6, 15, 0, 0), LocalDateTime.of(2020, 6, 15, 11, 0), 3, productId, 2, 30.50, "EUR"),
            new Price(1, LocalDateTime.of(2020, 6, 15, 16, 0), LocalDateTime.of(2020, 12, 31, 23, 59, 59), 4, productId, 1, 38.95, "EUR")
        );
        when(priceRepositoryPort.findByProductIdAndBrandId(productId, brandId)).thenReturn(prices);


        Price actualPrice = retrievePriceUseCase.getPrice(applicationDate, productId, brandId);


        assertEquals(expectedPrice.price(), actualPrice.price());
    }

}
