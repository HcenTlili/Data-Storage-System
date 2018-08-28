import java.io.Serializable;
import java.util.List;

public class Line implements Serializable{
	public Line(List<String> fields) {
		super();
		this.fields = fields;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<String> fields;
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		return result;
	}
	@Override
	public String toString() {
		String result="";
		for (String string : fields) {
			result +=(string+",");			
		}
		result=result.substring(0, result.length()-2)+'\n';
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Line other = (Line) obj;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		return true;
	}
	

}
