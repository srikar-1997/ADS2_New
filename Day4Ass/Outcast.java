public class Outcast {
    private WordNet wordnet;
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int c = 0;
    String outcast = null;

    for (String i : nouns) {
    int dist = 0;
    for (String j : nouns) {
    int dist_len = wordnet.distance(i, j);
    // System.out.println("distance (" + i + "," + j + ") :" + dist_len);
    dist += dist_len;
    }
    // System.out.println("max. distance: " + distance);
    if (dist > c) {
    c = dist;
    outcast = i;
    }
    // System.out.println("distance: " + d + "outcast: " + outcast);
    }
    return outcast;
    }
    
    // see test client below
    public static void main(String[] args) {

    }
 }