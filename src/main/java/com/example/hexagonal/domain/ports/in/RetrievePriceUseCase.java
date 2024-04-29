package com.example.hexagonal.domain.ports.in;

import com.example.hexagonal.domain.models.Price;
import java.time.LocalDateTime;
import java.util.List;

public interface RetrievePriceUseCase {
    List<Price> getPricesByProductIdAndBrandId(Integer productId, Integer brandId);
    public Price getPrice(LocalDateTime applicationDate,Integer productId, Integer brandId);

}
