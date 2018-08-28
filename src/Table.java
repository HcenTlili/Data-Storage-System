import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	String name;
	List<String> columns;
	Map<String, Line> lines;
	
	
	public Table(String name,List<String> columns) {
		super();
		this.name = name;
		this.columns = columns;
	}
	
	public Table(String fileName){
		String line;
		try{
		    InputStream fis = new FileInputStream(fileName);
		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		    BufferedReader br = new BufferedReader(isr);
		    line = br.readLine();
		    columns=Arrays.asList(line.split(","));
		    lines=new HashMap<String, Line>();
		    while ((line = br.readLine()) != null) {
		        List<String> fields=Arrays.asList(line.split(","));
		        lines.put(fields.get(0),new Line( fields.subList(1, fields.size()-1)));
		    }
		}catch (Exception e) {
			
		}
	}
		

	public boolean toFile(){
		PrintWriter tablePW;
		try {
			tablePW = new PrintWriter(this.name,"UTF-8");
			for (int i = 0; i < columns.size()-1; i++) {
				tablePW.print(columns.get(i)+",");
			}
			tablePW.print(columns.get(columns.size()-1)+'\n');
			return true;
		}catch(IOException e){
			return false;
		}finally{
		}
	}
	
	
	public boolean addLine(List<String> fields){
		if(lines==null){
			lines=new HashMap<String,Line>();
		}
		if(lines.containsKey(fields.get(0)))
			return false;
		Line line=new Line(fields.subList(1, fields.size()-1));
		String toFile=fields.get(0)+','+line.toString();
		try {
		    Files.write(Paths.get(name), toFile.getBytes(), StandardOpenOption.APPEND);
			lines.put(fields.get(0), line);
		}catch (IOException e){
			return false;
		}
		return true;
	}
	
	
}
