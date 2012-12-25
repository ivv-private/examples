package solr.f;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.Closure;
import static org.apache.commons.collections.CollectionUtils.*;
import static org.apache.commons.io.FileUtils.*;
import org.apache.solr.handler.dataimport.Context;
import static org.apache.solr.handler.dataimport.Context.*;
import org.apache.solr.handler.dataimport.ContextImpl;
import org.apache.solr.handler.dataimport.DataConfig;
import org.apache.solr.handler.dataimport.VariableResolverImpl;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *  Unit Test for class LoadQuery
 *
 *
 * Created: Wed Aug 29 10:10:33 2012
 *
 */
public class LoadQueryTest {

    File path = new File("target/test-classes/solr/load-query-test");
    ContextImpl ctx;
    LoadQuery loadQuery = new LoadQuery();

    @Before
    public void setup() {
        path.mkdir();

        Map<String, Object> foo = new HashMap();
        foo.put("bar", "bar");
        VariableResolverImpl resolver = new VariableResolverImpl(foo);
        resolver.addNamespace("bar", foo);

        DataConfig.Entity entity = new DataConfig.Entity();
        entity.allAttributes = new HashMap<String, String>();
        entity.allAttributes.put("name", "party");

        ctx = new ContextImpl(entity, resolver, null, Context.FULL_DUMP, null, null, null);
        ctx.setSessionAttribute("sql-path", path.toString(), SCOPE_ENTITY);
    }

    @After
    public void removePath() throws IOException {
        deleteDirectory(path);
    }
    
    public void writeFiles(Map<String, String> meta) {
        forAllDo(meta.entrySet(), new Closure() {
                public void execute(Object r) {
                    Map.Entry<String, String> row = (Map.Entry<String, String>) r;
                    try {
                        writeStringToFile(new File(path, row.getKey()), row.getValue());
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            });
    }

    @Test
    public void testOrder() throws Exception {
        writeFiles(new HashMap() {{
            put("manual.sql", "true");
            put("full-dump.sql", "false");
            put("party-full-dump.sql", "false");
        }});
        assertEquals("true", loadQuery.evaluate("'manual.sql'", ctx));
    }

    @Test
    public void testDefNamed() throws Exception {
        writeFiles(new HashMap() {{
            put("full-dump.sql", "false");
            put("party-full-dump.sql", "true");
        }});
        assertEquals("true", loadQuery.evaluate("", ctx));
    }

    @Test
    public void testDef() throws Exception {
        writeFiles(new HashMap() {{
            put("full-dump.sql", "true");
        }});
        assertEquals("true", loadQuery.evaluate("", ctx));
    }

    @Test
    public void testContent() throws Exception {
        writeFiles(new HashMap() {{
            put("full-dump.sql", "select foo from ${bar.bar}");
        }});
        assertEquals("select foo from bar", loadQuery.evaluate("", ctx));
    }

}
