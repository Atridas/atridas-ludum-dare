package cat.atridas87.ld27;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.newdawn.slick.util.ResourceLoader;

public abstract class Utils {
	private Utils() {
	}

	
	public static InputStream loadFile(String filename) {
		return ResourceLoader.getResourceAsStream(filename);
		
		//InputStream is = Utils.class.getClassLoader().getResourceAsStream(filename);
		//URL resource_url = Utils.class.getResource(filename);
		//try {
		//	return is;//Utils.class.getResourceAsStream(filename);
		//} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//	return null;
		//}
	}
	

	
	public static String loadFileAsString(String filename) {
		InputStream is = loadFile(filename);
		if(is != null) {
			return loadInputStreamAsString(is);
		} else {
			return null;
		}
	}
	
	public static String loadInputStreamAsString(InputStream is) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"));

			StringBuilder sb = new StringBuilder();
			String aux = reader.readLine();
			while (aux != null) {
				sb.append(aux);
				sb.append('\n');
				aux = reader.readLine();
			}

			reader.close();
			return sb.toString();
		} catch (FileNotFoundException e) {
			// LOGGER.warning(e.toString());
			return null;
		} catch (UnsupportedEncodingException e) {
			// LOGGER.warning(e.toString());
			return null;
		} catch (IOException e) {
			// LOGGER.warning(e.toString());
			return null;
		}
	}

}
