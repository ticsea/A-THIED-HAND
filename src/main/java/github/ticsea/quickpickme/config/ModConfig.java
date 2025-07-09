package github.ticsea.quickpickme.config;

import static github.ticsea.quickpickme.compatible.SupportedModJsonProvider.getSupportedMods;

import github.ticsea.quickpickme.compatible.SupportedModJsonProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModConfig {

    public static final ForgeConfigSpec CONFIG;
    public static final BooleanValue ENABLE_MOD;
    public static final BooleanValue DISPLAYCLASSNAME;
    private static final Logger LOGGER = LoggerFactory.getLogger(ModConfig.class);
    public static Map<String, BooleanValue> idToBooleanValueMap = new HashMap<>();
    private static List<SupportedModJsonProvider> supportedModList;

    static {
        supportedModList = getSupportedMods();
        if (supportedModList==null) {
            LOGGER.error("getSupportedMods() returned null. Using an empty list for supported mods.");
            supportedModList = new ArrayList<>();
        }

        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("QuickPick Settings");

        ENABLE_MOD = builder
                .comment("Enable or disable the QuickPick mod globally.\n[true | false]")
                .define("enable_mod", true);

        DISPLAYCLASSNAME = builder
                .comment("Enable or disable to display className of you opened chest .\n[true | false]")
                .define("display classname", false);

        for (SupportedModJsonProvider mod : supportedModList) {
            addBooleanValue(builder, mod, idToBooleanValueMap);
        }

        builder.pop();
        CONFIG = builder.build();
    }

    private static void addBooleanValue(ForgeConfigSpec.Builder builder,
                                        SupportedModJsonProvider mod,
                                        Map<String, BooleanValue> idToBooleanValueMap) {
        LOGGER.info("Starting to add BooleanValue for mod with id: {}", mod.getId());
        String[] define = mod.getDefine();
        if (define!=null && define.length==2) {
            try {
                BooleanValue value = builder
                        .comment(mod.getComment())
                        .define(define[0], Boolean.parseBoolean(define[1]));
                idToBooleanValueMap.put(mod.getId(), value);
                LOGGER.info("Successfully added BooleanValue for mod with id: {}", mod.getId());
            } catch (NumberFormatException e) {
                LOGGER.error("Failed to parse boolean value for mod with id: {}. Define value was: {}", mod.getId(), define[1], e);
            }
        } else {
            LOGGER.error("Invalid define for mod with id: {}", mod.getId());
        }
        LOGGER.info("Finished adding BooleanValue for mod with id: {}", mod.getId());
    }

    public static BooleanValue getBooleanValueByClassName(String className) {
        for (SupportedModJsonProvider mod : supportedModList) {
            if (mod.getClassName().equals(className)) {
                return idToBooleanValueMap.get(mod.getId());
            }
        }
        return null;
    }
}
