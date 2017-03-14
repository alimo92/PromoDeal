package com.promodeal.matekap.promodeal.Core.URL.Impl;

import com.promodeal.matekap.promodeal.Core.URL.URLBuilder;

/**
 * Created by Ali on 13/04/2016.
 */
public class URLBuilderImpl implements URLBuilder {
    private String Domaine;
    private String Application;
    private String Entity;
    private String Complement;

    public URLBuilderImpl(String domaine, String application, String entity, String complement) {
        Domaine = domaine;
        Application = application;
        Entity = entity;
        Complement = complement;
    }

    public URLBuilderImpl() {
        this.Domaine="http://192.168.23.1:8080";
        this.Application="";
    }

    public String getComplement() {
        return Complement;
    }

    public void setComplement(String complement) {
        Complement = complement;
    }

    public String getDomaine() {
        return Domaine;
    }

    public void setDomaine(String domaine) {
        Domaine = domaine;
    }

    public String getApplication() {
        return Application;
    }

    public void setApplication(String application) {
        Application = application;
    }

    public String getEntity() {
        return Entity;
    }

    public void setEntity(String entity) {
        Entity = entity;
    }

    @Override
    public String getURL(String entity, String complement) {
        String url = this.Domaine+"/"+this.Application+"/"+entity+"/"+complement;
        return url;
    }
}
