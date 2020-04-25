package com.dongba.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "page.config")
public class PaginationProperties {

    private Integer pageSize;

    public Integer getStartIndex(Integer pageCurrent) {
        return (pageCurrent-1)*pageSize;
    }
}
