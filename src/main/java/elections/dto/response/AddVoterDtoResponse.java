package elections.dto.response;

public class AddVoterDtoResponse {
    private String token;

    public AddVoterDtoResponse(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }
}
