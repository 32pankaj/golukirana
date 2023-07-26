package com.inn.cafe.dao;

import com.inn.cafe.POJO.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface VendorDao extends JpaRepository<Vendor, Long> {

//    @Query( "SELECT v FROM Vendor v where v.vendorName like %:vendorName% or v.shopName like %:shopName%")
//    List<Vendor>  searchVendor(@Param("vendorName") String vendorName,String shopName);

    @Query("SELECT v FROM Vendor v where v.shopName like %:shopName%")
    List<Vendor> searchVendor(@Param("shopName") String shopName);

    @Modifying
    @Transactional
    @Query("update Vendor v set v.status=:status where v.id=:id")
    void saveStatus(@Param("status") boolean status, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update Vendor v set v.totalCredit=:totalCredit , v.totalDebit=:totalDebit,v.totalBalance=:totalBalance where v.id=:id")
    void updateCredAndDebit(@Param("totalCredit") double totalCredit, @Param("totalDebit") double totalDebit, @Param("totalBalance") double totalBalance, @Param("id") Long id);
}
