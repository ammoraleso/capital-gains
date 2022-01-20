package models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;

public class JsonModel {

    protected static final Gson gson = new Gson();

    public static <T> T convertStringToJson(String jsonString , Type classOfT) {
        JsonObject jsonObject = new JsonObject();
        switch (classOfT.getTypeName()) {
            case "models.Account":
                jsonObject = JsonParser.parseString(jsonString).getAsJsonObject().get("account").getAsJsonObject();
                break;
            case "models.Transaction":
                jsonObject = JsonParser.parseString(jsonString).getAsJsonObject().get("transaction").getAsJsonObject();
        }
        return gson.fromJson(jsonObject, classOfT);
    }
}
