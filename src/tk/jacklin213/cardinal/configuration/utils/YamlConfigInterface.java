package tk.jacklin213.cardinal.configuration.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.yaml.snakeyaml.error.YAMLException;

public interface YamlConfigInterface {

	public void save(File file) throws FileNotFoundException, IOException;
	
	public void load(File file) throws FileNotFoundException, IOException, YAMLException;
	
}
