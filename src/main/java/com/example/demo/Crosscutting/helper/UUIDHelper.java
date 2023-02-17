package com.example.demo.Crosscutting.helper;
import static com.example.demo.Crosscutting.helper.ObjectHelper.getDefaultIfNull;
import java.util.UUID;

import com.example.demo.Crosscutting.exception.crosscutting.CrosscuttingCustomException;

public class UUIDHelper {
    	
	private static final String DEFAULT_UUID_AS_STRING = "00000000-0000-0000-0000-000000000000";
	private static final UUID DEFAULT_UUID = UUID.fromString(DEFAULT_UUID_AS_STRING);

	private UUIDHelper() {
		super();
	}
	
	public static final UUID getDefaultUUID(final UUID value) {
		return getDefaultIfNull(value, DEFAULT_UUID); 
	}

	public static final UUID getNewUUID() {

		UUID uuid;

		do {
			uuid = UUID.randomUUID();
		} while (isDefaultUUID(uuid));

		return UUID.randomUUID();
	}

	public static final String getUUIDAsString(final UUID value){
		return getDefaultUUID(value).toString();
	}
	public static final UUID getDefaultUUID() {
	    return DEFAULT_UUID;
	}

	public static final boolean isDefaultUUID(final UUID value){
		return DEFAULT_UUID.equals(value);
	}

	public static final UUID getUUIDFromString(final String value){
		try {
			return UUID.fromString(StringHelper.getDefaultString(value, DEFAULT_UUID_AS_STRING));
		} catch(IllegalArgumentException exception) {
			throw CrosscuttingCustomException.CreateTechnicalException("", exception);
		} catch (Exception exception){
			throw CrosscuttingCustomException.CreateTechnicalException("", exception);
		}
	}

}
