package solr.f;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import static org.apache.commons.lang.StringUtils.*;
import org.apache.solr.handler.dataimport.Context;
import org.apache.solr.handler.dataimport.Evaluator;
import org.apache.solr.handler.dataimport.EvaluatorBag;

/**
 * <code>Decode</code> is analog of the Oracle's "decode" function.
 *
 * @version 1.0
 */
public class Decode extends Evaluator {

    public String evaluate(String expression, Context context) {
        Iterator<Object> i;
        String master;
        try {
            List l = EvaluatorBag.parseParams(expression, context.getVariableResolver());
            master = defaultString(ObjectUtils.toString(l.get(0)));
            i = l.listIterator(1);
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Decode accepts at least 1 argument", e);
        }

        while (i.hasNext()) {
            String key = defaultString(ObjectUtils.toString(i.next()));
            if (!i.hasNext()) {
                return key; // last value is default
            } 
            String value = defaultString(ObjectUtils.toString(i.next()));
            if (master.equals(key)) {
                return value;
            }
        }
        return "";
    }
}
