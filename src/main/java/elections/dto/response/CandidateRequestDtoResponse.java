package elections.dto.response;

public class CandidateRequestDtoResponse {
    private String responseString;

    public CandidateRequestDtoResponse(String responseString) {
        this.responseString = responseString;
    }

    public String getResponseString() {
        return responseString;
    }
}
