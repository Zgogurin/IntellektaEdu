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
import ru.education.exceptions.EntityAlreadyExistsException;
import ru.education.exceptions.EntityHasDetailsException;
import ru.education.exceptions.EntityIllegalArgumentException;
import ru.education.exceptions.EntityNotFoundException;
import ru.education.service.impl.DefaultProductService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class DefaultProductServiceTest {

    @Autowired
    private DefaultProductService defaultProductService;

    @Test
    public void findAllTest() {
        List<Product> products = defaultProductService.findAll();
        Assert.assertEquals(products.size(), 2);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNullTest() { defaultProductService.findById(null); }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdIllegalIdTest() { defaultProductService.findById("test"); }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdNotFoundProductTest() { defaultProductService.findById(0); }

    @Test
    public void findByIdTest() {
        Product product = defaultProductService.findById(2);
        Assert.assertEquals(product.getName(),"bike_test");
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullProductException() {
        defaultProductService.create(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createProductIdNullTest() {
        Product product = new Product(null,"test");
        defaultProductService.create(product);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void createProductAlreadyExistsTest() {
        Product product = new Product(1,"test");
        defaultProductService.create(product);
    }

    @Test
    public void createProductTest() {
        Product product = new Product(3,"test");
        defaultProductService.create(product);
        List<Product> products = defaultProductService.findAll();
        Assert.assertEquals(products.size(), 3);
        defaultProductService.delete(3);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void deleteIdNullTest() { defaultProductService.delete(null); }

    @Test(expected = EntityIllegalArgumentException.class)
    public void deleteIdIllegalIdTest() { defaultProductService.delete("test"); }

    @Test(expected = EntityNotFoundException.class)
    public void deleteIdNotFoundTest() { defaultProductService.delete(0); }

    @Test(expected = EntityHasDetailsException.class)
    public void deleteIdHasDetailsTest() { defaultProductService.delete(2); }

    @Test
    public void deleteTest() {
        Product product = new Product(3,"test");
        defaultProductService.create(product);
        defaultProductService.delete(3);
        List<Product> products = defaultProductService.findAll();
        Assert.assertEquals(products.size(), 2);
    }
}
