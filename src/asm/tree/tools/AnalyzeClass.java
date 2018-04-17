package asm.tree.tools;

import java.awt.Frame;
import java.util.List;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.Interpreter;
import org.objectweb.asm.tree.analysis.Value;

public class AnalyzeClass {

	public void run(){
		
	}
	

}

class MyAnalyzer extends Analyzer {

	public MyAnalyzer(Interpreter arg0) {
		super(arg0);
	}
	
}