package berry.api;

public interface BerryMod {
    default String getModID () {
        try {
            return this.getClass () .getAnnotation (Mod.class) .modid ();
        } catch (NullPointerException e) {
            // TODO: maybe another exception?
            throw new RuntimeException ("Class does not have @Mod annotation nor getModID() method!");
        }
    }
    default String getModVersion () {
        try {
            return this.getClass () .getAnnotation (Mod.class) .version ();
        } catch (NullPointerException e) {
            // TODO: same as above
            throw new RuntimeException ("Class does not have @Mod annotation nor getModVersion() method!");
        }
    }
}
