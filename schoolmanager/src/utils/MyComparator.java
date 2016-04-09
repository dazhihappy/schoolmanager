package utils;

import java.util.Comparator;

import bean.SchoolInfo;
/*
 * 用于比较时间，安倒序排列
 */
public class MyComparator implements Comparator<SchoolInfo>{

	public int compare(SchoolInfo o1, SchoolInfo o2) {
		return o2.getDate().compareTo(o1.getDate());
	}
}
