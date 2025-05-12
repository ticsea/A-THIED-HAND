package github.ticsea.quickpick.events;

import com.mojang.logging.LogUtils;
import github.ticsea.quickpick.Main;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import java.util.concurrent.atomic.AtomicBoolean;
import static github.ticsea.quickpick.common.ModKeys.MOD_ON_OFF_KEY;


@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class KeyTriggerEvents {
    private static final Logger LOGGER = LogUtils.getLogger();
    //线程安全
    private static final AtomicBoolean ON_OFF = new AtomicBoolean(true);

    @SubscribeEvent
    public static void onKeyInput(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && MOD_ON_OFF_KEY.get().consumeClick()) {
            boolean newValue = ON_OFF.getAndSet(!ON_OFF.get());//切换开关
            LOGGER.debug("QuickPick 功能状态切换: {}", newValue ? "禁用" : "启用");
        }
    }

    // 提供线程安全的读取方法
    public static boolean isEnabled() {
        return ON_OFF.get();
    }
}