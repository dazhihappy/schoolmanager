package utils;

import java.util.Comparator;

import bean.SchoolInfo;
/*
 * ���ڱȽ�ʱ�䣬����������
 */
public class MyComparator implements Comparator<SchoolInfo>{

	public int compare(SchoolInfo o1, SchoolInfo o2) {
		return o2.getDate().compareTo(o1.getDate());
	}
}
