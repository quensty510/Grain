package com.quensty.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/2 11:12
 */
public class ExcelListener extends AnalysisEventListener<DemoData> {
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头：" + headMap);
    }

    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        System.out.println("*****" + data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
