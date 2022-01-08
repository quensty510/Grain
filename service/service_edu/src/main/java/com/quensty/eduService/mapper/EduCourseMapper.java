package com.quensty.eduService.mapper;

import com.quensty.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quensty.eduService.entity.frontVo.CourseFrontDetail;
import com.quensty.eduService.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
        /**
         * 根据课程id查询课程信息用以发布课程时的数据回显
         * @param courseId
         * @return
         */
        CoursePublishVo getCoursePublishInfo(String courseId);

        CourseFrontDetail getCourseDetail(String courseId);
}
