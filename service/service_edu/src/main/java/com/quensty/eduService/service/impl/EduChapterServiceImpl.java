package com.quensty.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quensty.eduService.entity.EduChapter;
import com.quensty.eduService.entity.EduVideo;
import com.quensty.eduService.entity.vo.ChapterVo;
import com.quensty.eduService.entity.vo.VideoVo;
import com.quensty.eduService.mapper.EduChapterMapper;
import com.quensty.eduService.service.EduChapterService;
import com.quensty.eduService.service.EduVideoService;
import com.quensty.serviceBase.handler.GuLiException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //查询章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        List<EduChapter> chapters = baseMapper.selectList(chapterQueryWrapper);
        //查询小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        List<EduVideo> videos = videoService.list(videoQueryWrapper);
        //封装章节信息
        ArrayList<ChapterVo> chapterVos = new ArrayList<>();
        for (EduChapter chapter : chapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            //遍历小节，封装进chapterVos
            ArrayList<VideoVo> videoVos = new ArrayList<>();
            for (EduVideo video : videos) {
                if (video.getChapterId().equals(chapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setSection(videoVos);
            chapterVos.add(chapterVo);
        }
        return chapterVos;
    }

    /**
     * 删除章节，若该章节下有小节，需先删除小节
     * @param chapterId
     * @return
     */
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapter_id查询小节表
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id",chapterId);
        int count = videoService.count(videoQueryWrapper);
        if (count > 0){
            //有小节，不可删除
            throw new GuLiException(20001,"无法删除有小节的章节");
        }else {
            return this.removeById(chapterId);
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
