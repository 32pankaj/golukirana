package com.inn.cafe.rest;

import com.inn.cafe.POJO.PaymentImage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(path = "/image")
public interface PaymentImageRest {

    @GetMapping(path = "/getByPaymentId/{id}")
     ResponseEntity<List<PaymentImage>> getAllImagesByPaymentId(@PathVariable Long id);
}
