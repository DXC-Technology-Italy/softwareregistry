package com.cm.dev.bean;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstraction of a Software Item that has been released
 * 
 */

public class ReleaseItem {

    private static final String SPTES_HOME = "/home2/sptes/";
    private static final String PEMES_HOME = "/home4/pemes/";
    private static final String SPTCO_HOME = "/home2/sptco/";
    private static final String PEMCO_HOME = "/home4/pemco/";
    private static final String WEBSPHERE_ESE = "Macchina Websphere di Esercizio";
    private static final String WEBSPHERE_COLL = "Macchina Websphere di Collaudo";
    private static final String PLSQL = "plsql";
    protected static final HashMap<String, String> pathMefMapEsercizio;

    static {
        HashMap<String, String> map = new HashMap<>();
        for (String[] data : new String[][]{
                {"area0196/sql_anonimi", SPTES_HOME + "sql"},
                {"area0196/database-spt", SPTES_HOME + PLSQL},
                {"area0196/procedure_batch", SPTES_HOME + "sh"},
                {"area0196/procedure_cobol_no_tuxedo", SPTES_HOME + "bin"},
                {"area0196/procedure_cobol_tuxedo", SPTES_HOME + "bin"},
                {"area0196/database-sic", SPTES_HOME + PLSQL},
                {"area0196/database-addspt", SPTES_HOME + PLSQL},
                {"area0196/batchjavastipendi_util", SPTES_HOME + "jar"},
                {"area0196/batchjavastipendi_tfr", SPTES_HOME + "jar"},
                {"area0196/batchjavastipendi_messaggi", SPTES_HOME + "jar"},
                {"area0196/batchjavastipendi_load", SPTES_HOME + "jar"},
                {"area0196/batchjavastipendi_gestore", SPTES_HOME + "jar"},
                {"area0196/batchjavastipendi_fondi", SPTES_HOME + "jar"},
                {"area0196/batchjavastipendi_enti", SPTES_HOME + "jar"},
                {"area0196/batchjavastipendi_cf", SPTES_HOME + "jar"},
                {"area0196/batchjavastipendi_amministrato", SPTES_HOME + "jar"},
                {"area0196/sptonline", WEBSPHERE_ESE},
                {"area0196/servizicedolinounicospt", WEBSPHERE_ESE},
                {"area0196/servizispt", WEBSPHERE_ESE},
                {"area0159/sql_anonimi", PEMES_HOME + "sql"},
                {"area0159/procedure-sh-py", PEMES_HOME + "sh"},
                {"area0159/procedure-cobol-tuxno", PEMES_HOME + "bin"},
        }) {
            if (map.put(data[0], data[1]) != null) {
                throw new IllegalStateException("Duplicate key");
            }
        }
        pathMefMapEsercizio = map;
    }

    protected static final Map<String, String> pathMefMapCollaudo;

    static {
        Map<String, String> map = new HashMap<>();
        for (String[] data : new String[][]{
                {"area0196/sql_anonimi", SPTCO_HOME + "sql"},
                {"area0196/database-spt", SPTCO_HOME + PLSQL},
                {"area0196/procedure_batch", SPTCO_HOME + "sh"},
                {"area0196/procedure_cobol_no_tuxedo", SPTCO_HOME + "bin"},
                {"area0196/procedure_cobol_tuxedo", SPTCO_HOME + "bin"},
                {"area0196/database-sic", SPTCO_HOME + PLSQL},
                {"area0196/database-addspt", SPTCO_HOME + PLSQL},
                {"area0196/batchjavastipendi_util", SPTCO_HOME + "jar"},
                {"area0196/batchjavastipendi_tfr", SPTCO_HOME + "jar"},
                {"area0196/batchjavastipendi_messaggi", SPTCO_HOME + "jar"},
                {"area0196/batchjavastipendi_load", SPTCO_HOME + "jar"},
                {"area0196/batchjavastipendi_gestore", SPTCO_HOME + "jar"},
                {"area0196/batchjavastipendi_fondi", SPTCO_HOME + "jar"},
                {"area0196/batchjavastipendi_enti", SPTCO_HOME + "jar"},
                {"area0196/batchjavastipendi_cf", SPTCO_HOME + "jar"},
                {"area0196/batchjavastipendi_amministrato", SPTCO_HOME + "jar"},
                {"area0196/sptonline", WEBSPHERE_COLL},
                {"area0196/servizicedolinounicospt", WEBSPHERE_COLL},
                {"area0196/servizispt", WEBSPHERE_COLL},
                {"area0159/sql_anonimi", PEMCO_HOME + "sql"},
                {"area0159/procedure-sh-py", PEMCO_HOME + "sh"},
                {"area0159/procedure-cobol-tuxno", PEMCO_HOME + "bin"},
        }) {
            if (map.put(data[0], data[1]) != null) {
                throw new IllegalStateException("Duplicate key");
            }
        }
        pathMefMapCollaudo = Collections.unmodifiableMap(map);
    }

    private String filename;
    private String repository;
    private String sha1sum;
    private String pathMef;


    
    /** 
     * @return String
     */
    public String getFilename() {
        return filename;
    }

    
    /** 
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    
    /** 
     * @return String
     */
    public String getRepository() {
        return repository;
    }

    
    /** 
     * @param repository
     */
    public void setRepository(String repository) {
        this.repository = repository;
    }

    
    /** 
     * @return String
     */
    public String getSha1sum() {
        return sha1sum;
    }

    
    /** 
     * @param sha1sum
     */
    public void setSha1sum(String sha1sum) {
        this.sha1sum = sha1sum;
    }

    
    /** 
     * @return String
     */
    public String getPathMef() {
        return pathMef;
    }

    
    /** 
     * @param pathMef
     */
    public void setPathMef(String pathMef) {
        this.pathMef = pathMef;
    }


    
    /** 
     * @param areaRepository
     * @return String
     */
    public static String getPathMefMapEsercizio(String areaRepository) {
        return pathMefMapEsercizio.getOrDefault(areaRepository, null);
    }


    
    /** 
     * @param areaRepository
     * @return String
     */
    public static String getPathMefMapCollaudo(String areaRepository) {
        return pathMefMapCollaudo.getOrDefault(areaRepository, null);
    }
}
