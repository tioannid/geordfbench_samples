package gr.uoa.di.rdf.geordfbench.sample.specification;

import com.fasterxml.jackson.databind.JsonMappingException;
import gr.uoa.di.rdf.geordfbench.runtime.datasets.complex.IGeospatialDataSet;
import gr.uoa.di.rdf.geordfbench.runtime.datasets.complex.impl.GeographicaDS;
import gr.uoa.di.rdf.geordfbench.runtime.datasets.simple.impl.GenericGeospatialSimpleDS;
import gr.uoa.di.rdf.geordfbench.runtime.datasets.util.DataSetUtil;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.eclipse.rdf4j.rio.RDFFormat;
import java.nio.file.Paths;

public class LUBMDataSetUtil {

    // --- Static members -----------------------------
    static Logger logger
            = Logger.getLogger(LUBMDataSetUtil.class.getSimpleName());
    static final String LUBM_1_0_JSONDEF_FILE = "LUBM_1_0DSoriginal.json";
    static final String NTRIPLES_STR = RDFFormat.NTRIPLES.getName().toUpperCase();

    // --- Methods -----------------------------------
    /**
     * Creates the JSON definition file for the LUBM(1, 0) dataset in
     * ./json_defs/datasets/LUBM_1_0DSoriginal.json
     *
     */
    public static void createLUBM_1_0_OriginalJSONDefFile(String outputDir) {
        // 1. Create the simple dataset spec
        GenericGeospatialSimpleDS sds
                = new GenericGeospatialSimpleDS("lubm-1_0",
                        "1_0",
                        "lubm-1_0.nt", NTRIPLES_STR);
        // No need for dataset prefix
        //sds.addUsefulNamespacePrefix("somepref", "<http://lubm_1_0/prefix>");
        // No need for geospatial properties, since LUBM is not geospatial
        //sds.addAsWKT("asWKT", "<http://lubm_1_0/asWKT>");
        //sds.addHasGeometry("hasGeometry", "<http://lubm_1_0/hasGeometry>");

        // 2. Create a complex dataset spec (collection of one or more simple dataset specs)
        GeographicaDS lubm_ds
                = new GeographicaDS();
        lubm_ds.setName("lubm-1_0");
        lubm_ds.setRelativeBaseDir("LUBM");
        lubm_ds.setN(1);
        lubm_ds.addSimpleGeospatialDataSet(sds);
        // no context, use default graph
        lubm_ds.addSimpleGeospatialDataSetContext("lubm-1_0", "");

        try {
            lubm_ds.serializeToJSON(Paths.get(outputDir, LUBM_1_0_JSONDEF_FILE).toFile());
        } catch (JsonMappingException ex) {
            logger.error(ex.getMessage());
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        // check number of arguments and print usage information
        if (args.length != 1) {
            logger.info("Arguments not correct");
            logger.info("Arguments: " + args);
            logger.info("Usage: " + LUBMDataSetUtil.class.getSimpleName() + " <JSON spec output path>");
            System.exit(-1);
        }
        String outputDir = args[0];
        // LUBM(1, 0) Dataset
        LUBMDataSetUtil.createLUBM_1_0_OriginalJSONDefFile(outputDir);
        String outputFile = Paths.get(outputDir, LUBM_1_0_JSONDEF_FILE).toString();
        IGeospatialDataSet lubm_ds
                = DataSetUtil.deserializeFromJSON(outputFile);
        logger.info(lubm_ds.serializeToJSON());
    }
}
