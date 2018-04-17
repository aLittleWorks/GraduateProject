package asm.tree.tools;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ScanClassByCore {
	
	public void ScanClass(String classfullname, String filedir) throws IOException{
		ClassReader cr = new ClassReader(classfullname);
		int sta = cr.getItem(0);
		System.out.println(sta);
		int cons = cr.getItemCount(); //Returns the number of constant pool 
		System.out.println(cons);
		
		System.out.println("header: " + cr.header);
		
	}
	
	
	
}
