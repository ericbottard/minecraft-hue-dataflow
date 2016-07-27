package com.example.examplemod;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Used to intercept {@link BlockEvent.PlaceEvent} events and broadcast interesting properties
 * using a {@link BroadcastServer}.
 *
 * @author Eric Bottard
 */
public class PlaceEventListener {

	private final BroadcastServer broadcastServer;

	public PlaceEventListener(BroadcastServer broadcastServer) {
		this.broadcastServer = broadcastServer;
	}

	@SubscribeEvent
	public void blockPlaced(BlockEvent.PlaceEvent blockEvent) {
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("x", blockEvent.getPos().getX());
		result.put("y", blockEvent.getPos().getY());
		result.put("z", blockEvent.getPos().getZ());

		PropertyEnum propertyEnum = PropertyEnum.create("color", EnumDyeColor.class);
		EnumDyeColor color = (EnumDyeColor) blockEvent.getPlacedBlock().getValue(propertyEnum);
		if (color != null) {
			Color c = ColorUtils.COLORS.get(color.toString().toUpperCase());
			result.put("red", c.getRed());
			result.put("green", c.getGreen());
			result.put("blue", c.getBlue());
			result.put("dye", color);
		}
		broadcastServer.broadcast(result);
	}

}
