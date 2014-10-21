

import java.util.Arrays;


public class Merge {

    public static void main(String[] args) {
	int[] a = new int[]{4, 5, 2, 3, 1, 2, 9, 8};
	System.out.println(Arrays.toString(merge(a, 0, a.length - 1)));
    }

    public static int[] merge(int[] a, int from, int to) {
	int mid = from + ((to - from) / 2);

	if (to <= from) {
	    return a;
	}
	
	merge(a, from, mid);
	merge(a, mid + 1, to);

	int[] buf = new int[to - from + 1];
	for (int i = 0; i < buf.length; i++) buf[i] = a[from + i];

	int l = from;
	int r = mid + 1;
	int i = from;

	while (l <= mid && r <= to) {
	    a[i++] = buf[l - from] < buf[r - from] ? buf[l++ - from] : buf[r++ - from];
	}
	
	int rest = l > mid ? r : l;

	while (i <= to) 
	    a[i++] = buf[rest++ - from];

	return a;
    }
    
}
