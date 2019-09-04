package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Brand;
import com.ucx.training.shop.entity.Platform;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@Log4j2
public class ProductService extends BaseService<Product, Integer> {

    private ProductRepository productRepository;
    private PlatformService platformService;
    private BrandService brandService;

    public ProductService(ProductRepository productRepository, PlatformService platformService, BrandService brandService) {
        this.productRepository = productRepository;
        this.platformService = platformService;
        this.brandService = brandService;
    }

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


    public Product createProductWithPlatformAndBrand(Product product) throws NotFoundException {
        if (product == null) {
            throw new IllegalArgumentException("Given product is null");
        }
        Platform platform = product.getPlatform();
        Brand brand = product.getBrand();
        if (product.getId() != null) {
            Product foundProduct = findById(product.getId());
            if (foundProduct == null) {
                throw new RuntimeException("The given id for the product is invalid");
            } else {
                platformValidation(product, platform);
                brandValidation(product, brand);
                return super.update(product, foundProduct.getId());
            }
        }
        platformValidation(product, platform);

        brandValidation(product, brand);
        return super.save(product);
    }

    private void brandValidation(Product product, Brand brand) {
        if (brand != null) {
            if (brand.getId() != null) {
                Brand foundBrand = brandService.findById(brand.getId());
                if (foundBrand == null) {
                    throw new RuntimeException("There isn't a Brand with the given id");
                } else {
                    product.setBrand(foundBrand);
                }
            } else {
                brandService.save(brand);
            }
        }
    }

    private void platformValidation(Product product, Platform platform) {
        if (platform != null) {
            if (platform.getId() == null) {
                throw new RuntimeException("You cannot add a new Platform, you must assign an already existing one");
            } else {
                Platform foundPlatform = platformService.findById(platform.getId());
                if (foundPlatform == null) {
                    throw new RuntimeException("There isn't a Platform with the given id");
                } else {
                    product.setPlatform(foundPlatform);
                }
            }
        }
    }

    public List<Product> findAllActive() {
        return productRepository.findAllActive();
    }

    public List<Product> searchProductByName(String name){
        return this.productRepository.searchProductByName(name);
    }
}
