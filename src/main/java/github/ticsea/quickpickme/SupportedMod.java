package github.ticsea.quickpickme;

import github.ticsea.quickpickme.util.ConfigHelper.SophisticatedBackpacks;
import github.ticsea.quickpickme.util.ConfigHelper.TouhouLittleMaid;
import java.util.function.Supplier;
import net.minecraft.world.inventory.AbstractContainerMenu;

public enum SupportedMod {
    TETRA(
            "se.mickelus.tetra.blocks.workbench.WorkbenchContainer",
            () -> false // 永不启用，相当于黑名单
    ),
    BACKPACK(
            "net.p3pp3rf1y.sophisticatedbackpacks.common.gui.BackpackContainer",
            SophisticatedBackpacks::isBackpackSupportEnabled
    ),
    TOUHOU_LITTLE_MAID(
            "com.github.tartaricacid.touhoulittlemaid.inventory.container.MaidMainContainer",
            TouhouLittleMaid::isLittleMaidSupportEnabled
    );

    private final String className;
    private final Supplier<Boolean> isEnabled;

    SupportedMod(String className, Supplier<Boolean> isEnabled) {
        this.className = className;
        this.isEnabled = isEnabled;
    }

    public boolean matchesSupportedMod(AbstractContainerMenu menu) {
        try {
            Class<?> clazz = Class.forName(className);
            return clazz.isInstance(menu);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public boolean isEnabled() {
        return isEnabled.get();
    }
}
