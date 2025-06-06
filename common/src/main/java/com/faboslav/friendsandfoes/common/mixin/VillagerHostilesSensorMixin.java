package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.sensing.VillagerHostilesSensor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerHostilesSensor.class)
public final class VillagerHostilesSensorMixin
{
	private static final ImmutableMap<EntityType<?>, Float> CUSTOM_SQUARED_DISTANCES_FOR_DANGER;

	static {
		CUSTOM_SQUARED_DISTANCES_FOR_DANGER = ImmutableMap.<EntityType<?>, Float>builder().put(FriendsAndFoesEntityTypes.ICEOLOGER.get(), 12.0F).build();
	}

	@Inject(
		at = @At("HEAD"),
		method = "isClose",
		cancellable = true
	)
	private void friendsandfoes_isCloseEnoughForDanger(
		LivingEntity villager,
		LivingEntity target,
		CallbackInfoReturnable<Boolean> cir
	) {
		if (CUSTOM_SQUARED_DISTANCES_FOR_DANGER.containsKey(target.getType())) {
			float distance = CUSTOM_SQUARED_DISTANCES_FOR_DANGER.get(target.getType());
			boolean isCloseEnoughForDanger = target.distanceToSqr(villager) <= (double) (distance * distance);
			cir.setReturnValue(isCloseEnoughForDanger);
		}
	}

	@Inject(
		at = @At("HEAD"),
		method = "isHostile",
		cancellable = true
	)
	private void friendsandfoes_isHostile(
		LivingEntity entity,
		CallbackInfoReturnable<Boolean> cir
	) {
		if (CUSTOM_SQUARED_DISTANCES_FOR_DANGER.containsKey(entity.getType())) {
			cir.setReturnValue(true);
		}
	}
}
