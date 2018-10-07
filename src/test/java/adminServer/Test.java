package adminServer;

import java.io.File;
import java.io.IOException;

public class Test {
	public static void main(String[] args) throws IOException {
		File fileParent = new File("e://test");
		if(!fileParent.exists()) {
			fileParent.mkdirs();
		}
		fileParent.createNewFile();
	}
}
