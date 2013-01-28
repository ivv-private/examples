/* Local Variables: */
/* eval: (unless junit-jar (setq junit-jar (car (find-lisp-find-files "~/.m2/repository/junit" ".*jar$")))) */
/* eval: (setq compile-command (format "export CLASSPATH=%s:%s" temporary-file-directory junit-jar)) */
/* eval: (setq compile-command (format "%s && javac -d %s %s" compile-command temporary-file-directory (buffer-name))) */
/* eval: (setq compile-command (format "%s && java org.junit.runner.JUnitCore %s" compile-command (file-name-sans-extension (buffer-name)))) */
/* End: */
import org.junit.*;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;

public class OnePassLikedList {
    
    public Entry<String, Entry> makeLinkedList(int count) {
        Entry<String, Entry> root = new AbstractMap.SimpleEntry(String.valueOf(1), null);
        Entry<String, Entry> node = root;
        for (int i = 2; i <= count; i++) {
            node.setValue(new AbstractMap.SimpleEntry(String.valueOf(i), null));
            node = node.getValue();
        }
        return root;
    }

    public String doit() {
        Entry<String, Entry> node = makeLinkedList(100);
        Entry<String, Entry> middleNode = node;
        boolean skip = true;
        while (node.getValue() != null) {
            if (!skip) {
                middleNode = middleNode.getValue();
            }
            skip = !skip;
            node = node.getValue();
        }
        return middleNode.getKey();
    }
    
    public String doitLag(int lag) {
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
    
    @Test
    public void testMiddle() {
        Assert.assertEquals("50", doit());
    }

    @Test
    public void testLag() {
        Assert.assertEquals("70", doitLag(30));
    }
}
