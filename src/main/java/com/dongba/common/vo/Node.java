package com.dongba.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Node implements Serializable {
    private static final long serialVersionUID = 7974150364180573037L;

    private Integer id;

    private String name;

    private Integer parentId;

}
