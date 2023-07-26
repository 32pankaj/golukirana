package com.inn.cafe.restimpl;

import com.inn.cafe.POJO.PaymentDetail;
import com.inn.cafe.POJO.PaymentImage;
import com.inn.cafe.constants.StoreContant;
import com.inn.cafe.rest.PaymentDetailRest;
import com.inn.cafe.service.PaymentDetailService;
import com.inn.cafe.utils.StoreUtils;
import com.inn.cafe.wrapper.PaymentDetailWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@Slf4j

public class PaymentDetailRestImpl implements PaymentDetailRest {
    @Autowired
    PaymentDetailService paymentDetailService;

    @Override
    public ResponseEntity<String> addPaymentDetailWithImage(PaymentDetail paymentDetail, MultipartFile[] file) {
        log.info("Inside addPaymentDetailWithImage controller {}", paymentDetail);
        try {
            return paymentDetailService.savePaymentDetailWithImage(paymentDetail,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreContant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> addPaymentDetail(PaymentDetail paymentDetail) {
        log.info("Inside Add paymentDetail controller {}", paymentDetail);
        try {
            System.out.println("-----------"+paymentDetail);
            return paymentDetailService.savePaymentDetail(paymentDetail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreContant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @Override
//    public ResponseEntity<String> updatePaymentDetail(PaymentDetail paymentDetail) {
//
//        log.info("Inside Update paymentDetail controller {}", paymentDetail);
//        try {
//            return paymentDetailService.updatePaymentDetail(paymentDetail);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return StoreUtils.getResponseEntity(StoreContant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @Override
    public ResponseEntity<Set<PaymentDetailWrapper>> getPaymentDetailByVendorId(Long id) {
        log.info("Inside paymentDetailRestImpl {}",id);
        try {
           return paymentDetailService.getPaymentDetailByVendorId(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new HashSet<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Set<PaymentDetailWrapper>> getPaymentDetailByDate(String date) {
        log.info("Inside PaymentDetail getPaymentDetailByDate {}", date);
        try {
            return paymentDetailService.getPaymentDetailByDate(date);
        }catch (Exception e ){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new HashSet<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<PaymentDetail>> getAllPayment() {
        log.info("Inside paymentDetailRestImpl getAllPayment {}");
        try {
            return paymentDetailService.getAllPaymentDetail();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
//   public ResponseEntity<Set<PaymentDetail>> getPaymentDetailByVendorId(Long id) {
//        log.info("Inside paymentDetailRestImpl {}",id);
//        try {
//           return paymentDetailService.getPaymentDetailByVendorId(id);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>(new HashSet<>(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @Override
    public ResponseEntity<String> deletePaymentDetailById(Long id) {
        log.info("Inside paymentDetailRestImpl {}",id);
        try {
            return paymentDetailService.deletePaymentDetailById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreContant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<PaymentImage>> getImagesByPaymentId(Long id) {
        log.info("Inside paymentDetailRestImpl {}",id);
        try {
            return paymentDetailService.getImagesByPaymentId(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
