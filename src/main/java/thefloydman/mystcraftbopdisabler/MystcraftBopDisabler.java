package thefloydman.mystcraftbopdisabler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import thefloydman.mystcraftbopdisabler.config.ModConfig;

@Mod(modid = MystcraftBopDisabler.MOD_ID, name = MystcraftBopDisabler.NAME, version = MystcraftBopDisabler.VERSION, dependencies = MystcraftBopDisabler.DEPENDENCIES)
public class MystcraftBopDisabler {

	public final static String MOD_ID = "mystcraftbopdisabler";
	public final static String NAME = "Mystcraft BoP Disabler";
	public final static String VERSION = "1.0.2";
	public final static String DEPENDENCIES = "";
	public static Logger logger;
	private static File configDir;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		configDir = event.getModConfigurationDirectory();
		writeToMystcraftSymbolConfig(ModConfig.bopBiomesEnabled);
	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {

	}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {

	}

	private static void writeToMystcraftSymbolConfig(boolean bopEnabled) {
		
		File mystcraftConfig = new File(configDir + "/mystcraft/symbols.cfg");
		File mystcraftConfigTemp = new File(configDir + "/mystcraft/symbols.cfg.temp");

		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new FileReader(mystcraftConfig));
			bw = new BufferedWriter(new FileWriter(mystcraftConfigTemp));
			String line;
			while ((line = br.readLine()) != null) {
				boolean skipped = true;
				for (int j = 40; j <= 110; j++) {
					if (line.contains("biome" + String.valueOf(j))) {
						line = line.replace(String.valueOf((bopEnabled == true) ? false : true),
								String.valueOf((bopEnabled == true) ? true : false));
						skipped = false;
						break;
					}
				}
				if (skipped = true) {
					bw.write(line + "\r\n");
				}
			}
		} catch (Exception e) {
			return;
		} finally {
			try {
				if (br != null) {
					br.close();
					logger.error("Error");
				}
			} catch (IOException e) {
			}
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// Once everything is complete, delete old file..
		File oldFile = mystcraftConfig;
		oldFile.delete();

		// And rename tmp file's name to old file name
		File newFile = mystcraftConfigTemp;
		newFile.renameTo(oldFile);
	}

}
