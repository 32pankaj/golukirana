package com.inn.cafe.serviceimpl;

import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.PaymentDetail;
import com.inn.cafe.POJO.PaymentImage;
import com.inn.cafe.POJO.Vendor;
import com.inn.cafe.constants.StoreContant;
import com.inn.cafe.dao.PaymentDetailDao;
import com.inn.cafe.dao.VendorDao;
import com.inn.cafe.service.PaymentDetailService;
import com.inn.cafe.utils.StoreUtils;
import com.inn.cafe.wrapper.PaymentDetailWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

@Slf4j
@Service
public class PaymentDetailServiceImpl implements PaymentDetailService {
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    PaymentDetailDao paymentDetailDao;
    @Autowired
    VendorDao vendorDao;
    long balance = 0;



    @Override
    public ResponseEntity<String> savePaymentDetailWithImage(PaymentDetail paymentDetail, MultipartFile[] file) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validatePaymentDetail(paymentDetail)) {
                    Vendor vendor = vendorDao.findById(paymentDetail.getVendor().getId()).get();


                    System.out.println("-----------------------------------------------------");
                    System.out.println(paymentDetail.getPaymentImages());

                    System.out.println("-----------------------------------------------------");
                    updateVendorData(vendor, paymentDetail);
                    System.out.println("--before Image-" + paymentDetail);
                    if (file.length > 0) {
                        paymentDetail.setNoOfImage(file.length);
                        uploadImage(paymentDetail, file);
//                        paymentDetail.setPaymentImages(paymentImages);
//                     paymentDetail.getPaymentImages().addAll(paymentImages);
                    }
                    System.out.println("---images" + paymentDetail.getPaymentImages().size() + "---" + paymentDetail);
                    paymentDetailDao.save(paymentDetail);
                    vendorDao.save(vendor);
                    return StoreUtils.getResponseEntity("PaymentDetail save successfully added with " + file.length + " images", HttpStatus.OK);
                } else {
                    return StoreUtils.getResponseEntity(StoreContant.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }

            } else {
                return StoreUtils.getResponseEntity(StoreContant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreContant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void updateVendorData(Vendor vendor, PaymentDetail paymentDetail) {


        if (paymentDetail.getId() != null && !paymentDetail.getId().toString().trim().equals("")) {
            Optional<PaymentDetail> optional = paymentDetailDao.findById(paymentDetail.getId());
            PaymentDetail paymentDetailPrevious = optional.get();
            long creditDiff = paymentDetailPrevious.getCredit() - paymentDetail.getCredit();
            long debitDiff = paymentDetailPrevious.getDebit() - paymentDetail.getDebit();
            vendor.setTotalCredit(vendor.getTotalCredit() - creditDiff);
            vendor.setTotalDebit(vendor.getTotalDebit() - debitDiff);
            vendor.setTotalBalance(vendor.getTotalCredit() - vendor.getTotalDebit());
        } else {
            vendor.setTotalCredit(vendor.getTotalCredit() + paymentDetail.getCredit());
            vendor.setTotalDebit(vendor.getTotalDebit() + paymentDetail.getDebit());
            vendor.setTotalBalance(vendor.getTotalCredit() - vendor.getTotalDebit());
        }
    }

//    @Override
//    public ResponseEntity<Set<PaymentDetail>> getPaymentDetailByVendorId(Long id) {
//        try {
//            if (jwtFilter.isAdmin()) {
//                Set<PaymentDetail> paymentDetails = paymentDetailDao.getByVendorId(id);
//
//                paymentDetails.forEach((payDetail) -> {
//                    balance += payDetail.getCredit();
//                    balance = balance - payDetail.getDebit();
//                    payDetail.setBalance(balance);
//                });
//                balance = 0;
//                return new ResponseEntity<>(paymentDetails, HttpStatus.OK);
//
//            } else {
//                return new ResponseEntity<>(new HashSet<>(), HttpStatus.UNAUTHORIZED);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>(new HashSet<>(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }


    @Override
    public ResponseEntity<Set<PaymentDetailWrapper>> getPaymentDetailByVendorId(Long id) {
        try {
            if (jwtFilter.isAdmin()) {
                Set<PaymentDetailWrapper> PaymentDetailWrapper = paymentDetailDao.getByVendorId(id);

                PaymentDetailWrapper.forEach((payDetail) -> {
                    balance += payDetail.getCredit();
                    balance = balance - payDetail.getDebit();
                    payDetail.setBalance(balance);
                });
                balance = 0;
                return new ResponseEntity<>(PaymentDetailWrapper, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new HashSet<>(), HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new HashSet<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<String> savePaymentDetail(PaymentDetail paymentDetail) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validatePaymentDetail(paymentDetail)) {
                    System.out.println("------------" + paymentDetail);

                    Vendor vendor = vendorDao.findById(paymentDetail.getVendor().getId()).get();
                    updateVendorData(vendor, paymentDetail);

                    vendorDao.save(vendor);
                    paymentDetailDao.save(paymentDetail);

                    System.out.println("----------" + vendor);


                    return StoreUtils.getResponseEntity("PaymentDetail save successfully", HttpStatus.OK);
                } else {
                    return StoreUtils.getResponseEntity(StoreContant.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }

            } else {
                return StoreUtils.getResponseEntity(StoreContant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreContant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
//
//    @Override
//    public ResponseEntity<String> updatePaymentDetail(PaymentDetail paymentDetail) {
//        try {
//            if (jwtFilter.isAdmin()) {
//                if (validatePaymentDetail(paymentDetail)) {
//                    Optional<PaymentDetail> optional = paymentDetailDao.findById(paymentDetail.getId());
//
//                    if (!optional.isEmpty()) {
//                        PaymentDetail paymentDetailPrevious = optional.get();
//                        Vendor vendor = paymentDetail.getVendor();
//
//                        long creditDiff = paymentDetailPrevious.getCredit() - paymentDetail.getCredit();
//
//
//                        long debitDiff = paymentDetailPrevious.getDebit() - paymentDetail.getDebit();
//
//
//                        vendor.setTotalCredit(vendor.getTotalCredit() - creditDiff);
//                        System.out.println("--- " + paymentDetailPrevious.getDebit() + "-" + paymentDetail.getDebit() + "-" + debitDiff + "-" + vendor.getTotalDebit());
//                        vendor.setTotalDebit(vendor.getTotalDebit() - debitDiff);
//                        System.out.println("--" + vendor.getTotalDebit());
//                        vendor.setTotalBalance(vendor.getTotalCredit() - vendor.getTotalDebit());
//
//                        paymentDetailDao.save(paymentDetail);
//                        vendorDao.save(vendor);
//                        return StoreUtils.getResponseEntity("PaymentDetail successfully Updated ", HttpStatus.OK);
//                    } else {
//                        StoreUtils.getResponseEntity("PaymentDetail not found to update by id " + paymentDetail.getId(), HttpStatus.BAD_REQUEST);
//                    }
//
//
//                } else {
//                    return StoreUtils.getResponseEntity(StoreContant.INVALID_DATA, HttpStatus.BAD_REQUEST);
//                }
//
//            } else {
//                return StoreUtils.getResponseEntity(StoreContant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return StoreUtils.getResponseEntity(StoreContant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @Override
    public ResponseEntity<String> deletePaymentDetailById(Long id) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<PaymentDetail> payById = paymentDetailDao.findById(id);
                if (!payById.isEmpty()) {

                    PaymentDetail paymentDetail = payById.get();
                    Vendor vendor = payById.get().getVendor();
                    vendor.setTotalCredit(vendor.getTotalCredit() - paymentDetail.getCredit());
                    vendor.setTotalDebit(vendor.getTotalDebit() - paymentDetail.getDebit());
                    vendor.setTotalBalance(vendor.getTotalCredit() - vendor.getTotalDebit());
                    vendorDao.save(vendor);
                    paymentDetailDao.deleteById(id);
                    return StoreUtils.getResponseEntity("Deleted Data Successfully..", HttpStatus.OK);
                } else {
                    return StoreUtils.getResponseEntity(StoreContant.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }

            } else {
                return StoreUtils.getResponseEntity(StoreContant.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreContant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<PaymentImage>> getImagesByPaymentId(Long id) {
        try {
            if (jwtFilter.isAdmin()) {
                return new ResponseEntity<>(paymentDetailDao.getImageByPaymentId(id), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<PaymentImage>> getImagesByVendorId(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<PaymentDetail>> getAllPaymentDetail() {
        try {
            if (jwtFilter.isAdmin()) {

                return new ResponseEntity<>(paymentDetailDao.findAllByOrderByPaymentDateDesc(), HttpStatus.OK);

            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Set<PaymentDetailWrapper>> getPaymentDetailByDate(String date) {
      log.info("Inside getPaymentDetailByDate imple {}", date);
      try {
          if (jwtFilter.isAdmin()){
//              Instant instant = Instant.parse(date);
//              ZoneId zoneId = ZoneId.systemDefault();
//              ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId);
//
//              LocalDateTime startDateTime = zonedDateTime.toLocalDate().atStartOfDay();
//              LocalDateTime endDateTime = zonedDateTime.toLocalDate().atTime(23, 59, 59);
              DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss");
              LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

              LocalDateTime startDateTime = dateTime.toLocalDate().atStartOfDay();
              LocalDateTime endDateTime = dateTime.toLocalDate().atTime(LocalTime.MAX);

              String formattedStartDateTime = startDateTime.format(formatter);
              String formattedEndDateTime = endDateTime.format(formatter);

              return  new ResponseEntity<>(paymentDetailDao.getByDateBetweenOrderByDate(formattedStartDateTime, formattedEndDateTime),HttpStatus.OK);
          }else {
              return new ResponseEntity<>(new HashSet<>(), HttpStatus.UNAUTHORIZED);
          }
      }catch (Exception e ){
          e.printStackTrace();
      }
      return new ResponseEntity<>(new HashSet<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private void uploadImage(PaymentDetail paymentDetail, MultipartFile[] file) throws IOException {
            Set<PaymentImage> images=new HashSet<>();
        for (MultipartFile file1 : file) {
            PaymentImage paymentImage = new PaymentImage();
            paymentImage.setPaymentDetail(paymentDetail);
            paymentImage.setFileName(file1.getOriginalFilename());
            paymentImage.setType(file1.getContentType());
            paymentImage.setPicByte(file1.getBytes());
            images.add(paymentImage);
        }
        paymentDetail.setPaymentImages(images);

    }

    private boolean validatePaymentDetail(PaymentDetail paymentDetail) {
        return true;
    }
}
