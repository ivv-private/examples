/* Local Variables: */
/* eval: (unless junit-jar (setq junit-jar (car (find-lisp-find-files "~/.m2/repository/junit" ".*jar$")))) */
/* eval: (setq compile-command (format "export CLASSPATH=%s:%s" temporary-file-directory junit-jar)) */
/* eval: (setq compile-command (format "%s && javac -d %s %s" compile-command temporary-file-directory (buffer-name))) */
/* eval: (setq compile-command (format "%s && java org.junit.runner.JUnitCore %s" compile-command (file-name-sans-extension (buffer-name)))) */
/* End: */
import org.junit.Test;
import static org.junit.Assert.*;

public class Polindrome {

    public String maxPolindrome2(String testee) {
        char[] s = testee.toCharArray();
        int[] max = new int[]{0, 0};
        int[] point = new int[]{0, 0};

        while (point[1] < s.length) {
            if (point[1] + 1 >= s.length)  {
                break;
            }

            // reduce seq
            if (s[point[1]] == s[point[1] + 1]) {
                point[1]++;
                continue;
            }

            for (int j = point[1]; j < s.length; j++) {
                // check bounds
                if ((point[0] - 1 < 0) || (point[1] + 1 >= s.length)) {
                    break;
                }
                point[0]--;
                point[1]++;
                if (s[point[0]] == s[point[1]]) {
                    if ((max[1] - max[0]) < (point[1] - point[0])) {
                        max = java.util.Arrays.copyOf(point, 2);
                    } else {
                        break;
                    }
                }
            }
            
            point[1]++;
            point[0] = point[1];
            // System.out.println(max[0]);
        }

        System.out.println(new String(s, max[0], max[1]-max[0]+1));
        return new String(s, max[0], max[1]-max[0]+1);
    }
    
    
    @Test
    public void makeTest() {
        assertEquals("abba", maxPolindrome2("abba"));
        assertEquals("aba", maxPolindrome2("aba"));
        assertEquals("hovoh", maxPolindrome2("adrhovoh"));
    }

}
