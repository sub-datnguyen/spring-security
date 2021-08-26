package vn.elca.training.learnjwt.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author vlp
 */
@Getter
@RequiredArgsConstructor
public class JwtResponse {
    private final String jwttoken;
}
