package com.example.hanriver.service;

import com.example.hanriver.model.Product;
import com.example.hanriver.model.Purchase;
import com.example.hanriver.dto.ProductCreateDTO;
import com.example.hanriver.repository.ProductRepository;
import com.example.hanriver.repository.PurchaseRepository;
import com.example.hanriver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private UserService userService; // UserService 주입
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private UserRepository userRepository; // Assuming UserRepository exists

    public Product addProduct(ProductCreateDTO createDTO) {
        Product product = new Product();
        product.setName(createDTO.getName());
        product.setDescription(createDTO.getDescription());
        product.setAccountType(createDTO.getAccountType());
        return productRepository.save(product);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Optional<Purchase> purchaseProduct(Long userId, Long productId, BigDecimal amount) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (!productOpt.isPresent()) {
            return Optional.empty();
        }

        // UserService를 통해 사용자 잔고 업데이트
        boolean balanceUpdated = userService.updateBalance(userId, amount.negate());
        if (!balanceUpdated) {
            return Optional.empty();
        }

        Purchase purchase = new Purchase();
        purchase.setUser(userRepository.findById(userId).get());
        purchase.setProduct(productOpt.get());
        purchase.setAmount(amount);
        return Optional.of(purchaseRepository.save(purchase));
    }

}
