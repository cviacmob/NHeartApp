package com.cviac.nheart.nheartapp.datamodel;

import java.util.List;

/**
 * Created by admin1 on 12/16/2016.
 */
public class GetCartItemsResponse {

    private List<ProductCartInfo> products;

    private List<CartTotalInfo> totals;

    public GetCartItemsResponse() {
    }

    public List<ProductCartInfo> getProds() {
        return products;
    }

    public void setProds(List<ProductCartInfo> prods) {
        this.products = prods;
    }

    public List<CartTotalInfo> getTotals() {
        return totals;
    }

    public void setTotals(List<CartTotalInfo> totals) {
        this.totals = totals;
    }
}
