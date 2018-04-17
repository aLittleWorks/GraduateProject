package test;

import java.util.ArrayList;

public class statictest1 {
	public static ArrayList<Integer> arrayList = new ArrayList<Integer>();
	public static void addInteger(int i){
		if(!arrayList.contains(i)){
			arrayList.add(i);
		}
	}
}
