package com.example.hexagonal.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.hexagonal.domain.models.Price;
import com.example.hexagonal.infrastructure.adapters.JpaPriceRepositoryAdapter;
import com.example.hexagonal.infrastructure.repositories.JpaPriceRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class JpaPriceRepositoryAdapterTest {

    private JpaPriceRepository jpaPriceRepository;
    private JpaPriceRepositoryAdapter priceRepositoryAdapter;

    @BeforeEach
    void setUp() {
        jpaPriceRepository = Mockito.mock(JpaPriceRepository.class);
        priceRepositoryAdapter = new JpaPriceRepositoryAdapter(jpaPriceRepository);
    }

    @Test
    void findByProductIdAndBrandIdNoPricesFoundTest() {
        Integer productId = 1;
        Integer brandId = 1;
        when(jpaPriceRepository.findByProductIdAndBrandId(productId, brandId)).thenReturn(Arrays.asList());

        List<Price> prices = priceRepositoryAdapter.findByProductIdAndBrandId(productId, brandId);

        assertEquals(0, prices.size());
    }

    @Test
    void findByProductIdAndBrandIdFoundPricesTest() {
        Integer productId = 1;
        Integer brandId = 1;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusHours(1);

        List<com.example.hexagonal.infrastructure.entities.Price> entityPrices =
            Arrays.asList(
                new com.example.hexagonal.infrastructure.entities.Price(1L, 1, startDate, endDate, 1, productId, 1
                    , 30.50, "EUR"),
                new com.example.hexagonal.infrastructure.entities.Price(2L, 2, startDate, endDate, 1, productId, 2
                    , 40.75, "EUR"));

        when(jpaPriceRepository.findByProductIdAndBrandId(productId, brandId)).thenReturn(entityPrices);

        List<Price> domainPrices = priceRepositoryAdapter.findByProductIdAndBrandId(productId, brandId);

        assertEquals(2, domainPrices.size());
    }
}



