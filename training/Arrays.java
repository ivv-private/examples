/* Local Variables: */
/* eval: (unless junit-jar (setq junit-jar (car (find-lisp-find-files "~/.m2/repository/junit" ".*jar$")))) */
/* eval: (setq compile-command (format "export CLASSPATH=%s:%s" temporary-file-directory junit-jar)) */
/* eval: (setq compile-command (format "%s && javac -d %s %s" compile-command temporary-file-directory (buffer-name))) */
/* eval: (setq compile-command (format "%s && java org.junit.runner.JUnitCore %s" compile-command (file-name-sans-extension (buffer-name)))) */
/* eval: (setq after-save-hook nil) */
/* End: */
import java.util.Random;

import org.junit.*;

public class Arrays {
    
    public int[] makeArray(int len) {
        int[] result = new int[len];
        Random rnd = new Random();
        for (int i = 0; i < len; i++) {
            result[i] = (int) (Math.random() * len);
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

    @Test
    public void testMergeSort() {
        int a[] = makeArray(100);
        printArray(a);
        Assert.assertFalse(isSorted(a));
        Assert.assertTrue(isSorted(mergeSort(a, 0, a.length - 1)));
        printArray(a);
    }

    @Test
    public void testBubbleSort() {
        int a[] = makeArray(100);
        Assert.assertFalse(isSorted(a));
        Assert.assertTrue(isSorted(bubbleSort(a)));
    }


    @Test
    public void testQuickSort() {
        int a[] = makeArray(100);
        Assert.assertFalse(isSorted(a));
        Assert.assertTrue(isSorted(quickSort(a, 0, a.length - 1)));
    }
}