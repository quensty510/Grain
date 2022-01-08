package com.quensty.eduService.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quensty.eduService.entity.EduComment;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
public interface EduCommentService extends IService<EduComment> {

    /**
     * 根据课程id获取评论
     * @param courseId
     * @return
     */
    IPage<EduComment> getCommentsByCoursesId(String courseId, long pageNum, long pageSize);

    boolean insertComment(EduComment comment);
}
