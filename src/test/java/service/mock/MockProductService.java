package service.mock;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.education.entity.Product;
import ru.education.entity.SalesPeriod;
import ru.education.exceptions.EntityAlreadyExistsException;
import ru.education.exceptions.EntityHasDetailsException;
import ru.education.exceptions.EntityIllegalArgumentException;
import ru.education.exceptions.EntityNotFoundException;
import ru.education.jpa.ProductRepository;
import ru.education.jpa.SalesPeriodJpaRepository;
import ru.education.service.ProductService;

import java.util.List;


@Service
public class MockProductService implements ProductService {

    private final ProductRepository productRepository;

    private final SalesPeriodJpaRepository salesPeriodJpaRepository;

    @Autowired
    public MockProductService(ProductRepository productRepository, SalesPeriodJpaRepository salesPeriodJpaRepository) {
        this.productRepository = productRepository;
        this.salesPeriodJpaRepository = salesPeriodJpaRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }


    @Override
    public Product findById(Object id) {
        Product product;
        Integer parseId;
        try {
            parseId = Integer.valueOf(id.toString());
        }
        catch (NumberFormatException ex) {
            throw new EntityIllegalArgumentException(String.format("Не удалось преобразовать идентификатор " +
                    "к нужному типу, текст ошибки %s",ex));
        }

        product = productRepository.findByIdEquals(parseId);

        if (product == null) {
            throw new EntityNotFoundException(Product.TYPE_NAME, parseId);
        }

        return product;
    }

    @Override
    public Product create(Product product) {

        Product existedProduct = productRepository.findByIdEquals(product.getId());
        if (existedProduct != null) {
            throw new EntityAlreadyExistsException(Product.TYPE_NAME, product.getId());
        }

        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        Product existedProduct = productRepository.findByIdEquals(product.getId());

        if (existedProduct == null) {
            throw new EntityNotFoundException(Product.TYPE_NAME,product.getId());
        }

        return productRepository.save(product);
    }

    @Override
    public void delete(Object id) {
        Product product = findById(id);
        List<SalesPeriod> salesPeriods = salesPeriodJpaRepository.findByProduct(product);
        if (salesPeriods.size() > 0) {
            throw new EntityHasDetailsException(SalesPeriod.TYPE_NAME, product.getId());
        }
        productRepository.deleteById(product.getId());
    }

}
