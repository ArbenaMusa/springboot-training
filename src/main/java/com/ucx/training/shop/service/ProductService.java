package com.ucx.training.shop.service;

import com.ucx.training.shop.entity.Brand;
import com.ucx.training.shop.entity.Category;
import com.ucx.training.shop.entity.Product;
import com.ucx.training.shop.repository.CategoryRepository;
import com.ucx.training.shop.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public Product createProductWithCategoryAndBrand(Product product){
        Category category = product.getCategory();
        Brand brand = product.getBrand();
        if (product == null || category == null || brand == null) {
            throw new IllegalArgumentException("Invalid argument found, please check for null!");
        }
        Product foundedProduct = findByName(product.getName());
        if (foundedProduct != null) {
            throw new RuntimeException("This product already exist");
        }
        if (category.getId() == null && brand.getId() == null) {
            categoryService.save(category);
            brandService.save(brand);
            product.setCategory(category);
            product.setBrand(brand);
        }else {
            Category foundCategory = categoryService.findById(category.getId());
            Brand foundBrand = brandService.findById(brand.getId());
            product.setCategory(foundCategory);
            product.setBrand(foundBrand);
        }
        return super.save(product);
    }

}
