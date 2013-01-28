/* Local Variables: */
/* eval: (unless junit-jar (setq junit-jar (car (find-lisp-find-files "~/.m2/repository/junit" ".*jar$")))) */
/* eval: (setq compile-command (format "export CLASSPATH=%s:%s" temporary-file-directory junit-jar)) */
/* eval: (setq compile-command (format "%s && javac -d %s %s" compile-command temporary-file-directory (buffer-name))) */
/* eval: (setq compile-command (format "%s && java org.junit.runner.JUnitCore %s" compile-command (file-name-sans-extension (buffer-name)))) */
/* End: */
import org.junit.*;
import java.util.Collection;
import java.util.TreeSet;

public class Strings {

    public String replace(String str, char skip) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != skip) {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    public String reverse(String s) {
        return s.length() == 1 ? s : reverse(s.substring(1)) + s.substring(0, 1);
    }

    public void swap(char[] s, int from, int to) {
        char buf = s[to];
        s[to] = s[from];
        s[from] = buf;
    }

    public Collection<String> perm(char[] s, Collection<String> bag, int... ptr) {
        int beg = ptr.length == 1 ? ptr[0] : 0;
        if (beg == s.length-1) {
            bag.add(new String(s));
        } else {
            for (int i = beg; i < s.length; i++) {
                swap(s, beg, i);
                perm(s, bag, beg + 1);
                swap(s, beg, i);
            }
        }
        return bag;
    }

    @Test
    public void replaceTest() {
        Assert.assertEquals("nbdy", replace("nobody", 'o'));
    }
 
    @Test
    public void reverseTest() {
        Assert.assertEquals("0987654321", reverse("1234567890"));
    }

    @Test
    public void rpermTest() {
        // factorial lenght unique elements
        Assert.assertEquals(1 * 2 * 3, perm("123".toCharArray(), new TreeSet<String>()).size());
        Assert.assertEquals(1 * 2 * 3 * 4, perm("1234".toCharArray(), new TreeSet<String>()).size());
    }

}
