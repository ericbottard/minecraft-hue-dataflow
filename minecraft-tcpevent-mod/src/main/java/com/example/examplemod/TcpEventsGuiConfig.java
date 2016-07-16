package com.example.examplemod;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;

/**
 * Created by ericbottard on 30/06/16.
 */
public class TcpEventsGuiConfig extends GuiConfig {

	public TcpEventsGuiConfig(GuiScreen parentScreen) {
		super(parentScreen, new ConfigElement(
				TcpEventsMod.config.getCategory("server"))
				.getChildElements(), TcpEventsMod.MODID, false, false, "Tcp Events Config");
	}
}
