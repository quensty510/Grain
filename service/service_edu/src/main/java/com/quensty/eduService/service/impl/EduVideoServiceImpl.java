package com.quensty.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quensty.eduService.client.VodClient;
import com.quensty.eduService.mapper.EduVideoMapper;
import com.quensty.eduService.entity.EduVideo;
import com.quensty.eduService.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    VodClient vodClient;

    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId).select("video_source_id");
        List<EduVideo> videoIdList = baseMapper.selectList(videoWrapper);
        List<String> videoId = videoIdList.stream().filter(str -> !StringUtils.isEmpty(str)).map(EduVideo::getVideoSourceId).collect(Collectors.toList());
        vodClient.deleteBatch(videoId);

        QueryWrapper<EduVideo> courseWrapper = new QueryWrapper<>();
        courseWrapper.eq("course_id",courseId);
        baseMapper.delete(courseWrapper);
    }
}
