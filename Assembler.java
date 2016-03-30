package assembler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class Assembler {
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}

	public static String hexToBinary(String hex) {
		hex = hex.replaceAll("0x", "");
	    int i = Integer.parseInt(hex, 16);
	    String bin = Integer.toBinaryString(i);
	    return bin;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		String finalText = "";
		String inputMips = null;
		try {
			inputMips = readFile("input.txt", StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Hashtable<String, operation> table = new Hashtable<String, operation>();
		operation OPadd = new operation("0x00", "0x20", "d", "s", "t", "R");
	    operation OPsub = new operation("0x00", "0x22", "d", "s", "t", "R");
	    operation OPaddi = new operation("0x08", "NA", "t", "s", "imm", "I");
	    operation OPmult = new operation("0x00", "0x18", "s", "t", "NA", "R");
	    operation OPdiv = new operation("0x00", "0x1A", "s", "t", "NA", "R");
	    operation OPslt = new operation("0x25", "0x2A", "d", "s", "t", "R");
	    operation OPslti = new operation("0x0A", "NA", "t", "s", "imm", "I");
	    operation OPand = new operation("0x00", "0x24", "d", "s", "t", "R");
	    operation OPor = new operation("0x00", "0x25", "d", "s", "t", "R");
	    operation OPnor = new operation("0x00", "0x27", "d", "s", "t", "R");
	    operation OPxor = new operation("0x00", "0x26", "d", "s", "t", "R");
	    operation OPandi = new operation("0x0C", "NA", "t", "s", "imm", "I");
	    operation OPori = new operation("0x0D", "NA", "t", "s", "imm", "I");
	    operation OPxori = new operation("0x0E", "NA", "t", "s", "imm", "I");
	    operation OPmfhi = new operation("0x00", "0x10", "d", "NA", "NA", "R");
	    operation OPmflo = new operation("0x00", "0x12", "d", "NA", "NA", "R");
	    operation OPlui = new operation("0x0F", "NA", "t", "imm", "NA", "I");
	    operation OPsll = new operation("0x00", "0x00", "d", "t", "h", "R");
	    operation OPsrl = new operation("0x00", "0x02", "d", "t", "h", "R");
	    operation OPsra = new operation("0x00", "0x03", "d", "t", "h", "R");
	    operation OPlw = new operation("0x23", "NA", "t", "imm", "s", "I");
	    operation OPlb = new operation("0x20", "NA", "t", "imm", "s", "I");
	    operation OPsw = new operation("0x2B", "NA", "t", "imm", "s", "I");
	    operation OPsb = new operation("0x28", "NA", "t", "imm", "s", "I");
	    operation OPbeq = new operation("0x04", "NA", "s", "t", "label", "I");
	    operation OPbne = new operation("0x05", "NA", "s", "t", "label", "I");
	    operation OPj = new operation("0x02", "NA", "label", "NA", "NA", "J");
	    operation OPjr = new operation("0x00", "0x08", "s", "NA", "NA", "R");
	    operation OPjal = new operation("0x03", "NA", "label", "NA", "NA", "J");
	    operation OPhalt = new operation("0x03", "NA", "label", "NA", "NA", "H");

	    table.put("add", OPadd);
	    table.put("sub", OPsub);
	    table.put("addi", OPaddi);
	    table.put("mult", OPmult);
	    table.put("div", OPdiv);
	    table.put("slt", OPslt);
	    table.put("slti", OPslti);
	    table.put("and", OPand);
	    table.put("or", OPor);
	    table.put("nor", OPnor);
	    table.put("xor", OPxor);
	    table.put("andi", OPandi);
	    table.put("ori", OPori);
	    table.put("xori", OPxori);
	    table.put("mfhi", OPmfhi);
	    table.put("mflo", OPmflo);
	    table.put("lui", OPlui);
	    table.put("sll", OPsll);
	    table.put("srl", OPsrl);
	    table.put("sra", OPsra);
	    table.put("lw", OPlw);
	    table.put("lb", OPlb);
	    table.put("sw", OPsw);
	    table.put("sb", OPsb);
	    table.put("beq", OPbeq);
	    table.put("bne", OPbne);
	    table.put("j", OPj);
	    table.put("jr", OPjr);
	    table.put("jal", OPjal);
	    table.put("halt", OPhalt);
	    
	    
	    List<String> instList = new ArrayList<String>(Arrays.asList(inputMips.split("\\r?\\n")));
	    Hashtable<String, Integer> labelList = new Hashtable<String, Integer>();
	    
	    
	    List<List> mainList = new ArrayList<List>();
	    
	    for(int i = 0; i < instList.size(); i++){
	    	String currentLine = instList.get(i);
	    	
	    	if(currentLine.contains(":")){
	    		labelList.put(currentLine.substring(0, currentLine.indexOf(":")), i);
	    		currentLine = currentLine.substring(currentLine.indexOf(":")+1);
	    		
	    	}
	    	if(currentLine.contains("#")){
	    		currentLine = currentLine.substring(0, currentLine.indexOf("#"));
	    		instList.set(i, currentLine);
	    		
	    	}
	    	
	    	
	    	
	    	
	    }
	    for(int i = 0; i < instList.size(); i++){
	    	String currentLine = instList.get(i);
	    	String[] tempSplit = currentLine.split("[\\s,(,)]+");
	    	List<String> tempSplit2 = new ArrayList<String>(Arrays.asList(tempSplit));
	    	tempSplit2.removeAll(Collections.singleton(""));
	    	
	    	for(int z = 0; z<tempSplit2.size(); z++){
	    		tempSplit2.set(z, tempSplit2.get(z).replace("$", ""));
	    		tempSplit2.set(z, tempSplit2.get(z).replace(",", ""));

	    	}
	    	
	    	if (tempSplit2.size() >= 2){
	    		mainList.add(tempSplit2);
	    	}
	    	
	    	
	    	
	    }
	    for(int i = 0; i < mainList.size(); i++){
	    	List<String> currentInstList = mainList.get(i);
	    	String operation = currentInstList.get(0);
	    	String opType = table.get(operation).getType();
	    	String opcode = table.get(operation).getOpcode();
	    	String rs ="";
	    	String rt = "";
	    	String rd = "";
	    	String shamt = "";
	    	String funct = "";
	    	String immediate = "";
	    	String Jaddr = "";
	    	
	    	
	    	opcode = hexToBinary(opcode);
	    	
	    	if (opType == "R"){
	    			funct = table.get(operation).getFunct();
	    			if (funct != "NA"){
	    				funct = hexToBinary(funct);
	    			}
	    			if (table.get(operation).getFirst() == "d"){
	    				rd = currentInstList.get(1);
	    			}
	    			else if (table.get(operation).getFirst() == "s"){
	    				rs = currentInstList.get(1);
	    			}
	    			else if (table.get(operation).getFirst() == "t"){
	    				rt = currentInstList.get(1);
	    			}
	    			
	    			if (table.get(operation).getSecond() == "d"){
	    				rd = currentInstList.get(2);
	    			}
	    			else if (table.get(operation).getSecond() == "s"){
	    				rs = currentInstList.get(2);
	    			}
	    			else if (table.get(operation).getSecond() == "t"){
	    				rt = currentInstList.get(2);
	    			}
	    			
	      			if (table.get(operation).getThird() == "d"){
	    				rd = currentInstList.get(3);
	    			}
	    			else if (table.get(operation).getThird() == "s"){
	    				rs = currentInstList.get(3);
	    			}
	    			else if (table.get(operation).getThird() == "t"){
	    				rt = currentInstList.get(3);
	    			}
	    	}
	    	else if (opType == "I"){
	    		if (table.get(operation).getFirst() == "imm"){
	    			immediate = currentInstList.get(1);
	    		}
	    		else if (table.get(operation).getFirst() == "s"){
	    			rs = currentInstList.get(1);
	    		}
	    		else if (table.get(operation).getFirst() == "t"){
	    			rt = currentInstList.get(1);
	    		}

	    		if (table.get(operation).getSecond() == "imm"){
	    			immediate = currentInstList.get(2);
	    		}
	    		else if (table.get(operation).getSecond() == "s"){
	    			rs = currentInstList.get(2);
	    		}
	    		else if (table.get(operation).getSecond() == "t"){
	    			rt = currentInstList.get(2);
	    		}

	    		if (table.get(operation).getThird() == "imm"){
	    			immediate = currentInstList.get(3);
	    		}
	    		else if (table.get(operation).getThird() == "s"){
	    			rs = currentInstList.get(3);
	    		}
	    		else if (table.get(operation).getThird() == "t"){
	    			rt = currentInstList.get(3);
	    		}
	    		else if (table.get(operation).getThird() == "label"){
	    			immediate = currentInstList.get(3);
	    		
	    			immediate =labelList.get(immediate).toString();
	    			 
	    			
	    		}

	    	}
	    	else if (opType == "J"){
	    		Jaddr = currentInstList.get(1);
	    		Jaddr = labelList.get(Jaddr).toString();
	    	}
	    	
	    	
	    	
	    	while(opcode.length() < 6){
				opcode = "0".concat(opcode);
			}
			
			if (rd == "NA" || rd == ""){
				rd = "00000";
			}
			else{
				rd = Integer.toString(Integer.parseInt(rd, 10), 2);
				while(rd.length() < 5){
					rd = "0".concat(rd);
				}
			}
			if (rs == "NA" || rs == ""){
				rs = "00000";
			}
			else{
				rs = Integer.toString(Integer.parseInt(rs, 10), 2);
				while(rs.length() < 5){
					rs = "0".concat(rs);
				}
			}
			if (rt == "NA" || rt == ""){
				rt = "00000";
			}
			else{
				rt = Integer.toString(Integer.parseInt(rt, 10), 2);
				while(rt.length() < 5){
					rt = "0".concat(rt);
				}
				
			}
			
			if (shamt == "NA" || shamt == ""){
				shamt = "00000";
			}
			else{
				shamt = Integer.toString(Integer.parseInt(shamt, 10), 2);
				while(shamt.length() < 5){
					shamt = "0".concat(shamt);
				}
				
			}
			
			if (funct == "NA" || funct == ""){
				funct = "000000";
			}
			else{
				
				while(funct.length() < 6){
					funct = "0".concat(funct);
				}

			}
			
			if (immediate == "NA" || immediate == ""){
				immediate = "0000000000000000";
			}
			else{
				Integer immediateInt = Integer.parseInt(immediate, 10);
				if (immediateInt < 0){
					immediate = Integer.toBinaryString(immediateInt);
					while(immediate.length() < 16){
						immediate = "1".concat(immediate);
					}
					immediate = immediate.substring(16);
				}
				else{
					immediate = Integer.toBinaryString(immediateInt);
					while(immediate.length() < 16){
						immediate = "0".concat(immediate);
					}
				}
				
				
				

			}
			if (Jaddr == "NA" || Jaddr == ""){
				Jaddr = "00000000000000000000000000";
			}
			else{
				
				Jaddr = Integer.toBinaryString(Integer.parseInt(Jaddr, 10));
				while(Jaddr.length() < 26){
					Jaddr = "0".concat(Jaddr);
				}

			}
	    	
	    
	    
	    String outputLine = "";
	    
		
		if(opType == "H"){
		
			outputLine = "01011000000000000000000000000000\n";
		}
		if(opType == "R"){
			
			
			outputLine = opcode + rs + rt + rd + shamt + funct + "\n";
				

				
		}
		if(opType == "I"){

			
			outputLine = opcode + rs + rt + immediate + "\n";

			
		}
		if(opType == "J"){
			

			outputLine = opcode + Jaddr + "\n";

			
		}
		
		finalText = finalText + outputLine;
	    
	    }
	    
	    try(  PrintWriter out = new PrintWriter( "output.txt" )  ){
	        out.println(finalText);
	    }
	    
	}



}
