package se.hig.dvg329.algomaze.storage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Is used to store data to text files. This class holds the file path which to write
 * data to.
 * @author Thomas Lundgren
 * @version 1.0.0
 * @since 1.0.0
 */
public class FileStorageManager {
	
	private Path savePath;
	private static final Charset CHARSET = Charset.forName("UTF-8");
	public static final String VERSION_NUMBER = "v 1.0.0";
	public static final String AUTHOR = "Thomas Lundgren";
	
	private FileStorageManager() {}
	
	public static FileStorageManager getInstance() {
		return SingletonHelper.getInstance();
	}
	
	private static class SingletonHelper {
		
		private final static FileStorageManager INSTANCE = new FileStorageManager();
		
		public static FileStorageManager getInstance() {
			return INSTANCE;
		}
	}
	
	public void storeData(String data) {
		BufferedWriter writer = null;
		try {
			if (!Files.exists(savePath)) {
				Files.createFile(savePath);
			}
			writer = Files.newBufferedWriter(savePath, CHARSET, StandardOpenOption.APPEND);
			writer.write(data);
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Invalid file name.");
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				}
				catch (IOException e) {
					System.err.println("Unexpected error when writing to file.");
					e.printStackTrace();
				}
			}
		}
	}
	
	public String readData() {
		return "";
	}
	
	public void setSavePath(String path) {
		savePath = Paths.get(path);
	}
	
	public Path getSavePath() {
		return savePath;
	}
}
