package com.quensty.eduService.mapper;

import com.quensty.eduService.entity.EduSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quensty.eduService.entity.vo.SubjectTree;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
public interface EduSubjectMapper extends BaseMapper<EduSubject> {
    List<SubjectTree> getSubjectTree();
}
