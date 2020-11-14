package service;

import config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.education.entity.Product;
import ru.education.entity.SalesPeriod;
import ru.education.exceptions.EntityAlreadyExistsException;
import ru.education.exceptions.EntityConflictException;
import ru.education.exceptions.EntityIllegalArgumentException;
import ru.education.exceptions.EntityNotFoundException;
import ru.education.service.impl.DefaultProductService;
import ru.education.service.SalesPeriodService;
import java.util.Date;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class SalesPeriodServiceTest {

    @Autowired
    private SalesPeriodService salesPeriodService;

    @Autowired
    private DefaultProductService defaultProductService;

    @Test
    public void findAllTest() {
        List<SalesPeriod> salesPeriods = salesPeriodService.findAll();
        Assert.assertEquals(salesPeriods.size(), 6);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNullTest() { salesPeriodService.findById(null); }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdIllegalIdTest() { salesPeriodService.findById("test"); }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdNotFoundTest() { salesPeriodService.findById(0); }

    @Test
    public void findByIdTest() {
        SalesPeriod salesPeriod = salesPeriodService.findById(2);
        Assert.assertEquals(salesPeriod.getPrice(),150);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullSalesPeriodException() {
        salesPeriodService.create(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createSalesPeriodIdNullTest() {
        Product product = defaultProductService.findById(1);
        Date date = new Date();
        Date date1 = new Date();
        SalesPeriod salesPeriod = new SalesPeriod(null, 100L,  date, date1, product);
        salesPeriodService.create(salesPeriod);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createSalesPeriodProductNullTest() {
        Date date = new Date();
        Date date1 = new Date();
        SalesPeriod salesPeriod = new SalesPeriod(10, 100L, date, date1, null);
        salesPeriodService.create(salesPeriod);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createSalesPeriodProductIdNullTest() {
        Product product = new Product(null,"test");
        Date date = new Date();
        Date date1 = new Date();
        SalesPeriod salesPeriod = new SalesPeriod(10, 100L, date, date1, product);
        salesPeriodService.create(salesPeriod);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createSalesPeriodProductIdNotFoundTest() {
        Product product = new Product(50,"test");
        Date date = new Date();
        Date date1 = new Date();
        SalesPeriod salesPeriod = new SalesPeriod(10, 100L, date, date1, product);
        salesPeriodService.create(salesPeriod);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createSalesPeriodDateFromNullTest() {
        Product product = defaultProductService.findById(1);
        Date date1 = new Date();
        SalesPeriod salesPeriod = new SalesPeriod(10, 100L, null, date1, product);
        salesPeriodService.create(salesPeriod);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void createSalesPeriodAlreadyExistsTest() {
        Product product = defaultProductService.findById(1);
        Date date = new Date();
        Date date1 = new Date();
        SalesPeriod salesPeriod = new SalesPeriod(1, 100L, date, date1, product);
        salesPeriodService.create(salesPeriod);
    }

    @Test(expected = EntityConflictException.class)
    public void createSalesPeriodAlreadyOpenPeriodTest() {
        Product product = defaultProductService.findById(2);
        Date date = new Date();
        Date date1 = new Date();
        SalesPeriod salesPeriod = new SalesPeriod(8, 100L, date, date1, product);
        salesPeriodService.create(salesPeriod);
    }

    @Test
    public void CreateSalesPeriodTest() {
        Product product = defaultProductService.findById(1);
        Date date = new Date();
        Date date1 = new Date();
        SalesPeriod salesPeriod = new SalesPeriod(8, 100L, date, date1, product);
        salesPeriodService.create(salesPeriod);
        List<SalesPeriod> salesPeriods = salesPeriodService.findAll();
        Assert.assertEquals(salesPeriods.size(), 7);
        salesPeriodService.delete(7);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void deleteByIdNullTest() { salesPeriodService.delete(null); }

    @Test(expected = EntityIllegalArgumentException.class)
    public void deleteByIdIllegalIdTest() { salesPeriodService.delete("test"); }

    @Test(expected = EntityNotFoundException.class)
    public void deleteByIdNotFoundTest() { salesPeriodService.delete(8); }

    @Test
    public void deleteByIdTest() {
        Product product = defaultProductService.findById(1);
        Date date = new Date();
        Date date1 = new Date();
        SalesPeriod salesPeriod = new SalesPeriod(8, 100L, date, date1, product);
        salesPeriodService.create(salesPeriod);
        salesPeriodService.delete(1);
        List<SalesPeriod> salesPeriods = salesPeriodService.findAll();
        Assert.assertEquals(salesPeriods.size(), 6);
    }

}
