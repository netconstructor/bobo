package com.browseengine.bobo.geosearch.solo.search.impl;

/**
 * 
 * @author gcooney
 *
 */
public class GeoOnlyHit {

    public double score;
    public byte[] uuid;

    public GeoOnlyHit(double score, byte[] uuid) {
        this.score = score;
        this.uuid = uuid;
    }
    
}