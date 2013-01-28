/* Local Variables: */
/* eval: (unless junit-jar (setq junit-jar (car (find-lisp-find-files "~/.m2/repository/junit" ".*jar$")))) */
/* eval: (setq compile-command (format "export CLASSPATH=%s:%s" temporary-file-directory junit-jar)) */
/* eval: (setq compile-command (format "%s && javac -d %s %s" compile-command temporary-file-directory (buffer-name))) */
/* eval: (setq compile-command (format "%s && java org.junit.runner.JUnitCore %s" compile-command (file-name-sans-extension (buffer-name)))) */
/* End: */
import org.junit.Test;
import static org.junit.Assert.*;

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

    public void perm(String s) {
        for (int i = 0; i < s.length(); i++) {
            char[] str = s.toCharArray();
            for (int j = 0; j < s.length(); j++) {
                char buf = str[i];
                str[i] = str[j];
                str[j] = buf;
                System.out.println(new String(str));
            }
        }
    }

    public void swap(char[] s, int from, int to) {
        char buf = s[to];
        s[to] = s[from];
        s[from] = buf;
    }

    public void out(int level, String format, Object... args) {
        for (int i=0; i < level; i++) {
            System.out.print("\t");
        }
        System.out.println(String.format(format, args));
    }

    public void rperm(char[] s, int beg, int end) {
        if (beg==end) {
            out(beg, "%d | result | %s", beg, new String(s));
        } else {
            out(beg, "%d | enter | %s", beg, new String(s));
            for (int i = beg; i <= end; i++) {
                out(beg, "%d/%d | swap [%s -> %s] | %s", beg, i, s[beg], s[i], new String(s));
                swap(s, beg, i);
                rperm(s, beg + 1, end);
                swap(s, beg, i);
                out(beg, "%d/%d | backtrace | %s", beg, i, new String(s));
            }
        }
    }

    @Test
    public void replaceTest() {
        assertEquals("nbdy", replace("nobody", 'o'));
    }
 

    @Test
    public void rpermTest() {
        rperm("abc".toCharArray(), 0, "abc".length() - 1);
    }
 
    // @Test
    // public void main(String[] args) {

    // }
 

}
