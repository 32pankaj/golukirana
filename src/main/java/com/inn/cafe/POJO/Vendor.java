package com.inn.cafe.POJO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Vendor  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vendorName;
    private String shopName;
    private String createdDate;
    private boolean status;
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<PaymentDetail> purchaseOrderSet = new LinkedHashSet<>();
    private double totalCredit;
    private double totalDebit;
    private double totalBalance;

    public boolean getStatus() {
        return this.status;
    }
}
