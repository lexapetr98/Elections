package elections.errors;

public enum ElectionsErrorCode {
    VOTER_WRONG_REGISTER_DATA("You've entered wrong data"),
    VOTER_ALREADY_EXIST("This voter already registered"),
    VOTER_ALREADY_LOGGEDIN("You already logged in"),
    VOTER_WRONG_LOGIN_DATA("Entered data is wrong"),
    VOTER_USE_OFFLINE("You need to login first"),
    VOTER_NOT_FOUND("Voter not found"),
    VOTER_PROGRAM_NOT_FOUND("Voter program not found"),
    VOTER_ALREADY_VOTE("You already voted"),
    VOTER_VOTE_SELF("You can't vote for yourself"),
    VOTER_ADD_EXIST_SUGGESTION("You already have this addition"),
    VOTER_SUGGESTION_NOT_FOUND("Suggestion not found"),
    VOTER_EMPTY_SUGGESTIONS("You need some additions"),
    VOTER_ELECTION_DAY_ERROR("You can't do it");


    private String errorString;

    ElectionsErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString(){
        return errorString;
    }
}
