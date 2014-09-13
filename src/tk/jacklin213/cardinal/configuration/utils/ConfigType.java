package tk.jacklin213.cardinal.configuration.utils;

public enum ConfigType {
	SYSTEM,
	CUSTOM;
	
	public static ConfigType getType(String string) {
		for (ConfigType type: ConfigType.values()) {
			if (type.toString().equalsIgnoreCase(string)) {
				return type;
			}
		}
		return null;
	}
}
