package asm.tree.tools;

import java.io.File;
import java.io.FileOutputStream;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import com.systools.FileOp;


public class testmain {
	
	public static String rootpackage = "asm.testmodel.bankmodel";
	public static String rootpackage2 = "asm.testmodel";
	public static String rootdir = "C:/Users/CY/Workspaces/GraduateProject/bin/asm/testmodel/bankmodel/";
	public static String rootdir2 = "C:/Users/CY/Workspaces/GraduateProject/bin/asm/testmodel/";

	public static void main(String[] args) {
		File[] fileslist = FileOp.findFilesAtHere(rootdir2);
		FileOp.printFileList(rootdir2);
		testmain m = new testmain();
		for(File file : fileslist){
			if(!file.isDirectory()){
				try {
					m.startScan(rootpackage2 + "." + file.getName().substring(0, file.getName().length()-6), rootdir2 + file.getName());
					//new ScanClassByCore().ScanClass(rootpackage2 + "." + file.getName().substring(0, file.getName().length()-6), rootdir2 + file.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	
	}
	
	
	
	public void startScan(String classfullname, String filedir) throws Exception {
		ClassReader cr = new ClassReader(classfullname);
		ClassNode cn = new ClassNode();
		cr.accept(cn, 0);
		
		TreeScanTest1 s = new TreeScanTest1(cn);

		
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cn.accept(cw);
		byte[] code = cw.toByteArray();
		
		/*File file = new File(filedir);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(code);
		fileOutputStream.close();*/
	
	}

}
