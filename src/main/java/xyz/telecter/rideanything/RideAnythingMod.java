package xyz.telecter.rideanything;

import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import xyz.telecter.rideanything.config.RideAnythingConfig;

public class RideAnythingMod implements ModInitializer {
	public static final String MOD_ID = "rideanything";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		RideAnythingConfig.HANDLER.load();

		UseEntityCallback.EVENT.register((player, world, hand, entity, result) -> {
			if (!world.isClientSide() && RideAnythingConfig.HANDLER.instance().enabled) {
				if (player.getMainHandItem().isEmpty() && shouldRide(player, entity)) {
					if (player.startRiding(entity)) {
						return InteractionResult.SUCCESS;
					}
				}
			}
			return InteractionResult.PASS;
		});
	}

	public static boolean shouldRide(Player player, Entity entity) {
		RideAnythingConfig config = RideAnythingConfig.HANDLER.instance();
		if ((config.mode == RideAnythingConfig.Mode.ANIMALS && entity instanceof Animal)
				|| (config.mode == RideAnythingConfig.Mode.ALL && entity instanceof LivingEntity)) {
			return true;
		}
		if (config.mode == RideAnythingConfig.Mode.CUSTOM) {
			Identifier origId = EntityType.getKey(entity.getType());

			for (String s : config.allowed) {
				Identifier id = Identifier.parse(s);

				if (origId.equals(id)) {
					return true;
				}
			}
		}

		return false;
	}
}