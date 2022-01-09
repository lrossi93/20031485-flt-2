package symbolTable;

import java.util.HashMap;
import java.util.Iterator;

public class SymbolTable {
	private static HashMap<String, Attributes> table;

	public static void init() {
		table = new HashMap<String, Attributes>();
	}

	public static boolean enter(String id, Attributes entry) {
		Attributes value = table.get(id);
		if (value != null)
			return false;
		table.put(id, entry);
		return true;
	}

	public static Attributes lookup(String id) {
		return table.get(id);
	}

	public static String toStr() { // Per output
		StringBuilder res = new StringBuilder("symbol table\n=============\n").append("[var]\t\t[type]\t\t[reg]\n");

		for (HashMap.Entry<String, Attributes> entry : table.entrySet())
			res.append(entry.getKey()).append("\t\t").append(entry.getValue().getType()).append("\t\t").append(entry.getValue().getRegister())
					.append("\n");
		res.append("\n=============\n");

		return res.toString();
	}

	public static int size() {
		return (table.size());
	}
	
	public static Iterator<Attributes> getTableIterator(){
		return table.values().iterator();
	}
}