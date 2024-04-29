package com.example.hexagonal.infrastructure.config;

import com.example.hexagonal.application.usecases.RetrievePriceUseCaseImpl;
import com.example.hexagonal.domain.ports.out.PriceRepositoryPort;
import com.example.hexagonal.infrastructure.adapters.JpaPriceRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public PriceRepositoryPort priceRepositoryPort(JpaPriceRepositoryAdapter jpaPriceRepositoryAdapter){
        return jpaPriceRepositoryAdapter;
    }

    @Bean
    public RetrievePriceUseCaseImpl retrievePriceUseCaseImpl(PriceRepositoryPort priceRepositoryPort){
        return new RetrievePriceUseCaseImpl(priceRepositoryPort);
    }

}
