package com.example.examplemod;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by ericbottard on 13/07/16.
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
		if ((int)entity.posX != (int)entity.prevPosX
				|| (int)entity.posY != (int)entity.prevPosY
				|| (int)entity.posZ != (int)entity.prevPosZ) {
			Map<String, Object> result = new LinkedHashMap<>();
			result.put("x", event.getEntity().getPosition().getX());
			result.put("y", event.getEntity().getPosition().getY());
			result.put("z", event.getEntity().getPosition().getZ());
			result.put("entity", event.getEntity().getEntityId());
			if (entity instanceof EntitySheep) {
				EntitySheep sheep = (EntitySheep) entity;
				EnumDyeColor color = sheep.getFleeceColor();
				Color c = ColorUtils.COLORS.get(color.toString().toUpperCase());
				result.put("red", c.getRed());
				result.put("green", c.getGreen());
				result.put("blue", c.getBlue());
			}

			broadcastServer.broadcast(result);
		}

	}
}
