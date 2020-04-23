package com.tensquare.recruit.dao;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

    /**
     *
     state为2   = findByState("2")
     按照createtime倒序  = OrderByCreatetimeDesc
     取前面3条 = findTop4By
     */
    public List<Recruit> findTop3ByStateOrderByCreatetimeDesc(String state);
    public List<Recruit> findTop3ByStateNotOrderByCreatetimeDesc(String state);
}
