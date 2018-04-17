package asm.tree.tools;

import java.awt.dnd.DnDConstants;
import java.util.List;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.Frame;
import org.objectweb.asm.tree.analysis.Interpreter;
import org.objectweb.asm.tree.analysis.Value;

public class TreeScanTest1 {
	private ClassNode cn;

	public TreeScanTest1(ClassNode cn) {
		super();
		this.cn = cn;
		System.out.println("====class message=====" + cn.name + "===========");
		for(int i=0; i<cn.fields.size(); i++){
			FieldNode fn = (FieldNode) cn.fields.get(i);
			System.out.println( i + 
			"| des: " + fn.desc + " | " + 
			"name: " + fn.name + " | " + 
			"sig: " + fn.signature + " | " + 
			"value: " + fn.value + " | " + 
			"access: " + fn.access + " | "
			);
		}
		if(!cn.attrs.isEmpty()){
		for(int i=0; i<cn.attrs.size(); i++){
			Attribute attribute = (Attribute) cn.attrs.get(i);
			System.out.println(attribute.type);
		}
		}
		
		for(int i=0; i<cn.invisibleAnnotations.size(); i++){
			AnnotationNode annotationNode = (AnnotationNode) cn.invisibleAnnotations.get(i);
			System.out.println(annotationNode.desc);
		}
		
		System.out.println("-------interface---------");
		for(int i=0; i<cn.interfaces.size(); i++){
			System.out.print(cn.interfaces.get(i) + " ");
		}
		System.out.println();
		System.out.println("---supername---" + cn.superName + "-----");
		
		//System.out.println("-------------methods message of the classs-------------");
		for(int i=0; i<cn.methods.size(); i++){
			MethodNode mn = (MethodNode) cn.methods.get(i);
			ScanMethods(mn);
		}
		
	}
	
	private void ScanMethods(MethodNode mn){
		//System.out.println("................" + mn.name + "................");
		MethodInsnNode newMIN2 = new MethodInsnNode(
				Opcodes.INVOKESTATIC, 
				"asm/core/method/StaticMethod", 
				"printCallStack", 
				"()V");
		mn.instructions.insert(mn.instructions.get(0),newMIN2);
	
		
		/*
		System.out.println("------localvariables------");
		for(int i=0; i<mn.localVariables.size(); i++){
			LocalVariableNode ln = (LocalVariableNode) mn.localVariables.get(i);
			System.out.println(
			"| des: " + ln.desc + " | " + 
			"end: " + ln.end.getOpcode() + " | " + 
			"index: " + ln.index + " | " + 
			"name: " + ln.name + " | " + 
			"sig: " + ln.signature + " | " + 
			"start: " + ln.start.getOpcode()
			);
		}
		*/
		
		
		//System.out.println("------instructions------");
	/*	for(int i=0; i<mn.instructions.size(); i++){
			int ins = mn.instructions.get(i).getOpcode();
			if(ins==Opcodes.INVOKEDYNAMIC || 
				ins==Opcodes.INVOKEINTERFACE || 
				ins==Opcodes.INVOKESPECIAL || 
				ins==Opcodes.INVOKESTATIC || 
				ins==Opcodes.INVOKEVIRTUAL){
				MethodInsnNode newMIN2 = new MethodInsnNode(
						Opcodes.INVOKESTATIC, 
						"asm/core/method/StaticMethod", 
						"printCallStack", 
						"()V");
				mn.instructions.insert(mn.instructions.get(i-1),newMIN2);
				++ i;
			}
		}*/

		
	}	
	
}


class MyInterpreter extends Interpreter{

	protected MyInterpreter(int api) {
		super(api);
	}

	@Override
	public Value binaryOperation(AbstractInsnNode insn, Value value1, Value value2) throws AnalyzerException {
		//Interprets a bytecode instruction with two arguments.
		
		return null;
	}

	@Override
	public Value copyOperation(AbstractInsnNode arg0, Value arg1) throws AnalyzerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value merge(Value arg0, Value arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value naryOperation(AbstractInsnNode arg0, List arg1) throws AnalyzerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value newOperation(AbstractInsnNode arg0) throws AnalyzerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value newValue(Type arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void returnOperation(AbstractInsnNode arg0, Value arg1, Value arg2) throws AnalyzerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Value ternaryOperation(AbstractInsnNode arg0, Value arg1, Value arg2, Value arg3) throws AnalyzerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value unaryOperation(AbstractInsnNode arg0, Value arg1) throws AnalyzerException {
		// TODO Auto-generated method stub
		return null;
	}
	
}

