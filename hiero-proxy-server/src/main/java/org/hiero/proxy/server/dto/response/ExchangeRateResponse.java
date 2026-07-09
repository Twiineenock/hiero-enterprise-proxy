package org.hiero.proxy.server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hiero.base.data.ExchangeRate;

@Schema(description = "Represents a single HBAR/USD exchange rate at a given point in time.")
public record ExchangeRateResponse(
        @Schema(description = "The USD cent equivalent in this rate.", example = "12")
        int centEquivalent,

        @Schema(description = "The HBAR equivalent in this rate.", example = "1")
        int hbarEquivalent,

        @Schema(description = "Unix epoch second at which this rate expires.", example = "1700000000")
        long expirationTimeEpochSecond
) {
    public static ExchangeRateResponse from(ExchangeRate rate) {
        return new ExchangeRateResponse(
                rate.centEquivalent(),
                rate.hbarEquivalent(),
                rate.expirationTime().getEpochSecond()
        );
    }
}
