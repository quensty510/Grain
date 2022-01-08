package com.quensty.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quensty.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quensty.eduService.entity.frontVo.CourseFrontDetail;
import com.quensty.eduService.entity.frontVo.CourseFrontQueryObject;
import com.quensty.eduService.entity.vo.CourseInfo;
import com.quensty.eduService.entity.vo.CoursePublishVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息
     * @param courseInfo
     */
    String saveCourseInfo(CourseInfo courseInfo);

    CourseInfo getCourseInfoById(String courseId);

    boolean updateCourseInfo(CourseInfo courseInfo);

    CoursePublishVo getCoursePublishInfoById(String courseId);

    void removeCourse(String courseId);

    List<EduCourse> getHotCourseList();

    /**
     * 前台条件分页查询
     * @param coursePage
     * @param courseFrontQueryObject
     * @return
     */
    Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontQueryObject courseFrontQueryObject);

    /**
     * 前台根据课程id查询课程信息
     * @param courseId
     * @return
     */
    CourseFrontDetail getCourseDetail(String courseId);
}
