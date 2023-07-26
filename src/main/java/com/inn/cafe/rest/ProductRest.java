package com.inn.cafe.rest;

import com.inn.cafe.wrapper.ProductWrapper;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/product")
public interface ProductRest {
    @PostMapping(path = "/add")
    ResponseEntity<String> addNewProduct( @RequestBody Map<String,String> requestMap);
    @GetMapping(path = "/get")
    ResponseEntity<List<ProductWrapper>> getAllProduct();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateProduct(@RequestBody Map<String,String> requestMap);

    @PostMapping(path = "/delete/{id}")

    ResponseEntity<String > deleteProduct(@PathVariable Integer id);

    @PostMapping("updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody Map<String,String> requestMap);

    @GetMapping(path = "/getByCategory/{id}")
    ResponseEntity<List<ProductWrapper>> getByCategory(@PathVariable Integer id);

    @GetMapping("/getById/{id}")
    ResponseEntity<ProductWrapper> getProductById(@PathVariable Integer id);

}
