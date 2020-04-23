package com.tensquare.base.controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/**
 * 标签的Controller
 */
@RestController   //@RestController=@Controller+@ResponseBody
@RequestMapping("/label")
@CrossOrigin // 解决跨域问题
public class LabelController {
    @Autowired
    private LabelService labelService;


    /**
     * 查询所有
     */
    @RequestMapping(method = RequestMethod.GET)
    //@GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",labelService.findAll());
    }

    /**
     * 查询一个
     */
    @RequestMapping(value = "/{id}" ,method =RequestMethod.GET )
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",labelService.findById(id));
    }

    /**
     * 添加
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Label label){
        labelService.add(label);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable String id,@RequestBody Label label){
        label.setId(id);
        labelService.update(label);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id){
        labelService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 标签条件查询
     */
    @RequestMapping(value = "/search" ,method = RequestMethod.POST )
    public Result findSearch(@RequestBody Map searchMap){
        List<Label> list = labelService.findSearch(searchMap);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }

    /**
     * 带条件的分页查询
     */
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result findSearch(@PathVariable int page,@PathVariable int size,@RequestBody Map searchMap){
        Page<Label> pageData = labelService.findSearch(page,size,searchMap);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }
}


