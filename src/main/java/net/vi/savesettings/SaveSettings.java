package net.vi.savesettings;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class SaveSettings implements ClientModInitializer {
    static File optionsFile = new File(FabricLoader.getInstance().getGameDir().toString() + "/options.txt");
    static File configFile = new File(FabricLoader.getInstance().getConfigDir() + "/Save-Settings.txt");

    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("savesettings")
            .executes(context -> {
                try {
                    Files.write(configFile.toPath(), Files.readAllLines(Path.of(optionsFile.getPath())));
                } catch (Exception ignored) {}

                context.getSource().sendFeedback(Text.literal("The command is executed in the client!"));
                return 1;
            }
        )));

        try {
            if (!optionsFile.exists()) {
                optionsFile.createNewFile();
                Files.write(configFile.toPath(), Files.readAllLines(Path.of(optionsFile.getPath())));
            }

            Files.write(Path.of(optionsFile.getPath()), Files.readAllLines(Paths.get(configFile.toString())));
        } catch (Exception ignored) {}
    }
}
