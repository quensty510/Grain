package com.quensty.demo;

import com.quensty.eduService.EduApplication;
import com.quensty.eduService.entity.EduCourse;
import com.quensty.eduService.entity.EduCourseDescription;
import com.quensty.eduService.entity.EduTeacher;
import com.quensty.eduService.entity.vo.CourseInfo;
import com.quensty.eduService.entity.vo.SubjectTree;
import com.quensty.eduService.mapper.EduSubjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/2 14:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EduApplication.class)
public class MapperTest {

    @Autowired
    EduSubjectMapper eduSubjectMapper;
    @Test
    public void getSubjectTreeTest (){
        for (SubjectTree subjectTree : eduSubjectMapper.getSubjectTree()) {
            System.out.println(subjectTree);
        }

    }

    @Test
    public void mpAutoWireTest(){
        EduTeacher eduTeacher = new EduTeacher();
        EduCourse eduCourse = new EduCourse();
        System.out.println("teacher:" + eduTeacher.getIsDeleted());
        System.out.println("teacher sort:" + eduTeacher.getSort());
        System.out.println("course:" + eduCourse.getIsDeleted());
    }

    @Test
    public void test01(){
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setId("123");
        courseInfo.setDescription("描述");
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo,eduCourse);
        System.out.println(eduCourse);
        EduCourseDescription courseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfo,courseDescription);
        System.out.println(courseDescription);
    }
}
