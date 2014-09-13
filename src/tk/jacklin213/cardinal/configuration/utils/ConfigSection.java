package tk.jacklin213.cardinal.configuration.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;

public class ConfigSection {
	protected final Map<String, Object> map = new LinkedHashMap<String, Object>();
    private final Config root;
    private final ConfigSection parent;
    private final String path;
    private final String fullPath;
    
    protected ConfigSection() {
    	 if (!(this instanceof Config)) {
             throw new IllegalStateException("Cannot construct a root MemorySection when not a Configuration");
         }

         this.path = "";
         this.fullPath = "";
         this.parent = null;
         this.root = (Config) this;
    }
    
    protected ConfigSection(ConfigSection parent, String path) {
        Validate.notNull(parent, "Parent cannot be null");
        Validate.notNull(path, "Path cannot be null");

        this.path = path;
        this.parent = parent;
        this.root = parent.getRoot();

        Validate.notNull(root, "Path cannot be orphaned");

        this.fullPath = createPath(parent, path);
    }
    
    public Set<String> getKeys(boolean deep) {
        Set<String> result = new LinkedHashSet<String>();

        Config root = getRoot();
        if (root != null && root.copyDefaults()) {
        	ConfigSection defaults = getDefaultSection();

            if (defaults != null) {
                result.addAll(defaults.getKeys(deep));
            }
        }

        mapChildrenKeys(result, this, deep);

        return result;
    }

    public Map<String, Object> getValues(boolean deep) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();

        Config root = getRoot();
        if (root != null && root.copyDefaults()) {
        	ConfigSection defaults = getDefaultSection();

            if (defaults != null) {
                result.putAll(defaults.getValues(deep));
            }
        }

        mapChildrenValues(result, this, deep);

