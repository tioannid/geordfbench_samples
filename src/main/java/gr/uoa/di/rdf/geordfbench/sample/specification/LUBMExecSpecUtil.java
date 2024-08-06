package gr.uoa.di.rdf.geordfbench.sample.specification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import gr.uoa.di.rdf.Geographica3.runtime.executionspecs.IExecutionSpec;
import gr.uoa.di.rdf.Geographica3.runtime.executionspecs.impl.SimpleES;
import gr.uoa.di.rdf.Geographica3.runtime.executionspecs.util.ExecutionSpecUtil;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.eclipse.rdf4j.rio.RDFFormat;
import java.nio.file.Paths;

public class LUBMExecSpecUtil {

    // --- Static members -----------------------------
    static Logger logger
            = Logger.getLogger(LUBMExecSpecUtil.class.getSimpleName());
    static final String LUBM_1_0_JSONDEF_FILE = "LUBM_1_0ESoriginal.json";
    static final String NTRIPLES_STR = RDFFormat.NTRIPLES.getName().toUpperCase();

    // --- Methods -----------------------------------
    /**
     * Creates the JSON definition file for the LUBM(1, 0) execution
     * specification in ./json_defs/executionspecs/LUBM_1_0ESoriginal.json
     *
     */
    public static void createLUBM_1_0_OriginalJSONDefFile(String outputDir) {
        // describe number of repetitions for each execution type
        Map<IExecutionSpec.ExecutionType, Integer> execTypeReps
                = new HashMap<>();
        execTypeReps.put(IExecutionSpec.ExecutionType.COLD, 3);
        execTypeReps.put(IExecutionSpec.ExecutionType.WARM, 3);
        SimpleES lubm_es = new SimpleES(
                execTypeReps, // execution types map
                30 * 60, // max duration (secs) per query repetition
                60 * 60, // max duration (secs) per execution 
                IExecutionSpec.Action.RUN, // run experiment instead of printing groud queryset
                // the "average" function to use as the query execution time
                IExecutionSpec.AverageFunction.QUERY_MEDIAN,
                // what to do if cold query exec fails
                IExecutionSpec.BehaviourOnColdExecutionFailure.SKIP_REMAINING_ALL_QUERY_EXECUTIONS,
                5000 // delay (msecs) for clear system cache to take effect
        );
        try {
            lubm_es.serializeToJSON(Paths.get(outputDir, LUBM_1_0_JSONDEF_FILE).toFile());
        } catch (JsonMappingException ex) {
            logger.error(ex.getMessage());
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    public static String serializeToJSON(SimpleES lubm_es) {
        // create the mapper
        ObjectMapper mapper = new ObjectMapper();

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // enable pretty printing
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // serialize the object
        StringWriter strwrt = new StringWriter();
        try {
            mapper.writeValue(strwrt, lubm_es);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
        return strwrt.toString();
    }

    public static void main(String[] args) {
        // check number of arguments and print usage information
        if (args.length != 1) {
            logger.info("Arguments not correct");
            logger.info("Arguments: " + args);
            logger.info("Usage: " + LUBMExecSpecUtil.class.getSimpleName() + " <JSON spec output path>");
            System.exit(-1);
        }
        String outputDir = args[0];
        // LUBM(0, 1) Workload
        LUBMExecSpecUtil.createLUBM_1_0_OriginalJSONDefFile(outputDir);
        String outputFile = Paths.get(outputDir, LUBM_1_0_JSONDEF_FILE).toString();
        SimpleES lubm_es
                = (SimpleES) ExecutionSpecUtil.deserializeFromJSON(outputFile);
        logger.info(serializeToJSON(lubm_es));
    }
}
