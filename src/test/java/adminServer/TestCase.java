package adminServer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.Test;

import tools.UnZipFiles;

public class TestCase {

	@Test
	public void test1() {
		
	        /** 
	         * ½âÑ¹ÎÄ¼þ 
	         */  
	        File zipFile = new File("e:/theFirstCpp.zip");  
	        String path = "e:/zipfile/";  
	        try {
				UnZipFiles.unZipFiles(zipFile, path);
			} catch (IOException e) {
				e.printStackTrace();
			}  
	}
	
	
	@Test
	public void test2() {
		ClassLoader classLoader = getClass().getClassLoader();  
	    File file = new File(classLoader.getResource("/admin/1.txt").getFile());  
		
		
		System.out.println(file.exists());
	}
	 
}
