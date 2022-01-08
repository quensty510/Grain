package com.quensty.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quensty.eduService.entity.EduCourse;
import com.quensty.eduService.entity.EduCourseDescription;
import com.quensty.eduService.entity.frontVo.CourseFrontDetail;
import com.quensty.eduService.entity.frontVo.CourseFrontQueryObject;
import com.quensty.eduService.entity.vo.CourseInfo;
import com.quensty.eduService.entity.vo.CoursePublishVo;
import com.quensty.eduService.mapper.EduCourseMapper;
import com.quensty.eduService.service.EduChapterService;
import com.quensty.eduService.service.EduCourseDescriptionService;
import com.quensty.eduService.service.EduCourseService;
import com.quensty.eduService.service.EduVideoService;
import com.quensty.serviceBase.handler.GuLiException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduCourseMapper courseMapper;

    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private EduCourseDescriptionService descriptionService;


    @Override
    public String saveCourseInfo(CourseInfo courseInfo) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setStatus(EduCourse.COURSE_DRAFT);
        BeanUtils.copyProperties(courseInfo,eduCourse);
        eduCourse.setIsDeleted(false);
        //1、向课程表添加信息
        boolean saveFlag = this.save(eduCourse);
        if (!saveFlag){
            throw new GuLiException(20001,"添加课程信息失败");
        }
        String descriptionId = eduCourse.getId();
        //2、向课程简介表添加信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfo.getDescription());
        //设置课程简介的id
        eduCourseDescription.setId(descriptionId);
        courseDescriptionService.save(eduCourseDescription);
        return eduCourse.getId();
    }

    @Override
    public CourseInfo getCourseInfoById(String courseId) {
        //课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfo courseInfo = new CourseInfo();
        BeanUtils.copyProperties(eduCourse,courseInfo);

        //课程描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        if(courseDescription != null){
            courseInfo.setDescription(courseDescription.getDescription());
        }
        return courseInfo;
    }

    @Override
    public boolean updateCourseInfo(CourseInfo courseInfo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo,eduCourse);
        boolean courseflag = this.updateById(eduCourse);

        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseDescription.getDescription());
        courseDescription.setId(eduCourse.getId());
        boolean descriptionFlag = courseDescriptionService.updateById(courseDescription);
        return (courseflag && descriptionFlag);
    }

    @Override
    public CoursePublishVo getCoursePublishInfoById(String courseId) {
        return courseMapper.getCoursePublishInfo(courseId);
    }

    @Override
    public void removeCourse(String courseId) {
        //删除小节
        videoService.removeVideoByCourseId(courseId);
        //删除章节
        chapterService.removeChapterByCourseId(courseId);
        //删除课程描述
        descriptionService.removeById(courseId);
        //删除课程
        int flag = baseMapper.deleteById(courseId);
        if(flag == 0){
            throw new GuLiException(20001,"删除失败");
        }
    }

    @Override
    @Cacheable(value = "course",key = "'selectHotCourse'")
    public List<EduCourse> getHotCourseList() {
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id").last("limit 8");
        return baseMapper.selectList(courseQueryWrapper);
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontQueryObject courseFrontQueryObject) {
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontQueryObject.getSubjectParentId())) {
            courseQueryWrapper.eq("subject_parent_id", courseFrontQueryObject.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseFrontQueryObject.getSubjectId())) {
            courseQueryWrapper.eq("subject_id", courseFrontQueryObject.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseFrontQueryObject.getBuyCountSort())) {
            courseQueryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseFrontQueryObject.getGmtCreateSort())) {
            courseQueryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontQueryObject.getPriceSort())) {
            courseQueryWrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam,courseQueryWrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseFrontDetail getCourseDetail(String courseId) {
        return baseMapper.getCourseDetail(courseId);
    }
}
