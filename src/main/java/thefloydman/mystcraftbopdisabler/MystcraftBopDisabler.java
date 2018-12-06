package thefloydman.mystcraftbopdisabler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import thefloydman.mystcraftbopdisabler.config.ModConfig;

@Mod(modid = MystcraftBopDisabler.MOD_ID, name = "Mystcraft BoP Disabler", version = "1.0.0", dependencies = "")
public class MystcraftBopDisabler {

	public final static String MOD_ID = "mystcraftbopdisabler";

	public static Logger logger;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		writeToMystcraftSymbolConfig(ModConfig.bopBiomesEnabled);
	}

	@EventHandler
	public static void init(FMLInitializationEvent event) {

	}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {

	}

	private static void writeToMystcraftSymbolConfig(boolean bopEnabled) {
		String baseDir = Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
		baseDir = baseDir.substring(0, baseDir.length() - 1);
		File mystcraftConfig = new File(baseDir + "config\\mystcraft\\symbols.cfg");
		File mystcraftConfigTemp = new File(baseDir + "config\\mystcraft\\symbols.cfg.temp");

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
					bw.write(line);
				}
			}
		} catch (Exception e) {
			return;
		} finally {
			try {
				if (br != null) {
					br.close();
					System.out.println("Error");
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
