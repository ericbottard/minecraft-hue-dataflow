package com.example.examplemod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLModDisabledEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * A simple mod that broadcasts all sorts of Minecraft events over a tcp server using Netty.
 * @author Eric Bottard
 */
@Mod(modid = TcpEventsMod.MODID,
		version = TcpEventsMod.VERSION,
		canBeDeactivated = true,
		guiFactory = "com.example.examplemod.TcpEventsModGuiFactory"
)
public class TcpEventsMod {

	public static final String MODID = "tcpevent";

	public static final String VERSION = "1.0";

	/*default*/ static Configuration config;

	private BroadcastServer broadcastServer;

	private PlaceEventListener blockPlacedListener;

	private LivingUpdateEventListener livingUpdateEventListener;

	@EventHandler
	public void init(FMLPreInitializationEvent event) {

		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		int port = getPort();

		broadcastServer = new BroadcastServer(port);
		Thread t = new Thread() {

			@Override
			public void run() {
				try {
					broadcastServer.run();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.start();

		blockPlacedListener = new PlaceEventListener(broadcastServer);
		livingUpdateEventListener = new LivingUpdateEventListener(broadcastServer);

		applyConfig();
		MinecraftForge.EVENT_BUS.register(new GuiConfigListener());
	}

	private void applyConfig() {
		boolean placeEvents = getPlacedEvents();
		if (placeEvents) {
			MinecraftForge.EVENT_BUS.register(blockPlacedListener);
		}
		else {
			MinecraftForge.EVENT_BUS.unregister(blockPlacedListener);
		}

		boolean livingUpdates = getLivingUpdateEvents();
		if (livingUpdates) {
			MinecraftForge.EVENT_BUS.register(livingUpdateEventListener);
		}
		else {
			MinecraftForge.EVENT_BUS.unregister(livingUpdateEventListener);
		}


	}

	private boolean getPlacedEvents() {
		return config.get("server", "blocksPlaced", true, "whether to listen to block placed events").getBoolean();
	}

	private boolean getLivingUpdateEvents() {
		return config.get("server", "livingUpdates", false, "whether to listen to living entity updates").getBoolean();
	}

	private int getPort() {
		return config.get("server", "port", 8080, "port the tcp server will be listening on").getInt();
	}

	@EventHandler
	public void disable(FMLModDisabledEvent event) {
		System.out.println("Disable " + event);
	}

	private class GuiConfigListener {
		@SubscribeEvent
		public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(MODID)) {
				config.save();
				applyConfig();
			}
		}
	}

}
