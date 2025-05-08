package berry.api;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixins;

import com.google.gson.JsonObject;

public class BerryAPIModContainer {
    private static final Map <String, BerryAPIModContainer> mods = new HashMap <> ();
    private final BerryMod mod;
    private final JsonObject modjson;
    @SuppressWarnings("unchecked")
    public BerryAPIModContainer (String clazz, JsonObject modjson) {
        this.modjson = modjson;
        try {
            Class <? extends BerryMod> cls = ((Class <? extends BerryMod>) Class.forName (clazz));
            Constructor <? extends BerryMod> constructor = cls.getConstructor ();
            BerryMod mod = constructor.newInstance ();
            this.mod = mod;
            mods.put (mod.getModID (), this);
        } catch (ClassCastException e) {
            // The given class does not implement BerryMod
            throw new IllegalArgumentException ("Mod class " + clazz + " does not implement BerryMod!", e);
        } catch (ClassNotFoundException e) {
            // Some mistake?
            throw new RuntimeException ("Error: cannot find class " + clazz + "! This may be a bug.", e);
        } catch (ReflectiveOperationException e) {
            // Does not have a required constructor (with no arguments)
            // or the construction failed
            throw new IllegalArgumentException ("Mod class " + clazz + " failed constructing!", e);
        }
        if (modjson.get ("mixin") != null) Mixins.addConfiguration (modjson.get ("mixin") .getAsString ());
    }
    public BerryMod getMod () {
        return this.mod;
    }
    public JsonObject getModJson () {
        return this.modjson;
    }
    public static BerryAPIModContainer getContainer (String modid) {
        return mods.get (modid);
    }
}
