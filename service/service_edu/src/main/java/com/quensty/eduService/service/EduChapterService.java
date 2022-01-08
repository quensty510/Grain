package com.quensty.eduService.service;

import com.quensty.eduService.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quensty.eduService.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 根据课程id查询课程大纲列表
     * @param courseId
     * @return
     */
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
