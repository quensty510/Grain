package com.quensty.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quensty.eduService.entity.EduSubject;
import com.quensty.eduService.entity.excel.SubjectData;
import com.quensty.eduService.entity.vo.SubjectTree;
import com.quensty.eduService.listener.SubjectExcelListener;
import com.quensty.eduService.mapper.EduSubjectMapper;
import com.quensty.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Autowired
    EduSubjectMapper eduSubjectMapper;

    @Override
    public void saveSubject(MultipartFile file) {
        try (InputStream fileInputStream = file.getInputStream()) {
            EasyExcel.read(fileInputStream, SubjectData.class,new SubjectExcelListener(this)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SubjectTree> getSubjectList() {
        return eduSubjectMapper.getSubjectTree();
    }
}
