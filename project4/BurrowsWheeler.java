import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        final String str = BinaryStdIn.readString();
        final CircularSuffixArray csa = new CircularSuffixArray(str);
        for(int i = 0; i < csa.length(); i++) {
            if(csa.index(i) == 0){
            BinaryStdOut.write(i);
            break;
            }
        }
        for(int i=0; i < csa.length(); i++) {
            if(csa.index(i) == 0) {
                BinaryStdOut.write(str.charAt(str.length() - 1));
            }else {
                BinaryStdOut.write(str.charAt(csa.index(i)-1));
            }
        }
        BinaryStdOut.close();
        BinaryStdIn.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String last = BinaryStdIn.readString();
        char[] lastCol = last.toCharArray();
        char[] firstCol = lastCol.clone();
        int[] next = new int[lastCol.length];
        Arrays.sort(firstCol);
        Hashtable<Character, ArrayList<Integer>> hash = new Hashtable<Character, ArrayList<Integer>>();
        for (int i = 0; i < lastCol.length; i++) {
            if (hash.get(lastCol[i]) != null) {
                hash.get(lastCol[i]).add(i);
            } else {
                ArrayList<Integer> arList = new ArrayList<Integer>();
                arList.add(i);
                hash.put(lastCol[i], arList);
            }
        }
        for (int i = 0; i < lastCol.length; i++) {
            next[i] = hash.get(firstCol[i]).get(0);
            hash.get(firstCol[i]).remove(0);
        }
        int[] result = new int[lastCol.length];
        int j = first;
        for(int i = 0; i < lastCol.length; i++) {
            result[i] = firstCol[j];
            j = next[j];
            BinaryStdOut.write((char) result[i]);
        }
        BinaryStdIn.close();
        BinaryStdOut.close();
        return;
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(final String[] args) {
        if (args[0].equals("-")) {
            transform();
        } else {
            inverseTransform();
        }

    }

}
