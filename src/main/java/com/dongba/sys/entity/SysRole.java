package com.dongba.sys.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class SysRole implements Serializable{
	private static final long serialVersionUID = -356538509994150709L;

	private Integer id;

	private String name;

	private String note;

	private Date createdTime;

	private Date modifiedTime;

	private String createdUser;

	private String modifiedUser;

}
