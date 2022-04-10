import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.Locale;

public class CountdownTimer {
    private static Minecraft mc = Minecraft.getMinecraft();
    private static FontRenderer fr = mc.fontRendererObj;

    private static long currentEndTime;
    private static int currentWidth;
    private static int currentHeight;
    private static double currentScale;
    private static String currentPrefix;

    private KeyBinding pressy = new KeyBinding("Reset Timer", Keyboard.KEY_V, "Testing");

    public CountdownTimer() {
        ClientRegistry.registerKeyBinding(pressy);
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (mc.currentScreen == null || mc.currentScreen instanceof GuiChat) // will only draw the Timer while no GUI or the Chat is open
            if (currentEndTime - System.currentTimeMillis() > 0)
                drawTimer();
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (pressy.isPressed()) {
            startCountdown(34.3, 33, 33, 1.5, "§1C§6ofl > §c");
        }
    }

    /**
     * @param seconds          will start a timer starting at seconds
     * @param widthPercentage  width in correlation to the window size
     * @param heightPercentage height in correlation to the window size
     * @param fontScale        scales the text size by factor (1 = no change)
     */
    public static void startCountdown(double seconds, int widthPercentage, int heightPercentage, double fontScale) {
        startCountdown(seconds, widthPercentage, heightPercentage, fontScale, "");
    }

    /**
     * @param seconds          will start a timer starting at seconds
     * @param widthPercentage  width in correlation to the window size
     * @param heightPercentage height in correlation to the window size
     * @param fontScale        scales the text size by factor (1 = no change)
     * @param prefix           will put that text infront of the seconds (supports color codes using §)
     */
    public static void startCountdown(double seconds, int widthPercentage, int heightPercentage, double fontScale, String prefix) {
        currentEndTime = (long) (System.currentTimeMillis() + (seconds * 1000));
        currentWidth = widthPercentage;
        currentHeight = heightPercentage;
        currentScale = fontScale;
        currentPrefix = prefix;
    }

    private static void drawTimer() {
        long curMillis = currentEndTime - System.currentTimeMillis();
        String render = getStringFromDouble(curMillis / 1000D);

        ScaledResolution scaled = new ScaledResolution(mc);

        GlStateManager.pushMatrix();
        GlStateManager.scale(currentScale, currentScale, currentScale);
        int scaledX = (int) (scaled.getScaledWidth() * (currentWidth / 100D)  / currentScale);
        int scaledY = (int) (scaled.getScaledHeight() * (currentHeight / 100D)  / currentScale);
        drawHVCenteredString(currentPrefix + render, scaledX, scaledY);
        GlStateManager.popMatrix();
    }

    private static String getStringFromDouble(double seconds) {
        String render;

        if (seconds > 100) {
            render = String.valueOf((int) seconds);
        } else {
            render = String.format(Locale.US, "%.3f", seconds).substring(0, 4);
        }

        return render + "s";
    }

    public static void drawHVCenteredString(String text, int x, int y) {
        text = text.replaceAll("§", "" + ((char) 167));
        fr.drawString(text, x - (fr.getStringWidth(text) >> 1), y - (fr.FONT_HEIGHT >> 1), 0xFFFFFFFF, true);
    }
}