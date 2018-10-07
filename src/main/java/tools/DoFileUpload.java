package tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.fileupload.FileItem;

public class DoFileUpload {
	public static String fileUpload(String parentPath,FileItem item) throws Exception {
		
		if(item.getName() != "" && item.getName() != null)
		{			
			String fileAddress = "";
			String fileName=item.getName().substring(item.getName().lastIndexOf("\\")+1);
			System.out.println(fileName);
			InputStream in=item.getInputStream();
			
			File file=new File(parentPath);
			file.setWritable(true,false);
			if(!file.exists())
			{
				file.mkdirs();
			}						
			fileAddress=parentPath+fileName;
			file=new File(fileAddress);
			file.setWritable(true,false);
			file.createNewFile();
			FileOutputStream out=new FileOutputStream(file);
			byte[] bytes=new byte[1024*10];//缓冲大小为10k
			int i=-1;
			while((i=in.read(bytes))!=-1)
			{
				out.write(bytes, 0, i);
			}		
			
			
			System.out.println("上传完毕");
			in.close();
			out.close();
			return fileAddress;
		}	
	return null;
}
}
