package com.example.hexagonal.infrastructure.adapters;

import com.example.hexagonal.domain.models.Price;
import com.example.hexagonal.domain.ports.out.PriceRepositoryPort;
import com.example.hexagonal.infrastructure.repositories.JpaPriceRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class JpaPriceRepositoryAdapter implements PriceRepositoryPort {

    private final JpaPriceRepository jpaPriceRepository;

    public JpaPriceRepositoryAdapter(JpaPriceRepository jpaPriceRepository) {
        this.jpaPriceRepository = jpaPriceRepository;
    }

    @Override
    public List<Price> findByProductIdAndBrandId(Integer productId, Integer brandId) {
        return jpaPriceRepository.findByProductIdAndBrandId(productId, brandId).stream()
            .map(com.example.hexagonal.infrastructure.entities.Price::toDomainModel).collect(Collectors.toList());
    }
}
