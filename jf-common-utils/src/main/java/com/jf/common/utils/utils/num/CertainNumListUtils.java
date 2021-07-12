package com.jf.common.utils.utils.num;

import java.util.*;

/**
 * 每次从list中获取一定数量的数据
 * 工具类
 *
 * @author 江峰
 * @since 2021/7/12
 */
public class CertainNumListUtils<T> {


    /**
     * 将list里面的数据，按照每limit条数据放到map的value中，key为当前次数。
     *
     * @param list
     * @param limit
     * @param <T>
     * @return
     */
    public static <T> Map<Integer, List<T>> getCertainNumMap(List<T> list, Integer limit) {

        Map<Integer, List<T>> map = new HashMap();

        int index = 0;
        for (int i = 0; i < list.size(); i = i + limit) {
            List<T> tempList = getCertainNumDataFromList(list, i, limit);
            map.put(index++, tempList);
        }

        return map;
    }

    /**
     * 从指定位置开始，从list里面获取一定数量的数据。
     *
     * @param list
     * @param offet list索引下标
     * @param limit 每次从list里面获取数据的数量
     * @return
     */
    public static <T> List<T> getCertainNumDataFromList(List<T> list, Integer offet, Integer limit) {

        if (Objects.isNull(list) || list.size() == 0) {
            return Collections.emptyList();
        }

        int len = list.size();
        int fromIndex = offet;
        int toIndex = offet + limit;
        if (toIndex > len) {
            toIndex = len;
        }

        return list.subList(fromIndex, toIndex);

    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        int limit = 2;
        Map<Integer, List<Integer>> certainNumMap = getCertainNumMap(list, limit);
        System.out.println(certainNumMap);

    }
}
