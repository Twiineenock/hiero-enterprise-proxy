package org.hiero.proxy.server.dto.response;

import com.hedera.hashgraph.sdk.FileId;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response returned when a new HFS file is created successfully.")
public record FileCreatedResponse(
        @Schema(description = "The ID of the newly created file.", example = "0.0.77001")
        String fileId
) {
    public static FileCreatedResponse of(FileId fileId) {
        return new FileCreatedResponse(fileId.toString());
    }
}
