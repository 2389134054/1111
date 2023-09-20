package net.fabricmc.example;
import net.fabricmc.example.mod.FireballHandler;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExampleMod implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("jiangmod");

	@Override
	public void onInitialize() {

		LOGGER.info("Hello Fabric world!");

		Registry.register(Registry.ITEM, new Identifier("jiangmod", "fireball_handler"), new FireballHandler(ToolMaterials.IRON));


	}
}