package net.example.timer.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;

public class TimerModClient implements ClientModInitializer {
    private static int ticksActive = 0;
    private static boolean wasInWorld = false;

    @Override
    public void onInitializeClient() {
        // يحسب التيكس (Ticks) لتحديث الوقت بدقة (كل 20 تيك تساوي ثانية واحدة)
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world != null && !client.isPaused()) {
                if (!wasInWorld) {
                    wasInWorld = true;
                    ticksActive = 0; // يصفر العداد عند دخول عالم جديد
                }
                ticksActive++;
            } else if (client.world == null) {
                wasInWorld = false; // يعيد التهيئة عند الخروج للقائمة الرئيسية
            }
        });

        // يقوم برسم النص وحساب الوقت على الشاشة (HUD)
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.world == null) return;

            TextRenderer textRenderer = client.textRenderer;
            
            // تحويل الـ Ticks إلى ساعات ودقائق وثواني
            int totalSeconds = ticksActive / 20;
            int hours = totalSeconds / 3600;
            int minutes = (totalSeconds % 3600) / 60;
            int seconds = totalSeconds % 60;

            String timeDisplay = String.format("الوقت في العالم: %02d:%02d:%02d", hours, minutes, seconds);
            
            // رسم النص في أعلى يسار الشاشة بخلفية مدمجة للحماية من ألوان اللعبة
            drawContext.drawText(textRenderer, Text.literal(timeDisplay), 10, 10, 0xE0E0E0, true);
        });
    }
}
