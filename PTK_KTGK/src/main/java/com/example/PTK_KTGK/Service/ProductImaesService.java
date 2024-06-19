package com.example.PTK_KTGK.Service;


import com.example.PTK_KTGK.Model.ProductImages;
import com.example.PTK_KTGK.repository.ProductImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImaesService {
    private final ProductImagesRepository productImagesRepository;
    // Retrieve all products from the database
    public List<ProductImages> getAllProductsImages() {
        return productImagesRepository.findAll();
    }
    // Retrieve a product by its id
    public Optional<ProductImages> getProductById(Long id) {
        return productImagesRepository.findById(id);
    }
    // Add a new product to the database
    public ProductImages addProductImage(ProductImages product) {
        return productImagesRepository.save(product);
    }

    public void deleteProductById(Long id) {
        if (!productImagesRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        productImagesRepository.deleteById(id);
    }

}
