package berry.api.utils.respack;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import net.minecraft.server.MinecraftServer.ServerResourcePackInfo;

public class PackInfoIO {
    public static void write (Optional <ServerResourcePackInfo> opt) { write (opt, "respack.json"); }
    public static void write (Optional <ServerResourcePackInfo> opt, String filename) {
        JsonObject jobj = new JsonObject ();
        if (opt.isPresent ()) {
            ServerResourcePackInfo info = opt.get ();
            jobj.add ("present", new JsonPrimitive (true));
            jobj.add ("id", new JsonPrimitive (info.id () .toString ()));
            jobj.add ("url", new JsonPrimitive (info.url ()));
            jobj.add ("sha1", new JsonPrimitive (info.hash ()));
            jobj.add ("required", new JsonPrimitive (info.isRequired ()));
            // Unfortunately prompt is not supported currently
        } else jobj.add ("present", new JsonPrimitive (false));
        try {
            var os = new FileOutputStream (filename);
            os.write (jobj.toString () .getBytes ());
            os.close ();
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }
    public static Optional <ServerResourcePackInfo> read () { return read ("respack.json"); }
    public static Optional <ServerResourcePackInfo> read (String filename) {
        JsonObject jobj;
        try {
            var is = new FileInputStream (filename);
            jobj = JsonParser.parseReader (new InputStreamReader (is)) .getAsJsonObject ();
            is.close ();
        } catch (IOException e) {
            return Optional.empty ();
        } catch (IllegalStateException e) {
            return Optional.empty ();
        }
        try {
            boolean present = jobj.get ("present") .getAsBoolean ();
            if (!present) return Optional.empty ();
            UUID uuid = UUID.fromString (jobj.get ("id") .getAsString ());
            String url = jobj.get ("url") .getAsString ();
            String hash = jobj.get ("sha1") .getAsString ();
            boolean required = jobj.get ("required") .getAsBoolean ();
            return Optional.of (new ServerResourcePackInfo (uuid, url, hash, required, null));
        } catch (Exception e) {
            return Optional.empty ();
        }
    }
}
