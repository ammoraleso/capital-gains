package models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonModel {

    protected static final Gson gson = new Gson();

    /**
     * Convert a json string to an object
     * @param jsonString the json string
     * @param classOfT the class of the object
     * @return the object
     */
    public static ArrayList<?> convertJsonArrayToArrayList(String jsonString, Type classOfT) {
        Type founderListType = null;
        switch (classOfT.getTypeName()) {
            case "models.Transaction":
                founderListType = new TypeToken<ArrayList<Transaction>>(){}.getType();
        }
        ArrayList<?> arrayObject = gson.fromJson(jsonString, founderListType);
        return arrayObject;
    }

    public static <T> String ObjectToJson(T object) {
        return gson.toJson(object);
    }
}
