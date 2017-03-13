package com.cviac.nheart.nheartapp.datamodel;

import java.util.List;

/**
 * Created by admin1 on 3/13/2017.
 */

public class PaymentMethodsResponse {

    private List<PaymentMethodsInfo> payment_methods;

    public List<PaymentMethodsInfo> getPayment_methods() {
        return payment_methods;
    }

    public void setPayment_methods(List<PaymentMethodsInfo> payment_methods) {
        this.payment_methods = payment_methods;
    }
}

