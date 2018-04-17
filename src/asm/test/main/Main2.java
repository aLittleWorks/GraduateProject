package asm.test.main;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.FieldPosition;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import com.systools.FileOp;

import asm.core.method.StaticMethod;
import asm.tree.tools.ScanClassToGetAction;

public class Main2 {
	
	public static String rootpackage = "asm.testmodel.bankmodel";
	public static String rootpackage2 = "asm.testmodel";
	public static String rootdir = "C:/Users/CY/Workspaces/GraduateProject/bin/asm/testmodel/bankmodel/";
	public static String rootdir2 = "C:/Users/CY/Workspaces/GraduateProject/bin/asm/testmodel/";


	public static void main(String[] args) {
		Main2 main2 = new Main2();
		
		main2.run();
		
	}

	public void run() {
		File[] fileslist = FileOp.findFilesAtHere(rootdir2);
		FileOp.printFileList(rootdir2);
		Main2 m = new Main2();
		for(File file : fileslist){
			if(!file.isDirectory()){
				try {
					m.startScan(rootpackage2 + "." + file.getName().substring(0, file.getName().length()-6), rootdir2 + file.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	
		System.out.println("Main thread:..................");
		//µ÷ÓÃ
		Class<?> clazz;
		try {
			//clazz = Class.forName(rootpackage + ".Main_Entry");
			clazz = Class.forName(rootpackage2 + ".DeadLockType1");
			Method method = clazz.getMethod("run");
			method.invoke(clazz.newInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static boolean threadsEnable(){
		for(Thread thread : StaticMethod.threadList){
			if(thread.getState().equals(Thread.State.RUNNABLE)){
				return true;
			}
		}
		return false;
	}
	
	public void startScan(String classfullname, String filedir) throws Exception {
		ClassReader cr = new ClassReader(classfullname);
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		//ScanClassByTree s = new ScanClassByTree(cn);
		
		ScanClassToGetAction s = new ScanClassToGetAction(cn);
	
		//System.out.println(s.getInsCounter());
		
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		byte[] code = cw.toByteArray();
		
		File file = new File(filedir);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(code);
		fileOutputStream.close();
	
	}

}
