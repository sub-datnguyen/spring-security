package vn.elca.codebase.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ElcaBusinessErrorParam {
    private String key;
    private Object value;
}
