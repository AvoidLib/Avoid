package pl.olafcio.avoid.mods.config;

import java.util.*;
import java.util.function.Function;

public final class YamlConfiguration implements Configuration {
    private final Map<String, Object> yaml;

    public YamlConfiguration(Map<String, Object> yaml) {
        this.yaml = yaml;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Configuration getSection(String path) {
        var obj = yaml;

        while (!path.isEmpty()) {
            int dot = path.indexOf(".");
            if (dot == -1)
                dot = 0;

            if (obj.get(path.substring(0, dot)) instanceof Map<?, ?> next)
                obj = (Map<String, Object>) next;
            else return null;

            path = path.substring(dot + 1);
        }

        return new YamlConfiguration(obj);
    }

    @SuppressWarnings("unchecked")
    private Object get(String path) {
        var obj = yaml;

        int dot;
        while ((dot = path.indexOf(".")) != -1) {
            if (obj.get(path.substring(0, dot)) instanceof Map<?, ?> next)
                obj = (Map<String, Object>) next;
            else return null;

            path = path.substring(dot + 1);
        }

        return obj.get(path);
    }

    private <T> T map(String path, Function<Object, T> mapper) {
        var value = get(path);
        if (value == null)
            return null;

        try {
            return mapper.apply(value);
        } catch (Throwable e) {
            if (e instanceof ConfigurationIssue ci && ci.getMessage() != null)
                throw ci;


            var stack = Arrays.stream(e.getStackTrace())
                              .filter(el -> el.getClassName().equals(YamlConfiguration.class.getName()))
                              .toArray(StackTraceElement[]::new)[1];

            throw new ConfigurationIssue("Expected %s, got: '%s'".formatted(stack.getMethodName().substring(3), value), e);
        }
    }

    @Override
    public String getString(String path) {
        return map(path, Object::toString);
    }

    @Override
    public Integer getInteger(String path) {
        return map(path, Numbers::toInt);
    }

    @Override
    public Float getFloat(String path) {
        return map(path, Numbers::toFloat);
    }

    @Override
    public Long getLong(String path) {
        return map(path, Numbers::toLong);
    }

    @Override
    public Short getShort(String path) {
        return map(path, Numbers::toShort);
    }

    @Override
    public Byte getByte(String path) {
        return map(path, Numbers::toByte);
    }

    @Override
    public Double getDouble(String path) {
        return map(path, Numbers::toDouble);
    }

    @Override
    public List<?> getList(String path) {
        return map(path, value -> (List<?>) value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> getListOf(String path, Class<T> elementType) {
        return map(path, value -> {
            if (value instanceof List<?> list) {
                for (var el : list)
                    if (!elementType.isInstance(el))
                        throw new ConfigurationIssue("Expected %sList, got element: '%s'".formatted(elementType.getSimpleName(), el));

                return (List<T>) list;
            } else {
                throw new ConfigurationIssue();
            }
        });
    }

    @Override
    public <T> List<T> getListOf(String path, Class<T> elementType, Function<Object, T> elementConverter) {
        return map(path, value -> {
            if (value instanceof List<?> list) {
                var out = new ArrayList<T>();

                for (var el : list) {
                    try {
                        out.add(elementConverter.apply(el));
                    } catch (Throwable e) {
                        throw new ConfigurationIssue("Expected %sList, got element: '%s'".formatted(elementType.getSimpleName(), el));
                    }
                }

                return out;
            } else {
                throw new ConfigurationIssue();
            }
        });
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getNew(String path) {
        var obj = yaml;

        int dot;
        while ((dot = path.indexOf(".")) != -1) {
            var key = path.substring(0, dot);

            if (obj.get(key) instanceof Map<?, ?> next) {
                obj = (Map<String, Object>) next;
            } else {
                var val = new HashMap<String, Object>();

                obj.put(key, val);
                obj = val;
            }

            path = path.substring(dot + 1);
        }

        return obj;
    }

    @Override
    public Map<String, Object> put(String path, Object value) {
        var key = path.substring(path.lastIndexOf('.') + 1);
        var parent = getNew(path);

        parent.put(key, value);

        return parent;
    }

    @Override
    public Object remove(String path) {
        var key = path.substring(path.lastIndexOf('.') + 1);
        var parent = getNew(path);

        return parent.remove(key);
    }
}
