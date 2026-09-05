package com.cheatmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import com.cheatmod.screen.CheatMenuScreen;

@Environment(EnvType.CLIENT)
public class CheatMod implements ClientModInitializer {
    
    private static final KeyBinding OPEN_MENU = KeyBindingHelper.registerKeyBinding(
        new KeyBinding(
            "key.cheatmod.menu",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_INSERT,
            "category.cheatmod"
        )
    );

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_MENU.wasPressed()) {
                if (client.player != null) {
                    client.setScreen(new CheatMenuScreen());
                }
            }
        });
    }
}
