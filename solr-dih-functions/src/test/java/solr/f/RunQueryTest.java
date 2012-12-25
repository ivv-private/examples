package solr.f;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import static org.apache.commons.io.FileUtils.*;
import org.apache.solr.handler.dataimport.*;
import static org.apache.solr.handler.dataimport.Context.*;
import org.apache.solr.handler.dataimport.DataConfig.Entity;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
/**
 *  Unit Test for class 
 *
 *
 * Created: Wed Aug 29 10:10:33 2012
 *
 */
public class RunQueryTest {

    File script = new File("target/a-before.sql");

    @Before
    public void setup() throws IOException {
        script.createNewFile();
        writeStringToFile(script, "foo-bar");
    }

    @After
    public void removePath() throws IOException {
        script.delete();
    }
    
    @Test
    public void testRun() throws Exception {
        RunQuery func = new RunQuery();

        Map<String, Object> foo = new HashMap();
        foo.put("bar", "bar");
        VariableResolverImpl resolver = new VariableResolverImpl(foo);
        resolver.addNamespace("bar", foo);
        
        Entity entity = new DataConfig.Entity();
        entity.allAttributes = new HashMap<String, String>();
        entity.allAttributes.put("name", "a");

        final AtomicInteger i = new AtomicInteger(0);
        Context ctx = new ContextImpl(entity, resolver, null, Context.FULL_DUMP, null, null, null) {
                public DataSource getDataSource() {
                    return new DataSource<Object>() {
                        public void init(Context context, Properties initProps) {
                        }

                        public Object getData(String query) {
                            i.compareAndSet(0, 1);
                            assertEquals("foo-bar", query);
                            return "";
                        }

                        public void close() {
                        }
                    };
                }
            };

        ctx.setSessionAttribute("sql-path", "target/", SCOPE_ENTITY);
        func.evaluate("'a-before.sql'", ctx);
        assertEquals(1, i.get());
    }

}
