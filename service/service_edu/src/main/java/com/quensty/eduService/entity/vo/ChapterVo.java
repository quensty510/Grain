package com.quensty.eduService.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/6 10:45
 */
@Data
public class ChapterVo {
    private String id;
    private String title;
    private List<VideoVo> section;
}
