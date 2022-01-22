package models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonModel {

    protected static final Gson gson = new Gson();

    public static <T> T convertStringToJson(String jsonString , Type classOfT) {
        JsonObject jsonObject = new JsonObject();
        switch (classOfT.getTypeName()) {
        case "models.Transaction":
            jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        }
        return gson.fromJson(jsonObject, classOfT);
    }

    public static <T> ArrayList<T> convertJsonArrayToArrayList(String jsonString , Type classOfT) {
        Type userListType = new TypeToken<ArrayList<T>>(){}.getType();
        ArrayList<T> arrayObject = gson.fromJson(jsonString, userListType);
        return arrayObject;
    }
}
