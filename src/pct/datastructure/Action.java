package pct.datastructure;

/*
 * 事件类
 * 保存事件是第几条指令，以及事件的操作码是什么
 * 循环怎么办?
 */

public class Action{
	public int ActionId;
	public int Opcode_; 
	
	public Action(int actionId, int opcode_) {
		super();
		ActionId = actionId;
		Opcode_ = opcode_;
	}
}
