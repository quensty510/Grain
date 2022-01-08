package com.quensty.eduService.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/2 13:45
 */
@Data
public class SubjectTree {
    private String id;
    private String title;
    private List<SubjectTree> children;
}
