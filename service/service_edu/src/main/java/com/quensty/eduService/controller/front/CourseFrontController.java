package com.quensty.eduService.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quensty.commonutils.CommonResult;
import com.quensty.commonutils.JwtUtils;
import com.quensty.eduService.client.OrderClient;
import com.quensty.eduService.entity.EduCourse;
import com.quensty.eduService.entity.frontVo.CourseFrontDetail;
import com.quensty.eduService.entity.frontVo.CourseFrontQueryObject;
import com.quensty.eduService.entity.vo.ChapterVo;
import com.quensty.eduService.service.EduChapterService;
import com.quensty.eduService.service.EduCourseService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/12/30 12:37
 */
@RestController
@RequestMapping("/eduService/course/front")
//@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private OrderClient orderClient;

    /**
     * 条件查询带分页
     * @param pageNum
     * @param pageSize
     * @param courseFrontQueryObject
     * @return
     */
    @PostMapping("/getFrontCourseList/{pageNum}/{pageSize}")
    public CommonResult getCoursesList(@PathVariable long pageNum, @PathVariable long pageSize,
                                            @RequestBody(required = false) CourseFrontQueryObject courseFrontQueryObject){
        Page<EduCourse> coursePage = new Page<>(pageNum, pageSize);
        Map<String,Object> map = courseService.getCourseFrontList(coursePage, courseFrontQueryObject);
        return CommonResult.success().data(map);
    }

    /**
     * 根据课程id查询课程及章节信息
     * @param courseId
     * @return
     */
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public CommonResult getCourseInfo(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId,
            HttpServletRequest request){
        CourseFrontDetail courseDetail = courseService.getCourseDetail(courseId);
        List<ChapterVo> chapterVideoList= chapterService.getChapterVideoByCourseId(courseId);

        //根据课程id和用户id查询课程购买状态
        boolean isPurchased = false;
        try {
            isPurchased = orderClient.isPurchased(courseId, JwtUtils.getMemberIdByJwtToken(request));
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.success().data("courseDetail",courseDetail).data("chapterVideoList",chapterVideoList);
        }

        return CommonResult.success().data("courseDetail",courseDetail).data("chapterVideoList",chapterVideoList).data("isPurchased",isPurchased);
    }
}
