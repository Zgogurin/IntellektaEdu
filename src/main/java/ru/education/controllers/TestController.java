package ru.education.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.education.entity.SalesPeriodJdbcDemo;
import ru.education.entity.SalesPeriod;
import ru.education.jdbc.SalesPeriodJdbcRepository;
import ru.education.entity.Product;
import ru.education.jpa.ProductRepository;
import ru.education.jpa.SalesPeriodJpaRepository;
import ru.education.model.Formatter;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class TestController {

    private final ProductRepository productRepository;

    private final SalesPeriodJdbcRepository salesPeriodJdbcRepository;

    private final Formatter formatter;

    private final SalesPeriodJpaRepository salesPeriodJpaRepository;

    public TestController(@Qualifier("fooFormatter") Formatter formatter, ProductRepository productRepository, SalesPeriodJdbcRepository salesPeriodJdbcRepository, SalesPeriodJpaRepository salesPeriodJpaRepository) {
        this.formatter = formatter;
        this.productRepository = productRepository;
        this.salesPeriodJdbcRepository = salesPeriodJdbcRepository;
        this.salesPeriodJpaRepository = salesPeriodJpaRepository;
    }

    @GetMapping("/hello")
    public String getHello() {
        return "Hello, world!";
    }

    @GetMapping("/format")
    public String getFormat() {
        return formatter.format();
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/sales/count")
    public Integer getSalesCount() {
        return salesPeriodJdbcRepository.count();
    }

    @GetMapping("sales")
    public List<SalesPeriodJdbcDemo> getSalesPeriods() {
        return salesPeriodJdbcRepository.getSalesPeriods();
    }

    @GetMapping("/sales/byhigherprice")
    public List<SalesPeriodJdbcDemo> getPeriodsByHigherPrice() {
        return salesPeriodJdbcRepository.getSalesPeriodsPriceIsHigher(90);
    }

    @GetMapping("/products/sales/active")
    public List<Product> getProductsWithActivePeriod() {
        return salesPeriodJdbcRepository.getProductsWithActivePeriod();
    }

    @GetMapping("/sales/jpa")
    public List<SalesPeriod> getSalesPeriodJpa() {
        return salesPeriodJpaRepository.findAll();
    }

    @PostMapping("/sales/jpa")
    public SalesPeriod addSalesPeriodJpa (@RequestBody SalesPeriod salesPeriod) {
        return salesPeriodJpaRepository.save(salesPeriod);
    }

    @GetMapping("/sales/jpa/max/price")
    public Integer getMaxPriceByProductId() {
        return salesPeriodJpaRepository.getMaxPriceByProductId(1);
    }

    @GetMapping("/sales/jpa/exists/price")
    public boolean existsByPrice() {
        return salesPeriodJpaRepository.existsByPrice(60);
    }

    @GetMapping("/sales/jpa/active")
    public List<SalesPeriod> findByDateToNull() {
        return salesPeriodJpaRepository.findByDateToIsNull();
    }

    @GetMapping("/sales/jpa/byproductname")
    public List<SalesPeriod> findByProductName() {
        return salesPeriodJpaRepository.findByProductName("bike");
    }

}
