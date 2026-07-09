package org.hiero.proxy.server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Base64;

@Schema(description = "Response containing the contents of an HFS file.")
public record FileContentsResponse(
        @Schema(description = "The file ID.", example = "0.0.77001")
        String fileId,

        @Schema(description = "Base64-encoded file contents.",
                example = "SGVsbG8gSGllcm8h")
        String contents
) {
    public static FileContentsResponse of(String fileId, byte[] contents) {
        return new FileContentsResponse(fileId, Base64.getEncoder().encodeToString(contents));
    }
}
