package com.inn.cafe.service;

import com.inn.cafe.POJO.PaymentImage;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaymentImageService {
    ResponseEntity<List<PaymentImage>> getAllImagesByPaymentId(Long id);
}
