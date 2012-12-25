package solr.f;

import java.util.HashMap;
import java.util.Map;

import org.apache.solr.handler.dataimport.Context;
import org.apache.solr.handler.dataimport.ContextImpl;
import org.apache.solr.handler.dataimport.VariableResolverImpl;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *  Unit Test for class DecodeTest
 *
 *
 * Created: Fri Aug 24 10:01:25 2012
 *
 */
public class DecodeTest {

    Context ctx;
    Decode decode;
    
    @Before
    public void setup() {
        Map<String, Object> foo = new HashMap();
        foo.put("bar", "bar");
        VariableResolverImpl resolver = new VariableResolverImpl(foo);
        resolver.addNamespace("foo", foo);
        ctx = new ContextImpl(null, resolver, null, null, null, null, null);
        decode =  new Decode();
    }

    @Test
    public void testMethod() {
        assertEquals("rigth", decode.evaluate("foo.bar, 'bar', 'rigth', 'wrong'", ctx));
        assertEquals("rigth", decode.evaluate("foo.bar, 'yaaar', 'wrong', 'rigth'", ctx));
        assertEquals("", decode.evaluate("foo.barn, 'bar', 'wrong'", ctx));
        assertEquals("right", decode.evaluate("foo.barn, 'bar', 'wrong', 'right'", ctx));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArgs() {
        decode.evaluate("", ctx);
    }

}
