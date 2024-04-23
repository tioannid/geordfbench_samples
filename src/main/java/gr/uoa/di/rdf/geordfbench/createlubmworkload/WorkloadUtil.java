package gr.uoa.di.rdf.geordfbench.createlubmworkload;

import com.fasterxml.jackson.databind.JsonMappingException;
import gr.uoa.di.rdf.Geographica3.runtime.datasets.complex.impl.GeographicaDS;
import gr.uoa.di.rdf.Geographica3.runtime.datasets.simple.impl.GenericGeospatialSimpleDS;
import gr.uoa.di.rdf.Geographica3.runtime.executionspecs.impl.SimpleES;
import gr.uoa.di.rdf.Geographica3.runtime.querysets.partofworkload.impl.StaticTempParamQS;
import gr.uoa.di.rdf.Geographica3.runtime.querysets.simple.IQuery;
import gr.uoa.di.rdf.Geographica3.runtime.querysets.simple.impl.SimpleQuery;
import gr.uoa.di.rdf.Geographica3.runtime.workloadspecs.IGeospatialWorkLoadSpec;
import gr.uoa.di.rdf.Geographica3.runtime.workloadspecs.impl.SimpleGeospatialWL;
import gr.uoa.di.rdf.Geographica3.runtime.workloadspecs.util.WorkLoadSpecUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.eclipse.rdf4j.rio.RDFFormat;
import java.nio.file.Paths;

public class WorkloadUtil {

    // --- Static members -----------------------------
    static Logger logger
            = Logger.getLogger(WorkloadUtil.class.getSimpleName());
    static final String LUBM_1_0_JSONDEF_FILE = "LUBM_1_0_WLoriginal_GOLD_STANDARD.json";
    static final String NTRIPLES_STR = RDFFormat.NTRIPLES.getName().toUpperCase();

    // --- Methods -----------------------------------
    // A) -- Methods that can re-create the JSON definition files
    //       for GeoRDFBench workloads
    /**
     * Creates the JSON definition file for the LUBM(1, 0) workload in
     * ./json_defs/workloads/LUBM_1_0_WLoriginal_GOLD_STANDARD.json
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

        // 3. Create a queryset spec
        Map<String, String> mapTemplateParams = new HashMap<>();
        Map<String, String> mapUsefulNamespacePrefixes = new HashMap<>();
        mapUsefulNamespacePrefixes.put("ub", "<https://swat.cse.lehigh.edu/onto/univ-bench.owl#>");
        // populate Graph prefixes map
        Map<String, String> mapLiteralValues = new HashMap<>();

        Map<Integer, IQuery> mapQry = new HashMap<>();
        // using SimpleQuery(String label, String query, boolean usePredicate, long expectedResults)
        mapQry.put(0, new SimpleQuery("Q1_GradStudents_Taken_GradCource0_At_Univ0",
                "SELECT ?x WHERE { \n ?x rdf:type ub:GraduateStudent . \n ?x ub:takesCourse <http://www.Department0.University0.edu/GraduateCourse0> .\n} \n",
                false,
                4));
        mapQry.put(1, new SimpleQuery("Q2_GradStudents_From_Any_Univ_Depart",
                "SELECT ?x ?y ?z WHERE { \n ?x rdf:type ub:GraduateStudent .\n"
                + " ?y rdf:type ub:University .\n"
                + " ?z rdf:type ub:Department .\n"
                + " ?x ub:memberOf ?z .\n"
                + " ?z ub:subOrganizationOf ?y .\n"
                + " ?x ub:undergraduateDegreeFrom ?y .\n} \n",
                false,
                0));
        mapQry.put(2, new SimpleQuery("Q3_Publications_Of_AssistProfessor0_In_Univ0_Depart0",
                "SELECT ?x WHERE { \n ?x rdf:type ub:Publication .\n"
                + " ?x ub:publicationAuthor <http://www.Department0.University0.edu/AssistantProfessor0> .\n} \n",
                false,
                6));
        mapQry.put(3, new SimpleQuery("Q14_UndergradStudents_From_Any_Univ_Depart",
                "SELECT ?x WHERE { \n ?x rdf:type ub:UndergraduateStudent .\n} \n",
                false,
                5916));

        StaticTempParamQS lubm_qs = new StaticTempParamQS(SimpleES.newMicroES(), "lubm-1_0", "", false, mapQry,
                mapTemplateParams, mapUsefulNamespacePrefixes,
                mapLiteralValues);
        SimpleGeospatialWL swl = new SimpleGeospatialWL("LUBM-1_0", "",
                lubm_ds,
                lubm_qs);
        try {

            swl.serializeToJSON(Paths.get(outputDir, LUBM_1_0_JSONDEF_FILE).toFile());
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
            logger.info("Usage: " + WorkloadUtil.class.getSimpleName() + " <JSON spec output path>");
            System.exit(-1);
        }
        String outputDir = args[0];
        String outputFile = Paths.get(outputDir, LUBM_1_0_JSONDEF_FILE).toString();
        // LUBM Workload
        WorkloadUtil.createLUBM_1_0_OriginalJSONDefFile(outputDir);
        IGeospatialWorkLoadSpec lubm1_0WLS
                = WorkLoadSpecUtil.deserializeFromJSON(outputFile);
        logger.info(lubm1_0WLS.serializeToJSON());
    }
}
