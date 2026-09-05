package com.cheatmod.screen;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import com.cheatmod.util.ArmorPoserExploit;

@Environment(EnvType.CLIENT)
public class CheatMenuScreen extends Screen {
    
    private TextFieldWidget commandInput;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;

    public CheatMenuScreen() {
        super(Text.literal("§6Cheat Menu - Armor Poser Exploit"));
    }

    @Override
    protected void init() {
        super.init();
        
        // Поле ввода команды
        this.commandInput = new TextFieldWidget(
            this.textRenderer, 
            this.width / 2 - 100, 
            this.height / 2 - 40, 
            200, 
            20, 
            Text.literal("Команда")
        );
        this.commandInput.setMaxLength(512);
        this.addDrawableChild(this.commandInput);
        
        // Кнопка отправки команды на стойку
        this.addDrawableChild(
            ButtonWidget.builder(
                Text.literal("§aОтправить команду"),
                button -> this.sendCommand()
            )
            .dimensions(this.width / 2 - BUTTON_WIDTH / 2, this.height / 2 + 10, BUTTON_WIDTH, BUTTON_HEIGHT)
            .build()
        );
        
        // Кнопка выдачи блока
        this.addDrawableChild(
            ButtonWidget.builder(
                Text.literal("§eВыдать Command Block"),
                button -> this.giveCommandBlock()
            )
            .dimensions(this.width / 2 - BUTTON_WIDTH / 2, this.height / 2 + 35, BUTTON_WIDTH, BUTTON_HEIGHT)
            .build()
        );
        
        // Кнопка закрытия
        this.addDrawableChild(
            ButtonWidget.builder(
                Text.literal("§cЗакрыть"),
                button -> this.close()
            )
            .dimensions(this.width / 2 - BUTTON_WIDTH / 2, this.height / 2 + 60, BUTTON_WIDTH, BUTTON_HEIGHT)
            .build()
        );
        
        this.setFocused(this.commandInput);
    }

    private void sendCommand() {
        String command = this.commandInput.getText().trim();
        
        if (!command.isEmpty() && this.client != null && this.client.player != null) {
            // Отправляем команду через Armor Poser баг
            ArmorPoserExploit.sendCommandViaArmorPoser(command);
            
            this.client.player.sendMessage(
                Text.literal("§a✓ Команда отправлена: §f" + command),
                false
            );
        }
        
        this.commandInput.setText("");
    }

    private void giveCommandBlock() {
        if (this.client != null && this.client.player != null) {
            // Выдаём Command Block напрямую через баг
            ArmorPoserExploit.giveItem("command_block");
            
            this.client.player.sendMessage(
                Text.literal("§a✓ Command Block выдан!"),
                false
            );
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        
        // Заголовок
        context.drawCenteredTextWithShadow(
            this.textRenderer, 
            "§6§lCHEAT MENU", 
            this.width / 2, 
            this.height / 2 - 70, 
            0xFFFF00
        );
        
        // Подсказка
        context.drawCenteredTextWithShadow(
            this.textRenderer, 
            "§7Armor Poser Exploit", 
            this.width / 2, 
            this.height / 2 - 55, 
            0xAAAAAA
        );
        
        context.drawCenteredTextWithShadow(
            this.textRenderer, 
            "§7Введите команду (без /)", 
            this.width / 2, 
            this.height / 2 - 45, 
            0xAAAAAA
        );
        
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // ESC
            this.close();
            return true;
        }
        
        if (keyCode == 257) { // ENTER
            this.sendCommand();
            return true;
        }
        
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
