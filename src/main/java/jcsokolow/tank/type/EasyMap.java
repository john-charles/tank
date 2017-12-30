package jcsokolow.tank.type;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class EasyMap implements Map<String, Object> {

    Map<String, Object> root;

    public EasyMap() {
        root = new HashMap<>();
    }

    public EasyMap(Map<String, Object> child) {
        root = child;
    }

    @Override
    public int size() {
        return root.size();
    }

    @Override
    public boolean isEmpty() {
        return root.isEmpty();
    }

    @Override
    public boolean containsKey(Object o) {
        return root.containsKey(o);
    }

    @Override
    public boolean containsValue(Object o) {
        return root.containsValue(o);
    }

    @Override
    public Object get(Object o) {
        return root.get(o);
    }

    @Override
    public Object put(String s, Object o) {
        return root.put(s, o);
    }

    @Override
    public Object remove(Object o) {
        return root.remove(o);
    }

    @Override
    public void putAll(Map<? extends String, ?> map) {
        root.putAll(map);
    }

    @Override
    public void clear() {
        root.clear();
    }

    @Override
    public Set<String> keySet() {
        return root.keySet();
    }

    @Override
    public Collection<Object> values() {
        return root.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return root.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return root.equals(o);
    }

    @Override
    public int hashCode() {
        return root.hashCode();
    }

    @Override
    public Object getOrDefault(Object o, Object o2) {
        return root.getOrDefault(o, o2);
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super Object> biConsumer) {
        root.forEach(biConsumer);
    }

    @Override
    public void replaceAll(BiFunction<? super String, ? super Object, ?> biFunction) {
        root.replaceAll(biFunction);
    }

    @Override
    public Object putIfAbsent(String s, Object o) {
        return root.putIfAbsent(s, o);
    }

    @Override
    public boolean remove(Object o, Object o1) {
        return root.remove(o, o1);
    }

    @Override
    public boolean replace(String s, Object o, Object v1) {
        return root.replace(s, o, v1);
    }

    @Override
    public Object replace(String s, Object o) {
        return root.replace(s, o);
    }

    @Override
    public Object computeIfAbsent(String s, Function<? super String, ?> function) {
        return root.computeIfAbsent(s, function);
    }

    @Override
    public Object computeIfPresent(String s, BiFunction<? super String, ? super Object, ?> biFunction) {
        return root.computeIfPresent(s, biFunction);
    }

    @Override
    public Object compute(String s, BiFunction<? super String, ? super Object, ?> biFunction) {
        return root.compute(s, biFunction);
    }

    @Override
    public Object merge(String s, Object o, BiFunction<? super Object, ? super Object, ?> biFunction) {
        return root.merge(s, o, biFunction);
    }

    public String getString(String key) {
        Object value = root.get(key);

        if (value instanceof String) {
            return (String) value;
        } else {
            return "";
        }
    }

    public Boolean getBool(String key) {

        Object value = root.get(key);

        if (value == null) {
            return false;
        }
        if (!(value instanceof Boolean)) {
            return false;
        } else {
            return (Boolean) value;
        }
    }

    public List<EasyMap> getListOfMaps(String key) {

        Object value = root.get(key);

        if (value == null) {
            return new LinkedList<>();
        }

        if (value instanceof List) {
            List<EasyMap> result = new LinkedList<>();

            for (Object o : (List) value) {

                if (o instanceof Map) {
                    result.add(new EasyMap((Map<String, Object>) o));
                }
            }

            return result;

        }

        return null;
    }

    public EasyMap getMap(String key) {

        Object value = root.get(key);

        if (value == null) {
            return null;
        } else if (value instanceof Map) {
            return new EasyMap((Map<String, Object>) value);
        }

        return null;
    }

}
