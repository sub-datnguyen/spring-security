package vn.elca.codebase.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectServiceDto {
    private Long projectId;
    private String name;
    private LocalDate finishingDate;
    private String taskNames;
}
