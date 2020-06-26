import java.util.Arrays;

public class CircularSuffixArray {
    private final String text;
    private Suffix[] sufArr;

    // circular suffix array of s
    public CircularSuffixArray(final String s) {
        if(s == null) throw new IllegalArgumentException();
        this.text = s;
        final int n = text.length();
        sufArr = new Suffix[n];
        for (int i = 0; i < n; i++) {
            sufArr[i] = new Suffix(text.substring(i) + text.substring(0, i), i);
        }
        Arrays.sort(sufArr);

    }

    // length of s
    public int length() {
        return text.length();

    }

    // returns index of ith sorted suffix
    public int index(final int i) {
        if(i < 0 || i > sufArr.length-1) throw new IllegalArgumentException();
        return sufArr[i].index;

    }

    private static class Suffix implements Comparable<Suffix> {
        private final String str;
        private final int index;

        private Suffix(final String str, final int index) {
            this.str = str;
            this.index = index;

        }

        private int length() {
            return str.length();
        }

        private char charAt(final int i) {
            return str.charAt(i);

        }

        public int compareTo(final Suffix that) {
            if (this == that)
                return 0;
            final int n = Math.min(this.length(), that.length());
            for (int i = 0; i < n; i++) {
                if (this.charAt(i) < that.charAt(i))
                    return -1;
                if (this.charAt(i) > that.charAt(i))
                    return 1;
            }
            return this.length() - that.length();
        }
    }

    // unit testing (required)
    public static void main(final String[] args) {

    }
}
