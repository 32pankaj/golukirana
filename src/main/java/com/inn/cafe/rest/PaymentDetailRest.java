package com.inn.cafe.rest;


import com.inn.cafe.POJO.PaymentDetail;
import com.inn.cafe.POJO.PaymentImage;
import com.inn.cafe.wrapper.PaymentDetailWrapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Set;

@RequestMapping(path = "/payment")
public interface PaymentDetailRest {

    @PreAuthorize("hasRole('Admin')")
    @PostMapping(value = "/saveWithImage", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<String> addPaymentDetailWithImage(@RequestPart("paymentDetail") PaymentDetail paymentDetail,
                                                     @RequestPart("imageFile") MultipartFile[] file);

    @PostMapping(path = "/save")
    ResponseEntity<String> addPaymentDetail(@RequestBody PaymentDetail paymentDetail);

    @GetMapping(path = "/getByVendorId/{id}")
    ResponseEntity<Set<PaymentDetailWrapper>> getPaymentDetailByVendorId(@PathVariable Long id);

    @GetMapping(path = "/getByDate/{date}")
    ResponseEntity<Set<PaymentDetailWrapper>> getPaymentDetailByDate(@PathVariable String date);

    @GetMapping(path = "/getAllPayment")
    ResponseEntity<List<PaymentDetail>> getAllPayment();
//    @GetMapping(path = "/getByVendorId/{id}")
//    ResponseEntity<Set<PaymentDetail>> getPaymentDetailByVendorId(@PathVariable Long id);


    @PostMapping(path = "/deletePaymentById/{id}")
    ResponseEntity<String> deletePaymentDetailById(@PathVariable Long id);

    @GetMapping(path = "/getImageByPaymentId/{id}")

    ResponseEntity<List<PaymentImage>> getImagesByPaymentId(Long id);

}
