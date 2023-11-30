import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import static java.lang.Character.isDigit;

public class Assembler {
    static String[] registerArray = {"R0", "R1", "R2", "R3", "R4", "R5", "R6", "R7", "R8", "R9", "R10", "R11", "R12", "R13", "R14", "R15"};
    //registerArray contains 16 registers from R0 to R15
    public static void main(String[] args) {
        try {
            File inputFile = new File("input.txt");    //input file
            File outputFile = new File("output.hex");   //output file
            PrintWriter printWriter = new PrintWriter(outputFile);   //to be able to write into our output file
            String output = "v2.0 raw";
            Scanner sc = new Scanner(inputFile);
            while (sc.hasNextLine()) {       //reading input file line by line
                String line = sc.nextLine();
                String[] lineArray = line.split(" "); //splitting lines into words
                int count = lineArray.length;  //number of words in a line

                if (line.isBlank()) {   //if line is empty, continue executing
                    continue;
                }

                //if count is equal to 4, there are 6 possibilities such as ADD, NOR, AND, NAND, ADDI, ANDI
                //invoke the corresponding function and write it to output file
                if (count == 4) {
                    if (lineArray[0].equals("ADD") && isRegister(lineArray[1]) && isRegister(lineArray[2]) && isRegister(lineArray[3])) {
                        output += "\n" + (add(lineArray[1], lineArray[2], lineArray[3]));
                    } else if (lineArray[0].equals("NOR") && isRegister(lineArray[1]) && isRegister(lineArray[2]) && isRegister(lineArray[3])) {
                        output += "\n" + (nor(lineArray[1], lineArray[2], lineArray[3]));
                    } else if (lineArray[0].equals("AND") && isRegister(lineArray[1]) && isRegister(lineArray[2]) && isRegister(lineArray[3])) {
                        output += "\n" + (and(lineArray[1], lineArray[2], lineArray[3]));
                    } else if (lineArray[0].equals("NAND") && isRegister(lineArray[1]) && isRegister(lineArray[2]) && isRegister(lineArray[3])) {
                        output += "\n" + (nand(lineArray[1], lineArray[2], lineArray[3]));
                    } else if (lineArray[0].equals("ADDI") && isRegister(lineArray[1]) && isRegister(lineArray[2]) && isInteger(lineArray[3])) {
                        output += "\n" + (addi(lineArray[1], lineArray[2], lineArray[3]));
                    } else if (lineArray[0].equals("ANDI") && isRegister(lineArray[1]) && isRegister(lineArray[2]) && isInteger(lineArray[3])) {
                        output += "\n" + (andi(lineArray[1], lineArray[2], lineArray[3]));
                    } else {
                        System.out.println("Invalid instruction.");
                    }
                }
                //if count is equal to 3, there are 3 possibilities such as LD, ST, CMP.
                //invoke the corresponding function and write it to output file
                else if (count == 3) {
                    if (lineArray[0].equals("LD") && isRegister(lineArray[1]) && isInteger(lineArray[2])) {
                        output += "\n" + (ld(lineArray[1], lineArray[2]));
                    } else if (lineArray[0].equals("ST") && isRegister(lineArray[1]) && isInteger(lineArray[2])) {
                        output += "\n" + (st(lineArray[1], lineArray[2]));
                    } else if (lineArray[0].equals("CMP") && isRegister(lineArray[1]) && isRegister(lineArray[2])) {
                        output += "\n" + (cmp(lineArray[1], lineArray[2]));
                    } else {
                        System.out.println("Invalid instruction.");
                    }
                }
                //if count is equal to 2, there are 6 possibilities such as JUMP, JE, JA, JB, JAE, JBE.
                //invoke the corresponding function and write it to output file
                else if (count == 2) {
                    if (lineArray[0].equals("JUMP") && isInteger(lineArray[1])) {
                        output += "\n" + (jump(lineArray[1]));
                    } else if (lineArray[0].equals("JE") && isInteger(lineArray[1])) {
                        output += "\n" + (je(lineArray[1]));
                    } else if (lineArray[0].equals("JA") && isInteger(lineArray[1])) {
                        output += "\n" + (ja(lineArray[1]));
                    } else if (lineArray[0].equals("JB") && isInteger(lineArray[1])) {
                        output += "\n" + (jb(lineArray[1]));
                    } else if (lineArray[0].equals("JAE") && isInteger(lineArray[1])) {
                        output += "\n" + (jae(lineArray[1]));
                    } else if (lineArray[0].equals("JBE") && isInteger(lineArray[1])) {
                        output += "\n" + (jbe(lineArray[1]));
                    } else {
                        System.out.println("Invalid instruction.");
                    }
                } else {
                    System.out.println("Invalid number of arguments in instruction.");
                }
            }
            sc.close();
            printWriter.print(output);
            printWriter.close();
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    //checking given argument contains invalid registers or not
    public static boolean isRegister(String reg) {
        boolean isReg = false;
        for (String s : registerArray) {
            if (s.equals(reg)) {
                isReg = true;
                break;
            }
        }
        return isReg;
    }

    //checking given argument contains integer
    public static boolean isInteger(String str) {
        boolean isInteger = true;
        if (str.charAt(0) == '-' || isDigit(str.charAt(0))) {
            for (int i = 1; i < str.length(); i++) {
                if (!isDigit(str.charAt(i))) {
                    isInteger = false;
                    return isInteger;
                }
            }
        } else {
            isInteger = false;
            return isInteger;
        }
        return isInteger;
    }

    public static String andi(String dest, String src1, String imm) {
        String instructionInBinary = "0100";
        src1 = getBinary4Registers(src1);
        imm = getBinary(imm, 6);
        dest = getBinary4Registers(dest);
        instructionInBinary += dest;
        instructionInBinary += src1;
        instructionInBinary += imm;
        return binary2Hex(instructionInBinary);
    }

    public static String nand(String dest, String src1, String src2) {
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

    public static String ld(String dest, String addr) {
        String instructionInBinary = "0110";
        dest = getBinary4Registers(dest);
        addr = getBinary(addr, 10);
        instructionInBinary += dest;
        instructionInBinary += addr;
        return binary2Hex(instructionInBinary);
    }

    public static String st(String src, String addr) {
        String instructionInBinary = "0111";
        src = getBinary4Registers(src);
        addr = getBinary(addr, 10);
        instructionInBinary += src;
        instructionInBinary += addr;
        return binary2Hex(instructionInBinary);
    }

    public static String ja(String addr) {
        String instructionInBinary = "1011";
        addr = getBinary(addr, 10);
        instructionInBinary += addr;
        instructionInBinary += "0000";
        return binary2Hex(instructionInBinary);
    }

    public static String jb(String addr) {
        String instructionInBinary = "1100";
        addr = getBinary(addr, 10);
        instructionInBinary += addr;
        instructionInBinary += "0000";
        return binary2Hex(instructionInBinary);
    }

    public static String jae(String addr) {
        String instructionInBinary = "1101";
        addr = getBinary(addr, 10);
        instructionInBinary += addr;
        instructionInBinary += "0000";
        return binary2Hex(instructionInBinary);
    }

    public static String jbe(String addr) {
        String instructionInBinary = "1110";
        addr = getBinary(addr, 10);
        instructionInBinary += addr;
        instructionInBinary += "0000";
        return binary2Hex(instructionInBinary);
    }

    public static String add(String dest, String src1, String src2) {
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

    public static String addi(String dest, String src, String imm) {
        String instructionInBinary = "0001";
        dest = getBinary4Registers(dest);
        src = getBinary4Registers(src);
        imm = getBinary(imm, 10);
        instructionInBinary += dest;
        instructionInBinary += src;
        instructionInBinary += imm;
        return binary2Hex(instructionInBinary);
    }

    public static String nor(String dest, String src1, String src2) {
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

    public static String and(String dest, String src1, String src2) {
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

    //cmp method converts desired instruction to binary which contains specific opcode and registers, after that converts it to hexadecimal
    public static String cmp(String op1, String op2) {
        String instructionInBinary = "1000";
        op1 = getBinary4Registers(op1);
        op2 = getBinary4Registers(op2);
        instructionInBinary += op1;
        instructionInBinary += op2;
        instructionInBinary += "000000";
        return binary2Hex(instructionInBinary);
    }

    //jump method converts desired instruction to binary which contains specific opcode and address, after that converts it to hexadecimal
    public static String jump(String addr) {
        String instructionInBinary = "1001";
        addr = getBinary(addr, 10);
        instructionInBinary += addr;
        instructionInBinary += "0000";
        return binary2Hex(instructionInBinary);
    }

    //je method converts desired instruction to binary which contains specific opcode and address, after that converts it to hexadecimal
    public static String je(String addr) {
        String instructionInBinary = "1010";
        addr = getBinary(addr, 10);
        instructionInBinary += addr;
        instructionInBinary += "0000";
        return binary2Hex(instructionInBinary);
    }

    // Method to convert binary to hex
    public static String binary2Hex(String binary) {
        double check = Math.ceil(binary.length() / 4.0);
        String part;
        String hex = "";
        for (int i = 0; i < check; i++) {
            if (binary.length() >= 4) {
                part = binary.substring(binary.length() - 4);
                binary = binary.substring(0, binary.length() - 4);
            } else {
                part = binary;
                switch (part.length()) {
                    case 1:
                        part = "000" + part;
                        break;
                    case 2:
                        part = "00" + part;
                        break;
                    case 3:
                        part = "0" + part;
                        break;
                }
            }
            hex = switch (part) {
                case "0000" -> "0" + hex;
                case "0001" -> "1" + hex;
                case "0010" -> "2" + hex;
                case "0011" -> "3" + hex;
                case "0100" -> "4" + hex;
                case "0101" -> "5" + hex;
                case "0110" -> "6" + hex;
                case "0111" -> "7" + hex;
                case "1000" -> "8" + hex;
                case "1001" -> "9" + hex;
                case "1010" -> "A" + hex;
                case "1011" -> "B" + hex;
                case "1100" -> "C" + hex;
                case "1101" -> "D" + hex;
                case "1110" -> "E" + hex;
                case "1111" -> "F" + hex;
                default -> hex;
            };
        }
        return hex;
    }

    // Gets decimal as an input and convert it to binary representation
    public static String getBinary(String immediateString, int bitSize) {
        String binary = "";
        double immediate = 0;
        try {
            immediate = Double.parseDouble(immediateString);
        } catch (Exception e) {
            System.out.println("Given immediate value does not correct : " + immediateString);
            System.exit(1);
            return binary;
        }
        int power = bitSize - 2;
        double upperLimit = Math.pow(2, bitSize) - 1;
        double lowerLimit = -1 * Math.pow(2, bitSize);


        if (immediate < lowerLimit || immediate > upperLimit) {
            System.out.println("Given immediate value can not be represented in our system : " + immediateString);
            System.exit(1);
            return binary;
        }

        if (immediate >= 0) {
            binary += "0";
            for (; power >= 0; power--) {
                if (immediate >= Math.pow(2, power)) {
                    immediate -= Math.pow(2, power);
                    binary += "1";
                } else {
                    binary += "0";
                }
            }
        } else {
            binary += "1";
            double positiveImmediate = -1 * immediate - 1;

            for (; power >= 0; power--) {
                if (positiveImmediate >= Math.pow(2, power)) {
                    positiveImmediate -= Math.pow(2, power);
                    binary += "0";
                } else {
                    binary += "1";
                }
            }
        }
        return binary;
    }


    public static String getBinary4Registers(String register) {
        String binary = "";

        if (!(register.startsWith("R") || register.startsWith("r"))) {
            System.out.println("Given parameter is not register name !");
            System.exit(1);
            return binary;
        }

        double registerNumber = 0;

        try {
            registerNumber = Double.parseDouble(register.substring(1));
        } catch (Exception e) {
            System.out.println("Given register does not exist : " + register);
            System.exit(1);
            return binary;
        }

        if (registerNumber <= 0 || registerNumber >= 16) {
            System.out.println("Given register does not exist : " + register);
            return binary;
        }

        for (int power = 3; power >= 0; power--) {
            if (registerNumber >= Math.pow(2, power)) {
                registerNumber -= Math.pow(2, power);
                binary += "1";
            } else {
                binary += "0";
            }
        }

        return binary;
    }


}