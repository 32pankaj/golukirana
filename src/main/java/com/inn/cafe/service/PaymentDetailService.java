package com.inn.cafe.service;

import com.inn.cafe.POJO.PaymentDetail;
import com.inn.cafe.POJO.PaymentImage;
import com.inn.cafe.wrapper.PaymentDetailWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface PaymentDetailService {
    ResponseEntity<String> savePaymentDetailWithImage(PaymentDetail paymentDetail, MultipartFile[] file);

//    ResponseEntity<Set<PaymentDetail>> getPaymentDetailByVendorId(Long id);
    ResponseEntity<Set<PaymentDetailWrapper>> getPaymentDetailByVendorId(Long id);

    ResponseEntity<String> savePaymentDetail(PaymentDetail paymentDetail);


    ResponseEntity<String> deletePaymentDetailById(Long id);

    ResponseEntity<List<PaymentImage>> getImagesByPaymentId(Long id);

    ResponseEntity<List<PaymentImage>> getImagesByVendorId(Long id);

    ResponseEntity<List<PaymentDetail>> getAllPaymentDetail();

    ResponseEntity<Set<PaymentDetailWrapper>> getPaymentDetailByDate(String date);
}
