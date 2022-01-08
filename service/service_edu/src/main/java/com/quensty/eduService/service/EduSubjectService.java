package com.quensty.eduService.service;

import com.quensty.eduService.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.quensty.eduService.entity.vo.SubjectTree;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     * @param file
     */
    void saveSubject(MultipartFile file);

    List<SubjectTree> getSubjectList();
}
