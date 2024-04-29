package com.example.hexagonal.application.usecases;

import com.example.hexagonal.application.exceptions.PriceNotFoundException;
import com.example.hexagonal.domain.models.Price;
import com.example.hexagonal.domain.ports.in.RetrievePriceUseCase;
import com.example.hexagonal.domain.ports.out.PriceRepositoryPort;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class RetrievePriceUseCaseImpl implements RetrievePriceUseCase {

    private final PriceRepositoryPort priceRepositoryPort;

    public RetrievePriceUseCaseImpl(PriceRepositoryPort priceRepositoryPort) {
        this.priceRepositoryPort = priceRepositoryPort;
    }

    @Override
    public List<Price> getPricesByProductIdAndBrandId(Integer productId, Integer brandId) {
        return priceRepositoryPort.findByProductIdAndBrandId(productId, brandId);
    }

    public Price getPrice(LocalDateTime applicationDate, Integer productId, Integer brandId) {
        List<Price> prices = getPricesByProductIdAndBrandId(productId, brandId);
        Optional<Price> optionalPrice = prices.stream()
            .filter(price -> applicationDate.isAfter(price.startDate()) && applicationDate.isBefore(price.endDate()))
            .max(Comparator.comparingInt(Price::priority));

        return optionalPrice.orElseThrow(() -> new PriceNotFoundException(
            String.format("Price not found for this product %d, this brand %d between this date %s", productId, brandId,
                applicationDate)));
    }

}
