import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
	
	List<Table> tables;
	public DataBase() {
		super();
		tables=new ArrayList<>();
	}
	
	public Table declare(String tableName, List<String> columns) throws IOException{
		if(findTable(tableName)!=null)
			return null;
		Table table=new Table(tableName,columns);
		if(table.toFile()){
			tables.add(table);
			return table;
		}
		return null;
	}
	
	
	public boolean add(List<String> fields, String tableName){
		Table table=findTable(tableName);
		if(table==null)
			return false;
		if(table.columns.size()!=fields.size())
			return false;
		return table.addLine(fields);
	}
	
	
	public Line find(String id, String tableName){
		Table table=findTable(tableName);
		if(table==null)
			return null;
		if(table.lines==null)
			return null;
		return table.lines.get(id);
		
	}
	public boolean Update(String id,String tableName,String column,String field){
		Table table=findTable(tableName);
		if(table==null)
			return false;
		if(table.lines==null)
			return false;
		Line line=table.lines.get(id);
		if(line==null)
			return false;
		int index=table.columns.indexOf(column);
		if(index==0||index==-1)
			return false;
		line.fields.set(index, field);
		table.toFile();
		return true;
	}
	public boolean delete(String id,String tableName){
		Table table=findTable(tableName);
		if(table==null)
			return false;
		if(table.lines==null)
			return false;
		table.lines.remove(id);
		table.toFile();
		return true;
		
	}
	public Table findTable(String tableName){
		for (Table table : tables) {
			if(table.name==tableName)
				return table;
		}
		File f = new File(tableName);
		if(f.exists() && !f.isDirectory()) { 
		    return new Table(tableName);
		}
		return null;
	}
	

}
