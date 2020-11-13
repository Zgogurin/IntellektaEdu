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
import ru.education.service.ProductService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void findAllTest() {
        List<Product> products = productService.findAll();
        Assert.assertEquals(products.size(), 2);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNullTest() { productService.findById(null); }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdIllegalIdTest() { productService.findById("test"); }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdNotFoundProductTest() { productService.findById(0); }

    @Test
    public void findByIdTest() {
        Product product = productService.findById(2);
        Assert.assertEquals(product.getName(),"bike_test");
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullProductException() {
        productService.create(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createProductIdNullTest() {
        Product product = new Product(null,"test");
        productService.create(product);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void createProductAlreadyExistsTest() {
        Product product = new Product(1,"test");
        productService.create(product);
    }

    @Test
    public void createProductTest() {
        Product product = new Product(3,"test");
        productService.create(product);
        List<Product> products = productService.findAll();
        Assert.assertEquals(products.size(), 3);
        productService.delete(3);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void deleteIdNullTest() { productService.delete(null); }

    @Test(expected = EntityIllegalArgumentException.class)
    public void deleteIdIllegalIdTest() { productService.delete("test"); }

    @Test(expected = EntityNotFoundException.class)
    public void deleteIdNotFoundTest() { productService.delete(0); }

    @Test(expected = EntityHasDetailsException.class)
    public void deleteIdHasDetailsTest() { productService.delete(2); }

    @Test
    public void deleteTest() {
        Product product = new Product(3,"test");
        productService.create(product);
        productService.delete(3);
        List<Product> products = productService.findAll();
        Assert.assertEquals(products.size(), 2);
    }
}
