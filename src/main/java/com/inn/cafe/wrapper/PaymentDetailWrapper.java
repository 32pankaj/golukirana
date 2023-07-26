package com.inn.cafe.wrapper;

import com.inn.cafe.POJO.Vendor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class PaymentDetailWrapper {

    private Long id;


    private Long credit;
    private Long debit;
    private String paymentDate;
    private String paymentMode;
    private String PaymentProvider;
    private String comment;

    private Integer noOfImage;

    private Long balance;

    private Vendor vendor;

    public PaymentDetailWrapper(Long id, Long credit,
                                Long debit, String paymentDate,
                                String paymentMode, String paymentProvider,
                                String comment,
                                Integer noOfImage, Vendor vendor) {
        this.id = id;
        this.credit = credit;
        this.debit = debit;
        this.paymentDate = paymentDate;
        this.paymentMode = paymentMode;
        PaymentProvider = paymentProvider;
        this.comment = comment;
        this.noOfImage = noOfImage;
        this.vendor = vendor;
    }


}
