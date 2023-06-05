package com.yc.resfood.biz;


import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.resfood.model.CartItem;

import java.util.Set;

public interface ResorderBiz {

    public int order(Resorder resorder, Set<CartItem> cartItems, Resuser resuser);
}
