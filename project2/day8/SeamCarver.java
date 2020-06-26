//import java.io.File;
import java.util.Arrays;
import java.lang.IllegalArgumentException;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture pict;
    private double[][] energy;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }
        this.pict = new Picture(picture);
        energy = energyMat(pict.height(), pict.width());
    }
 
    // current picture
    public Picture picture() {
        Picture p = new Picture(this.pict);
        return p;
    }
 
    // width of current picture
    public int width() {
        return this.pict.width();
    }
 
    // height of current picture
    public int height() {
        return this.pict.height();
    }
 
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if(x < 0 || y < 0 || x >= width() || y >= height()) {
            throw new IllegalArgumentException();
        }
        if (x == 0 || y == 0 || x == (width() - 1) || y == (height() - 1)) {
            return 1000.0;
        }
        int x1r = pict.getRGB((x + 1), y) >> 16 & 0xFF;
        int x1g = pict.getRGB((x + 1), y) >> 8 & 0xFF;
        int x1b = pict.getRGB((x + 1), y) >> 0 & 0xFF;

        int x2r = pict.getRGB((x - 1), y) >> 16 & 0xFF;
        int x2g = pict.getRGB((x - 1), y) >> 8 & 0xFF;
        int x2b = pict.getRGB((x - 1), y) >> 0 & 0xFF;

        int y1r = pict.getRGB(x, (y + 1)) >> 16 & 0xFF;
        int y1g = pict.getRGB(x, (y + 1)) >> 8 & 0xFF;
        int y1b = pict.getRGB(x, (y + 1)) >> 0 & 0xFF;

        int y2r = pict.getRGB(x, (y - 1)) >> 16 & 0xFF;
        int y2g = pict.getRGB(x, (y - 1)) >> 8 & 0xFF;
        int y2b = pict.getRGB(x, (y - 1)) >> 0 & 0xFF;

        double xt = ((x1r - x2r) * (x1r - x2r)) + ((x1g - x2g) * (x1g - x2g)) + ((x1b - x2b) * (x1b - x2b));
        double yt = ((y1r - y2r) * (y1r - y2r)) + ((y1g - y2g) * (y1g - y2g)) + ((y1b - y2b) * (y1b - y2b));

        double energy = Math.sqrt(xt + yt);

        return energy;
    }

    private double[][] energyMat(int w, int h) {
        double em[][] = new double[w][h];
        //System.out.println(w);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                em[i][j] = energy(j, i);
            }
        }
        return em;
    }
private double[][] cumilativeMat(double[][] energy) {
    int numRows = energy.length;
    //System.out.println(numRows);
    int numCol = energy[0].length;
    //System.out.println(numCol);
    double[][] cum = new double[numRows][numCol];

    for (int i = 0; i < numRows; i++) {
        for (int j = 0; j < numCol; j++) {
            if (i == 0) {
                cum[i][j] = energy[i][j];
            } else if (i > 0 && j == 0) {
                double smallest = Math.min(cum[i - 1][j], cum[i - 1][j + 1]);
                cum[i][j] = energy[i][j] + smallest;
            } else if (i > 0 && j < numCol - 1) {
                double smallest = Math.min(cum[i - 1][j - 1], Math.min(cum[i - 1][j], cum[i - 1][j + 1]));
                //System.out.println(smallest);
               cum[i][j] = energy[i][j] + smallest;
            } else if (i > 0 && j >= numCol - 1) {
                double smallest = Math.min(cum[i - 1][j - 1], cum[i - 1][j]);
                cum[i][j] = energy[i][j] + smallest;
            } 
        }
    }
    return cum;
}

    //sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        double[][] ap = energy;
        int m = ap.length;
        int n = ap[0].length;

        double[][] transposedMatrix = new double[n][m];
        for(int x = 0; x < n; x++) {
            for(int y = 0; y < m; y++) {
                transposedMatrix[x][y] = ap[y][x];
            }
        }
        return findSeam(cumilativeMat(transposedMatrix));
    }
 
    //sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] ver = cumilativeMat(energy);
        return findSeam(ver);
    }

    private int[] findSeam(double[][] ver) {
        int numRows = ver.length;
        //System.out.println(numRows);
        int numCol = ver[0].length;
        int[] arr = new int[numRows];
        for (int i = numRows - 1; i > 0; i--) {
            double max = Double.MAX_VALUE;
            double min = 0;
            //System.out.println(i);
            if (i == numRows - 1) {
                for (int j = 0; j < numCol; j++) {
                    min = ver[i][j];
                    if (min < max) {
                        max = ver[i][j];
                        arr[i] = j;
                    }
                    //System.out.println(arr[i]);
                }
            } 
            if ( i < numRows - 1 && i > 0) {
                int k = arr[i + 1];
                //System.out.println(k);
                if (k - 1 >= 0 && k - 1 < numCol - 1 && k + 1 <= numCol - 1) {
                    min = Math.min(ver[i][k - 1], Math.min(ver[i][k], ver[i][k + 1]));
                    //System.out.println(min);
                    for (int j = 0; j < numCol; j++) {
                        if (min == ver[i][j]) {
                            arr[i] = j;
                            break;
                        }
                    }
                }
                if ( k - 1 < 0) {
                    min = Math.min(ver[i][k], ver[i][k + 1]);
                    //System.out.println(min);
                    for (int j = 0; j < numCol; j++) {
                        if (min == ver[i][j]) {
                            arr[i] = j;
                            break;
                        }
                    }
                }
                if (k + 1 > numCol - 1) {
                    min = Math.min(ver[i][k - 1], ver[i][k]);
                    //System.out.println(min);
                    for (int j = 0; j < numCol; j++) {
                        if (min == ver[i][j]) {
                            arr[i] = j;
                            break;
                        }
                    }
                }
            }
            arr[0] = arr[1] - 1;
        }
        return arr;
    }

    // // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if(seam == null) {
            throw new IllegalArgumentException();
        }
        Picture P = new Picture(this.pict.width(), this.pict.height() - 1);
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < seam[i]; j++) {
                P.set(i, j, pict.get(i, j));
            }
            for (int j = seam[i] + 1; j < height(); j++) {
                P.set(i,j-1, pict.get(i, j));
            }
        }
        this.pict = P;
    }
 
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if(seam == null) {
            throw new IllegalArgumentException();
        }
        Picture P = new Picture(this.pict.width() - 1, this.pict.height());
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < seam[i]; j++) {
                P.set(j, i, pict.get(j, i));
            }
            for (int j = seam[i] + 1; j < width(); j++) {
                P.set(j - 1, i, pict.get(j, i));
            }
        }
        this.pict = P;
    }
 
    //  unit testing (optional)
    public static void main(String[] args) {
        SeamCarver obj = new SeamCarver(new Picture("6x5.png"));
        // //System.out.println(obj.height() + " " + obj.width());
        double[][] arr = obj.energyMat(obj.height(), obj.width());
        // //System.out.println((obj.width() + "" + obj.height()));
        //System.out.println(Arrays.toString(arr[5]));
        double[][] arr1 = obj.cumilativeMat(arr);
        System.out.println(Arrays.toString(arr1[4]));
        // int[] arr3 = obj.findVerticalSeam();
        // obj.removeVerticalSeam(arr3);
        int[] arr4 = obj.findHorizontalSeam();
        obj.removeHorizontalSeam(arr4);
        for (int row = 0; row < obj.height(); row++) {
            for (int col = 0; col < obj.width(); col++)
                System.out.printf("%9.0f ", obj.energy(col, row));
            System.out.println();
        }
    }
}
