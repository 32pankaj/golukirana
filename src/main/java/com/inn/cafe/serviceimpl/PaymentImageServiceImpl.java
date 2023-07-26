package com.inn.cafe.serviceimpl;

import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.PaymentDetail;
import com.inn.cafe.POJO.PaymentImage;
import com.inn.cafe.dao.PaymentDetailDao;
import com.inn.cafe.dao.PaymentImageDao;
import com.inn.cafe.service.PaymentImageService;
import com.inn.cafe.utils.StoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentImageServiceImpl implements PaymentImageService {

    @Autowired
    PaymentImageDao imageDao;
    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    PaymentDetailDao paymentDetailDao;
    @Override
    public ResponseEntity<List<PaymentImage>> getAllImagesByPaymentId(Long id) {
        try {
            PaymentDetail paymentDetail=new PaymentDetail();
            paymentDetail.setId(id);
            if (jwtFilter.isAdmin() ) {


       return new ResponseEntity<>( imageDao.findByPaymentDetail(paymentDetail), HttpStatus.OK);

            }else{
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
