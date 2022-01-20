package elections.utils;

import com.google.gson.Gson;
import elections.dto.request.LoginDtoRequest;
import elections.dto.request.RegisterVoterDtoRequest;
import elections.model.Voter;

public class ConverterUtils {
    public static <T> String convertToJson(T object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static Voter convertFromJsonToVoter(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,Voter.class);
    }

    public static RegisterVoterDtoRequest convertFromJsonToRvdr(String json){
        Gson gson = new Gson();
        return gson.fromJson(json,RegisterVoterDtoRequest.class);
    }

    public static String convertFromJsonToErrorMessage(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, String.class);
    }

    public static LoginDtoRequest convertFromJsonToLdr(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, LoginDtoRequest.class);
    }
}
