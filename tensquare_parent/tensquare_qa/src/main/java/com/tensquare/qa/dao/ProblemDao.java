package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    /**
     * 最新问答
     *   @Query注解里面的是JPQL语句，最后翻译成sql语句，翻译的时候把JPQL的类名翻译成表名，属性名称翻译字段名称！！！
     *
     * 写JPQL语句的建议：
     *     先写sql语句 ，再把sql翻译JPQL语句
     *
     * 1）最新问答的sql语句
     *     -- 1）查询中间表查出问题
                SELECT pl.problemid FROM tb_pl pl WHERE pl.labelid = '1082901529616314368';

                -- 2) 根据上面查出来的问题id，到问题表查询出对应信息
                SELECT * FROM tb_problem p WHERE p.id
                IN (SELECT pl.problemid FROM tb_pl pl WHERE pl.labelid = '1082901529616314368')
                ORDER BY p.replytime DESC;

    select * from tb_problem p where p.id in (select  problemid from tb_pl pl where pl.labelid = '1')
    order by p.replytime desc
       2）把sql翻译成JPQL语句
            SELECT p FROM Problem p WHERE p.id
                IN (SELECT pl.problemid FROM Pl pl WHERE pl.labelid = ?1 )
                    ORDER BY p.replytime DESC;
     *
     */
    @Query("select p from Problem p where p.id in (select  pl.problemid from Pl pl where pl.labelid = ?1)\n" +
            "    order by p.replytime desc")
    public Page<Problem> queryNewlistByLabelid(String labelid, Pageable pageable);

    /**
     * 热门问答
     */
    @Query("SELECT p FROM Problem p WHERE p.id" +
            "                IN (SELECT pl.problemid FROM Pl pl WHERE pl.labelid = ?1 )" +
            "                    ORDER BY p.reply DESC")
    public Page<Problem> queryHotlistByLabelid(String labelid, Pageable pageable);

    /**
     * 等待问答
     */
    @Query("SELECT p FROM Problem p WHERE p.id" +
            "                IN (SELECT pl.problemid FROM Pl pl WHERE pl.labelid = ?1 )" +
            "                    AND p.reply = 0 ORDER BY p.createtime DESC")
    public Page<Problem> queryWaitlistByLabelid(String labelid, Pageable pageable);
}
