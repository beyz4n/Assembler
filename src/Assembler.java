import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Assembler {
    public static void main(String[] args) {

        String a = "-1";
        String a_s = getBinary(a,10);
        System.out.println(a_s);

        try {

            File file = new File("Demo.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                int count = 0;
                String[] arr = line.split(" ");
                count = arr.length;

                if (count == 4) {
                  if(arr[0].equals("ADD") && isRegister(arr[1]) && isRegister(arr[2]) && isRegister(arr[3])){
                      add(arr[1],arr[2],arr[3]);
                  }


                  else if(arr[0].equals("NOR") && isRegister(arr[1]) && isRegister(arr[2]) && isRegister(arr[3])){
                        nor(arr[1],arr[2],arr[3]);
                  }

                  else if(arr[0].equals("AND") && isRegister(arr[1]) && isRegister(arr[2]) && isRegister(arr[3])){
                      and(arr[1],arr[2],arr[3]);
                  }

                  else if(arr[0].equals("NAND") && isRegister(arr[1]) && isRegister(arr[2]) && isRegister(arr[3])){
                      nand(arr[1],arr[2],arr[3]);
                  }

                  else if(arr[0].equals("ADDI") && isRegister(arr[1]) && isRegister(arr[2]) && isInteger(arr[3])){
                      addi(arr[1],arr[2],arr[3]);
                  }

                  else if(arr[0].equals("ANDI") && isRegister(arr[1]) && isRegister(arr[2]) && isInteger(arr[3])){
                      andi(arr[1],arr[2],arr[3]);
                  }
                  else{
                      System.out.println("Invalid instruction.");
                  }
                }

                else if (count == 3) {
                    if(arr[0].equals("LD") && isRegister(arr[1]) && isInteger(arr[2])){
                        ld(arr[1],arr[2]);
                    }
                    else if(arr[0].equals("ST") && isRegister(arr[1]) && isInteger(arr[2])){
                        st(arr[1],arr[2]);
                    }
                    else if(arr[0].equals("CMP") && isRegister(arr[1]) && isRegister(arr[2])){
                        cmp(arr[1],arr[2]);
                    }
                    else {
                        System.out.println("Invalid instruction.");
                    }
                }
                else if (count == 2) {
                    if(arr[0].equals("JUMP") && isInteger(arr[1])){
                        jump(arr[1]);
                    }
                    else if(arr[0].equals("JE") && isInteger(arr[1])){
                        je(arr[1]);
                    }
                    else if(arr[0].equals("JA") && isInteger(arr[1])){
                        ja(arr[1]);
                    }
                    else if(arr[0].equals("JB") && isInteger(arr[1])){
                        jb(arr[1]);
                    }
                    else if(arr[0].equals("JAE") && isInteger(arr[1])){
                        jae(arr[1]);
                    }
                    else if(arr[0].equals("JBE") && isInteger(arr[1])){
                        jbe(arr[1]);
                    }
                    else{
                        System.out.println("Invalid instruction.");
                    }
                }
                else {
                    System.out.println("Invalid number of arguments in instruction.");
                }
                }
                sc.close();
            }

        catch(IOException e) {
            System.out.println("File not found.");
        }
        }

        static String[] registerArray = {"R0","R1","R2","R3","R4","R5","R6","R7","R8","R9",
                "R10","R11","R12","R13","R14","R15"};
    public static boolean isRegister(String reg){
        boolean isReg = false;
        for(int i = 0 ; i < registerArray.length ; i++){
            if(registerArray[i].equals(reg)){
                isReg = true;
                break;
            }
        }
            return isReg;
    }

    public static boolean isInteger(String reg) {
        boolean isInt = true;
        for (int i = 0; i < reg.length(); i++) {
            if (reg.charAt(i) >= 58 || reg.charAt(i) <= 47) {
                isInt = false;
                break;
            }
        }
        return isInt;
    }
    public static String andi(String dest, String src1, String imm){
        String instructionInBinary = "0100";
        src1 = getBinary4Registers(src1);
        imm = getBinary(imm,6);
        dest = getBinary4Registers(dest);
        instructionInBinary += dest;
        instructionInBinary += src1;
        instructionInBinary += imm;
        return binary2Hex(instructionInBinary);
    }

    public static String nand(String dest, String src1, String src2){
        String instructionInBinary = "0101";
        dest = getBinary4Registers(dest);
        src1 = getBinary4Registers(src1);
        src2 = getBinary4Registers(src2);
        instructionInBinary += dest;
        instructionInBinary += src1;
        instructionInBinary += src2;
        instructionInBinary += "00";
        return binary2Hex(instructionInBinary);
    }

    public static String ld(String dest, String addr){
        String instructionInBinary = "0110";
        dest = getBinary4Registers(dest);
        addr = getBinary(addr, 10);
        instructionInBinary += dest;
        instructionInBinary += addr;
        return binary2Hex(instructionInBinary);
    }

    public static String st(String src, String addr){
        String instructionInBinary = "0111";
        src = getBinary4Registers(src);
        addr = getBinary(addr, 10);
        instructionInBinary += src;
        instructionInBinary += addr;
        return binary2Hex(instructionInBinary);
    }

    public static String ja(String addr){
        String instructionInBinary = "1011";
        addr = getBinary(addr, 10);
        instructionInBinary += addr;
        instructionInBinary += "0000";
        return binary2Hex(instructionInBinary);
    }

    public static String jb(String addr){
        String instructionInBinary = "1100";
        addr = getBinary(addr, 10);
        instructionInBinary += addr;
        instructionInBinary += "0000";
        return binary2Hex(instructionInBinary);
    }

    public static String jae(String addr){
        String instructionInBinary = "1101";
        addr = getBinary(addr, 10);
        instructionInBinary += addr;
        instructionInBinary += "0000";
        return binary2Hex(instructionInBinary);
    }

    public static String jbe(String addr){
        String instructionInBinary = "1110";
        addr = getBinary(addr, 10);
        instructionInBinary += addr;
        instructionInBinary += "0000";
        return binary2Hex(instructionInBinary);
    }

    public static String add(String dest, String src1, String src2){
        String instructionInBinary = "0000";
        dest = getBinary4Registers(dest);
        src1 = getBinary4Registers(src1);
        src2 = getBinary4Registers(src2);
        instructionInBinary += dest;
        instructionInBinary += src1;
        instructionInBinary += src2;
        instructionInBinary += "00";
        return binary2Hex(instructionInBinary);
    }

    public static String addi(String dest, String src, String imm){
        String instructionInBinary = "0001";
        dest = getBinary4Registers(dest);
        src = getBinary4Registers(src);
        imm = getBinary(imm, 10);
        instructionInBinary += dest;
        instructionInBinary += src;
        instructionInBinary += imm;
        return binary2Hex(instructionInBinary);
    }
    public static String nor(String dest, String src1, String src2){
        String instructionInBinary = "0010";
        dest = getBinary4Registers(dest);
        src1 = getBinary4Registers(src1);
        src2 = getBinary4Registers(src2);
        instructionInBinary += dest;
        instructionInBinary += src1;
        instructionInBinary += src2;
        instructionInBinary += "00";
        return binary2Hex(instructionInBinary);
    }
    public static String and(String dest, String src1, String src2){
        String instructionInBinary = "0011";
        dest = getBinary4Registers(dest);
        src1 = getBinary4Registers(src1);
        src2 = getBinary4Registers(src2);
        instructionInBinary += dest;
        instructionInBinary += src1;
        instructionInBinary += src2;
        instructionInBinary += "00";
        return binary2Hex(instructionInBinary);
    }

    public static String cmp(String op1, String op2){
        String instructionInBinary = "1000";
        op1 = getBinary4Registers(op1);
        op2 = getBinary4Registers(op2);
        instructionInBinary += op1;
        instructionInBinary += op2;
        instructionInBinary += "000000";
        return binary2Hex(instructionInBinary);
    }

    public static String jump(String addr){
        String instructionInBinary = "1001";
        addr = getBinary(addr, 10);
        instructionInBinary += addr;
        instructionInBinary += "0000";
        return binary2Hex(instructionInBinary);
    }

    public static String je(String addr){
        String instructionInBinary = "1010";
        addr = getBinary(addr, 10);
        instructionInBinary += addr;
        instructionInBinary += "0000";
        return binary2Hex(instructionInBinary);
    }

    // Method to convert binary to hex
    public static String binary2Hex(String binary){
        int check = (int)(Math.ceil(binary.length() / 4));
        String part;
        String hex = "";
        for(int i = 0; i < check; i++){
            if(binary.length() >= 4) {
                part = binary.substring(binary.length() - 4);
                binary = binary.substring(0, binary.length() - 4);
            }
            else{
                part = binary;
                switch (part.length()){
                    case 1: part = "000" + part; break;
                    case 2: part = "00" + part; break;
                    case 3: part = "0" + part; break;
                }
            }
            switch (part) {
                case "0000": hex = "0" + hex; break;
                case "0001": hex = "1" + hex; break;
                case "0010": hex = "2" + hex; break;
                case "0011": hex = "3" + hex; break;
                case "0100": hex = "4" + hex; break;
                case "0101": hex = "5" + hex; break;
                case "0110": hex = "6" + hex; break;
                case "0111": hex = "7" + hex; break;
                case "1000": hex = "8" + hex; break;
                case "1001": hex = "9" + hex; break;
                case "1010": hex = "A" + hex; break;
                case "1011": hex = "B" + hex; break;
                case "1100": hex = "C" + hex; break;
                case "1101": hex = "D" + hex; break;
                case "1110": hex = "E" + hex; break;
                case "1111": hex = "F" + hex; break;
            }
        }
        return hex;
    }

    // Gets decimal as an input and convert it to binary representation
    public static String getBinary(String immediateString, int bitSize){
       String binary = "";
       double immediate = 0;
       try {
           immediate = Double.parseDouble(immediateString);
       } catch (Exception e){
           System.out.println("Given immediate value does not correct : " + immediateString);
           System.exit(1);
           return binary;
       }
        int power = bitSize - 2;
        double upperLimit = Math.pow(2, bitSize) -1;
        double lowerLimit = -1 * Math.pow(2, bitSize);



       if(immediate < lowerLimit || immediate > upperLimit){
           System.out.println("Given immediate value can not be represented in our system : " + immediateString);
           System.exit(1);
           return binary;
       }

       if(immediate >= 0){
           binary += "0";
           for(; power >= 0 ; power--){
               if(immediate >= Math.pow(2, power)){
                   immediate -= Math.pow(2, power);
                   binary += "1";
               }
               else{
                   binary += "0";
               }
           }
        }
       else{
           binary += "1";
           double positiveImmediate = -1*immediate - 1;

           for(; power >= 0; power--){
               if(positiveImmediate >= Math.pow(2, power)){
                   positiveImmediate -= Math.pow(2, power);
                   binary += "0";
               }
               else{
                   binary += "1";
               }
           }
        }
        return binary;
    }



    public static String getBinary4Registers(String register){
        String binary = "";

        if(!(register.startsWith("R") || register.startsWith("r"))){
            System.out.println("Given parameter is not register name !");
            System.exit(1);
            return binary;
        }

        double registerNumber = 0;

        try {
            registerNumber = Double.parseDouble(register.substring(1));
        }
        catch(Exception e){
            System.out.println("Given register does not exist : " + register);
            System.exit(1);
            return binary;
        }

        if( registerNumber <= 0 || registerNumber >= 16){
            System.out.println("Given register does not exist : " + register);
            return binary;
        }

        for(int power = 3; power >= 0 ; power--){
            if(registerNumber >= Math.pow(2, power)){
                registerNumber -= Math.pow(2, power);
                binary += "1";
            }
            else{
                binary += "0";
            }
        }

        return binary;
    }


}