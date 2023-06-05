package com.yc.resfood.biz;

import com.yc.bean.Resorder;
import com.yc.bean.Resorderitem;
import com.yc.bean.Resuser;
import com.yc.resfood.dao.ResorderDao;
import com.yc.resfood.dao.ResorderitemDao;
import com.yc.resfood.model.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(  propagation= Propagation.REQUIRED,
        isolation= Isolation.DEFAULT,  timeout=2000,
        readOnly=false, rollbackFor=RuntimeException.class
)
@Slf4j
public class ResoderBizImpl implements ResorderBiz{
    @Autowired
    private ResorderDao resorderDao;
    @Autowired
    private ResorderitemDao resorderitemDao;

    @Override
    public int order(Resorder resorder, Set<CartItem> cartItems, Resuser resuser) {
        this.resorderDao.insert( resorder );

        for( CartItem ci:cartItems){
            Resorderitem roi=new Resorderitem();
            roi.setRoid(     resorder.getRoid() );
            roi.setFid(    ci.getFood().getFid() );
            roi.setDealprice( ci.getFood().getRealprice());
            roi.setNum(   ci.getNum() );
            this.resorderitemDao.insert(   roi);
        }

        return 1;
    }
}
