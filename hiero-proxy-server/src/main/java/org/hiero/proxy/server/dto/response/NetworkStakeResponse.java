package org.hiero.proxy.server.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hiero.base.data.NetworkStake;

@Schema(description = "Current staking configuration and statistics for the Hiero network.")
public record NetworkStakeResponse(
        @Schema(description = "Maximum amount of tinybar that can be rewarded per staking period.", example = "17500000000000000")
        long maxStakeReward,

        @Schema(description = "Maximum reward in tinybar per 1 HBAR staked.", example = "6849")
        long maxStakeRewardPerHbar,

        @Schema(description = "Maximum total staking reward payout per period in tinybar.", example = "17500000000000000")
        long maxTotalReward,

        @Schema(description = "Fraction of network fees directed to node reward accounts.", example = "0.0")
        double nodeRewardFeeFraction,

        @Schema(description = "Tinybar amount reserved for staking rewards.", example = "50000000000000000")
        long reservedStakingRewards,

        @Schema(description = "Minimum reward account balance in tinybar before paying out rewards.", example = "25000000000000000")
        long rewardBalanceThreshold,

        @Schema(description = "Total tinybar amount currently staked for reward across all nodes.", example = "5000000000000000")
        long stakeTotal,

        @Schema(description = "Duration of a staking period in minutes.", example = "1440")
        long stakingPeriodDuration,

        @Schema(description = "Number of staking periods stored in the contract.", example = "365")
        long stakingPeriodsStored,

        @Schema(description = "Fraction of network fees directed to staking reward accounts.", example = "0.1")
        double stakingRewardFeeFraction,

        @Schema(description = "Tinybar reward rate per HBAR staked per period.", example = "100")
        long stakingRewardRate,

        @Schema(description = "Minimum stake total in tinybar before staking rewards are activated.", example = "25000000000000000")
        long stakingStartThreshold,

        @Schema(description = "Tinybar balance of the staking reward account not reserved.", example = "12000000000000000")
        long unreservedStakingRewardBalance
) {
    public static NetworkStakeResponse from(NetworkStake stake) {
        return new NetworkStakeResponse(
                stake.maxStakeReward(),
                stake.maxStakeRewardPerHbar(),
                stake.maxTotalReward(),
                stake.nodeRewardFeeFraction(),
                stake.reservedStakingRewards(),
                stake.rewardBalanceThreshold(),
                stake.stakeTotal(),
                stake.stakingPeriodDuration(),
                stake.stakingPeriodsStored(),
                stake.stakingRewardFeeFraction(),
                stake.stakingRewardRate(),
                stake.stakingStartThreshold(),
                stake.unreservedStakingRewardBalance()
        );
    }
}
