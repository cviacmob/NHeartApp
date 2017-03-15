package com.cviac.nheart.nheartapp.datamodel;

import java.io.Serializable;

/**
 * Created by admin1 on 3/14/2017.
 */

public class PaymentMethodsInfo implements Serializable {
    private String code;
    private String title;
    private String terms;
    private String sort_order;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }

}
