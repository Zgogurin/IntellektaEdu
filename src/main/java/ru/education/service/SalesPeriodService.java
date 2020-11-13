package ru.education.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.education.entity.Product;
import ru.education.entity.SalesPeriod;
import ru.education.exceptions.EntityAlreadyExistsException;
import ru.education.exceptions.EntityConflictException;
import ru.education.exceptions.EntityIllegalArgumentException;
import ru.education.exceptions.EntityNotFoundException;
import ru.education.jpa.ProductRepository;
import ru.education.jpa.SalesPeriodJpaRepository;

import java.util.List;

@Service
public class SalesPeriodService {

    final ProductRepository productRepository;

    final SalesPeriodJpaRepository salesPeriodJpaRepository;

    @Autowired
    public SalesPeriodService(ProductRepository productRepository, SalesPeriodJpaRepository salesPeriodJpaRepository) {
        this.productRepository = productRepository;
        this.salesPeriodJpaRepository = salesPeriodJpaRepository;
    }

    public List<SalesPeriod> findAll() {
        return salesPeriodJpaRepository.findAll();
    }

    public SalesPeriod findById(Object id) {
        SalesPeriod salesPeriod;
        if (id == null) {
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }
        Integer parseId;
        try {
            parseId = Integer.valueOf(id.toString());
        }
        catch (NumberFormatException ex) {
            throw new EntityIllegalArgumentException(String.format("Не удалось преобразовать идентификатор " +
                    "к  нужному типу, текст ошибки: %s",ex));
        }

        salesPeriod = salesPeriodJpaRepository.findByIdEquals(parseId);

        if (salesPeriod == null) {
            throw new EntityNotFoundException(SalesPeriod.TYPE_NAME, parseId);
        }

        return salesPeriod;
    }

    public SalesPeriod create(SalesPeriod salesPeriod) {
        if (salesPeriod == null) {
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть null");
        }

        if (salesPeriod.getId() == null) {
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }

        if (salesPeriod.getProduct() == null) {
            throw new EntityIllegalArgumentException("Продукт не может быть null");
        }

        if (salesPeriod.getProduct().getId() == null) {
            throw new EntityIllegalArgumentException("Идентификатор продукта не может быть null");
        }

        Product product = productRepository.findByIdEquals(salesPeriod.getProduct().getId());
        if (product == null) {
            throw new EntityIllegalArgumentException(String.format("Продукта с идентификатором %s не существует в базе", salesPeriod.getProduct().getId()));
        }

        if (salesPeriod.getDateFrom() == null) {
            throw new EntityIllegalArgumentException("Дата начала периода не может быть null");
        }

        SalesPeriod existedSalesPeriod = salesPeriodJpaRepository.findByIdEquals(salesPeriod.getId());
        if (existedSalesPeriod != null) {
            throw new EntityAlreadyExistsException(SalesPeriod.TYPE_NAME, existedSalesPeriod.getId());
        }

        List<SalesPeriod> lastSalesPeriods = salesPeriodJpaRepository.findByDateToIsNullAndProductId(salesPeriod.getProduct().getId());
        if (lastSalesPeriods.size() > 0) {
            throw new EntityConflictException(String.format("В системе имеются открытые торговые периоды для продукта с" +
                    " идентификатором %s",salesPeriod.getId()));
        }

        return salesPeriodJpaRepository.save(salesPeriod);
    }

    public void delete(Object id){
        SalesPeriod salesPeriod = findById(id);
        salesPeriodJpaRepository.delete(salesPeriod);
    }
}
