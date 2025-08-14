package com.example.sizechanger;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

/**
 * Client-only screen that lets the player choose a new size.  Six preset
 * buttons are provided ranging from 0.25× to 4.0×.  Clicking a button
 * closes the screen and sends a scale request to the server.
 */
@Environment(EnvType.CLIENT)
public class ClientGUI extends Screen {
    private final float currentScale;

    private ClientGUI(float currentScale) {
        super(Text.translatable("screen.sizechanger.title"));
        this.currentScale = currentScale;
    }

    /**
     * Open the GUI from client code.  The current scale should be passed
     * so that cost calculations can be displayed if desired.
     */
    public static void open(float currentScale) {
        MinecraftClient.getInstance().setScreen(new ClientGUI(currentScale));
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int cy = this.height / 2;
        // Top row: 0.25, 0.5, 1.0
        addPreset(cx - 90, cy - 10, 0.25f, Text.literal("0.25×"));
        addPreset(cx - 30, cy - 10, 0.5f,  Text.literal("0.5×"));
        addPreset(cx + 30, cy - 10, 1.0f,  Text.literal("1.0×"));
        // Bottom row: 2.0, 3.0, 4.0
        addPreset(cx - 90, cy + 20, 2.0f, Text.literal("2.0×"));
        addPreset(cx - 30, cy + 20, 3.0f, Text.literal("3.0×"));
        addPreset(cx + 30, cy + 20, 4.0f, Text.literal("4.0×"));
    }

    /**
     * Helper to add a button for a preset scale.  When clicked, sends a
     * request to the server and closes the screen.
     */
    private void addPreset(int x, int y, final float scale, Text label) {
        this.addDrawableChild(ButtonWidget.builder(label, b -> {
            ModNetworking.sendScaleRequest(scale);
            this.close();
        }).dimensions(x, y, 50, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        int cx = this.width / 2;
        // Title and subtitle
        context.drawCenteredTextWithShadow(this.textRenderer,
                Text.translatable("screen.sizechanger.title"), cx, 40, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer,
                Text.translatable("screen.sizechanger.subtitle"), cx, 58, 0xA0A0A0);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}