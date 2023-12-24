package io.github.apace100.wwfix;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class FluidStateHelper {
    public static FluidState getFluidState(BlockGetter world, BlockPos pos) {
        if (world.isOutsideBuildHeight(pos)) {
            return Fluids.EMPTY.defaultFluidState();
        } else {
            if (world instanceof LevelReader worldView) {
                ChunkAccess chunk = worldView.getChunk(pos);
                return chunk.getFluidState(pos);
            }
            return world.getFluidState(pos);
        }
    }
}