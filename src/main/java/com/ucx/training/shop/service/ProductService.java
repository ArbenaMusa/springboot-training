package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Brand;
import com.ucx.training.shop.entity.FileUpload;
import com.ucx.training.shop.entity.Platform;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.exception.NotFoundException;
import com.ucx.training.shop.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@Log4j2
public class ProductService extends BaseService<Product, Integer> {

    private ProductRepository productRepository;
    private CategoryService categoryService;
    private BrandService brandService;
    private FileUploadService fileUploadService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService, BrandService brandService, FileUploadService fileUploadService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.fileUploadService = fileUploadService;
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


    public Product createProductWithPlatformAndBrand(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Given product is null");
        }
        Platform platform = product.getPlatform();

        if (platform != null && platform.getId() == null) {
            categoryService.save(platform);
        } else {
            Platform foundPlatform = categoryService.findById(platform.getId());
            Assert.isTrue(foundPlatform != null, "Entity not found!");
            product.setPlatform(foundPlatform);
        }
        return super.save(product);
    }

    @org.springframework.transaction.annotation.Transactional
    public Product createProductWithImage(Product product, MultipartFile file) throws IOException, NotFoundException {
        Product createdProduct = createProductWithPlatformAndBrand(product);
        FileUpload uploadedFile = fileUploadService.uploadFile(file);
        FileUpload fileWithProduct = fileUploadService.save(uploadedFile, createdProduct.getId());
        return null;
    }
}
