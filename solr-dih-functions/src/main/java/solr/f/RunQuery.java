package solr.f;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.apache.commons.io.FileUtils.*;
import org.apache.solr.handler.dataimport.Context;
import org.apache.solr.handler.dataimport.DataImportHandlerException;
import org.apache.solr.handler.dataimport.Evaluator;
import org.apache.solr.handler.dataimport.EvaluatorBag;

public class RunQuery extends Evaluator {
    
    public String evaluate(String expression, Context context) {
        List args = EvaluatorBag.parseParams(expression, context.getVariableResolver());
        if (args.size() == 0) {
            return "";
        }
        
        File script = LoadQuery.getSqlFile(context, args.get(0).toString());
        if (!script.exists()) {
            return "";
        }

        try {
            context
                .getDataSource()
                .getData(context.getVariableResolver().replaceTokens(readFileToString(script)));
        } catch (IOException e) {
            DataImportHandlerException.wrapAndThrow(DataImportHandlerException.SEVERE, e);
        } catch (DataImportHandlerException e) {
            if (e.getCause() instanceof SQLException) {
                DataImportHandlerException.wrapAndThrow(DataImportHandlerException.SEVERE, e);
            }
        }
        return "";
    }
}
