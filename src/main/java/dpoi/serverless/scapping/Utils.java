package dpoi.serverless.scapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

/**
 * Utility class
 */
public interface Utils {

    /** Returns true if string is null or empty. */
    static boolean isEmpty(String value) { return value == null || value.trim().isEmpty(); }

    /** Returns an initialized Gson object with the default configuration */
    static Gson getGson() {
        return new GsonBuilder()
                //.enableComplexMapKeySerialization()
                //.serializeNulls()
                //.setDateFormat(DateFormat.LONG)
                //.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();
    }

    /** Return next random id. */
    static String nextId() { return UUID.randomUUID().toString().substring(0,6); }
}
