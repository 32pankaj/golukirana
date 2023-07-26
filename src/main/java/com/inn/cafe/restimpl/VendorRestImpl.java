package com.inn.cafe.restimpl;

import com.inn.cafe.POJO.Vendor;
import com.inn.cafe.constants.StoreContant;
import com.inn.cafe.rest.VendorRest;
import com.inn.cafe.service.VendorService;
import com.inn.cafe.utils.StoreUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin("*")
public class VendorRestImpl implements VendorRest {


    @Autowired
    VendorService vendorService;

    public ResponseEntity<String> saveVendor(@RequestBody Vendor vendor) {
        log.info("Inside vendor controller {}", vendor);
        try {
            return vendorService.saveVendor(vendor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreContant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Vendor>> findAllVendor() {
        log.info("Inside findAllVendor VendorController", "");
        try {
            return vendorService.findAllVendor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<Vendor>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Vendor> findByVendorId(@PathVariable Long id) {
        log.info("Inside vendor Controller - findByVendorName {}", id);
        try {
            return vendorService.getVendorByVendorId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new Vendor(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteVendor(Long id) {
        return vendorService.deleteVendor(id);
    }

    @Override
    public ResponseEntity<String> updateVendorStatus(Vendor vendor) {
      return vendorService.updateVendorStatus(vendor);
    }

    @Override
    public ResponseEntity<String> updateVendor(Vendor vendor) {
        return vendorService.updateVendor(vendor);
    }
}
