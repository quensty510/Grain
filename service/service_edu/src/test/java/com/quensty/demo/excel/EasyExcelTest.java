package com.quensty.demo.excel;

import com.alibaba.excel.EasyExcel;
import com.quensty.demo.excel.DemoData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/2 10:49
 */
public class EasyExcelTest {
    @Test
    public void write(){
        //实现excel写操作
        //设置写入文件夹地址和excel文件名
        String fileName = "E:\\CODE\\workSpace_IDEA\\guli_parent\\service\\service_edu\\src\\test\\resources\\easyExcelTest.xls";

        //调用EasyExcel中的方法实现写操作
        EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(getListData());
    }


    @Test
    public void read(){
        //实现excel读操作
        //设置写入文件夹地址和excel文件名
        String fileName = "E:\\CODE\\workSpace_IDEA\\guli_parent\\service\\service_edu\\src\\test\\resources\\easyExcelTest.xls";
        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();
    }

    private static List<DemoData> getListData(){
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i + 1);
            data.setSname("来quensty" + i + 1);
            list.add(data);
        }
        return list;
    }
}
