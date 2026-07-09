package org.hiero.proxy.server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for creating a new file on the Hiero File Service (HFS). "
        + "File contents are provided as a Base64-encoded string.")
public record CreateFileRequest(
        @Schema(
                description = "Base64-encoded content to store in the file.",
                example = "SGVsbG8gSGllcm8h",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String contents,

        @Schema(
                description = "Optional Unix epoch second at which the file should expire and be deleted. "
                        + "Must be a future timestamp. If omitted the network default expiry is used.",
                example = "1800000000",
                nullable = true)
        Long expirationTimeEpochSecond
) {}
