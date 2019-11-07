package tech.mhuang.ext.interchan.protocol;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class InsertInto<Id> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ADD = "1";
    public static final String UPDATE = "2";
    public static final String DELETE = "3";

    private Id id;

    private String reqNo;

    private String status;

    private String userId;

    private Date opDate;
}
