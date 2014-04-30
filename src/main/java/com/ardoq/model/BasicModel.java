package com.ardoq.model;

import java.util.Date;

/**
 * Created by magnulf on 23.04.14.
 */
public interface BasicModel {
    public String getId();
    public Integer get_version();
    public Date getCreated();
    public Date getLastUpdated();
}
