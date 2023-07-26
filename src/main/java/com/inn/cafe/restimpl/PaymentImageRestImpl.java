package com.inn.cafe.restimpl;

import com.inn.cafe.POJO.PaymentImage;
import com.inn.cafe.rest.PaymentImageRest;
import com.inn.cafe.service.PaymentImageService;
import com.inn.cafe.utils.StoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PaymentImageRestImpl implements PaymentImageRest {
    @Autowired
    PaymentImageService imageService;
    @Override
    public ResponseEntity<List<PaymentImage>> getAllImagesByPaymentId(Long id) {
        try {
          return   imageService.getAllImagesByPaymentId(id);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
