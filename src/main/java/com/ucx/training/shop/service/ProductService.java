package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Brand;
import com.ucx.training.shop.entity.Platform;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
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


    public Product create(Product product) throws NotFoundException {
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

    public Page<Product> findAllActive(Pageable pageable) {
        return productRepository.findAllActive(pageable);
    }

    public Page<Product> findAllByBrand(Pageable pageable, BigDecimal min, BigDecimal max, List<Integer> brandId) {
        List<Brand> foundBrand  = new ArrayList();
        Page<Product> foundProducts = null;
        for (Integer id: brandId) {
            foundBrand.add(brandService.findById(id));
        }
        foundProducts = productRepository.findAllProductByUnitPriceBetweenAndBrandIsIn(pageable, min, max, foundBrand);
        return foundProducts;
    }
    public Page<Product> findAllByBrandAndNameContaining(Pageable pageable, BigDecimal min, BigDecimal max, List<Integer> brandId, String name) {
        List<Brand> foundBrand  = new ArrayList();
        Page<Product> foundProducts = null;
        for (Integer id: brandId) {
            foundBrand.add(brandService.findById(id));
        }
        foundProducts = productRepository.findAllProductByUnitPriceBetweenAndBrandIsInAndNameContainingIgnoreCase(pageable, min, max, foundBrand, name);
        return foundProducts;
    }

    public Page<Product> findAllByPlatform(Pageable pageable, BigDecimal min, BigDecimal max, Integer platformId) {
        Platform foundPlatform = platformService.findById(platformId);
        Page<Product> foundProducts = null;
            foundProducts = productRepository.findAllProductByUnitPriceBetweenAndPlatform(pageable, min, max, foundPlatform);
        return foundProducts;
    }
    public Page<Product> findAllByPlatformAndNameContaining(Pageable pageable, BigDecimal min, BigDecimal max, Integer platformId, String name) {
        Platform foundPlatform = platformService.findById(platformId);
        Page<Product> foundProducts = null;
        foundProducts = productRepository.findAllProductByUnitPriceBetweenAndPlatformAndNameContainingIgnoreCase(pageable, min, max, foundPlatform, name);
        return foundProducts;
    }

    public Page<Product> findAllByPlatformAndBrand(Pageable pageable, BigDecimal min, BigDecimal max, Integer platformId, List<Integer> brandId) {
        Platform foundPlatform = platformService.findById(platformId);
        List<Brand> foundBrand  = new ArrayList();
        Page<Product> foundProducts = null;
        for (Integer id: brandId) {
            foundBrand.add(brandService.findById(id));
        }
        foundProducts = productRepository.findAllProductByUnitPriceBetweenAndBrandIsInAndPlatform(pageable, min, max, foundBrand, foundPlatform);
        return foundProducts;
    }
    public Page<Product> findAllByPlatformAndBrandAndNameContaining(Pageable pageable, BigDecimal min, BigDecimal max, Integer platformId, List<Integer> brandId, String name) {
        Platform foundPlatform = platformService.findById(platformId);
        List<Brand> foundBrand  = new ArrayList();
        Page<Product> foundProducts = null;
        for (Integer id: brandId) {
            foundBrand.add(brandService.findById(id));
        }
        foundProducts = productRepository.findAllProductByUnitPriceBetweenAndBrandIsInAndPlatformAndNameContainingIgnoreCase(pageable, min, max, foundBrand, foundPlatform, name);
        return foundProducts;
    }

    public Page<Product> findAllProductsPrice(Pageable pageable, BigDecimal lowest, BigDecimal highest) {
        Page<Product> foundProducts = null;
            foundProducts = productRepository.findAllProductByUnitPriceBetween(pageable, lowest, highest);
        return foundProducts;
    }

    public Page<Product> findAllProductsPriceAndNameContaining(Pageable pageable, BigDecimal lowest, BigDecimal highest, String name) {
        Page<Product> foundProducts = null;
        foundProducts = productRepository.findAllProductByUnitPriceBetweenAndNameContainingIgnoreCase(pageable, lowest, highest, name);
        return foundProducts;
    }

    public Number getLowestPrice() {
        return productRepository.getLowestPrice();
    }

    public Number getHighestPrice() {
        return productRepository.getHighestPrice();
    }

    public Page<Product> findAllByNameContaining(Pageable pageable, String name) {
        return this.productRepository.findAllByNameContainingIgnoreCase(pageable, name);
    }
}
