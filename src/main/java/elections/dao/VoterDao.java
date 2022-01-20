package elections.dao;

import elections.errors.ElectionsException;
import elections.model.Candidate;
import elections.model.Rating;
import elections.model.Suggestion;
import elections.model.Voter;

import java.util.Collection;
import java.util.Map;

public interface VoterDao {
    Voter get(String token);

    Collection<Voter> getAll();

    String create(Voter voter) throws ElectionsException;

    void delete(String token) throws ElectionsException;

    void saveDatabase();

    void logout(String token) throws ElectionsException;

    Voter getById(int id);

    Voter getByLogin(String login);

    String login(String login);

    int getVotersOnline();

    Map<Integer, Candidate> getCandidates();

    void addSuggestion(String token, String text);

    void addRatingToSuggestion(Integer id, Rating rating);

    Suggestion getSuggestion(Integer id);

    void addCandidateRating(int id, String tokenAuthor);

    void addVoteAgainstAll(String token);

    Candidate getCandidate(int id);

    int getVotesAgainstAll();

    void addCandidate(Voter voter);

    int getVotesCount(int id);
}
