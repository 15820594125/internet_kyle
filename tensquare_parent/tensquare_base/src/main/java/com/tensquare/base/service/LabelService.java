package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 标service类
 */
@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    /**
     * 查询一个
     */
    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    /**
     * 添加
     */
    public void add(Label label){
        //设置id
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    /**
     * 修改
     */
    public void update(Label label){ // label必须有数据库存在的ID值
        labelDao.save(label);
    }

    /**
     * 删除
     */
    public void delete(String id){
        labelDao.deleteById(id);
    }

    /**
     * 创建Specification对象
     */
    private Specification<Label> createSpecification(Map searchMap){
        //自动提供Specification的实现（采取匿名内部类型写法）
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

                //1.创建List集合，用于存放需要拼装的条件
                List<Predicate> preList = new ArrayList<Predicate>();
                //2.根据传递的查询条件，进行拼装条件，把条件放入List集合中
                if( searchMap.get("labelname")!=null && !searchMap.get("labelname").equals("") ){
                    // sql:  labelname like '%xxx%'
                    preList.add( cb.like(root.get("labelname").as(String.class) , "%"+searchMap.get("labelname")+"%" ));
                }
                if( searchMap.get("state")!=null && !searchMap.get("state").equals("") ){
                    // sql:  state = '1'
                    preList.add( cb.equal(root.get("state").as(String.class) , ""+searchMap.get("state")+"" ));
                }
                if( searchMap.get("recommend")!=null && !searchMap.get("recommend").equals("") ){
                    // sql:  recommend = '1'
                    preList.add( cb.equal(root.get("recommend").as(String.class) , ""+searchMap.get("recommend")+"" ));
                }
                //3.使用条件连接（and  or）把所有条件进行连接
                //  labelname like '%xxx%' and  state = '1' and  recommend = '1'
                //preList.toArray(preArray): 把preList集合里面每个元素取出来，放入到preArray数组中去，返回preArray数组
                Predicate[] preArray = new Predicate[preList.size()];
                return cb.and(preList.toArray(preArray));
            }
        };
    }


    /**
     * 条件查询
     */
    public List<Label> findSearch(Map searchMap){
        Specification<Label> spec = createSpecification(searchMap);
        return labelDao.findAll(spec);
    }

    /**
     * 带条件的分页
     */
    public Page<Label> findSearch(int page, int size, Map searchMap){
        Specification<Label> spec = createSpecification(searchMap);
        //注意:PageRequest的page，从0开始计算
        return labelDao.findAll(spec, PageRequest.of(page-1,size));
    }
}
