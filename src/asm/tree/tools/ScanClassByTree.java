package asm.tree.tools;

import java.util.ArrayList;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

/*
 * 
 * 扫描一个class的字节码
 * 获取该class中所有的方法名
 * 并为所有的指令计数
 * 
 * this is a test class
 */


public class ScanClassByTree {
	private ClassNode cn;
	private int methodCount = 0;
	private int insCount = 0;
	
	private int monitorCount = 0;
	private int monitorExitCount = 0;
	
	private int lockActionCount = 0;
	
	private ArrayList<MethodNode> MethodList;
	private ArrayList<Integer> OpcodesList;
	
	
	
	public ScanClassByTree(ClassNode cn) {
		super();
		this.cn = cn;
		MethodList = new ArrayList<MethodNode>();
		OpcodesList = new ArrayList<Integer>();
		ScanClassMethods();
	}

	public void ScanClassMethods(){
		
		//Iterator<MethodNode> iterator = cn.methods.iterator();

		for(int i=0; i<cn.methods.size(); i++){
			MethodNode mn = (MethodNode)  cn.methods.get(i);
			methodCount ++;
			MethodList.add(mn);
			//MethodNameList.add(cn.methods.get(i).);
			ScanInsofMethod(mn);
		}
	}
	
	private void getThreadMsgTest(MethodNode mn, int i){
		//System.out.println("Getting current thread message..." + " at " + mn.name);
		
		AbstractInsnNode nmn = new MethodInsnNode(
				Opcodes.INVOKESTATIC, 
				"asm/core/method/StaticMethod", 
				"addThreadToList", 
				"()V"
		);
		mn.instructions.insert(mn.instructions.get(i), nmn);
		
	}
	
	private void ScanInsofMethod(MethodNode mn) {
		//Iterator<InsnNode> iterator = mn.instructions.iterator();
		
		for(int i=0; i < mn.instructions.size(); i++){
			insCount ++;
			OpcodesList.add(mn.instructions.get(i).getOpcode());
			
			int currentOpcode = mn.instructions.get(i).getOpcode();
			
			if(currentOpcode == Opcodes.INVOKEINTERFACE && 
					mn.instructions.get(i).getPrevious().getOpcode() == Opcodes.GETFIELD &&
					mn.instructions.get(i).getNext().getOpcode() != Opcodes.PUTFIELD){
				//由于找不到获取指令操作数，这样是否就是一个lock或者unlock的事件？
				lockActionCount ++;
				MethodInsnNode nmn = new MethodInsnNode(
						Opcodes.INVOKESTATIC, 
						"asm/core/method/CheckMethod", 
						"lockaction", 
						"()V"
					);
				mn.instructions.insert(mn.instructions.get(i), nmn);
				getThreadMsgTest(mn, i);
			}
			
			
			
			if(currentOpcode == Opcodes.MONITORENTER){
				monitorCount ++;
				//mn.visitMethodInsn(Opcodes.INVOKESTATIC, "asm/core/method/CheckMethod", "acqmsg", "()V");
				MethodInsnNode nmn = new MethodInsnNode(
						Opcodes.INVOKESTATIC, 
						"asm/core/method/CheckMethod", 
						"acqmsg", 
						"()V"
					);
				mn.instructions.insert(mn.instructions.get(i), nmn);
				getThreadMsgTest(mn, mn.instructions.get(i).getOpcode());
			}
			if(currentOpcode == Opcodes.MONITOREXIT){
				/*
				 *  ALOAD 2
    			 *	MONITOREXIT
   				 *	L1
    			 *	GOTO L16
   				 *	L2
   				 *	FRAME FULL [TestModel$1 Object Object] [Throwable]
    			 *	ALOAD 2
    			 *	MONITOREXIT
				 */
				if(mn.instructions.get(i).getPrevious().getPrevious().getPrevious().getOpcode() != Opcodes.GOTO){
					monitorExitCount ++;
					//mn.visitMethodInsn(Opcodes.INVOKESTATIC, "asm/core/method/CheckMethod", "relmsg", "()V");
					MethodInsnNode nmn = new MethodInsnNode(
							Opcodes.INVOKESTATIC, 
							"asm/core/method/CheckMethod", 
							"relmsg", 
							"()V"
						);
					mn.instructions.insert(mn.instructions.get(i), nmn);
				}
			}
		}
	}
	
	public void printMethodsName(){
		for(int i=0; i<MethodList.size(); i++){
			System.out.print(MethodList.get(i).name + "; ");
		}
	}
	
	public void printInsOpcodes(){
		for(int i=0; i<OpcodesList.size(); i++){
			System.out.println(OpcodesList.get(i)+ "; ");
		}
	}

	public ArrayList<MethodNode> getMethodList() {
		return MethodList;
	}

	public ArrayList<Integer> getOpcodesList() {
		return OpcodesList;
	}

	public int getMethodCount() {
		return methodCount;
	}

	public int getInsCount() {
		return insCount;
	}

	public int getMonitorCount() {
		return monitorCount;
	}

	public int getMonitorExitCount() {
		return monitorExitCount;
	}

	public int getLockActionCount() {
		return lockActionCount;
	}
	
	
}
