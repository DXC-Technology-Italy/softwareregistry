package com.cm.dev.bean;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReleaseItem {
//TODO: aggiornare per collaudo ed esercizio, per websphere mettere solo macchina websphere collaudo o esercizio
    public static final Map<String, String> pathMefMapEsercizio = Stream.of(new String[][]{
            {"area0196/sql_anonimi", "/home2/sptes/sql"},
            {"area0196/database-spt", "/home2/sptes/plsql"},
            {"area0196/procedure_batch", "/home2/sptes/sh"},
            {"area0196/procedure_cobol_no_tuxedo", "/home2/sptes/bin"},
            {"area0196/procedure_cobol_tuxedo", "/home2/sptes/bin"},
            {"area0196/database-sic", "/home2/sptes/plsql"},
            {"area0196/database-addspt", "/home2/sptes/plsql"},
            {"area0196/batchjavastipendi_util", "/home2/sptes/jar"},
            {"area0196/batchjavastipendi_tfr", "/home2/sptes/jar"},
            {"area0196/batchjavastipendi_messaggi", "/home2/sptes/jar"},
            {"area0196/batchjavastipendi_load", "/home2/sptes/jar"},
            {"area0196/batchjavastipendi_gestore", "/home2/sptes/jar"},
            {"area0196/batchjavastipendi_fondi", "/home2/sptes/jar"},
            {"area0196/batchjavastipendi_enti", "/home2/sptes/jar"},
            {"area0196/batchjavastipendi_cf", "/home2/sptes/jar"},
            {"area0196/batchjavastipendi_amministrato", "/home2/sptes/jar"},
            {"area0196/sptonline", "Macchina Websphere di Esercizio"},
            {"area0196/servizicedolinounicospt", "Macchina Websphere di Esercizio"},
            {"area0196/servizispt", "Macchina Websphere di Esercizio"},
            {"area0159/sql_anonimi", "/home4/pemes/sql"},
            {"area0159/procedure-sh-py", "/home4/pemes/sh"},
            {"area0159/procedure-cobol-tuxno", "/home4/pemes/bin"},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    public static final Map<String, String> pathMefMapCollaudo = Stream.of(new String[][]{
            {"area0196/sql_anonimi", "/home2/sptco/sql"},
            {"area0196/database-spt", "/home2/sptco/plsql"},
            {"area0196/procedure_batch", "/home2/sptco/sh"},
            {"area0196/procedure_cobol_no_tuxedo", "/home2/sptco/bin"},
            {"area0196/procedure_cobol_tuxedo", "/home2/sptco/bin"},
            {"area0196/database-sic", "/home2/sptco/plsql"},
            {"area0196/database-addspt", "/home2/sptco/plsql"},
            {"area0196/batchjavastipendi_util", "/home2/sptco/jar"},
            {"area0196/batchjavastipendi_tfr", "/home2/sptco/jar"},
            {"area0196/batchjavastipendi_messaggi", "/home2/sptco/jar"},
            {"area0196/batchjavastipendi_load", "/home2/sptco/jar"},
            {"area0196/batchjavastipendi_gestore", "/home2/sptco/jar"},
            {"area0196/batchjavastipendi_fondi", "/home2/sptco/jar"},
            {"area0196/batchjavastipendi_enti", "/home2/sptco/jar"},
            {"area0196/batchjavastipendi_cf", "/home2/sptco/jar"},
            {"area0196/batchjavastipendi_amministrato", "/home2/sptco/jar"},
            {"area0196/sptonline", "Macchina Websphere di Collaudo"},
            {"area0196/servizicedolinounicospt", "Macchina Websphere di Collaudo"},
            {"area0196/servizispt", "Macchina Websphere di Collaudo"},
            {"area0159/sql_anonimi", "/home4/pemco/sql"},
            {"area0159/procedure-sh-py", "/home4/pemco/sh"},
            {"area0159/procedure-cobol-tuxno", "/home4/pemco/bin"},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    private String filename;
    private String repository;
    private String sha1sum;
    private String pathMef;


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getSha1sum() {
        return sha1sum;
    }

    public void setSha1sum(String sha1sum) {
        this.sha1sum = sha1sum;
    }

    public String getPathMef() {
        return pathMef;
    }

    public void setPathMef(String pathMef) {
        this.pathMef = pathMef;
    }
}
