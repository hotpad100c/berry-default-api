package berry.api.utils.reflection;
import java.lang.reflect.*;

public class ReflectionUnit {

    /**
     * 从实现了 EventListener<T extends Event> 的类中提取实际的 T 类型。
     *
     * @param listenerClass 实现了 EventListener 的类
     * @return T 的具体类，如果无法获取则返回 null
     */
    public static Class<? extends Event> getEventTypeFromListener(Class<?> listenerClass) {
        // 递归查找接口直到找到泛型参数
        while (listenerClass != null) {
            for (Type type : listenerClass.getGenericInterfaces()) {
                if (type instanceof ParameterizedType ptype) {
                    Type raw = ptype.getRawType();
                    if (raw instanceof Class<?> rawClass && EventListener.class.isAssignableFrom(rawClass)) {
                        Type actual = ptype.getActualTypeArguments()[0];

                        if (actual instanceof Class<?> eventClass) {
                            return eventClass.asSubclass(Event.class); // ✅ 成功
                        }

                        // 🟡 泛型参数不是类，比如是 ? 或 T（TypeVariable）
                        System.out.println("泛型参数不是具体类: " + actual.getTypeName());
                    }
                }
            }

            // 往上查找父类（比如继承了带泛型参数的父类）
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
