package elections.errors;

public class ElectionsException extends Exception {
    private ElectionsErrorCode electionsErrorCode;

    public ElectionsException(ElectionsErrorCode electionsErrorCode){
        this.electionsErrorCode = electionsErrorCode;
    }

    public ElectionsErrorCode getErrorCode(){
        return electionsErrorCode;
    }
}
