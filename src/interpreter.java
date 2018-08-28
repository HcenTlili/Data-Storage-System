
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class interpreter {
	private DataBase database;
	private PrintStream out=System.out;


	public void Verify(String query)
	{
		String methode = query.substring(0,query.indexOf(' '));

		if(methode.toLowerCase().equals("declare"))
		{
			String fieldsString= query.substring(query.indexOf("AS")+2,query.length()).trim();
			String[] fields =fieldsString.split(",");
			String tableName= query.substring(query.indexOf(methode)+7,query.indexOf("AS")-1).trim();
			try {
				database.declare(tableName,new ArrayList<String>(Arrays.asList(fields)));
			} catch (IOException e) {
				out.println("Error");
			}

		}

		if(methode.toLowerCase().equals("add"))
		{
			String fieldsString= query.substring(query.indexOf('('),query.indexOf(')')).trim();

			String[] fields =fieldsString.split(",");
			String tableName= query.substring(query.indexOf("TO")+2,query.length()).trim();

			if(database.add(new ArrayList<String>(Arrays.asList(fields)),tableName))
				out.println("OK");
			else
				out.println("ERROR");


		}

		if(methode.toLowerCase().equals("delete"))
		{
			String idString= query.substring(query.indexOf(methode)+6,query.indexOf("from")).trim();


			String tableName= query.substring(query.indexOf("from")+4,query.length()).trim();

			if(database.delete(idString,tableName))
				out.println("OK");
			else
				out.println("ERROR");

		}

		if(methode.toLowerCase().equals("find"))
		{
			String target= query.substring(5,query.indexOf(" ", 6)-1).trim();

			if (target.toLowerCase().equalsIgnoreCase("all")){

				String tableName= query.substring(query.indexOf("in")+2,query.length()).trim();

				Table table=database.findTable(tableName);
				if(table!=null){
					for (String key:table.lines.keySet()) {
						Line line=table.lines.get(key);
						out.print("("+key);
						for (String field : line.fields) {
							out.print(","+field);
						}
						out.println(")");
					}
				}
			}
			else{

				String tableName= query.substring(query.indexOf("in")+2,query.length()).trim();

				Line line=database.find(target,tableName);
				out.print("("+target);
				for (String field : line.fields) {
					out.print(","+field);
				}
				out.println(")");
			}
		}
	}

}
