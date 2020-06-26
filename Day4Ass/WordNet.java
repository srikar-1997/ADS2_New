import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Arrays;
import java.util.HashMap;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {
    private Digraph obj1;
    private WordNet wn;
    private HashMap<String, ArrayList<String>> synset = new HashMap<>();
    private HashMap<String, ArrayList<String>> hypernym = new HashMap<>();
    private int count = 0;
    private int count1 = 0;
    private String[] nouns1 = new String[100000];

    private WordNet() {
    }

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException("argument should not be null");
        }
        parseSynsets(synsets);
        parseHypernym(hypernyms);
        obj1 = new Digraph(hypernym.size());
        for (String key : hypernym.keySet()) {
            int v = Integer.parseInt(key);
            count++;
            for (String value : hypernym.get(key)) {
                int w = Integer.parseInt(value);
                obj1.addEdge(v, w);
                count1++;
            }
        }
        //System.out.println(count);
        //System.out.println(count1);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        if (nouns().iterator().equals(null)) {
            throw new IllegalArgumentException("argument should not be null");
        }
        return synset.keySet();

    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("argument should not be null");
        }
        return synset.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("noun is not in wordnet");
        }
        ArrayList<String> nA = synset.get(nounA);
        ArrayList<String> nB = synset.get(nounB);
        ArrayList<Integer> nA1 = new ArrayList<>();
        for (int i = 0; i < nA.size(); i++) {
            System.out.println(nA.size());
            nA1.add(Integer.parseInt(nA.get(i)));
        }
        ArrayList<Integer> nA2 = new ArrayList<>();
        for (int i = 0; i < nB.size(); i++) {
            nA2.add(Integer.parseInt(nB.get(i)));
        }
        SAP obj = new SAP(obj1);
        return obj.length(nA1, nA2);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA
    // and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("noun is not in wordnet");
        }
        ArrayList<String> nA = synset.get(nounA);
        ArrayList<String> nB = synset.get(nounB);
        ArrayList<Integer> nA1 = new ArrayList<>();
        for (int i = 0; i < nA.size(); i++) {
            nA1.add(Integer.parseInt(nA.get(i)));
        }
        ArrayList<Integer> nA2 = new ArrayList<>();
        for (int i = 0; i < nB.size(); i++) {
            nA2.add(Integer.parseInt(nB.get(i)));
        }
        SAP obj = new SAP(obj1);
        int i = obj.ancestor(nA1, nA2);
        return nouns1[i];
    }

    private void parseSynsets(String fileName) {
        if (fileName.equals(null)) {
            throw new IllegalArgumentException("argument should not be null");
        }
        String[] arr = WordNet.fileToArr(fileName);
        String[] arrSplit = new String[3];
        String[] nouns;
        // String[] store = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arrSplit = arr[i].split(",");
            nouns1[i] = arrSplit[1];
            nouns = arrSplit[1].split(" ");
            for (int j = 0; j < nouns.length; j++) {
                if (synset.containsKey(nouns[j])) {
                    synset.get(nouns[j]).add(arrSplit[0]);
                } else {
                    ArrayList<String> id = new ArrayList<>();
                    id.add(arrSplit[0]);
                    synset.put(nouns[j], id);
                }
            }
        }
        //System.out.println(synset);
        //System.out.println(Arrays.toString(nouns1));
    }

    private void parseHypernym(String fileName) {
        if (fileName.equals(null)) {
            throw new IllegalArgumentException("argument should not be null");
        }
        String[] arr = WordNet.fileToArr(fileName);
        String[] arrSplit = new String[2];
        String[] hypernyms;

        for (int i = 0; i < arr.length; i++) {
            ArrayList<String> s = new ArrayList<>();
            arrSplit = arr[i].split(",", 2);
            if (!arr[i].contains(",")) {
                hypernym.put(arr[i], s);
                continue;
            }
            // System.out.println(arrSplit[0]);
            hypernyms = arrSplit[1].split(",");
            for (int j = 0; j < hypernyms.length; j++) {
                if (hypernym.containsKey(arrSplit[0])) {
                    hypernym.get(arrSplit[0]).add(hypernyms[j]);
                } else {
                    s.add(hypernyms[j]);
                    hypernym.put(arrSplit[0], s);
                }
            }
        }
         //System.out.println(hypernym);
    }

    private static String[] fileToArr(String filename) {
        if (filename.equals(null)) {
            throw new IllegalArgumentException("argument should not be null");
        }
        ArrayList<String> text = new ArrayList<>();
        //File file = new File(filename);
        //Scanner sc = new Scanner(new File(filename));
        In sc = new In(filename);
        while (sc.hasNextLine()) {
            text.add(sc.readLine());
        }
        String[] array = text.toArray(new String[text.size()]);
        sc.close();
        return array;
    }

    public static void main(String[] args) {
        WordNet obj = new WordNet();
        String fileName = "synsets.txt";
        String fileName1 = "hypernyms.txt";
        new WordNet(fileName, fileName1);
        //System.out.println(Arrays.toString(obj.nouns1));
        //System.out.println(obj.hypernym);
    }
}