package com.yc.resfood.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resfood;
import com.yc.resfood.dao.ResfoodDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//Propagation.SUPPORTS 表示支持事务可有可无
//readOnly = true 只读事务
@Service
@Transactional(propagation = Propagation.SUPPORTS,
        isolation = Isolation.DEFAULT, timeout = 2000,
        readOnly = true, rollbackFor = RuntimeException.class
)
@Slf4j
public class ResfoodBizImpl implements ResfoodBiz {
    @Autowired
    private ResfoodDao resfoodDao;

    @Override
    public List<Resfood> findAll() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("fid");
        List l=this.resfoodDao.selectList(wrapper);
        return l;
        //return this.resfoodDao.selectList(wrapper);
    }

    @Override
    public Resfood findById(Integer fid) {
        return this.resfoodDao.selectById(fid);
    }

    @Override
    public Page<Resfood> findByPage(int pageno, int pagesize, String sortby, String sort) {
        QueryWrapper wrapper = new QueryWrapper();
        if ("asc".equalsIgnoreCase(sort)) {
            wrapper.orderByAsc(sortby);
        } else {
            wrapper.orderByDesc(sortby);
        }
        Page<Resfood> page = new Page<>(pageno, pagesize);
        //执行分页查询
        Page<Resfood> resfoodPage = this.resfoodDao.selectPage(page, wrapper);
        log.info("总记录数=" + resfoodPage.getTotal());
        log.info("总页数=" + resfoodPage.getPages());
        log.info("当前页码=" + resfoodPage.getCurrent());
        return resfoodPage;
    }
}
