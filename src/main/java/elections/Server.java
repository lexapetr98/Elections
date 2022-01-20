package elections;

import elections.errors.ElectionsException;
import elections.model.Suggestion;
import elections.service.ElectionService;
import elections.service.VoterService;



public class Server {
    private VoterService voterService;
    private ElectionService electionService;
    private boolean isOn = false;

    public void startServer() {
        voterService = new VoterService();
        isOn = true;
    }

    public void stopServer(){
        saveDatabase();
        isOn = false;
    }

    public int getVotersOnline(){
        return voterService.getVotersOnline();
    }

    public void saveDatabase(){
        voterService.saveDatabase();
    }

    public String registerVoterRequest(String json) {
        return voterService.addVoter(json);
    }

    public String loginVoterRequest(String json) {
        return voterService.loginVoter(json);
    }

    public String logoutVoter(String token) {
        return voterService.logout(token);
    }

    public String voterAddProgramString(String token, String text) {
        return voterService.addVoterProgram(token, text);
    }

    public String addRatingToVoterProgram(int suggestionId, String token, int id, Integer rating) throws ElectionsException {
        return voterService.addRatingToProgram(suggestionId, token, id, rating);
    }

    public String requestCandidate(String token, int id) {
        return voterService.requestCandidate(token, id);
    }

    public String answerOnCandidateRequest(String token, boolean answer) {
        return voterService.answerCandidateRequest(token, answer);
    }

    public String becomeCandidate(String token) {
        return voterService.becomeCandidate(token);
    }


    public void startElectionDay() {
        voterService.startElectionDay();
        electionService = new ElectionService();
    }

    public String voteFor(String tokenAuthor, int id) {
        return electionService.addVoteToCandidate(id, tokenAuthor);
    }

    public String voteAgainstAll(String token) {
        return electionService.againstAll(token);
    }

    public String endElections() {
        voterService.endElectionDay();
        return electionService.result();
    }

    public Suggestion getSuggestion(int id){
        return voterService.getSuggestion(id);
    }

    public int getCandidatesCount(){
        return voterService.getCandidatesCount();
    }

    public int getVotesCount(int id){
        return electionService.getVotesCount(id);
    }

    public int getVotesAgainsAll(){
        return electionService.getVotesAgainstAll();
    }
}



