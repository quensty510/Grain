package com.quensty.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quensty.commonutils.CommonResult;
import com.quensty.eduService.entity.EduCourse;
import com.quensty.eduService.entity.frontVo.CourseFrontDetail;
import com.quensty.eduService.entity.vo.CourseInfo;
import com.quensty.eduService.entity.vo.CoursePublishVo;
import com.quensty.eduService.entity.vo.CourseQuery;
import com.quensty.eduService.service.EduCourseService;
import com.quensty.serviceBase.handler.GuLiException;
import dto.CourseInfoForOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
@RestController
@RequestMapping("/eduService/edu-course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    EduCourseService courseService;

    @ApiOperation("添加课程信息")
    @PostMapping("/addCourseInfo")
    public CommonResult addCourseInfo(@RequestBody CourseInfo courseInfo){
        String id = courseService.saveCourseInfo(courseInfo);
        //返回添加课程之后的课程ID,为后续添加章节信息操作使用
        return CommonResult.success().data("courseId",id);
    }

    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("/getCourseInfo/{courseId}")
    public CommonResult getCourseInfo(@PathVariable String courseId){
        CourseInfo courseInfo = courseService.getCourseInfoById(courseId);
        return CommonResult.success().data("courseInfo",courseInfo);
    }

    @ApiOperation("修改课程信息")
    @PutMapping("/updateCourseInfo")
    public CommonResult updateCourse(@RequestBody CourseInfo courseInfo){
        boolean flag = courseService.updateCourseInfo(courseInfo);
        if (!flag){
            throw new GuLiException(20001,"更新课程信息失败");
        }
        return CommonResult.success();
    }

    @ApiOperation("获取课程发布信息")
    @GetMapping("/getCoursePublisInfo/{courseId}")
    public CommonResult getCoursePublishInfo(@PathVariable String courseId){
        CoursePublishVo coursePublishInfo = courseService.getCoursePublishInfoById(courseId);
        return CommonResult.success().data("publishInfo",coursePublishInfo);
    }

    @ApiOperation("发布课程")
    @PutMapping("/publish/{courseId}")
    public CommonResult publishCourse(@PathVariable String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus(EduCourse.COURSE_NORMAL);
        courseService.updateById(eduCourse);
        return CommonResult.success();
    }
    @ApiOperation("课程列表")
    @GetMapping("/courseList")
    public CommonResult getCourseList(){
        List<EduCourse> list = courseService.list(null);
        return CommonResult.success().data("list",list);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("/deleteCourse/{courseId}")
    public CommonResult deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return CommonResult.success();
    }
    @ApiOperation("课程带条件分页查询")
    @PostMapping("/pageCondition/{pageNum}/{pageSize}")
    public CommonResult pageConditionCourseQuery(
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("pageSize") Integer pageSize,
            @RequestBody(required = false) CourseQuery courseQuery
            ){
        Page<EduCourse> eduCoursePage = new Page<>(pageNum,pageSize);
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        String subject = courseQuery.getSubject();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();
        if (!StringUtils.isEmpty(title)){
            queryWrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(status)){
            queryWrapper.eq("status",status);
        }
        if (!StringUtils.isEmpty(subject)){
            queryWrapper.eq("subject_parent_id",subject);
        }
        if (!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create",end);
        }
        queryWrapper.orderByDesc("gmt_modified");
        courseService.page(eduCoursePage, queryWrapper);
        long total = eduCoursePage.getTotal();
        List<EduCourse> rows = eduCoursePage.getRecords();
        return CommonResult.success().data("total",total).data("rows",rows);
    }

    /**
     * 根据课程id获取课程信息
     * @param id
     * @return
     */
    @PostMapping("/getCourseInfoForOrder/{id}")
    public CourseInfoForOrder getCourseInfoFroOrder(@PathVariable String id){
        CourseInfoForOrder courseInfoForOrder = new CourseInfoForOrder();
        CourseFrontDetail courseDetail = courseService.getCourseDetail(id);
        BeanUtils.copyProperties(courseDetail,courseInfoForOrder);
        return courseInfoForOrder;
    }
}

