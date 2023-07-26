package com.inn.cafe.serviceimpl;

import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.Vendor;
import com.inn.cafe.constants.CafeConstant;
import com.inn.cafe.constants.StoreContant;
import com.inn.cafe.dao.VendorDao;
import com.inn.cafe.service.VendorService;
import com.inn.cafe.utils.CafeUtils;
import com.inn.cafe.utils.StoreUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class VendorServerImpl implements VendorService {

    String tempMessage;
    @Autowired
    VendorDao vendorRepository;
    @Autowired
    JwtFilter jwtFilter;
//    @Autowired
//    PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public ResponseEntity<String> saveVendor(Vendor vendor) {
        log.info("Inside Vendor Service {}", vendor);

        try {
            if (validateVendor(vendor)) {

                List<Vendor> tempVender = vendorRepository.searchVendor(vendor.getShopName());
                System.out.println("--" + tempVender.toString());
                if (tempVender.size() < 1) {
                    vendor = vendorRepository.save(vendor);
                    return StoreUtils.getResponseEntity("Vendor Successfully Registered with - " + vendor
                            , HttpStatus.OK);
                } else {
                    return StoreUtils.getResponseEntity("Vendor already resgisterd with -" + vendor
                            , HttpStatus.BAD_REQUEST);
                }

            } else {
                return StoreUtils.getResponseEntity(StoreContant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreContant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);

    }

    private boolean validateVendor(Vendor vendor) {
        boolean status = true;
        tempMessage = "";

        if (vendor.getShopName() == null || vendor.getShopName().trim().equals("")) {
            status = false;
            tempMessage = tempMessage + ", vendor shopname ";
        }
        tempMessage = tempMessage + " should not be empty";
        return status;

    }

    @Override
    public ResponseEntity<List<Vendor>> findAllVendor() {
        log.info("Inside vendor findAllVendor {}", "");
        try {
            List<Vendor> vendorList = vendorRepository.findAll();

            return new ResponseEntity<>(vendorList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<Vendor> getVendorByVendorId(Long id) {
        log.info("Inside verndorServiceimpl -getVendorByUserName -{} ", id);
        try {
            Vendor vendor = vendorRepository.findById(id).get();

            return new ResponseEntity<>(vendor, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Vendor>(new Vendor(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateVendor(Vendor vendor) {
        log.info("Inside Vendor Service updateVendor {}", vendor);

        try {
            if (jwtFilter.isAdmin()){
                Optional<Vendor> vend = vendorRepository.findById(vendor.getId());
                if (!vend.isEmpty()){
                   vendorRepository.save(vendor);
                    return CafeUtils.getResponseEntity("Product status updated Successfully", HttpStatus.OK);

                }
                return StoreUtils.getResponseEntity("Product id does not exist", HttpStatus.OK);
            }else {
                return StoreUtils.getResponseEntity(StoreContant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreContant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteVendor(Long id) {

        try {
            if (jwtFilter.isAdmin()){
                Optional<Vendor> optional = vendorRepository.findById(id);
                if (!optional.isEmpty()){
                    vendorRepository.deleteById(id);
                    return StoreUtils.getResponseEntity("Vendor deleted Successfully",HttpStatus.OK);
                }
                return StoreUtils.getResponseEntity("Vendor id does not exist", HttpStatus.OK);
            }else {
                return StoreUtils.getResponseEntity(CafeConstant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreContant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateVendorStatus(Vendor vendor) {
        log.info("Inside Vendor Service updateVendorStatus {}", vendor);

        try {
            if (jwtFilter.isAdmin()){
                Optional<Vendor> vend = vendorRepository.findById(vendor.getId());
                if (!vend.isEmpty()){
                    vendorRepository.saveStatus(vendor.getStatus(), vendor.getId());
                    return CafeUtils.getResponseEntity("Product status updated Successfully", HttpStatus.OK);

                }
                return CafeUtils.getResponseEntity("Product id does not exist", HttpStatus.OK);
            }
            else {
                return StoreUtils.getResponseEntity(StoreContant.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreContant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
