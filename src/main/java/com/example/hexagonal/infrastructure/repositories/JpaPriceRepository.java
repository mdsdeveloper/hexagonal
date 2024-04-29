package com.example.hexagonal.infrastructure.repositories;

import com.example.hexagonal.infrastructure.entities.Price;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByProductIdAndBrandId(Integer productId, Integer brandId);
}
