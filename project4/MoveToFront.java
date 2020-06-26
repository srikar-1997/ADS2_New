import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static int N = 256;

    public static void encode() {
        char[] arr = new char[N];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (char) i;
        }
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int ind = -1;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == c) {
                    ind = i;
                    BinaryStdOut.write((char)ind);
                    break;
                }
            }
            arr = shiftArr(ind, arr, c);

        }
        BinaryStdIn.close();
        BinaryStdOut.close();
    }
    private static char[] shiftArr(int position,char[] brr,char c) {
        for (int i = position; i > 0; i--) {
            brr[i] = brr[i - 1];
        }
        brr[0] = c;
        return brr;
    }

    public static void decode() {
        char[] arr = new char[N];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = (char) i;
        }
        while(!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int ind = -1;
            BinaryStdOut.write(arr[c]);

            for (int j = 0; j < arr.length; j++) {
                if (arr[j] == arr[c]) {
                    ind = j;
                    break;
                }
            }

            arr = shiftArr(ind, arr,arr[c]);
        }
        BinaryStdIn.close();
        BinaryStdOut.close();

    }
    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        } else {
            decode();
        }
    }
}
