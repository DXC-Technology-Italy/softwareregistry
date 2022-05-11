package com.cm.dev.bean;

import java.util.*;

/**
 * Abstraction of a Software Release
 * 
 */
public class Release {


    protected static final Map<String, String> decodeTipoMergeRequest;

    static {
        Map<String, String> map = new HashMap<>();
        for (String[] data : new String[][]{
                {"29", "COLLAUDO"},
                {"30", "ESERCIZIO"}
        }) {
            if (map.put(data[0], data[1]) != null) {
                throw new IllegalStateException("Duplicate key");
            }
        }
        decodeTipoMergeRequest = Collections.unmodifiableMap(map);
    }

    private String author;
    private String date;
    private String mergeRequestType;
    private List<String> nexusUrls;
    private List<ReleaseItem> releaseItems;

    
    /** 
     * @return String
     */
    public String getAuthor() {
        return author;
    }

    
    /** 
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    
    /** 
     * @return String
     */
    public String getDate() {
        return date;
    }

    
    /** 
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    
    /** 
     * @return String
     */
    public String getMergeRequestType() {
        return mergeRequestType;
    }

    
    /** 
     * @param mergeRequestType
     */
    public void setMergeRequestType(String mergeRequestType) {
        this.mergeRequestType = mergeRequestType;
    }

    
    /** 
     * @return List<String>
     */
    public List<String> getNexusUrls() {
        return new ArrayList<>(nexusUrls);
    }

    
    /** 
     * @param nexusUrls
     */
    public void setNexusUrls(List<String> nexusUrls) {
        this.nexusUrls = new ArrayList<>(nexusUrls);
    }

    
    /** 
     * @return List<ReleaseItem>
     */
    public List<ReleaseItem> getReleaseItems() {
        return releaseItems;
    }

    
    /** 
     * @param releaseItems
     */
    public void setReleaseItems(List<ReleaseItem> releaseItems) {
        this.releaseItems = releaseItems;
    }

    
    /** 
     * @param idAmbiente
     * @return String
     */
    public static String getDecodeTipoMergeRequest(String idAmbiente) {
        return decodeTipoMergeRequest.getOrDefault(idAmbiente, null);
    }
}
