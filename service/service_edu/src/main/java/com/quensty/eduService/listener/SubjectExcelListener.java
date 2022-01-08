package com.quensty.eduService.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quensty.eduService.entity.EduSubject;
import com.quensty.eduService.entity.excel.SubjectData;
import com.quensty.eduService.service.EduSubjectService;
import com.quensty.serviceBase.handler.GuLiException;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/2 11:46
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
//    SubjectExcelListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
    public EduSubjectService eduSubjectService;

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }
    public SubjectExcelListener() {
    }

    private String twoPid = "";
    @Override
    public void invoke(SubjectData data, AnalysisContext context) {
        if (eduSubjectService == null){
            throw new GuLiException(20001,"文件数据为空");
        }
        //一行一行读取，每次读取到了两个值，第一个为一级分类，第二个二级分类
        //添加一级分类
        EduSubject existOneSubject = this.existOneSubject(eduSubjectService,data.getOneSubjectName());
        if(existOneSubject == null) {//没有相同的
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(data.getOneSubjectName());
            existOneSubject.setParentId("0");
            eduSubjectService.save(existOneSubject);
        }

        //获取一级分类id值
        String pid = existOneSubject.getId();

        //添加二级分类
        EduSubject existTwoSubject = this.existTwoSubject(eduSubjectService,data.getTwoSubjectName(), pid);
        if(existTwoSubject == null) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(data.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            eduSubjectService.save(existTwoSubject);
        }
    }

    /**
     * 判断一级分类是否存在
     * @param name
     * @return
     */
    private EduSubject existOneSubject(EduSubjectService subjectService,String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }

    /**
     * 判断二级分类是否存在
     * @param name
     * @param pid
     * @return
     */
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject eduSubject = subjectService.getOne(wrapper);
        return eduSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
