package io.github.apace100.wwfix.mixin;

import io.github.apace100.wwfix.FluidShapes;
import io.github.apace100.wwfix.FluidStateHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class FluidloggedWalkMixin {

    @Inject(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("RETURN"), cancellable = true)
    private void wwfix$fixFluidloggedCollisionShape(BlockGetter world, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        FluidState fluidState = world.getFluidState(pos);
        int level = fluidState.getAmount();
        if(level == 0) {
            return;
        }
        VoxelShape shape = FluidShapes.VOXEL_SHAPES[level];
        VoxelShape shapeBelow = FluidShapes.VOXEL_SHAPES[level - 1];
        if (context.isAbove(shapeBelow, pos, true)
                && context.canStandOnFluid(world.getFluidState(pos.above()), fluidState)) {
            VoxelShape original = cir.getReturnValue();
            cir.setReturnValue(Shapes.or(original, shape));
        }
    }
}
