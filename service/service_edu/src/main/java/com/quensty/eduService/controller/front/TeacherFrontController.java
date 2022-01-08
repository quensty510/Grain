package com.quensty.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quensty.commonutils.CommonResult;
import com.quensty.eduService.entity.EduCourse;
import com.quensty.eduService.entity.EduTeacher;
import com.quensty.eduService.service.EduCourseService;
import com.quensty.eduService.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/12/29 11:26
 */
@RestController
@RequestMapping("/eduService/teacher/front")
//@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    /**
     * 分页查询讲师列表
     * @return
     */
    @PostMapping("/getFrontTeacher/{pageNum}/{pageSize}")
    public CommonResult getTeacherList(@PathVariable long pageNum,@PathVariable long pageSize){
        Page<EduTeacher> pageParam = new Page<EduTeacher>(pageNum, pageSize);
        Map<String,Object> map = teacherService.getTeacherFrontList(pageParam);
        return CommonResult.success().data(map);
    }

    /**
     * 获取讲师详情及其课程信息
     * @param teacherId
     * @return
     */
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public CommonResult getTeacherFrontInfo(@PathVariable long teacherId){
        EduTeacher teacher = teacherService.getById(teacherId);
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = courseService.list(courseQueryWrapper);
        return CommonResult.success().data("teacher",teacher).data("courseList",courseList);
    }

}
