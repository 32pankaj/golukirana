package com.inn.cafe.dao;

import com.inn.cafe.POJO.PaymentDetail;
import com.inn.cafe.POJO.PaymentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaymentImageDao extends JpaRepository<PaymentImage,Long> {

    List<PaymentImage> findByPaymentDetail(PaymentDetail paymentDetail);
}
