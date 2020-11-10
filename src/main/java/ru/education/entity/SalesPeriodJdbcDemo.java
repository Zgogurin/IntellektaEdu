package ru.education.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SalesPeriodJdbcDemo {

    private Integer id, product;
    private long price;
    private Date dateFrom;
    private Date dateTo;

    public SalesPeriodJdbcDemo(Integer id, long price, Date dateFrom, Date dateTo, Integer product) {
        this.id = id;
        this.product = product;
        this.price = price;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
}
