

import java.util.List;
import java.util.Map;
import java.util.Arrays;


public class Sort {

    public static void main(String[] args) {
	int[] a = new int[]{4, 5, 2, 3, 1, 2, 8};
	System.out.println(Arrays.toString(quickSortP(a, 0, a.length - 1)));
    }

    public static void swap(int[] a, int from, int to) {
	if (from == to || a[from] == a [to]) return;
	
	int buf = a[from];
	a[from] = a[to];
	a[to] = buf;	
	System.out.printf("swaped: %d <-> %d\n", from, to);
	System.out.println(Arrays.toString(a));
    }

    public static int part2(int[] a, int low, int high) {
	int pidx = low + ((high - low) / 2);
	int p = a[pidx];

        swap(a, pidx, high);
        int storeIndex = low;
        for (int i = low; i < high; i++) {
            if (a[i] < p) {
                swap(a, storeIndex, i);
                storeIndex++;
            }
        }
        swap(a, high, storeIndex);
        return storeIndex; 
    }


    public static int part(int[] a, int beg, int end) {
	int pivot = a[beg + ((end - beg) / 2)];
	
	int i = beg;
	int j = end;

	while (i < j) {
	    while (a[i] < pivot) i++;
	    while (a[j] > pivot) j--;

	    if (i <= j) swap(a, i++, j--);
	}

	return i;
    }

    public static int[] quickSortP(int[] a, int start, int end) {
	int p = part(a, start, end);

	if (p > start)
	    quickSort(a, start, p);

	if (p < end)
	    quickSort(a, p, end);
 
	return a;
    }


    public static int[] quickSort(int[] a, int start, int end) {
	int pivot = a[start + ((end - start) / 2)];
	int i = start;
	int j = end;

	while (i < j) {
	    while (a[i] < pivot) i++;
	    while (a[j] > pivot) j--;
	    
	    if (i <= j) swap(a, i++, j--);
	}

	if (j > start)
	    quickSort(a, start, j);

	if (i < end)
	    quickSort(a, i, end);
 
	return a;
    }

    
}
