# GeoRDFBench Framework - Samples
> Samples for using the GeoRDFBench Runtime API

[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.13120112.svg)](https://doi.org/10.5281/zenodo.13120112)

## A. Using the GeoRDFBench JSON Generator API 

### Create specifications for the **LUBM** SPARQL benchmark

#### 1. Generate the **compact** workload specification

i. Generate the [workload specification](/src/main/java/gr/uoa/di/rdf/geordfbench/sample/specification/LUBMWorkloadUtil.java)

#### 2. Generate the **detailed** workload specification

i. Generate the [dataset specification](/src/main/java/gr/uoa/di/rdf/geordfbench/sample/specification/LUBMDataSetUtil.java)

ii. Generate the [queryset specification](/src/main/java/gr/uoa/di/rdf/geordfbench/sample/specification/LUBMQuerySetUtil.java)

iii. Generate the [execution specification](/src/main/java/gr/uoa/di/rdf/geordfbench/sample/specification/LUBMExecSpecUtil.java)

#### 3. Generate **compact** and **detailed** workload specification with a script

i. A [convenience generation script](/scripts/generateLUBM_1_0_specs.sh)

The script allows the automatic generation of all four specifications to a user-provided target directory which is expected to follow the GeoRDFBench JSON library's (/json_defs) hierarchy.

## Comments
Documentation for the project is maintained at our [GeoRDFBench web site](https://geordfbench.di.uoa.gr/).