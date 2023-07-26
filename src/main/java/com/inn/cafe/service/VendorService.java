package com.inn.cafe.service;

import com.inn.cafe.POJO.Vendor;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VendorService {
    ResponseEntity<String> saveVendor(Vendor vendor);

    ResponseEntity<List<Vendor>> findAllVendor();

    ResponseEntity<Vendor> getVendorByVendorId(Long vendorName);


    ResponseEntity<String> updateVendor(Vendor vendor);

    ResponseEntity<String> deleteVendor(Long id);

    ResponseEntity<String> updateVendorStatus(Vendor vendor);
}
