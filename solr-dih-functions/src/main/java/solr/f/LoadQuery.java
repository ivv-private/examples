package solr.f;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static org.apache.commons.collections.CollectionUtils.*;
import static org.apache.commons.collections.PredicateUtils.*;
import static org.apache.commons.collections.TransformerUtils.*;
import static org.apache.commons.io.FileUtils.*;
import static org.apache.commons.lang.ObjectUtils.*;
import org.apache.solr.handler.dataimport.Context;
import static org.apache.solr.handler.dataimport.Context.*;
import org.apache.solr.handler.dataimport.DataImportHandlerException;
import org.apache.solr.handler.dataimport.Evaluator;
import org.apache.solr.handler.dataimport.EvaluatorBag;

public class LoadQuery extends Evaluator {

    public static String getSqlPath(Context context) {
        return context.getSessionAttribute("sql-path", SCOPE_ENTITY) != null
            ? (String) context.getSessionAttribute("sql-path", SCOPE_ENTITY)
            : context.getSolrCore().getSolrConfig().getResourceLoader().getConfigDir();
    }

    public static File getSqlFile(Context context, String fileName) {
        return new File(new File(getSqlPath(context)), fileName);
    }

    public String evaluate(String expression, Context context) {
        String entityName = context.getEntityAttribute("name");
        String suffix = context.currentProcess().toLowerCase(Locale.ENGLISH).replace('_', '-') + ".sql";
        Collection suggestions = new LinkedList<File>();

        try {
            if (expression.startsWith("dataimporter.functions.")) {
                suggestions.add(getSqlFile(context, context.getVariableResolver().replaceTokens( "${" + expression + "}")));
            } else {
                List args = EvaluatorBag.parseParams(expression, context.getVariableResolver());
                suggestions.add(getSqlFile(context, args.get(0).toString()));
            }
        } catch (StringIndexOutOfBoundsException e) {
            suggestions.add(getSqlFile(context, entityName + "-" + suffix));
            suggestions.add(getSqlFile(context, suffix));
        }
        
        File f = (File) find(suggestions, invokerPredicate("exists"));
        try {
            return f == null ? "" : context.getVariableResolver().replaceTokens(readFileToString(f));
        } catch (IOException e) {
            DataImportHandlerException.wrapAndThrow(DataImportHandlerException.SEVERE, e);
            return "";
        }
    }
}
