package com.inn.cafe.POJO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "payment_image")
public class PaymentImage {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String type;

    @Column(length = 2000000)
    private byte[] picByte;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentdetail_fk")
    @JsonIgnore
    private PaymentDetail paymentDetail;
}
