package elections.daoImpl;

import elections.dao.VoterDao;
import elections.database.Database;
import elections.errors.ElectionsException;
import elections.model.Candidate;
import elections.model.Rating;
import elections.model.Suggestion;
import elections.model.Voter;

import java.io.IOException;
import java.util.*;

public class VoterDaoImpl implements VoterDao {
    private Database database;

    public VoterDaoImpl() {
        this.database = Database.getInstance();
    }

    @Override
    public void saveDatabase() {
        try {
            database.saveToFile();
        } catch (IOException ex) {
        }
    }

    @Override
    public void logout(String token) throws ElectionsException {
        database.logout(token);
    }

    @Override
    public Voter getById(int id) {
        return database.getById(id);
    }

    @Override
    public Voter get(String token) {
        return database.getByToken(token);
    }

    @Override
    public Collection<Voter> getAll() {
        return database.getAll();
    }

    @Override
    public String create(Voter voter) throws ElectionsException {
        return database.create(voter);
    }

    @Override
    public void delete(String token) throws ElectionsException {
        database.delete(token);
    }

    @Override
    public Voter getByLogin(String login) {
        return database.getByLogin(login);
    }

    @Override
    public String login(String login) {
        return database.login(login);
    }

    @Override
    public Map<Integer, Candidate> getCandidates() {
        return database.getCandidates();
    }

    @Override
    public void addSuggestion(String token, String text) {
        database.addSuggestion(token, text);
    }

    @Override
    public void addRatingToSuggestion(Integer id, Rating rating) {
        database.addRatingToSuggestion(id, rating);
    }

    @Override
    public Suggestion getSuggestion(Integer id) {
        return database.getSuggestionById(id);
    }

    @Override
    public void addCandidateRating(int id, String tokenAuthor) {
        database.addCandidateRating(id, tokenAuthor);
    }

    @Override
    public void addVoteAgainstAll(String token) {
        database.addVoteAgainstAll(token);
    }

    @Override
    public void addCandidate(Voter voter) {
        database.addCandidate(voter);
    }

    @Override
    public int getVotesCount(int id){
        return database.getVotesCount(id);
    }

    @Override
    public int getVotersOnline(){
        return database.getVotersOnline();
    }

    @Override
    public Candidate getCandidate(int id) {
        return database.getCandidate(id);
    }

    @Override
    public int getVotesAgainstAll() {
        return database.getVotesAgainstAll();
    }
}
