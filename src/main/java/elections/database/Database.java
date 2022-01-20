package elections.database;

import com.google.gson.Gson;
import elections.errors.ElectionsErrorCode;
import elections.errors.ElectionsException;
import elections.model.Candidate;
import elections.model.Rating;
import elections.model.Suggestion;
import elections.model.Voter;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import java.io.*;
import java.util.*;

public class Database implements Serializable {
    private static Database instance;
    private BidiMap<String, Voter> votersToken;
    private Map<String, Voter> votersLogin;
    private Map<Integer, Voter> votersId;
    private Map<Integer, Candidate> candidates;
    private MultiValuedMap<Suggestion, Rating> suggestions;
    private Map<Integer, Suggestion> suggestionsById;
    private int currentID;
    private int currentSuggestionId;
    private int votesAgainstAll;
    private static final String fileName = "Database.txt";

    private Database() {
        votersToken = new DualHashBidiMap<>();
        votersLogin = new HashMap<>();
        candidates = new HashMap<>();
        votersId = new HashMap<>();
        suggestions = new HashSetValuedHashMap<>();
        suggestionsById = new HashMap<>();
        currentSuggestionId = 0;
        currentID = 0;
        votesAgainstAll = 0;
    }

    private Database loadFromFile() throws IOException {
        Gson gson = new Gson();
        File file = new File(fileName);
        try {
            if (file.length() != 0) {
                BufferedReader br = new BufferedReader(new FileReader(fileName));
                Database database = gson.fromJson(br, Database.class);
                br.close();
                return database;
            } else {
                return new Database();
            }
        } catch (IOException ex) {
            throw ex;
        }
    }

    public void saveToFile() throws IOException {
        Gson gson = new Gson();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName)));
            gson.toJson(this, bw);
            bw.close();
        } catch (IOException ex) {
            throw ex;
        }
    }

    public static Database getInstance() {
        try {
            if (instance == null) {
                instance = new Database().loadFromFile();
            }
        } catch (IOException ex) {
        }

        return instance;
    }

    public void addSuggestion(String token, String text) {
        Voter voter = getByToken(token);
        Suggestion suggestion = new Suggestion(text, voter.getUser(), currentSuggestionId);
        suggestions.put(suggestion, new Rating(voter, 5));
        suggestionsById.put(currentSuggestionId, suggestion);
        currentSuggestionId++;
    }

    public Suggestion getSuggestionById(Integer id) {
        return suggestionsById.get(id);
    }

    public void addRatingToSuggestion(Integer id, Rating rating) {
        Suggestion suggestion = getSuggestionById(id);
        suggestions.put(suggestion, rating);
    }

    public int getVotersOnline(){
        return votersToken.size();
    }

    public Voter getByToken(String token) {
        return votersToken.get(token);
    }

    public Voter getById(int id) {
        return votersId.get(id);
    }

    public Voter getByLogin(String login) {
        return votersLogin.get(login);
    }

    public Collection<Voter> getAll() {
        return votersToken.values();
    }

    public String create(Voter voter) throws ElectionsException {
        String token = UUID.randomUUID().toString();
        voter.setId(currentID);
        if (votersToken.putIfAbsent(token, voter) != null) {
            throw new ElectionsException(ElectionsErrorCode.VOTER_ALREADY_EXIST);
        }
        votersLogin.putIfAbsent(voter.getUser().getLogin(), voter);
        votersId.putIfAbsent(currentID, voter);
        currentID++;
        return token;
    }

    public void delete(String token) throws ElectionsException {
        if (votersToken.get(token) == null) {
            throw new ElectionsException(ElectionsErrorCode.VOTER_NOT_FOUND);
        }
        votersToken.remove(token);
        votersLogin.remove(getByToken(token).getUser().getLogin());
        votersId.remove(getByToken(token).getId());
    }

    public void logout(String token) throws ElectionsException {
        Voter voter = votersToken.get(token);
        if (voter == null) {
            throw new ElectionsException(ElectionsErrorCode.VOTER_NOT_FOUND);
        }
        votersToken.remove(token);
    }

    public String login(String login) {
        Voter voter = getByLogin(login);
        String token = UUID.randomUUID().toString();
        votersToken.putIfAbsent(token, voter);
        return token;
    }

    public void addCandidate(Voter voter) {
        candidates.put(voter.getId(), new Candidate(voter));
    }

    public Map<Integer, Candidate> getCandidates() {
        return candidates;
    }

    public int getVotesAgainstAll() {
        return votesAgainstAll;
    }

    public void addVoteAgainstAll(String tokenAuthor) {
        votesAgainstAll++;
        votersToken.get(tokenAuthor).setVoted(true);
    }

    public void addCandidateRating(int id, String tokenAuthor) {
        candidates.get(id).addVote();
        votersToken.get(tokenAuthor).setVoted(true);
    }

    public int getVotesCount(int id){
        return getCandidate(id).getVotes();
    }

    public Candidate getCandidate(int id) {
        return candidates.get(id);
    }
}
