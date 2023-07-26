package com.inn.cafe.POJO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentDetail{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long credit;
    private Long debit;
    private String paymentDate;
    private String paymentMode;
    private String PaymentProvider;
    private String comment;
    @ManyToOne(fetch = FetchType.EAGER)

    private Vendor vendor;

    private Integer noOfImage;



    @OneToMany(mappedBy = "paymentDetail",fetch = FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval=true)

    private Set<PaymentImage> paymentImages=new HashSet<>();

    @Transient
private Long balance;




    @Override
    public String toString() {
        System.out.println( "PaymentDetail{" +
                "id=" + id +
                ", credit=" + credit +
                ", debit=" + debit +
                ", paymentDate='" + paymentDate + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                ", PaymentProvider='" + PaymentProvider + '\'' +
                ", comment='" + comment + '\'' +
                ", noOfImage=" + noOfImage +
                ", balance=" + balance +
                '}');
        return "";
    }
}
