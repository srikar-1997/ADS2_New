import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Solution {
HashMap<Integer, String> email = new HashMap<>();
HashMap<Integer, ArrayList<Integer>> log = new HashMap<>();
HashMap<String, Integer> ss = new HashMap<>();

public void parseEmailLog(String fileName) throws Exception {
    String[] arr1 = Solution.fileToArr(fileName);
    String[] arrSplit1 = new String[2];
    String[] logSplit = new String[2];
    for (int i = 0; i < arr1.length; i++) {
        arrSplit1 = arr1[i].split(" ");
        logSplit = arrSplit1[1].split(",");
        if (log.containsKey(Integer.parseInt(arrSplit1[3]))) {
            log.get(Integer.parseInt(arrSplit1[3])).add(Integer.parseInt(logSplit[0]));
        } else {
            ArrayList<Integer> id = new ArrayList<>();
            id.add(Integer.parseInt(logSplit[0]));
            log.put(Integer.parseInt(arrSplit1[3]) ,id);
        }
    }
   //System.out.println(log.get(179170).size());

}

public void parseEmail(String fileName) throws Exception {
    String[] arr = Solution.fileToArr(fileName);
    String[] arrSplit = new String[2];

    for (int i = 0; i < arr.length; i++) {
        arrSplit = arr[i].split(";");
        //System.out.println(Arrays.toString(arrSplit));
        email.put(Integer.parseInt(arrSplit[0]), arrSplit[1]);
        
    }
    //System.out.println(email.get(179170));

}

    public static String[] fileToArr(String fileName) throws Exception {
        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        ArrayList<String> fil = new ArrayList<>();
        while (sc.hasNextLine()) {
            fil.add(sc.nextLine());
        }
        String[] arr = fil.toArray(new String[fil.size()]);
        return arr;
    }
    public static void main(String[] args) throws Exception{
        String file = "emails.txt";
        String file1 = "email-logs.txt";
        Solution obj = new Solution();
        int count = 0;
        obj.parseEmail(file);
       //ArrayList<Integer> s = new ArrayList<>();
        obj.parseEmailLog(file1);
        ArrayList<Integer> sort = new ArrayList<>();
        for (int i : obj.log.keySet()) {
            sort.add(obj.log.get(i).size());
        }
        Collections.sort(sort);
        Collections.reverse(sort);
        // for (int i = 0; i < 10; i++) {
        //     System.out.println(sort.get(i));
        // }
        int[] arr = new int[10];
        
            for (int  j = 0; j < 10; j++) {
                for (int i : obj.log.keySet()) {
                if (obj.log.get(i).size() == sort.get(j)) {
                    System.out.println(obj.email.get(i) + " " + sort.get(j));
                    //arr[j] = sort.get(j);
                }
            }
        }
        }
    }