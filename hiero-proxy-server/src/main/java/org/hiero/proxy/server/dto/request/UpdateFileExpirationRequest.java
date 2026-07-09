package org.hiero.proxy.server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for updating the expiration time of an existing HFS file.")
public record UpdateFileExpirationRequest(
        @Schema(
                description = "New expiration time as a Unix epoch second. Must be in the future.",
                example = "1900000000",
                requiredMode = Schema.RequiredMode.REQUIRED)
        long expirationTimeEpochSecond
) {}
