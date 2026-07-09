package org.hiero.proxy.server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Metadata about an HFS file — size, deletion status, and expiration time.")
public record FileInfoResponse(
        @Schema(description = "The file ID.", example = "0.0.77001")
        String fileId,

        @Schema(description = "Size of the file in bytes.", example = "42")
        int size,

        @Schema(description = "Whether the file has been deleted.", example = "false")
        boolean deleted,

        @Schema(description = "Unix epoch second at which the file expires.", example = "1800000000")
        long expirationTimeEpochSecond
) {
    public static FileInfoResponse of(String fileId, int size, boolean deleted, long expirationEpochSecond) {
        return new FileInfoResponse(fileId, size, deleted, expirationEpochSecond);
    }
}
