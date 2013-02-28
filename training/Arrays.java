/* Local Variables: */
/* eval: (unless junit-jar (setq junit-jar (car (find-lisp-find-files "~/.m2/repository/junit" ".*jar$")))) */
/* eval: (setq compile-command (format "export CLASSPATH=%s:%s" temporary-file-directory junit-jar)) */
/* eval: (setq compile-command (format "%s && javac -d %s %s" compile-command temporary-file-directory (buffer-name))) */
/* eval: (setq compile-command (format "%s && java org.junit.runner.JUnitCore %s" compile-command (file-name-sans-extension (buffer-name)))) */
/* eval: (setq after-save-hook nil before-save-hook nil) */
/* End: */
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.junit.*;

public class Arrays {
    
    public int[] makeArray(int len) {
        int[] result = new int[len];
        Random rnd = new Random();
        for (int i = 0; i < len; i++) {
            result[i] = (int) (Math.random() * 99);
            // result[i] = len - i; // reverse
        }
        return result;
    }
    
    public void printArray(int[] array) {
        System.out.println("");
        for (int i = 0; i  < array.length; i++) {
            System.out.print(String.format("%-3d%s", array[i], (i+1) % 10 == 0 ? "\n" : " "));
        }
        System.out.println("");
    }

    public boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i-1] > a[i]) {
                return false;
            }
        }
        return true;
    }

    public void swap(int[] a, int from, int to) {
        int buf = a[to];
        a[to] = a[from];
        a[from] = buf;
    }

    public int[] quickSort(int[] a, int low, int high) {
        int p = a[low + ((high - low) / 2)],
            i = low, 
            j = high;
        while (i<j) {
            while(a[i] < p) {
                i++;
            }

            while(a[j] > p) {
                j--;
            }

            if (i<=j) {
                swap(a, i, j);
                i++; 
                j--;
            }
        }

        if (low < j) {
            quickSort(a, low, j);
        };

        if (i < high) {
            quickSort(a, i, high);
        };
        return a;
    }

    public int[] bubbleSort(int[] a) {
        int iter = 0;
        boolean swapped = true;
        int top = a.length - 1;
        while (swapped) {
            swapped = false;
            int t = top;
            for (int i = 0; i < t; i++) {
                iter++;
                if (a[i] > a[i+1]) {
                    swap(a, i, i + 1);
                    swapped = true;
                    top = i;
                }
            }
        }
        return a;
    }

    public int[] mergeSort(int[] a, int low, int high) {
        if (low >= high) {
            return a;
        }
        int mid = low + ((high - low) / 2);
        mergeSort(a, low, mid);
        mergeSort(a, mid + 1, high);

        int[] buf = new int[(high - low) + 1];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = a[low + i];
        }

        int i = low, 
            j = mid + 1,
            k = low;

        while (i <= mid && j <= high) {
            if (buf[i-low] <= buf[j-low]) {
                a[k] = buf[i-low];
                i++;
            } else {
                a[k] = buf[j-low];
                j++;
            }
            k++;
        }
        
        while (i <= mid) {
            a[k] = buf[i-low];
            k++;
            i++;
        }
        return a;
    }

    public int partition(int[] a, int low, int high, int pivot) {
        int pivotValue = a[pivot];
        swap(a, pivot, high);
        int storeIndex = low;
        for (int i = low; i <= high - 1; i++) {
            if (a[i] < pivotValue) {
                swap(a, storeIndex, i);
                storeIndex++;
            }
        }
        swap(a, high, storeIndex);
        return storeIndex; 
    }

    public int[] select(int[] a, int low, int high, int k) {
        if (low == high) {
            return a;
        }
        
        int pivotIndex = partition(a, low, high, a.length / 2);
        int pivotDest = pivotIndex - low + 1;
        if (pivotDest == k) {
            return a;
        } else {
            if (k < pivotDest) {
                return select(a, low, pivotIndex - 1, k);
            } else {
                return select(a, pivotIndex + 1, high, k - pivotDest);
            }
        }
        
    }




    @Test
    public void testPartition() {
        int[] a = makeArray(20);
        System.out.println("Original array:");
        printArray(a);
        // System.out.println("New pivot:");

        // System.out.println(String.format("pivot a[%d]: %d", a.length / 2, a[a.length / 2])); 
        // int pivot = partition(a, 0, a.length - 1, a.length / 2) ;
        // System.out.println(String.format("new pivot a[%d]: %d", pivot, a[pivot])); 

        // System.out.println("Partial array:");
        // printArray(a);

        printArray(select(a, 0, a.length - 1, 5));
    }

    // @Test
    public void testBubbleSort() {
        int a[] = makeArray(100);
        Assert.assertFalse(isSorted(a));
        Assert.assertTrue(isSorted(bubbleSort(a)));
    }


    public int findArray(int[] array, int[] subArray) {
        int pos = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == subArray[i - pos]) {
                if (subArray.length == (i - pos + 1)) {
                    return pos;
                }
            } else {
                pos = i;
            }
        }
        return -1;
    }


    public Map<String, String> decode(String s) {
        if (s == null) {
            return null;
        }
        Map<String, String> map = new TreeMap<String, String>();
        if (!"".equals(s)) {
            String[] pairs = s.split("&");
            for (int i = 0; i < pairs.length; i++) {
                String[] pair = pairs[i].split("=");
                String key = "";
                String val = "";
                if (pairs[i].indexOf("=") == 0) {
                    val = pair[0];
                } else {
                    key = pair[0];
                }
                if (pair.length == 2) {
                    key = pair[0];
                    val = pair[1];
                }
                map.put(key, val);
            }
        }
        return map;
    }

    public boolean isLossy(String s, String encoding) throws UnsupportedEncodingException
    {
        Charset cs = Charset.forName(encoding);
        if (cs == null) {
            throw new UnsupportedEncodingException(encoding);
        }
        CharsetDecoder decoder = cs.newDecoder();
        try {
            CharBuffer buf = decoder.decode(ByteBuffer.wrap(s.getBytes()));
        } catch (CharacterCodingException e) {
            return false;
        }
        return true;
    }



    public String findCommmonAncestor(String[] commitHashes, String[][] parentHashes, String commitHash1, String commitHash2) {
        int c1 = -1, c2 = -1;             
        int p1 = -1, p2 = -1;

        for (int i = 0; i < commitHashes.length; i++) {
            if (commitHash1.equals(commitHashes[i])) {
                c1 = i;
            }

            if (commitHash2.equals(commitHashes[i])) {
                c2 = i;
            }

            if (c1 >= 0) {
                p1++;
            }

            if (c2 >= 0) {
                p2++;
            }

            if (p1 >= 0 && p2 >= 0) {
                for (int j = 0; j < parentHashes[p1].length; j++) {
                    for (int k = 0; k < parentHashes[p2].length; k++) {
                        if (parentHashes[p1][j].equals(parentHashes[p2][k])) {
                            return parentHashes[p1][j];
                        }
                    }
                }
                
            }
            
        }
        return null;
    }

    public int testReturn() {
        try {
            return 4;
        } finally {
            return 6;
        }
    }


    public int[] halfMergeSort(int a[]) {
        if (a.length <= 1) {
            return a;
        }
        int mid = (a.length / 2);

        int[] left = halfMergeSort(java.util.Arrays.copyOfRange(a, 0, mid));
        int[] right = halfMergeSort(java.util.Arrays.copyOfRange(a, mid, a.length));

        int[] merged = new int[a.length];
        int l = 0, r = 0, i = 0;
        while (l < left.length && r < right.length) {
            if (left[l] <= right[r]) {
                merged[i] = left[l];
                l++;
            } else {
                merged[i] = right[r];
                r++;
            }
            i++;
        }

        int[] tail = r < right.length ? right : left;
        int t  = r < right.length ? r : l;
            
        while (i < merged.length) {
            merged[i] = tail[t];
            t++;
            i++;
        }
        return merged;
    }



    // public int[] halfMergeSort(int[] a, int low, int high) {
    //     if (low > high) {
    //         return a;
    //     }
    //     int mid = (high - low) / 2;
    //     halfMergeSort(a, low, mid);
    //     halfMergeSort(a, mid + 1, high);

    //     int[]
        
    //     return a;
    // }

   // @test 
   public void test() throws Exception {
       System.out.println(testReturn());
        System.out.println("10:");
        for (int i = 1024; i <= 1034; i+=1) {
            System.out.println(i % 2048);
        }
        
        // System.out.println("11:");

        // for (int i = 0; i <= 10; i+=2) {
        //     System.out.println(i % 11);
        // }
    }



    public void testaa() throws Exception {
        // String[] commits = {"G", "F", "E", "D", "C", "B", "A"};
        // String[][] parents ={{"F","D"},{"E"}, {"B"}, {"C"}, {"B"}, {"A"}, null}; 
        // String commit1 = "D";
        // String commit2 = "F";
        // System.out.println(findCommmonAncestor(commits, parents, commit1, commit2));
    }

    // @Test
    public void testQuickSort() {
        int a[] = makeArray(10000000);
        Assert.assertFalse(isSorted(a));
        Assert.assertTrue(isSorted(quickSort(a, 0, a.length - 1)));
    }

    // @Test
    public void testMergeSort() {
        int a[] = makeArray(10000000);
        // Assert.assertFalse(isSorted(a));
        // Assert.assertTrue(isSorted(mergeSort(a, 0, a.length - 1)));
        mergeSort(a, 0, a.length - 1);
    }

   // @Test
   public void testHalfMergeSort() {
        int a[] = makeArray(10000000);
        // Assert.assertFalse(isSorted(a));
        // Assert.assertTrue(isSorted(halfMergeSort(a)));
        halfMergeSort(a);
        
        // printArray(a);
        // System.out.println("and");
        // printArray(halfMergeSort(a));
    }

}