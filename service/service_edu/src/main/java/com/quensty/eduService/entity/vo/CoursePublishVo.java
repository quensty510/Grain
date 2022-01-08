package com.quensty.eduService.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/7 22:26
 */
@ApiModel(value = "课程发布信息")
@Data
public class CoursePublishVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String cover;
    private Integer lessonNum;
    private String primarySubject;
    private String secondarySubject;
    private String teacherName;
    private String price;
}
