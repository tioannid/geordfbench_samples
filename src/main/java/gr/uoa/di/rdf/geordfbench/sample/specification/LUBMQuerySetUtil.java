package gr.uoa.di.rdf.geordfbench.sample.specification;

import com.fasterxml.jackson.databind.JsonMappingException;
import gr.uoa.di.rdf.geordfbench.runtime.querysets.complex.IQuerySet;
import gr.uoa.di.rdf.geordfbench.runtime.querysets.complex.impl.StaticTempParamQS;
import gr.uoa.di.rdf.geordfbench.runtime.querysets.simple.IQuery;
import gr.uoa.di.rdf.geordfbench.runtime.querysets.simple.impl.SimpleQuery;
import gr.uoa.di.rdf.geordfbench.runtime.querysets.util.QuerySetUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.eclipse.rdf4j.rio.RDFFormat;
import java.nio.file.Paths;

public class LUBMQuerySetUtil {

    // --- Static members -----------------------------
    static Logger logger
            = Logger.getLogger(LUBMQuerySetUtil.class.getSimpleName());
    static final String LUBM_1_0_JSONDEF_FILE = "LUBM_1_0QS_GOLD_STANDARD.json";
    static final String NTRIPLES_STR = RDFFormat.NTRIPLES.getName().toUpperCase();

