package xyz.telecter.rideanything.config;

import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.controller.ControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.ConfigField;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.CustomDescription;
import dev.isxander.yacl3.config.v2.api.autogen.EnumCycler;
import dev.isxander.yacl3.config.v2.api.autogen.ListGroup;
import dev.isxander.yacl3.config.v2.api.autogen.OptionAccess;
import dev.isxander.yacl3.config.v2.api.autogen.TickBox;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.impl.controller.StringControllerBuilderImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import xyz.telecter.rideanything.RideAnythingMod;

public class RideAnythingConfig {
        public static ConfigClassHandler<RideAnythingConfig> HANDLER = ConfigClassHandler
                        .createBuilder(RideAnythingConfig.class)
                        .id(Identifier.of(RideAnythingMod.MOD_ID, "config"))
                        .serializer(config -> GsonConfigSerializerBuilder.create(config)
                                        .setPath(FabricLoader.getInstance().getConfigDir()
                                                        .resolve(RideAnythingMod.MOD_ID + ".json5"))
                                        .setJson5(true)
                                        .build())
                        .build();

        @SerialEntry
        @AutoGen(category = "general")
        @TickBox
        @CustomDescription(value = "Enable or disable the mod.")
        public boolean enabled = true;

        @SerialEntry
        @AutoGen(category = "general")
        @EnumCycler
        @CustomDescription(value = "Controls what type of mobs can be ridden.")
        public Mode mode = Mode.ANIMALS;

        @SerialEntry
        @AutoGen(category = "general")
        @ListGroup(controllerFactory = StringControllerFactory.class, valueFactory = StringValueFactory.class)
        public List<String> allowed = Collections.emptyList();

        public enum Mode {
                @SerializedName("all")
                ALL,
                @SerializedName("custom")
                CUSTOM,
                @SerializedName("animals")
                ANIMALS
        }

        public static class StringValueFactory implements ListGroup.ValueFactory<String> {
                public StringValueFactory() {
                }

                @Override
                public String provideNewValue() {
                        return "minecraft:creeper";
                }
        }

        public static class StringControllerFactory implements ListGroup.ControllerFactory<String> {
                public StringControllerFactory() {
                }

                @Override
                public ControllerBuilder<String> createController(
                                ListGroup annotation,
                                ConfigField<List<String>> field,
                                OptionAccess storage,
                                Option<String> option) {
                        return new StringControllerBuilderImpl(option);
                }
        }
}
