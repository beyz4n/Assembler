public class Assembler {
    public static void main(String[] args) {

    }

    public static String andi(String dest, String src1, String imm){
        String instructionInBinary = "0100";
        src1 = getBinary(src1);
        imm = getBinary(imm);
        instructionInBinary += src1;
        instructionInBinary += imm;
        return binary2Hex(instructionInBinary);
    }

    public static String nand(String dest, String src1, String src2){
        String instructionInBinary = "0101";
        dest = getBinary(dest);
        src1 = getBinary(src1);
        src2 = getBinary(src2);
        instructionInBinary += dest;
        instructionInBinary += src1;
        instructionInBinary += src2;
        instructionInBinary += "00";
        return binary2Hex(instructionInBinary);
    }

    public static String ld(String dest, String addr){
        String instructionInBinary = "0110";
        dest = getBinary(dest);
        addr = getBinary(addr);
        instructionInBinary += dest;
        instructionInBinary += addr;
        return binary2Hex(instructionInBinary);
    }

    public static String st(String src, String addr){
        String instructionInBinary = "0111";
        src = getBinary(src);
        addr = getBinary(addr);
        instructionInBinary += src;
        instructionInBinary += addr;
        return binary2Hex(instructionInBinary);
    }

    public static String ja(String addr){
        String instructionInBinary = "1011";
        addr = getBinary(addr);
        instructionInBinary += addr;
        instructionInBinary += "0000";
        return binary2Hex(instructionInBinary);
    }

    public static String jb(String addr){
        String instructionInBinary = "1100";
        addr = getBinary(addr);
        instructionInBinary += addr;
        instructionInBinary += "0000";
        return binary2Hex(instructionInBinary);
    }

    public static String jae(String addr){
        String instructionInBinary = "1101";
        addr = getBinary(addr);
        instructionInBinary += addr;
        instructionInBinary += "0000";
        return binary2Hex(instructionInBinary);
    }

    public static String jbe(String addr){
        String instructionInBinary = "1110";
        addr = getBinary(addr);
        instructionInBinary += addr;
        instructionInBinary += "0000";
        return binary2Hex(instructionInBinary);
    }

    public static String add(String dest, String src1, String src2){
        String instructionInBinary = "0000";
        dest = getBinary(dest);
        src1 = getBinary(src1);
        src2 = getBinary(src2);
        instructionInBinary += dest;
        instructionInBinary += src1;
        instructionInBinary += src2;
        instructionInBinary += "00";
        return binary2Hex(instructionInBinary);
    }

    public static String addi(String dest, String src, String imm){
        String instructionInBinary = "0001";
        src = getBinary(src);
        imm = getBinary(imm);
        instructionInBinary += src;
        instructionInBinary += imm;
        return binary2Hex(instructionInBinary);
    }
    public static String nor(String dest, String src1, String src2){
        String instructionInBinary = "0010";
        dest = getBinary(dest);
        src1 = getBinary(src1);
        src2 = getBinary(src2);
        instructionInBinary += dest;
        instructionInBinary += src1;
        instructionInBinary += src2;
        instructionInBinary += "00";
        return binary2Hex(instructionInBinary);
    }
    public static String and(String dest, String src1, String src2){
        String instructionInBinary = "0011";
        dest = getBinary(dest);
        src1 = getBinary(src1);
        src2 = getBinary(src2);
        instructionInBinary += dest;
        instructionInBinary += src1;
        instructionInBinary += src2;
        instructionInBinary += "00";
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

    public static String getBinary(String a){
        return "";
    }

}