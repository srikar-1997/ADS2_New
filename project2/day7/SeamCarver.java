import java.io.File;
import java.util.Arrays;

public class SeamCarver {
    Picture pict;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.pict = new Picture(picture);
    }
 
    // current picture
    public Picture picture() {
        return pict;
    }
 
    // width of current picture
    public int width() {
        return pict.width();
    }
 
    // height of current picture
    public int height() {
        return pict.height();
    }
 
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x <= 0 || y <= 0 || x >= (width() - 1) || y >= (height() - 1)) {
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
    // // sequence of indices for horizontal seam
    // public int[] findHorizontalSeam() {

    // }
 
    // // sequence of indices for vertical seam
    // public int[] findVerticalSeam() {

    // }
 
    // // remove horizontal seam from current picture
    // public void removeHorizontalSeam(int[] seam) {

    // }
 
    // // remove vertical seam from current picture
    // public void removeVerticalSeam(int[] seam) {

    // }
 
    //  unit testing (optional)
    public static void main(String[] args) {
        SeamCarver obj = new SeamCarver(new Picture("3x4.png"));
        //System.out.println(obj.height() + " " + obj.width());
        double[][] arr = obj.energyMat(obj.height(), obj.width());
        //System.out.println((obj.width() + "" + obj.height()));
        System.out.println(Arrays.toString(arr[0]));

    }
}
