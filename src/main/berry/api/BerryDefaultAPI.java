package berry.api;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import berry.loader.BerryClassTransformer;
import berry.loader.BerryLoader;
import berry.loader.BerryModInitializer;
import berry.loader.JarContainer;
import berry.utils.StringSorter;

public class BerryDefaultAPI implements BerryModInitializer {
    @Override
    public void preinit (StringSorter sorter, JarContainer jar, String name) {
        sorter.addValue (name);
        sorter.addRule ("berrybuiltins", name);
    }
    @Override
    public void initialize (String[] argv) {
        BerryLoader.preloaders.add (cl -> this.preload ());
    }
    public void preload () {
        // Find all mods!
        new BerryAPIModContainer (
            "berry.api.BerryDefaultAPIMod",
            JsonParser.parseReader (new InputStreamReader (this.getClass () .getClassLoader () .getResourceAsStream ("berry.defaultapi.mod.json"))) .getAsJsonObject ());
        for (String name : JarContainer.containers.keySet ()) {
            JarContainer container = JarContainer.containers.get (name);
            JarFile file = container.file ();
            JarEntry modjson = file.getJarEntry ("berry.mod.json");
            if (modjson != null) {
                try {
                    JsonObject jsn = JsonParser.parseReader (new InputStreamReader (file.getInputStream (modjson))) .getAsJsonObject ();
                    // String modid = jsn.get ("modid") .getAsString ();
                    // TODO: parse dependencies and versions!
                    String modclass = jsn.get ("modclass") .getAsString ();
                    BerryClassTransformer.instrumentation () .appendToSystemClassLoaderSearch (file);
                    new BerryAPIModContainer (modclass, jsn);
                } catch (RuntimeException e) {
                    e.printStackTrace ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
        }
    }
}
