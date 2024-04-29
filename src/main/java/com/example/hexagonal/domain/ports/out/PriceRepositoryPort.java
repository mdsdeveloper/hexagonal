package com.example.hexagonal.domain.ports.out;

import com.example.hexagonal.domain.models.Price;
import java.util.List;

public interface PriceRepositoryPort {

    List<Price> findByProductIdAndBrandId(Integer productId, Integer brandId);

}
