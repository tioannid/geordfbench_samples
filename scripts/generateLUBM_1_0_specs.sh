#!/bin/bash

# SYNTAX :
#    <script> OUTPUT_SPEC_DIR
SCRIPT_NAME=`basename "$0"`
SYNTAX1="SYNTAX 1: $SCRIPT_NAME <OUTPUT_SPEC_DIR>"
SYNTAX="$SYNTAX1
\t<OUTPUT_SPEC_DIR>\t\t:\tOutput base directory for specifications, e.g. /data/json_defs"

if (( $# != 1 )); then
        # script cannot proceed, because number of arguments are wrong
        echo -e "Illegal number of parameters!\n$SYNTAX"
	exit 1   
fi

# read the output base directory for specifications
OUTPUT_SPEC_DIR=${1}

# find the directory where the script is located in
BASE="$( cd -P "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo "BASE = $BASE"

# record current working directory
PWD=`pwd`
echo "PWD = $PWD"

# move to the build directory
cd ${BASE}/../target

MAIN_JAR="geordfbench-samples-2.0.0-SNAPSHOT-jar-with-dependencies.jar"

# generate the LUBM(1, 0) workload specification
WORKLOAD_SPEC_DIR="${OUTPUT_SPEC_DIR}/workloads"
MAIN_CLASS="gr.uoa.di.rdf.geordfbench.sample.specification.LUBMWorkloadUtil"
echo -e "\nGenerating the LUBM(1, 0) workload specification using GeoRDFBench Runtime\n> ..."
java -cp ${MAIN_JAR} ${MAIN_CLASS} ${WORKLOAD_SPEC_DIR}

# generate the LUBM(1, 0) dataset specification
DATASET_SPEC_DIR="${OUTPUT_SPEC_DIR}/datasets"
MAIN_CLASS="gr.uoa.di.rdf.geordfbench.sample.specification.LUBMDataSetUtil"
echo -e "\nGenerating the LUBM(1, 0) dataset specification using GeoRDFBench Runtime\n> ..."
java -cp ${MAIN_JAR} ${MAIN_CLASS} ${DATASET_SPEC_DIR}

# generate the LUBM(1, 0) queryset specification
QUERYSET_SPEC_DIR="${OUTPUT_SPEC_DIR}/querysets"
MAIN_CLASS="gr.uoa.di.rdf.geordfbench.sample.specification.LUBMQuerySetUtil"
echo -e "\nGenerating the LUBM(1, 0) queryset specification using GeoRDFBench Runtime\n> ..."
java -cp ${MAIN_JAR} ${MAIN_CLASS} ${QUERYSET_SPEC_DIR}

# generate the LUBM(1, 0) execution specification
EXECSPEC_SPEC_DIR="${OUTPUT_SPEC_DIR}/executionspecs"
MAIN_CLASS="gr.uoa.di.rdf.geordfbench.sample.specification.LUBMExecSpecUtil"
echo -e "\nGenerating the LUBM(1, 0) execution specification using GeoRDFBench Runtime\n> ..."
java -cp ${MAIN_JAR} ${MAIN_CLASS} ${EXECSPEC_SPEC_DIR}

# restore current working directory
cd ${PWD}
