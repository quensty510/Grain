package com.quensty.eduService.controller;


import com.quensty.commonutils.CommonResult;
import com.quensty.eduService.entity.EduChapter;
import com.quensty.eduService.entity.vo.ChapterVo;
import com.quensty.eduService.service.EduChapterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/eduService/edu-chapter")
//@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    @ApiOperation("获取章节、小节列表")
    @GetMapping("/getChapterVideo/{courseId}")
    public CommonResult getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return CommonResult.success().data("allChapterVideo", list);
    }

    @ApiOperation("添加章节")
    @PostMapping("/addChapter")
    public CommonResult addChpater(@RequestBody EduChapter chapter) {
        chapterService.save(chapter);
        return CommonResult.success();
    }

    @ApiOperation("根据id获取章节信息")
    @GetMapping("/getChapterInfo/{chapterId}")
    public CommonResult getChapterInfo(@PathVariable String chapterId) {
        EduChapter chapter = chapterService.getById(chapterId);
        return CommonResult.success().data("chapter",chapter);
    }

    @ApiOperation("修改章节信息")
    @PutMapping("/updateChapter")
    public CommonResult updateChapter(@RequestBody EduChapter chapter) {
        chapterService.updateById(chapter);
        return CommonResult.success();
    }

    @ApiOperation("删除章节")
    @DeleteMapping("/deleteChapter/{chapterId}")
    public CommonResult deleteChapter(@PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        if (flag) {
            return CommonResult.success();
        }else {
            return CommonResult.failed();
        }
    }

}

