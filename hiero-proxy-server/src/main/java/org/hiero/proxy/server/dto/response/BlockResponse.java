package org.hiero.proxy.server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hiero.base.data.Block;

/**
 * API representation of a single Hiero network block retrieved from the mirror node.
 *
 * <p>Block numbers and hashes mirror Ethereum conventions — {@code hash} and
 * {@code previousHash} are the EVM-compatible 32-byte block hashes, and {@code number}
 * is the sequential block index on the network.
 */
@Schema(description = "A single Hiero network block as returned by the mirror node.")
public record BlockResponse(
        @Schema(description = "Number of transactions included in this block.", example = "3")
        long count,

        @Schema(description = "HAPI (Hedera API) version active when this block was produced.",
                example = "0.49.0")
        String hapiVersion,

        @Schema(description = "The EVM-compatible 32-byte hash of this block (hex-encoded).",
                example = "0x5b6e7a8c...")
        String hash,

        @Schema(description = "Human-readable block name.", example = "2024-01-15T10:30:00Z")
        String name,

        @Schema(description = "Sequential block number on the Hiero network.", example = "61234567")
        long number,

        @Schema(description = "Hash of the immediately preceding block.",
                example = "0x4a5d6b7c...")
        String previousHash,

        @Schema(description = "Total size of the block in bytes.", example = "2048")
        long size,

        @Schema(description = "Unix epoch second marking the start of this block's time window.",
                nullable = true,
                example = "1700000000")
        Long fromTimestampEpochSecond,

        @Schema(description = "Unix epoch second marking the end of this block's time window.",
                nullable = true,
                example = "1700000002")
        Long toTimestampEpochSecond,

        @Schema(description = "Total EVM gas consumed by all transactions in this block.",
                example = "850000")
        long gasUsed,

        @Schema(description = "EVM logs bloom filter (hex-encoded). Null if no EVM logs were emitted.",
                nullable = true,
                example = "0x00000000...")
        String logsBloom
) {
    /** Maps a domain {@link Block} to the API representation. */
    public static BlockResponse from(Block block) {
        return new BlockResponse(
                block.count(),
                block.hapiVersion(),
                block.hash(),
                block.name(),
                block.number(),
                block.previousHash(),
                block.size(),
                block.timestamp().from() != null ? block.timestamp().from().getEpochSecond() : null,
                block.timestamp().to()   != null ? block.timestamp().to().getEpochSecond()   : null,
                block.gasUsed(),
                block.logsBloom()
        );
    }
}