        return result;
    }

    public boolean contains(String path) {
        return get(path) != null;
    }
    
    public String getName() {
        return path;
    }

    public Config getRoot() {
        return root;
    }

    public ConfigSection getParent() {
        return parent;
    }
    
    public String getCurrentPath() {
        return fullPath;
    }
    
    public void addDefault(String path, Object value) {
        Validate.notNull(path, "Path cannot be null");

        Config root = getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot add default without root");
        }
        if (root == this) {
            throw new UnsupportedOperationException("Unsupported addDefault(String, Object) implementation");
        }
        root.addDefault(createPath(this, path), value);
    }

    public ConfigSection getDefaultSection() {
    	Config root = getRoot();
    	Config defaults = root == null ? null : root.getDefaults();

        if (defaults != null) {
            if (defaults.isConfigSection(getCurrentPath())) {
                return defaults.getConfigSection(getCurrentPath());
            }
        }

        return null;
    }

    public void set(String path, Object value) {
        Validate.notEmpty(path, "Cannot set to an empty path");

        Config root = getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot use section without a root");
        }

        // i1 is the leading (higher) index
        // i2 is the trailing (lower) index
        int i1 = -1, i2;
        ConfigSection section = this;
        while ((i1 = path.indexOf(".", i2 = i1 + 1)) != -1) {
            String node = path.substring(i2, i1);
            ConfigSection subSection = section.getConfigSection(node);
            if (subSection == null) {
                section = section.createSection(node);
            } else {
                section = subSection;
            }
        }

        String key = path.substring(i2);
        if (section == this) {
            if (value == null) {
                map.remove(key);
            } else {
                map.put(key, value);
            }
        } else {
            section.set(key, value);
        }
    }
    
    public Object get(String path) {
        return get(path, getDefault(path));
    }

    public Object get(String path, Object def) {
        Validate.notNull(path, "Path cannot be null");

        if (path.length() == 0) {
            return this;
        }

        Config root = getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot access section without a root");
        }

        // i1 is the leading (higher) index
        // i2 is the trailing (lower) index
        int i1 = -1, i2;
        ConfigSection section = this;
        while ((i1 = path.indexOf(".", i2 = i1 + 1)) != -1) {
            section = section.getConfigSection(path.substring(i2, i1));
            if (section == null) {
                return def;
            }
        }

        String key = path.substring(i2);
        if (section == this) {
            Object result = map.get(key);
            return (result == null) ? def : result;
        }
        return section.get(key, def);
    }
    
    public ConfigSection createSection(String path) {
        Validate.notEmpty(path, "Cannot create section at empty path");
        Config root = getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot create section without a root");
        }

        // i1 is the leading (higher) index
        // i2 is the trailing (lower) index
        int i1 = -1, i2;
        ConfigSection section = this;
        while ((i1 = path.indexOf(".", i2 = i1 + 1)) != -1) {
            String node = path.substring(i2, i1);
            ConfigSection subSection = section.getConfigSection(node);
            if (subSection == null) {
                section = section.createSection(node);
            } else {
                section = subSection;
            }
        }

        String key = path.substring(i2);
        if (section == this) {
        	ConfigSection result = new ConfigSection(this, key);
            map.put(key, result);
            return result;
        }
        return section.createSection(key);
    }
    
    public ConfigSection createSection(String path, Map<?, ?> map) {
        ConfigSection section = createSection(path);

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (entry.getValue() instanceof Map) {
                section.createSection(entry.getKey().toString(), (Map<?, ?>) entry.getValue());
            } else {
                section.set(entry.getKey().toString(), entry.getValue());
            }
        }

        return section;
    }

    // Primitives
    public String getString(String path) {
        Object def = getDefault(path);
        return getString(path, def != null ? def.toString() : null);
    }

    public String getString(String path, String def) {
        Object val = get(path, def);
        return (val != null) ? val.toString() : def;
    }

    public boolean isString(String path) {
        Object val = get(path);
        return val instanceof String;
    }

    public int getInt(String path) {
        Object def = getDefault(path);
        return getInt(path, (def instanceof Number) ? ((Number) def).intValue() : 0);
    }

    public int getInt(String path, int def) {
        Object val = get(path, def);
        return (val instanceof Number) ? ((Number) def).intValue() : def;
    }

    public boolean isInt(String path) {
        Object val = get(path);
        return val instanceof Integer;
    }

    public boolean getBoolean(String path) {
        Object def = getDefault(path);
        return getBoolean(path, (def instanceof Boolean) ? (Boolean) def : false);
    }

    public boolean getBoolean(String path, boolean def) {
        Object val = get(path, def);
        return (val instanceof Boolean) ? (Boolean) val : def;
    }

    public boolean isBoolean(String path) {
        Object val = get(path);
        return val instanceof Boolean;
    }
    
    // Java
    public List<?> getList(String path) {
        Object def = getDefault(path);
        return getList(path, (def instanceof List) ? (List<?>) def : null);
    }

    public List<?> getList(String path, List<?> def) {
        Object val = get(path, def);
        return (List<?>) ((val instanceof List) ? val : def);
    }

    public boolean isList(String path) {
        Object val = get(path);
        return val instanceof List;
    }

    public List<String> getStringList(String path) {
        List<?> list = getList(path);

        if (list == null) {
            return new ArrayList<String>(0);
        }

        List<String> result = new ArrayList<String>();

        for (Object object : list) {
            if ((object instanceof String) || (isPrimitiveWrapper(object))) {
                result.add(String.valueOf(object));
            }
        }

        return result;
    }

    public List<Integer> getIntegerList(String path) {
        List<?> list = getList(path);

        if (list == null) {
            return new ArrayList<Integer>(0);
        }

        List<Integer> result = new ArrayList<Integer>();

        for (Object object : list) {
            if (object instanceof Integer) {
                result.add((Integer) object);
            } else if (object instanceof String) {
                try {
                    result.add(Integer.valueOf((String) object));
                } catch (Exception ex) {
                }
            } else if (object instanceof Character) {
                result.add((int) ((Character) object).charValue());
            } else if (object instanceof Number) {
                result.add(((Number) object).intValue());
            }
        }

        return result;
    }

    public List<Boolean> getBooleanList(String path) {
        List<?> list = getList(path);

        if (list == null) {
            return new ArrayList<Boolean>(0);
        }

        List<Boolean> result = new ArrayList<Boolean>();

        for (Object object : list) {
            if (object instanceof Boolean) {
                result.add((Boolean) object);
            } else if (object instanceof String) {
                if (Boolean.TRUE.toString().equals(object)) {
                    result.add(true);
                } else if (Boolean.FALSE.toString().equals(object)) {
                    result.add(false);
                }
            }
        }

        return result;
    }
    
    public ConfigSection getConfigSection(String path) {
        Object val = get(path, null);
        if (val != null) {
            return (val instanceof ConfigSection) ? (ConfigSection) val : null;
        }

        val = get(path, getDefault(path));
        return (val instanceof ConfigSection) ? createSection(path) : null;
    }
    
    public boolean isConfigSection(String path) {
        Object val = get(path);
        return val instanceof ConfigSection;
    }
    
    protected boolean isPrimitiveWrapper(Object input) {
        return input instanceof Integer || input instanceof Boolean ||
                input instanceof Character || input instanceof Byte ||
                input instanceof Short || input instanceof Double ||
                input instanceof Long || input instanceof Float;
    }

    protected Object getDefault(String path) {
        Validate.notNull(path, "Path cannot be null");

        Config root = getRoot();
        Config defaults = root == null ? null : root.getDefaults();
        return (defaults == null) ? null : defaults.get(createPath(this, path));
    }

    protected void mapChildrenKeys(Set<String> output, ConfigSection section, boolean deep) {
        if (section instanceof ConfigSection) {
        	ConfigSection sec = (ConfigSection) section;

            for (Map.Entry<String, Object> entry : sec.map.entrySet()) {
                output.add(createPath(section, entry.getKey(), this));

                if ((deep) && (entry.getValue() instanceof ConfigSection)) {
                	ConfigSection subsection = (ConfigSection) entry.getValue();
                    mapChildrenKeys(output, subsection, deep);
                }
            }
        } else {
            Set<String> keys = section.getKeys(deep);

            for (String key : keys) {
                output.add(createPath(section, key, this));
            }
        }
    }

    protected void mapChildrenValues(Map<String, Object> output, ConfigSection section, boolean deep) {
        if (section instanceof ConfigSection) {
        	ConfigSection sec = (ConfigSection) section;

            for (Map.Entry<String, Object> entry : sec.map.entrySet()) {
                output.put(createPath(section, entry.getKey(), this), entry.getValue());

                if (entry.getValue() instanceof ConfigurationSection) {
                    if (deep) {
                        mapChildrenValues(output, (ConfigSection) entry.getValue(), deep);
                    }
                }
            }
        } else {
            Map<String, Object> values = section.getValues(deep);

            for (Map.Entry<String, Object> entry : values.entrySet()) {
                output.put(createPath(section, entry.getKey(), this), entry.getValue());
            }
        }
    }
    
    /**
     * Creates a full path to the given {@link ConfigurationSection} from its
     * root {@link Configuration}.
     * <p>
     * You may use this method for any given {@link ConfigurationSection}, not
     * only {@link MemorySection}.
     *
     * @param section Section to create a path for.
     * @param key Name of the specified section.
     * @return Full path of the section from its root.
     */
    public static String createPath(ConfigSection section, String key) {
        return createPath(section, key, (section == null) ? null : section.getRoot());
    }

    /**
     * Creates a relative path to the given {@link ConfigurationSection} from
     * the given relative section.
     * <p>
     * You may use this method for any given {@link ConfigurationSection}, not
     * only {@link MemorySection}.
     *
     * @param section Section to create a path for.
     * @param key Name of the specified section.
     * @param relativeTo Section to create the path relative to.
     * @return Full path of the section from its root.
     */
    public static String createPath(ConfigSection section, String key, ConfigSection relativeTo) {
        Validate.notNull(section, "Cannot create path without a section");
        Config root = section.getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot create path without a root");
        }

        StringBuilder builder = new StringBuilder();
        if (section != null) {
            for (ConfigSection parent = section; (parent != null) && (parent != relativeTo); parent = parent.getParent()) {
                if (builder.length() > 0) {
                    builder.insert(0, ".");
                }

                builder.insert(0, parent.getName());
            }
        }

        if ((key != null) && (key.length() > 0)) {
            if (builder.length() > 0) {
                builder.append(".");
            }

            builder.append(key);
        }

        return builder.toString();
    }

    @Override
    public String toString() {
    	Config root = getRoot();
        return new StringBuilder()
            .append(getClass().getSimpleName())
            .append("[path='")
            .append(getCurrentPath())
            .append("', root='")
            .append(root == null ? null : root.getClass().getSimpleName())
            .append("']")
            .toString();
    }
}
