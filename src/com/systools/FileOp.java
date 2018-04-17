package com.systools;

import java.io.File;

public class FileOp {
	public static File[] findFilesAtHere(String rootDir_){
		File tempf = new File(rootDir_);
		if(tempf.isDirectory()){
			File[] c = tempf.listFiles();
			
			/*
			for(int i=0; i<c.length; i++){
				System.out.println(c[i]);
			}
			*/
			return c;
		}else{
			return null;
		}
	}
	
	public static void printFileList(String rootDir_){
		File[] files = FileOp.findFilesAtHere(rootDir_);
		if(files.length > 0){
			for(int i=0; i<files.length; i++){
				System.out.println(files[i].getName());
			}
			
		}else{
			System.out.println("Empty!");
		}
	}
}
