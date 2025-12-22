package xyz.telecter.rideanything;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import xyz.telecter.rideanything.config.RideAnythingConfig;

public class RideAnythingMod implements ModInitializer {
	public static final String MOD_ID = "rideanything";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		RideAnythingConfig.HANDLER.load();

		UseEntityCallback.EVENT.register((player, world, hand, entity, result) -> {
			if (!world.isClient() && RideAnythingConfig.HANDLER.instance().enabled) {
				if (player.getStackInHand(hand).isEmpty() && shouldRide(player, entity)) {
					if (player.startRiding(entity)) {
						return ActionResult.SUCCESS;
					}
				}
			}
			return ActionResult.PASS;
		});
	}

	public static boolean shouldRide(PlayerEntity player, Entity entity) {
		RideAnythingConfig config = RideAnythingConfig.HANDLER.instance();
		if ((config.mode == RideAnythingConfig.Mode.ANIMALS && entity instanceof AnimalEntity)
				|| (config.mode == RideAnythingConfig.Mode.ALL && entity instanceof LivingEntity)) {
			return true;
		}
		if (config.mode == RideAnythingConfig.Mode.CUSTOM) {
			Identifier origId = EntityType.getId(entity.getType());

			for (String s : config.allowed) {
				Identifier id = Identifier.of(s);

				if (origId.equals(id)) {
					return true;
				}
			}
		}

		return false;
	}
}