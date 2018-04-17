package asm.tree.tools;


import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;



import asm.core.method.StaticMethod;
import pct.datastructure.Action;

public class ScanClassToGetAction {
	//public ArrayList<Action> ActionList = new ArrayList<Action>();
	
	//private int insCounter;
	
	private ClassNode cn;
	
	private boolean checkflag = false;

	public ScanClassToGetAction(ClassNode cn) throws AnalyzerException{
		super();
		this.cn = cn;
		//insCounter = 0;
		
		for(int i=0; i<cn.methods.size(); i++){
			MethodNode mn = (MethodNode)  cn.methods.get(i);
			ScanIns(mn);
		}
		//StaticMethod.printActionList();
		//StaticMethod.printThread();
		
	}
	
	private void ScanIns(MethodNode mn){
		
		System.out.println(cn.name + ":" + mn.name);

		if(cn.superName.equals("java/lang/Thread") || 
				cn.interfaces.contains("java/lang/Runnable")){
			if(mn.name.equals("run")){	
				MethodInsnNode newMIN = new MethodInsnNode(
					Opcodes.INVOKESTATIC, 
					"asm/staticmethod/Scheduler", 
					"initial", 
					"()V");
			
				mn.instructions.insert(mn.instructions.get(1), newMIN);
				
			//}
		//}
		
				for(int i=0; i<mn.instructions.size(); i++){

					int currentOpcode = mn.instructions.get(i).getOpcode();
					//AbstractInsnNode abstractInsnNode = mn.instructions.get(i);
			
					if(currentOpcode == Opcodes.MONITORENTER 	&& 
							mn.instructions.get(i-4).getOpcode() != Opcodes.INVOKESTATIC){
						Action action = new Action(i, currentOpcode);
						StaticMethod.ActionList.add(action);
						/*	
				  		GETSTATIC DeadLockType1.mutex_1 : Object	--- i-3
				  		
    					DUP		--- i-2
    					
    					ASTORE 2	--- i-1
    					
    					MONITORENTER	--- i
    					*/
						MethodInsnNode newMIN2 = new MethodInsnNode(
								Opcodes.INVOKESTATIC, 
								"asm/staticmethod/Scheduler", 
								"PCTScheduler", 
								"()V");
				 
						mn.instructions.insert(mn.instructions.get(i-4),newMIN2);
						++ i; 
					
					}else if(mn.instructions.get(i).getOpcode() == Opcodes.ALOAD 	&&
							mn.instructions.get(i).getNext().getOpcode() == Opcodes.MONITOREXIT 	&&
							mn.instructions.get(i-1).getOpcode() != Opcodes.INVOKESTATIC){
						/*
						 *  ALOAD 3
    						MONITOREXIT
						 */
						MethodInsnNode newMIN2 = new MethodInsnNode(
								Opcodes.INVOKESTATIC, 
								"asm/staticmethod/Scheduler", 
								"PCTScheduler", 
								"()V");
				
						Action action = new Action(i, currentOpcode);
						StaticMethod.ActionList.add(action);
						mn.instructions.insert(mn.instructions.get(i-1), newMIN2);
						++ i;
					}//if
					/*					
					else{
						
						MethodInsnNode checkPoint = new MethodInsnNode(
								Opcodes.INVOKESTATIC, 
								"asm/staticmethod/Scheduler", 
								"foo", 
								"()V");
						
						mn.instructions.insert(mn.instructions.get(i), checkPoint);
						++ i;
						
					}
					*/
				}//for
			}//if
		}//if

		//}
	}
/*
	public int getInsCounter() {
		return insCounter;
	}
	
	*/
}
/*			
if(
	(currentOpcode == Opcodes.INVOKEINTERFACE && 
		mn.instructions.get(i).getPrevious().getOpcode() == Opcodes.GETFIELD &&
		mn.instructions.get(i).getNext().getOpcode() != Opcodes.PUTFIELD)	|| 
	(currentOpcode == Opcodes.MONITORENTER)		||
	(currentOpcode == Opcodes.MONITOREXIT && 
		mn.instructions.get(i-4).getOpcode() != Opcodes.GOTO)	&& 
	(mn.instructions.get(i).getNext().getOpcode() != Opcodes.INVOKESTATIC)
){
	Action action = new Action(i, currentOpcode);
	StaticMethod.ActionList.add(action);
	//System.out.println("add action to actionlist");
	//StaticMethod.printActionList();
	
	MethodInsnNode newMIN2 = new MethodInsnNode(
			Opcodes.INVOKESTATIC, 
			"asm/core/method/StaticMethod", 
			"letThreadYield", 
			"()V");
	mn.instructions.insert(mn.instructions.get(i-1),newMIN2);
	++ i; //³¢ÊÔok
}*/