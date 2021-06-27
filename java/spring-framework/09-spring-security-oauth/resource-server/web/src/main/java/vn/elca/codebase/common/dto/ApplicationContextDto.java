package vn.elca.codebase.common.dto;

import lombok.Data;

@Data
public class ApplicationContextDto {
    private String loginUser;
    private String version;
    private String baseHref;
}
