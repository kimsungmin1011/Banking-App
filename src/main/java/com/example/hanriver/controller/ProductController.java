package com.example.hanriver.controller;

import com.example.hanriver.model.Product;
import com.example.hanriver.dto.ProductCreateDTO;
import com.example.hanriver.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductCreateDTO createDTO) {
        try {
            Product product = productService.addProduct(createDTO);
            // 상품 생성 성공 시, 201 Created 상태 코드와 함께 생성된 상품 정보 반환
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        } catch (Exception e) {
            // 예외 처리 시, 403 Forbidden 상태 코드 반환
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }



    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productService.findProductById(id);
    }

    // Other endpoints
}
