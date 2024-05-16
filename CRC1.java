import java.util.Scanner;

public class CRC1{
    
    public static String xor(String a, String b) {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < b.length(); i++) {
            if (a.charAt(i) == b.charAt(i)) {
                result.append('0');
            } else {
                result.append('1');
            }
        }
        return result.toString();
    }

    public static String mod2div(String dividend, String divisor) {
        int pick = divisor.length();
        String tmp = dividend.substring(0, pick);
        int n = dividend.length();
        
        while (pick < n) {
            if (tmp.charAt(0) == '1') {
                tmp = xor(divisor, tmp) + dividend.charAt(pick);
            } else {
                tmp = xor("0".repeat(pick), tmp) + dividend.charAt(pick);
            }
            pick += 1;
        }

        if (tmp.charAt(0) == '1') {
            tmp = xor(divisor, tmp);
        } else {
            tmp = xor("0".repeat(pick), tmp);
        }
        
        return tmp;
    }

    public static String encodeData(String data, String key) {
        int l_key = key.length();
        String appendedData = data + "0".repeat(l_key - 1);
        String remainder = mod2div(appendedData, key);
        return data + remainder;
    }

    public static String decodeData(String codeword, String key) {
        return mod2div(codeword, key);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a binary string of 7 or 8 bits: ");
        String data = scanner.nextLine();

        // Validate input length
        if (data.length() != 7 && data.length() != 8) {
            System.out.println("Invalid input length. Please enter a binary string of exactly 7 or 8 bits.");
            return;
        }

        // Validate input content
        if (!data.matches("[01]+")) {
            System.out.println("Invalid input content. Please enter a binary string containing only '0' and '1'.");
            return;
        }

        String key = "100000111";  // CRC-8 polynomial

        System.out.println("\nEncoding...");
        String encodedData = encodeData(data, key);
        System.out.println("Encoded binary data: " + encodedData);

        System.out.println("\nIntroduce an error in the encoded data.");
        System.out.println("For example, flip a bit from 0 to 1 or 1 to 0.");
        System.out.print("Enter corrupted binary string: ");
        String corruptedData = scanner.nextLine();

        // Validate corrupted data length
        if (corruptedData.length() != encodedData.length()) {
            System.out.println("Invalid corrupted data length. Please enter a binary string of length: " + encodedData.length());
            return;
        }

        // Validate corrupted data content
        if (!corruptedData.matches("[01]+")) {
            System.out.println("Invalid corrupted data content. Please enter a binary string containing only '0' and '1'.");
            return;
        }

        System.out.println("\nDecoding...");
        String remainder = decodeData(corruptedData, key);
        if (remainder.contains("1")) {
            System.out.println("Error detected in received data.");
        } else {
            System.out.println("No error detected in received data.");
            String decodedBinaryData = corruptedData.substring(0, corruptedData.length() - key.length() + 1);
            System.out.println("Decoded binary data: " + decodedBinaryData);
        }

        scanner.close();
    }
}
