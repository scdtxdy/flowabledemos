package com.scd.flowablesystem.common;

import lombok.Data;

import java.util.Date;

/**
 *
 * @author scd
 * @since
 */
@Data
public class ModelResponse {
    private String id;

    private String name;
    private String key;
    private String category;
    private String description;
    private String tenantId;
    private String editor;

    private Date createTime;
    private Date lastUpdateTime;
    private Boolean deployed;
    private Integer version;
}
