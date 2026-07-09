package org.hiero.proxy.server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for replacing the contents of an existing HFS file. "
        + "The new content is provided as a Base64-encoded string.")
public record UpdateFileRequest(
        @Schema(
                description = "Base64-encoded content that will fully replace the current file contents.",
                example = "VXBkYXRlZCBjb250ZW50",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String contents
) {}
