package com.quensty.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quensty.commonutils.CommonResult;
import com.quensty.eduService.entity.EduTeacher;
import com.quensty.eduService.entity.vo.TeacherQuery;
import com.quensty.eduService.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-29
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduService/teacher")
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询讲师表所有数据
     * @return
     */
    @ApiOperation("获取全部讲师信息")
    @GetMapping("/findAll")
    public CommonResult findAllTeacher(){
        List<EduTeacher> teachers = teacherService.list(null);
        return CommonResult.success().data("list",teachers);
    }

    /**
     * 根据id删除讲师
     * @return
     */
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("/remove/{id}")
    public CommonResult removeTeacherById(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if (flag){
            return CommonResult.success();
        }else {
            return CommonResult.failed();
        }
    }

    /**
     * 分页查询讲师信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation("分页查询teacher")
    @GetMapping("/page/{pageNum}/{pageSize}")
    public CommonResult pageList(@ApiParam(name = "pageNum",value = "当前页码",required = true)@PathVariable Long pageNum,
                                 @ApiParam(name = "pageSize",value = "每页记录数",required = true)@PathVariable Long pageSize){
        Page<EduTeacher> pageParam = new Page<>(pageNum,pageSize);
        teacherService.page(pageParam,null);
        List<EduTeacher> rows = pageParam.getRecords();
        Long total = pageParam.getTotal();
        return CommonResult.success().data("total",total).data("rows",rows);
    }

    /**
     * 条件查询带分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation("带条件分页讲师列表")
    @PostMapping("/pageCondition/{pageNum}/{pageSize}")
    public CommonResult pageCondition(@ApiParam(name = "pageNum",value = "当前页码",required = true)@PathVariable Long pageNum,
                                      @ApiParam(name = "pageSize",value = "每页记录数",required = true)@PathVariable Long pageSize,
                                      @ApiParam(name = "teacherQuery",value = "附带条件")@RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> pageTeacher = new Page<>(pageNum, pageSize);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        //排序
        wrapper.orderByDesc("gmt_modified");
        teacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> rows = pageTeacher.getRecords();
        return CommonResult.success().data("total",total).data("rows",rows);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping("/addTeacher")
    public CommonResult addTeacher(@ApiParam(name = "teacher", value = "讲师对象", required = true)
                                   @RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if (save){
            return CommonResult.success();
        }else {
            return CommonResult.failed();
        }
    }
    @ApiOperation("根据ID查询讲师")
    @GetMapping("/getTeacher/{id}")
    public CommonResult getTeacherById(@ApiParam(name = "id",value = "讲师id",required = true)@PathVariable Long id){
        EduTeacher teacher = teacherService.getById(id);
        return CommonResult.success().data("teacher",teacher);
    }

    @ApiOperation("修改讲师信息")
    @PostMapping("/updateTeacher")
    public CommonResult updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean update = teacherService.updateById(eduTeacher);
        if (update){
            return CommonResult.success();
        }else {
            return CommonResult.failed();
        }
    }
}

