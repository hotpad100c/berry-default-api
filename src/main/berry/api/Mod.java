package berry.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention (RetentionPolicy.RUNTIME)
public @interface Mod {
    /**
     * Mod ID.
     * @return Mod ID
     */
    String modid ();
    /**
     * Version.
     * @return Mod version
     */
    String version ();
}
