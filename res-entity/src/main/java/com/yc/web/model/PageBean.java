package com.yc.web.model;

import java.util.List;

public class PageBean<T> {
    //以下两个属性是界面给的已知参数
    private int pageno=1;    //当前第几页
    private int pagesize=5;   //每页多少条
    private String sortby;    //排序列的列名
    private String sort;    //取值为: asc/desc


    //以下两个属性是数据库查询得到的结果
    private long total;    //总记录数
    private List<T> dataset;
    //需要在业务层中计算.
    private int totalpages;    //总共多少页.
    private int pre;    //上一页的页数
    private int next;  //下一页的页数






    public void setTotalpages(int totalpages) {
        this.totalpages = totalpages;
    }

    public void setSortby(String sortby) {
        this.sortby = sortby;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getTotalpages() {
        return totalpages;
    }

    public String getSortby() {
        return sortby;
    }

    public String getSort() {
        return sort;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setPageno(int pageno) {
        this.pageno = pageno;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public void setPre(int pre) {
        this.pre = pre;
    }

    public void setNext(int next) {
        this.next = next;
    }



    public void setDataset(List<T> dataset) {
        this.dataset = dataset;
    }

    public long getTotal() {
        return total;
    }

    public int getPageno() {
        return pageno;
    }

    public int getPagesize() {
        return pagesize;
    }

    public int getPre() {
        return pre;
    }

    public int getNext() {
        return next;
    }



    public List<T> getDataset() {
        return dataset;
    }
}
