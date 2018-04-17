package asm.test.main;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import com.systools.FileOp;

import asm.core.method.StaticMethod;
import asm.tree.tools.ScanClassByTree;
import asm.tree.tools.ScanClassToGetAction;

public class Main {
	
	public static String rootpackage = "asm.testmodel.bankmodel";
	public static String rootpackage2 = "asm.testmodel";
	public static String rootdir = "C:/Users/CY/Workspaces/GraduateProject/bin/asm/testmodel/bankmodel/";
	public static String rootdir2 = "C:/Users/CY/Workspaces/GraduateProject/bin/asm/testmodel/";

	public static void main(String[] args) throws Exception {
		Main.run();
	}
	
	public static void run() throws Exception{
		
		File[] fileslist = FileOp.findFilesAtHere(rootdir);
		FileOp.printFileList(rootdir);
		Main m = new Main();
		for(File file : fileslist){
			if(!file.isDirectory()){
				//System.out.println(rootdir + file.getName());
				//System.out.println(rootpackage + "." + file.getName().substring(0, file.getName().length()-6));
				m.startScan(rootpackage + "." + file.getName().substring(0, file.getName().length()-6), rootdir + file.getName());
			}
		}	
		
		//µ÷ÓÃ
		Class<?> clazz = Class.forName(rootpackage + ".Main_Entry");
		//Class<?> clazz = Class.forName(rootpackage2 + ".DeadLockType1");
		Method method = clazz.getMethod("run");
		method.invoke(clazz.newInstance());
		
	}
	
	public void startScan(String classfullname, String filedir) throws Exception {
		ClassReader cr = new ClassReader(classfullname);
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		//ScanClassByTree s = new ScanClassByTree(cn);
		
		ScanClassToGetAction s = new ScanClassToGetAction(cn);
		
		//s.printMethodsName();
		//System.out.println(s.getInsCounter());
		//System.out.println("MONITORENTOR: " + s.getMonitorCount());
		//System.out.println("MONITOREXIT: " + s.getMonitorExitCount());
		//System.out.println("LOCKACTION: " + s.getLockActionCount());
		
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		byte[] code = cw.toByteArray();
		
		File file = new File(filedir);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(code);
		fileOutputStream.close();
	
	}
	
}
