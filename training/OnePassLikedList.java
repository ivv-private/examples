/* Local Variables: */
/* eval: (unless junit-jar (setq junit-jar (car (find-lisp-find-files "~/.m2/repository/junit" ".*jar$")))) */
/* eval: (setq compile-command (format "export CLASSPATH=%s:%s" temporary-file-directory junit-jar)) */
/* eval: (setq compile-command (format "%s && javac -d %s %s" compile-command temporary-file-directory (buffer-name))) */
/* eval: (setq compile-command (format "%s && java org.junit.runner.JUnitCore %s" compile-command (file-name-sans-extension (buffer-name)))) */
/* End: */
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.*;


public class OnePassLikedList {

    public static class Cell {
        String data;
        Cell next;
        
        public String toString() {
            return data;
        }
    }
    
    public Entry<String, Entry> makeLinkedList(int count) {
        Entry<String, Entry> root = new AbstractMap.SimpleEntry(String.valueOf(1), null);
        Entry<String, Entry> node = root;
        for (int i = 2; i <= count; i++) {
            node.setValue(new AbstractMap.SimpleEntry(String.valueOf(i), null));
            node = node.getValue();
        }
        return root;
    }

    public Cell construct(int count) {
        Cell root = new Cell(), 
            cur = root;
        root.data = "1";
        for (int i = 2; i <= count; i++) {
            cur.next = new Cell();
            cur.next.data = String.valueOf(i);
            cur = cur.next;
        }
        return root;
    }

    public String getMiddle() {
        Entry<String, Entry> node = makeLinkedList(100);
        Entry<String, Entry> middleNode = node;
        boolean skip = false;
        while (node.getValue() != null) {
            if (!skip) {
                middleNode = middleNode.getValue();
            }
            skip = !skip;
            node = node.getValue();
        }
        return middleNode.getKey();
    }
    
    public String getByLag(int lag) {
        Entry<String, Entry> node = makeLinkedList(100);
        Entry<String, Entry> middleNode = node;
        int curLag = 0;
        while (node.getValue() != null) {
            if (curLag == lag) {
                middleNode = middleNode.getValue();
            } else {
                curLag++;
            }
            node = node.getValue();
        }
        return middleNode.getKey();
    }

    public Cell getLoop(Cell list) {
        int i = 0;
        Cell slow = list, fast = list.next;
        boolean skip = true;
        while (fast != null && fast != slow) {
            System.out.println(String.format("%s\t%s\t%d", fast, slow, ++i));
            fast = fast.next;
            if (!skip) {
                slow = slow.next;
            }
            skip = !skip;
        }
        return fast == slow ? fast : null;
    }

    public boolean hasLoop(Cell list) {
        Cell slow = list,
            fast1 = list,
            fast2 = list;

        while (slow != null 
               && (fast1 = fast2.next) != null 
               && (fast2 = fast1.next) != null) {

            if (slow == fast1 || slow == fast2) {
                return true;
            }
            slow = slow.next;
        }
        return false;
    }

    public Cell getNth(Cell list, int n) {
        Cell ptr = list;
        for (int i = 1; i <= n - 1; i++) {
            ptr = ptr.next;
        }
        return ptr;
    }

    public void print(Cell list) {
        Cell i = list;
        while (i != null) {
            System.out.println(i);
            i = i.next;
        }
    }

    public Cell last(Cell list) {
        return list.next == null ? list : last(list.next);        
    }

    public Cell reverseRecursive(Cell list) {
        if (list.next == null) {
            return list;
        }

        Cell next = list.next;
        list.next = null;
        Cell rest = reverse(next);
        next.next = list;
        return rest;
    }

    public Cell reverse(Cell list) {
        Cell cell = list;
        Cell prev = null;
        while (cell != null) {
            Cell rest = cell.next;
            cell.next = prev;
            prev = cell;
            cell = rest;
        }
        return prev;
    }

    @Test
    public void testFindLoop() {
        Cell list = construct(100);
        Assert.assertFalse(hasLoop(list));
        last(list).next = getNth(list, 50);
        Assert.assertTrue(hasLoop(list));
    }

    @Test
    public void testReverse() {
        Cell list = construct(6);
        // Assert.assertEquals("100", reverse(list).data);
        print(list);
        System.out.println("doh");
        print(reverse(list));
    }


    // @Test
    public void testMiddle() {
        // one-pass calculation of the middle item
        Assert.assertEquals("50", getMiddle());
    }

    // @Test
    public void testLag() {
        // one-pass calculation of the middle item
        Assert.assertEquals("70", getByLag(30));
    }
}
