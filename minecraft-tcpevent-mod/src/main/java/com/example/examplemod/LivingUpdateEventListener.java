package com.example.examplemod;

import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * A listener that emits an event every time an entity (either a sheep or a player)
 * changes coordinates on the map. For sheep, also adds the color of that sheep as RGB values.
 *
 * @author Eric Bottard
 */
public class LivingUpdateEventListener {

	private final BroadcastServer broadcastServer;

	public LivingUpdateEventListener(BroadcastServer broadcastServer) {
		this.broadcastServer = broadcastServer;
	}

	@SubscribeEvent
	public void living(LivingEvent.LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (!(entity instanceof EntitySheep) && !(entity instanceof EntityPlayer)) {
			return;
		}
		int dx = (int) entity.posX - (int) entity.prevPosX;
		int dy = (int) entity.posY - (int) entity.prevPosY;
		int dz = (int) entity.posZ - (int) entity.prevPosZ;
		if (dx != 0 || dy != 0 || dz != 0) {
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("x", event.getEntity().getPosition().getX());
			result.put("y", event.getEntity().getPosition().getY());
			result.put("z", event.getEntity().getPosition().getZ());
			result.put("dx", dx);
			result.put("dy", dy);
			result.put("dz", dz);
			result.put("entity", event.getEntity().getEntityId());
			if (entity instanceof EntitySheep) {
				EntitySheep sheep = (EntitySheep) entity;
				EnumDyeColor color = sheep.getFleeceColor();
				Color c = ColorUtils.COLORS.get(color.name().toUpperCase());
				result.put("red", c.getRed());
				result.put("green", c.getGreen());
				result.put("blue", c.getBlue());
				result.put("dye", color);
			}

			broadcastServer.broadcast(result);
		}

	}
}
