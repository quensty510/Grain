package com.quensty.eduService.controller;


import com.quensty.commonutils.CommonResult;
import com.quensty.eduService.entity.vo.SubjectTree;
import com.quensty.eduService.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
@Api(tags="课程分类管理")
@RestController
@RequestMapping("/eduService/edu-subject")
//@CrossOrigin(allowCredentials = "true")
public class EduSubjectController {

    @Autowired
    EduSubjectService eduSubjectService;

    @ApiOperation(value = "Excel批量导入")
    @PostMapping("/addSubject")
    public CommonResult addSubject(MultipartFile file){
        eduSubjectService.saveSubject(file);
        return CommonResult.success();
    }

    @ApiOperation("获取所有课程分类，以树形结构返回")
    @GetMapping("/getAllSubject")
    public CommonResult getAllSubject(){
        List<SubjectTree> tree = eduSubjectService.getSubjectList();
        return  CommonResult.success().data("list",tree);
    }
}

