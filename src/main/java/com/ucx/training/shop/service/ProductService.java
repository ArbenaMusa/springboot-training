package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Brand;
import com.ucx.training.shop.entity.Platform;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@Log4j2
public class ProductService extends BaseService<Product, Integer> {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;

    public Product findByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Null argument provided!");
        }

        return productRepository.findByName(name);
    }

    public List<Product> findAllByUnitPrice(BigDecimal unitPrice) {
        if (unitPrice == null) {
            throw new IllegalArgumentException("Null argument provided!");
        }

        return productRepository.findAllByUnitPrice(unitPrice);
    }


    public Product createProductWithPlatformAndBrand(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Given product is null");
        }
        Platform platform = product.getPlatform();
        Brand brand = product.getBrand();
        if (platform.getId() == null) {
            categoryService.save(platform);
        } else {
            Platform foundPlatform = categoryService.findById(platform.getId());
            Assert.isTrue(foundPlatform != null, "Entity not found!");
            product.setPlatform(foundPlatform);
        }

        if (brand.getId() == null) {
            brandService.save(brand);
        } else {
            Brand foundBrand = brandService.findById(brand.getId());
            Assert.isTrue(foundBrand != null, "Entity not found!");
            product.setBrand(foundBrand);
        }
        return super.save(product);
    }
}
