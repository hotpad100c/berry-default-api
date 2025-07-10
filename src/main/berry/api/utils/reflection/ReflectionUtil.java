package berry.api.utils.reflection;
import java.lang.reflect.*;

public class ReflectionUnit {

    /**
     * Parse T from EventListener<T extends Event> 
     *
     * @param listenerClass( EventListener )
     * @return T's actrual class, null if cannot find one.
     */
    public static Class<? extends Event> getEventTypeFromListener(Class<?> listenerClass) {
        // S
        while (listenerClass != null) {
            for (Type type : listenerClass.getGenericInterfaces()) {
                // foreach interface
                if (type instanceof ParameterizedType ptype) {
                    Type raw = ptype.getRawType();
                    if (raw instanceof Class<?> rawClass && EventListener.class.isAssignableFrom(rawClass)) {
                        Type actual = ptype.getActualTypeArguments()[0];

                        if (actual instanceof Class<?> eventClass) {
                            return eventClass.asSubclass(Event.class); 
                        }
                        System.out.println("Generics is a abstract class: " + actual.getTypeName());
                    }
                }
            }

            // Search parents
            Type superType = listenerClass.getGenericSuperclass();
            if (superType instanceof ParameterizedType) {
                listenerClass = (Class<?>) ((ParameterizedType) superType).getRawType();
            } else if (superType instanceof Class<?>) {
                listenerClass = (Class<?>) superType;
            } else {
                break;
            }
        }

        return null;
    }

}
