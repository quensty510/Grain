package com.quensty.eduService.controller.front;

import com.quensty.commonutils.CommonResult;
import com.quensty.eduService.entity.EduCourse;
import com.quensty.eduService.entity.EduTeacher;
import com.quensty.eduService.service.EduCourseService;
import com.quensty.eduService.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/25 15:22
 */
@RestController
@RequestMapping("/eduService/front/index")
//@CrossOrigin
public class indexFrontController {

    @Autowired
    EduCourseService courseService;
    @Autowired
    EduTeacherService teacherService;

    /**
     * 查询前八门热门课程和前四名热门老师
     * @return
     */
    @GetMapping("/getData")
    public CommonResult getHotCourseAndTeacher(){
        List<EduCourse> courseList = courseService.getHotCourseList();
        List<EduTeacher> teacherList = teacherService.getHotTeacherList();

        return CommonResult.success().data("courseList",courseList).data("teacherList",teacherList);
    }
}
