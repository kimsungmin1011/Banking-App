package com.example.hanriver.service;

import com.example.hanriver.model.Product;
import com.example.hanriver.dto.ProductCreateDTO;
import com.example.hanriver.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(ProductCreateDTO createDTO) throws Exception {
            Product product = new Product();
            product.setName(createDTO.getName());
            product.setDescription(createDTO.getDescription());
            product.setPrice(createDTO.getPrice());

            // DTO에서 제품 정보 설정
            return productRepository.save(product);

    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }
}
