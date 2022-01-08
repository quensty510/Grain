package com.quensty.eduService.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.quensty.commonutils.CommonResult;
import com.quensty.eduService.entity.EduComment;
import com.quensty.eduService.service.EduCommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
@RestController
@RequestMapping("/eduService/edu-comment")
//@CrossOrigin
public class EduCommentController {
    @Autowired
    EduCommentService commentService;

    @ApiOperation("评论分页查询")
    @GetMapping("/getComments/{courseId}/{pageNum}/{pageSize}")
    public CommonResult getComments(@PathVariable String courseId,@PathVariable long pageNum,@PathVariable long pageSize){
        IPage<EduComment> page = commentService.getCommentsByCoursesId(courseId,pageNum,pageSize);
        return CommonResult.success().data("commentPage",page);
    }

    @ApiOperation("发表评论")
    @PostMapping("/publishComment")
    public CommonResult publishComment(@RequestBody EduComment comment){
        boolean flag = commentService.insertComment(comment);
        if(flag){
            return CommonResult.success();
        }else {
            return CommonResult.failed().message("发表失败");
        }
    }
}

