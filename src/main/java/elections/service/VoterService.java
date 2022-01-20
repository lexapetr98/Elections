package elections.service;

import elections.dao.VoterDao;
import elections.daoImpl.VoterDaoImpl;
import elections.dto.request.LoginDtoRequest;
import elections.dto.request.RegisterVoterDtoRequest;
import elections.dto.response.*;
import elections.errors.ElectionsErrorCode;
import elections.errors.ElectionsException;
import elections.model.Rating;
import elections.model.Suggestion;
import elections.model.Voter;
import elections.utils.ConverterUtils;
import elections.utils.ValidationUtils;


import java.io.IOException;
import java.util.*;


public class VoterService {
    private VoterDao voterDao = new VoterDaoImpl();
    private boolean electionDay = false;

    public void saveDatabase() {
        voterDao.saveDatabase();
    }

    public String addVoter(String json) {
        if (electionDay) {
            return ConverterUtils.convertToJson(new ErrorDtoResponse(new ElectionsException(ElectionsErrorCode.VOTER_ELECTION_DAY_ERROR)));
        }
        RegisterVoterDtoRequest registerVoterDtoRequest = ConverterUtils.convertFromJsonToRvdr(json);
        try {
            ValidationUtils.validateVoter(registerVoterDtoRequest);
            Voter voter = new Voter(registerVoterDtoRequest.getName(), registerVoterDtoRequest.getSurname(), registerVoterDtoRequest.getPatronymic(), registerVoterDtoRequest.getStreet(), registerVoterDtoRequest.getHouse(), registerVoterDtoRequest.getFlat(), registerVoterDtoRequest.getLogin(), registerVoterDtoRequest.getPassword(), 0);
            return voterDao.create(voter);
        } catch (ElectionsException ex) {
            return ConverterUtils.convertToJson(new ErrorDtoResponse(ex).getErrorString());
        }
    }

    public String loginVoter(String json) {
        LoginDtoRequest loginDtoRequest = ConverterUtils.convertFromJsonToLdr(json);
        Voter voter = voterDao.getByLogin(loginDtoRequest.getLogin());
        if (voter == null) {
            return ConverterUtils.convertToJson(new ErrorDtoResponse(new ElectionsException(ElectionsErrorCode.VOTER_NOT_FOUND)));
        }
        if (!voter.getUser().getPassword().equals(loginDtoRequest.getPassword())) {
            return ConverterUtils.convertToJson(new ErrorDtoResponse(new ElectionsException(ElectionsErrorCode.VOTER_WRONG_LOGIN_DATA)));
        }
        return voterDao.login(voter.getUser().getLogin());
    }


    public String logout(String token) {
        try {
            voterDao.logout(token);
        } catch (ElectionsException ex) {
            return ConverterUtils.convertToJson(new ErrorDtoResponse(new ElectionsException(ElectionsErrorCode.VOTER_NOT_FOUND)));
        }
        return ConverterUtils.convertToJson(new LogoutDtoResponse());
    }

    private Voter getVoterByToken(String token) {
        return voterDao.get(token);
    }

    public String addVoterProgram(String token, String text) {
        if (electionDay) {
            return ConverterUtils.convertToJson(new ErrorDtoResponse(new ElectionsException(ElectionsErrorCode.VOTER_ELECTION_DAY_ERROR)));
        }
        Voter voter = getVoterByToken(token);
        if (voter == null) {
            return ConverterUtils.convertToJson(new ErrorDtoResponse(new ElectionsException(ElectionsErrorCode.VOTER_NOT_FOUND)));
        }
        voterDao.addSuggestion(token, text);
        return ConverterUtils.convertToJson(new AddVoterProgramDtoResponse());
    }

    public String addRatingToProgram(int suggestionId, String token, int id, Integer rating) {
        if (electionDay) {
            return ConverterUtils.convertToJson(new ErrorDtoResponse(new ElectionsException(ElectionsErrorCode.VOTER_ELECTION_DAY_ERROR)));
        }
        Voter voter = voterDao.getById(id);
        if (voter == null) {
            return ConverterUtils.convertToJson(new ErrorDtoResponse(new ElectionsException(ElectionsErrorCode.VOTER_NOT_FOUND)));
        }
        Suggestion suggestion = voterDao.getSuggestion(suggestionId);
        if (suggestion == null) {
            return ConverterUtils.convertToJson(new ErrorDtoResponse(new ElectionsException(ElectionsErrorCode.VOTER_SUGGESTION_NOT_FOUND)));
        }
        voterDao.addRatingToSuggestion(suggestionId, new Rating(getVoterByToken(token), rating));
        return ConverterUtils.convertToJson(new AddRatingDtoResponse());
    }

    public int getVotersOnline(){
        return voterDao.getVotersOnline();
    }

    public Suggestion getSuggestion(int id){
        return voterDao.getSuggestion(id);
    }

    public int getCandidatesCount(){
        return voterDao.getCandidates().size();
    }

    public void deleteAllVotes(String token) {
//        List<Voter> voters = voterDao.getAll();
//        for (Voter voter : voters) {
//            if (!voter.equals(getVoterByToken(token))) {
//                voter.getSuggestions().removeMapping()
//            }
//        }
    }

    public boolean electionDay() {
        return electionDay;
    }

    public void endElectionDay() {
        electionDay = false;
    }

    public void startElectionDay() {
        electionDay = true;
    }

    public String requestCandidate(String token, int id) {
        if (electionDay) {
            return ConverterUtils.convertToJson(new ErrorDtoResponse(ElectionsErrorCode.VOTER_ELECTION_DAY_ERROR.getErrorString()).getErrorString());
        }
        Voter voter = voterDao.getById(id);
        if (getVoterByToken(token) != null && voter != null) {
            voter.setRequestedToBecomeCandidate(true);
            return ConverterUtils.convertToJson(new CandidateRequestDtoResponse("Requested to become candidate").getResponseString());
        } else {
            return ConverterUtils.convertToJson(new ErrorDtoResponse(ElectionsErrorCode.VOTER_NOT_FOUND.getErrorString()).getErrorString());
        }
    }

    public String answerCandidateRequest(String token, boolean answer) {
        Voter voter = getVoterByToken(token);
        if (voter != null) {
            if (answer) {
                return becomeCandidate(voter);
            } else {
                voter.setRequestedToBecomeCandidate(false);
                return ConverterUtils.convertToJson(new CandidateRequestDtoResponse("You declined request").getResponseString());
            }
        } else {
            return ConverterUtils.convertToJson(new ErrorDtoResponse(ElectionsErrorCode.VOTER_NOT_FOUND.getErrorString()).getErrorString());
        }
    }

    public String becomeCandidate(Voter voter) {
        if (voter != null) {
            voter.setCandidate();
            voterDao.addCandidate(voter);
            return ConverterUtils.convertToJson(new CandidateRequestDtoResponse("Become candidate").getResponseString());
        } else {
            return ConverterUtils.convertToJson(new ErrorDtoResponse(ElectionsErrorCode.VOTER_NOT_FOUND.getErrorString()).getErrorString());
        }

    }

    public String becomeCandidate(String token) {
        Voter voter = getVoterByToken(token);
        if (voter != null) {
            voter.setCandidate();
            voterDao.addCandidate(voter);
            return ConverterUtils.convertToJson(new CandidateRequestDtoResponse("Become candidate").getResponseString());
        } else {
            return ConverterUtils.convertToJson(new ErrorDtoResponse(ElectionsErrorCode.VOTER_NOT_FOUND.getErrorString()).getErrorString());
        }
    }


}
