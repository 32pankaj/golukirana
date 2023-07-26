package com.inn.cafe.rest;


import com.inn.cafe.POJO.Vendor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/vendors")
public interface VendorRest {
    @PostMapping("/add")
    public ResponseEntity<String> saveVendor(@RequestBody Vendor vendor);
    @GetMapping("/get")
    public ResponseEntity<List<Vendor>> findAllVendor();
    @GetMapping("/get/{id}")
    public ResponseEntity<Vendor> findByVendorId(@PathVariable Long id);
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable Long id);
    @PostMapping("/updateStatus")
    public ResponseEntity<String> updateVendorStatus(@RequestBody Vendor vendor);
    @PostMapping("/update")
    public ResponseEntity<String> updateVendor(@RequestBody Vendor vendor);

}
