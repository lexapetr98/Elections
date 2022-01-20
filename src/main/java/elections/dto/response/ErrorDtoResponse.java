package elections.dto.response;

import elections.errors.ElectionsException;

public class ErrorDtoResponse {
    private String error;

    public ErrorDtoResponse(String error){
        this.error = error;
    }

    public ErrorDtoResponse(ElectionsException electionsError){
        this.error = electionsError.getErrorCode().getErrorString();
    }

    public String getErrorString(){
        return error;
    }
}
