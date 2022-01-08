package com.quensty.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quensty.eduService.entity.EduComment;
import com.quensty.eduService.mapper.EduCommentMapper;
import com.quensty.eduService.service.EduCommentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {


    @Override
    public IPage<EduComment> getCommentsByCoursesId(String courseId, long pageNum, long pageSize) {
        Page<EduComment> commentPage = new Page<EduComment>(pageNum, pageSize);
        QueryWrapper<EduComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId).orderByDesc("gmt_modified");
        IPage<EduComment> eduCommentIPage = baseMapper.selectPage(commentPage, queryWrapper);
        return eduCommentIPage;
    }

    @Override
    public boolean insertComment(EduComment comment) {
        return this.save(comment);
    }
}
