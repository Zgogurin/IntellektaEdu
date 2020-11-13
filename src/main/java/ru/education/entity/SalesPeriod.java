package ru.education.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sales_period")
//@NoArgsConstructor
@Setter
@Getter
public class SalesPeriod {


    public static String TYPE_NAME = "Торговый период";

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_period_id_seq")
    @SequenceGenerator(name = "sales_period_id_seq", sequenceName = "sales_period_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "price", nullable = false)
    private long price;

    @Column(name = "date_from", nullable = false)
    private Date dateFrom;

    @Column(name = "date_to")
    private Date dateTo;

    @OneToOne
    @JoinColumn(name = "product", referencedColumnName = "id", nullable = false)
    private Product product;

    public SalesPeriod(Integer id, Long price, Date dateFrom, Date dateTo, Product product) {
        this.id = id;
        this.price = price;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.product = product;
    }

    public SalesPeriod() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