    // --- Methods -----------------------------------
    /**
     * Creates the JSON definition file for the LUBM(1, 0) queryset in
     * ./json_defs/querysets/LUBM_1_0_WLoriginal_GOLD_STANDARD.json
     *
     */
    public static void createLUBM_1_0_OriginalJSONDefFile(String outputDir) {
        // 1. Create a queryset spec
        Map<String, String> mapTemplateParams = new HashMap<>();
        Map<String, String> mapUsefulNamespacePrefixes = new HashMap<>();
        mapUsefulNamespacePrefixes.put("ub", "<https://swat.cse.lehigh.edu/onto/univ-bench.owl#>");
        // populate Graph prefixes map
        Map<String, String> mapLiteralValues = new HashMap<>();
        // populate simple queries map
        Map<Integer, IQuery> mapQry = new HashMap<>();
        // using SimpleQuery(String label, String query, boolean usePredicate, long expectedResults)
        // NOTE: The expectedResults values were filled with the values reported in the paper
        //      https://swat.cse.lehigh.edu/pubs/guo05a.pdf, page 20, Table 2 "Query soundness of OWLJessKB"
        mapQry.put(0, new SimpleQuery("Q1_GradStudents_Taken_GradCource0_At_Univ0",
                "SELECT ?x WHERE {\n"
                + " ?x rdf:type ub:GraduateStudent .\n"
                + " ?x ub:takesCourse <http://www.Department0.University0.edu/GraduateCourse0> .\n"
                + "} \n",
                false,
                4));
        mapQry.put(1, new SimpleQuery("Q2_GradStudents_From_Any_Univ_Depart",
                "SELECT ?x ?y ?z WHERE {\n"
                + " ?x rdf:type ub:GraduateStudent .\n"
                + " ?y rdf:type ub:University .\n"
                + " ?z rdf:type ub:Department .\n"
                + " ?x ub:memberOf ?z .\n"
                + " ?z ub:subOrganizationOf ?y .\n"
                + " ?x ub:undergraduateDegreeFrom ?y .\n"
                + "} \n",
                false,
                0));
        mapQry.put(2, new SimpleQuery("Q3_Publications_Of_AssistProfessor0_In_Univ0_Depart0",
                "SELECT ?x WHERE { \n ?x rdf:type ub:Publication .\n"
                + " ?x ub:publicationAuthor <http://www.Department0.University0.edu/AssistantProfessor0> .\n"
                + "} \n",
                false,
                6));
        mapQry.put(3, new SimpleQuery("Q4_Properties_Of_Professors_In_Univ0_Depart0",
                "SELECT ?x ?y1 ?y2 ?y3 WHERE {\n"
                + "  ?x rdf:type ub:Professor .\n"
                + "  ?x ub:worksFor <http://www.Department0.University0.edu> .\n"
                + "  ?x ub:name ?y1 .\n"
                + "  ?x ub:emailAddress ?y2 .\n"
                + "  ?x ub:telephone ?y3 .\n"
                + "} \n",
                false,
                41));
        mapQry.put(4, new SimpleQuery("Q5_Persons_MembersOf_In_Univ0_Depart0",
                "SELECT ?x WHERE {\n"
                + "  ?x rdf:type ub:Person .\n"
                + "  ?x ub:memberOf <http://www.Department0.University0.edu> .\n"
                + "} \n",
                false,
                719));
        mapQry.put(5, new SimpleQuery("Q6_All_Students",
                "SELECT ?x WHERE {\n"
                + "  ?x rdf:type ub:Student .\n"
                + "} \n",
                false,
                8330));
        mapQry.put(6, new SimpleQuery("Q7_All_Students_Courses_AssProfessor0",
                "SELECT ?x ?y WHERE {\n"
                + "  ?x rdf:type ub:Student .\n"
                + "  ?y rdf:type ub:Course .\n"
                + "  ?x ub:takesCourse ?y .\n"
                + "  <http://www.Department0.University0.edu/AssociateProfessor0> ub:teacherOf ?y .\n"
                + "} \n",
                false,
                67));
        mapQry.put(7, new SimpleQuery("Q8_All_Students_Emails_Univ0",
                "SELECT ?x ?y ?z WHERE {\n"
                + "  ?x rdf:type ub:Student .\n"
                + "  ?y rdf:type ub:Department .\n"
                + "  ?x ub:memberOf ?y .\n"
                + "  ?y ub:subOrganizationOf <http://www.University0.edu> .\n"
                + "  ?x ub:emailAddress ?z .\n"
                + "} \n",
                false,
                8330));
        mapQry.put(8, new SimpleQuery("Q9_All_Students_Teachers_Courses",
                "SELECT ?x ?y ?z WHERE {\n"
                + "  ?x rdf:type ub:Student .\n"
                + "  ?y rdf:type ub:Faculty .\n"
                + "  ?z rdf:type ub:Course .\n"
                + "  ?x ub:advisor ?y .\n"
                + "  ?y ub:teacherOf ?z .\n"
                + "  ?x ub:takesCourse ?z .\n"
                + "} \n",
                false,
                208));
        mapQry.put(9, new SimpleQuery("Q10_All_Students_GradCourse0_Dept0_Univ0",
                "SELECT ?x WHERE {\n"
                + "  ?x rdf:type ub:Student .\n"
                + "  ?x ub:takesCourse <http://www.Department0.University0.edu/GraduateCourse0> .\n"
                + "} \n",
                false,
                4));
        mapQry.put(10, new SimpleQuery("Q11_All_Researchgroups_Univ0",
                "SELECT ?x WHERE {\n"
                + "  ?x rdf:type ub:ResearchGroup .\n"
                + "  ?x ub:subOrganizationOf <http://www.University0.edu> .\n"
                + "} \n",
                false,
                224));
        mapQry.put(11, new SimpleQuery("Q12_All_Chairs_Dept_Univ0",
                "SELECT ?x ?y WHERE {\n"
                + "  ?x rdf:type ub:Chair .\n"
                + "  ?y rdf:type ub:Department .\n"
                + "  ?x ub:worksFor ?y .\n"
                + "  ?y ub:subOrganizationOf <http://www.University0.edu> .\n"
                + "} \n",
                false,
                540));
        mapQry.put(12, new SimpleQuery("Q13_All_Students",
                "SELECT ?x WHERE {\n"
                + "  ?x rdf:type ub:Person .\n"
                + "  <http://www.University0.edu> ub:hasAlumnus ?x .\n"
                + "} \n",
                false,
                1));
        mapQry.put(13, new SimpleQuery("Q14_UndergradStudents_From_Any_Univ_Depart",
                "SELECT ?x WHERE {\n"
                + "  ?x rdf:type ub:UndergraduateStudent .\n"
                + "} \n",
                false,
                5916));

        StaticTempParamQS lubm_qs
                = new StaticTempParamQS(
                        "lubm-1_0", // queryset name
                        "", // relative base dir
                        false, // does not have predicate queries
                        mapQry, // map of simple queries
                        mapTemplateParams, // map of template params
                        mapUsefulNamespacePrefixes, // map of useful prefixes
                        mapLiteralValues // map of values for template params 
                );
        try {
            lubm_qs.serializeToJSON(Paths.get(outputDir, LUBM_1_0_JSONDEF_FILE).toFile());
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
            logger.info("Usage: " + LUBMQuerySetUtil.class.getSimpleName() + " <JSON spec output path>");
            System.exit(-1);
        }
        String outputDir = args[0];
        // LUBM(0, 1) Queryset
        LUBMQuerySetUtil.createLUBM_1_0_OriginalJSONDefFile(outputDir);
        String outputFile = Paths.get(outputDir, LUBM_1_0_JSONDEF_FILE).toString();
        IQuerySet qs = QuerySetUtil.deserializeFromJSON(outputFile);
        logger.info(qs.serializeToJSON());
    }
}
