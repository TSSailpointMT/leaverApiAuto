package leaver.models.manual;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import sailpoint.api.SailPointContext;
import sailpoint.server.Importer;
import sailpoint.tools.GeneralException;

public class ImportModel{
	
	private SailPointContext context;
	
	public ImportModel(SailPointContext context){
		this.context = context;
	}
	
	public void importFile(String path) throws GeneralException, IOException{
		File xml = new File(path);
		Path xmlPath = Paths.get(xml.getPath());
		String content = new String(Files.readAllBytes(xmlPath));

		Importer importer = new Importer(context);
		importer.importXml(content);
	}
	
}
