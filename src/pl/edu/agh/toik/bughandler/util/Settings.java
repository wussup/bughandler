package pl.edu.agh.toik.bughandler.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Settings {

	private Map<String, String> settings = new LinkedHashMap<String, String>();

	public Settings() {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("local.settings"));

			String line;
			while ((line = br.readLine()) != null) {
				if (!line.startsWith("#")) {
					String[] splittedLine = line.split("=");
					if (splittedLine.length == 2)
						settings.put(splittedLine[0], splittedLine[1]);
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> getSettings() {
		return settings;
	}
}
