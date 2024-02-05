package com.example.hanriver.controller;

import com.example.hanriver.model.Cart;
import com.example.hanriver.dto.CartItemDTO;
import com.example.hanriver.dto.CartDTO;
import com.example.hanriver.model.CartItem;
import org.springframework.http.HttpStatus;
import com.example.hanriver.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCartByUserId(@PathVariable Long userId) {
        CartDTO cartDTO = cartService.getCartDTOByUserId(userId);
        return ResponseEntity.ok(cartDTO);
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<?> addProductToCart(@PathVariable Long userId, @RequestBody CartItemDTO cartItemDTO) {
        try {
            CartItem cartItem = cartService.addProductToCart(userId, cartItemDTO.getProductId(), cartItemDTO.getQuantity());
            return ResponseEntity.ok(cartItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    // 추가 엔드포인트: 상품 추가, 제거 등
}
