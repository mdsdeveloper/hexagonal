package com.example.hexagonal.infrastructure.controllers;

import com.example.hexagonal.domain.models.Price;
import com.example.hexagonal.domain.ports.in.RetrievePriceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class PriceController {
    private final RetrievePriceUseCase retrievePriceUseCase;

    public PriceController(RetrievePriceUseCase retrievePriceUseCase) {

        this.retrievePriceUseCase = retrievePriceUseCase;
    }

    @Operation(summary = "Get price details", description = "Get price details based on the given parameters")
    @GetMapping("/getPrice")
    public Price getPrice(
        @Parameter(description = "Application date", required = true, example = "2022-05-01T12:00:00") @RequestParam("applicationDate")
        @NotNull(message = "Application date cannot be null") LocalDateTime applicationDate,

        @Parameter(description = "Product ID", required = true) @RequestParam("productId")
        @NotNull(message = "Product ID cannot be null") int productId,

        @Parameter(description = "Brand ID", required = true) @RequestParam("brandId")
        @NotNull(message = "Brand ID cannot be null") int brandId
    ) {
        log.info("Price request received for the product " + productId + " and brand " + brandId);
        return retrievePriceUseCase.getPrice(applicationDate, productId, brandId);
    }
}

