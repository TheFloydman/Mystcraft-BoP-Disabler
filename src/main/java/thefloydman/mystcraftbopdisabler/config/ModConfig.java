package thefloydman.mystcraftbopdisabler.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thefloydman.mystcraftbopdisabler.MystcraftBopDisabler;

@Config(modid = "mystcraftbopdisabler")
@Config.LangKey("moremystcraft.config.title")
public class ModConfig {

	@Config.Name("BoP Biomes Enabled in Mystcraft Dimensions")
	@Config.Comment("Whether Pages are generated for Biomes o' Plenty Biomes for use in Mystcraft Dimensions.")
	@Config.RequiresMcRestart
	public static boolean bopBiomesEnabled = false;

	@Mod.EventBusSubscriber
	public static class EventHandler {
		@SubscribeEvent
		public void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(MystcraftBopDisabler.MOD_ID)) {
				ConfigManager.sync(MystcraftBopDisabler.MOD_ID, Config.Type.INSTANCE);
			}
		}
	}

}
