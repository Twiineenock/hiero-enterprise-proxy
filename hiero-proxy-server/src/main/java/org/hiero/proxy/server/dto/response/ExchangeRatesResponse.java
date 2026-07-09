package org.hiero.proxy.server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hiero.base.data.ExchangeRates;

@Schema(description = "The current and next HBAR/USD exchange rates from the Hiero network.")
public record ExchangeRatesResponse(
        @Schema(description = "The currently active exchange rate.")
        ExchangeRateResponse currentRate,

        @Schema(description = "The next exchange rate that will take effect when the current one expires.")
        ExchangeRateResponse nextRate
) {
    public static ExchangeRatesResponse from(ExchangeRates rates) {
        return new ExchangeRatesResponse(
                ExchangeRateResponse.from(rates.currentRate()),
                ExchangeRateResponse.from(rates.nextRate())
        );
    }
}
