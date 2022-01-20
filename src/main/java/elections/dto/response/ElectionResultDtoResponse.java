package elections.dto.response;

import elections.model.Candidate;
import elections.utils.ConverterUtils;

public class ElectionResultDtoResponse {
    private boolean success;
    private int id;
    private int votes;

    public ElectionResultDtoResponse(){
        success = false;
    }

    public ElectionResultDtoResponse(int id, int votes){
        success = true;
        this.id = id;
        this.votes = votes;
    }

    public String getResult(){
        if(!success){
            return ConverterUtils.convertToJson(success);
        }else {
            return ConverterUtils.convertToJson(id + votes);
        }
    }
}
