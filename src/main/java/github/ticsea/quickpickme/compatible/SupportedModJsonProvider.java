package github.ticsea.quickpickme.compatible;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import github.ticsea.quickpickme.QuickPickMe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class SupportedModJsonProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupportedModJsonProvider.class);
    private static final Gson GSON = new Gson();

    private static List<SupportedModJsonProvider> cachedMods = null;

    @SerializedName("id")
    String id;
    @SerializedName("className")
    String className;
    @SerializedName("comment")
    String comment;
    @SerializedName("define")
    String[] define;

    public static List<SupportedModJsonProvider> getSupportedMods() {
        if (cachedMods!=null) return cachedMods;

        try (InputStream inputStream = QuickPickMe.class.getResourceAsStream("/config/supported_mods.json")) {
            if (inputStream==null) {
                LOGGER.warn("supported_mods.json not found in resources. Returning empty list.");
                cachedMods = Collections.emptyList();
                return cachedMods;
            }

            try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                Type listType = new TypeToken<List<SupportedModJsonProvider>>() {
                }.getType();
                List<SupportedModJsonProvider> list = GSON.fromJson(reader, listType);

                if (list==null) {
                    LOGGER.error("Parsed supported_mods.json but got null. Returning empty list.");
                    cachedMods = Collections.emptyList();
                } else {
                    cachedMods = list;
                }
            }

        } catch (Exception e) {
            LOGGER.error("Exception while reading supported_mods.json", e);
            cachedMods = Collections.emptyList();
        }

        return cachedMods;
    }

    public String getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public String getComment() {
        return comment;
    }

    public String[] getDefine() {
        return define;
    }
}
