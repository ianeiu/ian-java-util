package com.ianeiu.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @Description: List工具类
 * @author wm
 * @date 2018/12/27 17:13 
 * @version: 1.0
 */
public class ListUtils {

	/**
	 * ids转化为特殊ids
	 * @param ids  1111,2222
	 * @param regex ,
	 * @param appendRegex '
	 * @return '1111','2222'
	 */
	public static String getFIdsFromIds(String ids,String regex,String appendRegex){
		if("".equals(ids.trim())||ids.trim().equals(regex.trim())){
			return "";
		}
		
		List<String> idList = getListFromIds(ids, regex);
		
		StringBuffer result = new StringBuffer();
		for(String id : idList){
			result.append(appendRegex).append(id).append(appendRegex).append(regex);
		}
		//去掉最后一个 regex
		result.deleteCharAt(result.length() - 1);
		
		return result.toString();
	}
	
	/**
	 * ids转化为idList
	 * @param ids  1111,2222 或 1111 或 ,
	 * @param regex
	 * @return List
	 */
	public static List<String> getListFromIds(String ids,String regex){
		if("".equals(ids.trim())){
			return new ArrayList<String>();
		}
		
		List<String> idList = Arrays.asList(ids.split(regex));
		return idList;
	}
	
	/**
	 * idList转化为ids
	 * @param list
	 * @param regex
	 * @return ids  1111,1112
	 */
	public static String getIdsFromList(List<String> list,String regex){
		
		if(list.size()==0){
			return "";
		}
		StringBuffer ids = new StringBuffer();
		for(String id : list){
			ids.append(id).append(regex);
		}
		//去掉最后一个 regex
		ids.deleteCharAt(ids.length() - 1);
		
		return ids.toString();
	}
	
	/**
	 * idList转化为特殊ids
	 * @param list
	 * @param regex   ,
	 * @param regex2  '
	 * @return ids  '111','222'
	 */
	public static String getIdsFromList(List<String> list,String regex,String regex2){
		if(list.size()==0){
			return "";
		}
		StringBuffer ids = new StringBuffer();
		for(String id : list){
			ids.append(regex2).append(id).append(regex2).append(regex);
		}
		//去掉最后一个 regex
		ids.deleteCharAt(ids.length() - 1);
		
		return ids.toString();
	}


	/**
	 * 去除重复元素
	 * @param list
	 */
	public static void removeDuplicate(List list) {
		HashSet h = new HashSet(list);
		list.clear();
		list.addAll(h);
	}
	
	/**
	 * 将集合拆分成多个指定元素个数的集合
	 * @param <T>
	 * @param resList  要拆分的集合
	 * @param count  每个集合的元素个数
	 * @return 返回拆分后的各个集合
	 */
	public static <T> List<List<T>> split(List<T> resList, int count) {
		if (resList == null || count < 1) {
			return null;
		}
		List<List<T>> ret = new ArrayList<List<T>>();
		int size = resList.size();
		// 数据量不足count指定的大小
		if (size <= count) {
			ret.add(resList);
		} else {
			int pre = size / count;
			int last = size % count;
			// 前面pre个集合，每个大小都是count个元素
			for (int i = 0; i < pre; i++) {
				List<T> itemList = new ArrayList<T>();
				for (int j = 0; j < count; j++) {
					itemList.add(resList.get(i * count + j));
				}
				ret.add(itemList);
			}
			// last的进行处理
			if (last > 0) {
				List<T> itemList = new ArrayList<T>();
				for (int i = 0; i < last; i++) {
					itemList.add(resList.get(pre * count + i));
				}
				ret.add(itemList);
			}
		}
		return ret;
	}
	
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("111");
		list.add("111");
		list.add("111");
		list.add("111");
		
		System.out.println(getIdsFromList(list, ",","'"));
	}
	
}
