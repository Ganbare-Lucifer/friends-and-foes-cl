package com.faboslav.friendsandfoes.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.animal.Bee;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Bee.BeeGoToHiveGoal.class)
public class BeeEntityMoveToHiveGoalMixin
{
	@Final
	@Shadow(aliases = {"field_226508_a_", "field_20371", "f_27979_"})
	private Bee this$0;

	/**
	 * Inspired by use in Bumblezone mod
	 *
	 * @author TelepathicGrunt
	 * @reason Always use the entity's own randomSource instead of world's when creating/initing entities or else you risk a crash from threaded worldgen entity spawning. Fixed this bug with vanilla bees.
	 * <a href="https://github.com/TelepathicGrunt/Bumblezone/blob/1.20-Arch/common/src/main/java/com/telepathicgrunt/the_bumblezone/mixin/entities/BeeGoToKnownFlowerGoalMixin.java</a>
	 */
	@ModifyReceiver(
		method = "<init>(Lnet/minecraft/world/entity/animal/Bee;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"
		)
	)
	private RandomSource friendsandfoes_fixGoalRandomSourceUsage2(RandomSource randomSource, int range) {
		return this$0.getRandom();
	}
}
