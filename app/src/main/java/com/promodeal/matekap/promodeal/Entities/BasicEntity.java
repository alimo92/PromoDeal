package com.promodeal.matekap.promodeal.Entities;

import java.sql.Timestamp;

/**
 * Created by Ali on 13/04/2016.
 */
public abstract class BasicEntity {
    public Timestamp DateCreated;

    public Timestamp getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        DateCreated = dateCreated;
    }
}
