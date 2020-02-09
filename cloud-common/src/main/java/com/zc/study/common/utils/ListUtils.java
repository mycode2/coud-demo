package com.zc.study.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @version v1.0
 * @ProjectName: cloud-common
 * @ClassName: ListUtils
 * @Description: TODO(List工具类)
 * @Author: zhouchao
 * @Date: 2020/2/9 22:15
 */
public class ListUtils {

    /**
     * 当object为空时，返回一个容量为0的ArrayList。用于for循环中使用，取代判空的语句，可以缩减代码层次。
     * @param object
     * @param <T>
     * @return
     */
    public static <T> List<T> nvlList(List<T> object) {
        return null == object ? new ArrayList<T>(0) : object;
    }
}
