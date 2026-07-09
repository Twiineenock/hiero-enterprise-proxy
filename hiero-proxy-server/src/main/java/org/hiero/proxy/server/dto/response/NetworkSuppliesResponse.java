package org.hiero.proxy.server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hiero.base.data.NetworkSupplies;

@Schema(description = "Current HBAR supply figures for the Hiero network.")
public record NetworkSuppliesResponse(
        @Schema(description = "Total amount of HBAR that has been released into circulation (in tinybar).",
                example = "5000000000000000000")
        String releasedSupply,

        @Schema(description = "Total supply of HBAR including unreleased treasury holdings (in tinybar).",
                example = "5000000000000000000")
        String totalSupply
) {
    public static NetworkSuppliesResponse from(NetworkSupplies supplies) {
        return new NetworkSuppliesResponse(supplies.releasedSupply(), supplies.totalSupply());
    }
}
