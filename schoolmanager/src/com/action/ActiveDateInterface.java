package com.action;

import java.util.ArrayList;

import bean.UserCount;

public interface ActiveDateInterface {
	public ArrayList<UserCount> getData(String time , int page) throws Exception; 
}
