package com.inn.cafe.dao;

import com.inn.cafe.POJO.PaymentDetail;
import com.inn.cafe.POJO.PaymentImage;
import com.inn.cafe.wrapper.PaymentDetailWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface PaymentDetailDao extends JpaRepository<PaymentDetail,Long> {

//    @Query("select p from PaymentDetail p where p.vendor.id=:id order by p.id")
//    Set<PaymentDetail> getByVendorId(@Param("id") Long id);

    @Query("select new com.inn.cafe.wrapper.PaymentDetailWrapper(p.id,p.credit,p.debit,p.paymentDate,p.paymentMode,p.PaymentProvider,p.comment,p.noOfImage,p.vendor) from PaymentDetail p where p.vendor.id=:id order by p.id")
    Set<PaymentDetailWrapper> getByVendorId(@Param("id") Long id);

    List<PaymentDetail> findAllByOrderByPaymentDateDesc();
    @Query("select p.paymentImages from PaymentDetail p where  p.id=:id ")
    List<PaymentImage> getImageByPaymentId(@Param("id") Long id);

    @Query("select new com.inn.cafe.wrapper.PaymentDetailWrapper(p.id,p.credit,p.debit,p.paymentDate,p.paymentMode,p.PaymentProvider,p.comment,p.noOfImage,p.vendor) from PaymentDetail p where p.paymentDate BETWEEN ?1 AND ?2 order by p.paymentDate ASC")
    Set<PaymentDetailWrapper> getByDateBetweenOrderByDate(String startDate,String endDate);


}
