package utils;
import java.util.Random;

public class Util {
	public static String DoGetString(String str) throws Exception{
		byte [] bs = str.getBytes("ISO8859-1");
		return new String(bs,"UTF-8");
	}
	public static String GetOutActivityCategory(String x){
		if(x=="1"){
			return "讲座";
		}
		if(x=="2"){
			return "比赛";
		}
		if(x=="3"){
			return "公益";
		}
		if(x=="4"){
			return "志愿";
		}
		if(x=="5"){
			return "other";
		}
		return "";
	}
		
	public static String GetRandomString(int length) { 
	    StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"); 
	    StringBuffer sb = new StringBuffer(); 
	    Random random = new Random(); 
	    int range = buffer.length(); 
	    for (int i = 0; i < length; i ++) { 
	        sb.append(buffer.charAt(random.nextInt(range))); 
	    } 
	    return sb.toString(); 
	}
	
}
