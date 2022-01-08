package com.quensty.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.quensty.eduService.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-29
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> getHotTeacherList();

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageParam);
}
