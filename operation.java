package assembler;

public class operation {
	
	public String opcode;
	public String funct;
	public String first;
	public String second;
	public String third;
	public String type;
	
	public String getOpcode() {
		return opcode;
	}
	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}
	public String getFunct() {
		return funct;
	}
	public void setFunct(String funct) {
		this.funct = funct;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getSecond() {
		return second;
	}
	public void setSecond(String second) {
		this.second = second;
	}
	public String getThird() {
		return third;
	}
	public void setThird(String third) {
		this.third = third;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public operation(String opcode, String funct, String first, String second,
			String third, String type) {
		super();
		this.opcode = opcode;
		this.funct = funct;
		this.first = first;
		this.second = second;
		this.third = third;
		this.type = type;
		
	}
}
