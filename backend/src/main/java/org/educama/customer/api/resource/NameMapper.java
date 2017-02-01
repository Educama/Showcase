package org.educama.customer.api.resource;
import static org.springframework.util.Assert.notNull;

import org.educama.customer.model.Name;

public class NameMapper {
	private static final String DELIMITER = " ";
	
	public static Name toName(String nameAsString) {
		notNull(nameAsString);
		return new Name(getFirstname(nameAsString), getLastname(nameAsString));
	}
	
	public static String toString(Name name) {
		notNull(name);
		return name.firstname + DELIMITER + name.lastname;
	}
	
	private static String getFirstname(String name) {
		String result = "";
		notNull(name);
		int index = name.indexOf(DELIMITER);
		if (index != -1) {
			result = name.substring(0, index);
		}
		return result;
	}
	
	private static String getLastname(String name) {
		String result = name;
		notNull(name);
		int index = name.indexOf(DELIMITER);
		if (index != -1) {
			result = name.substring(index + 1);
		}
		return result;
	}

}
