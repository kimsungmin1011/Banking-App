package com.example.hanriver.service;

import com.example.hanriver.dto.CartDTO;
import com.example.hanriver.dto.CartItemDTO;
import com.example.hanriver.model.Cart;
import com.example.hanriver.model.CartItem;
import com.example.hanriver.model.Product;
import com.example.hanriver.model.User;
import com.example.hanriver.repository.CartItemRepository;
import com.example.hanriver.repository.CartRepository;
import com.example.hanriver.repository.ProductRepository;
import com.example.hanriver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public CartDTO getCartDTOByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setUserId(cart.getUser().getId());
        cartDTO.setItems(cartItems.stream().map(this::convertToCartItemDTO).collect(Collectors.toList()));

        return cartDTO;
    }

    public Cart getCartByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
        cart.setItems(cartItems);
        return cart;
    }

    public CartItem addProductToCart(Long userId, Long productId, int quantity, BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = user.getCart(); // Directly obtain Cart from User
        if (cart == null) {
            throw new RuntimeException("Cart not found for user: " + userId);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update user's balance
        BigDecimal newBalance = user.getBalance().subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        user.setBalance(newBalance);
        userRepository.save(user);

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setAmount(amount);

        return cartItemRepository.save(cartItem);
    }

    private CartItemDTO convertToCartItemDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setProductId(cartItem.getProduct().getId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setAmount(cartItem.getAmount());
        return cartItemDTO;
    }

    // Here you can implement additional features like removing products, changing quantities, etc.
}
