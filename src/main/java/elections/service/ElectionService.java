package elections.service;

import elections.dao.VoterDao;
import elections.daoImpl.VoterDaoImpl;
import elections.dto.response.AddRatingDtoResponse;
import elections.dto.response.ElectionResultDtoResponse;
import elections.dto.response.ErrorDtoResponse;
import elections.dto.response.VoteDtoResponse;
import elections.errors.ElectionsErrorCode;
import elections.errors.ElectionsException;
import elections.model.Candidate;
import elections.model.Voter;
import elections.utils.ConverterUtils;

public class ElectionService {
    private VoterDao voterDao = new VoterDaoImpl();

    public String addVoteToCandidate(int id, String tokenAuthor) {
        Candidate candidate = voterDao.getCandidate(id);
        Voter voter = voterDao.get(tokenAuthor);
        if(candidate == null || voter == null){
            return ConverterUtils.convertToJson(new ErrorDtoResponse(new ElectionsException(ElectionsErrorCode.VOTER_NOT_FOUND)));
        }
        if(voter.isVoted()){
            return ConverterUtils.convertToJson(new ErrorDtoResponse(new ElectionsException(ElectionsErrorCode.VOTER_ALREADY_VOTE)));
        }
        if(voter.getId() == candidate.getId()){
            return ConverterUtils.convertToJson(new ErrorDtoResponse(new ElectionsException(ElectionsErrorCode.VOTER_VOTE_SELF)));
        }
        voterDao.addCandidateRating(id, tokenAuthor);
        return ConverterUtils.convertToJson(new AddRatingDtoResponse());
    }

    public String againstAll(String tokenAuthor) {
        Voter voter = voterDao.get(tokenAuthor);
        if(voter == null){
            return ConverterUtils.convertToJson(new ErrorDtoResponse(ElectionsErrorCode.VOTER_NOT_FOUND.getErrorString()).getErrorString());
        }
        if(voter.isVoted()){
            return ConverterUtils.convertToJson(new ErrorDtoResponse(new ElectionsException(ElectionsErrorCode.VOTER_ALREADY_VOTE)));
        }
        voterDao.addVoteAgainstAll(tokenAuthor);
        return ConverterUtils.convertToJson(new VoteDtoResponse());
    }

    private int maxVotes() {
        int maxVotes = 0;
        int id = -1;
        for (Candidate candidate : voterDao.getCandidates().values()) {
            if (candidate.getVotes() > maxVotes) {
                maxVotes = candidate.getVotes();
                id = candidate.getId();
            }
        }
        return id;
    }

    public int getVotesCount(int id){
        return voterDao.getVotesCount(id);
    }

    public int getVotesAgainstAll(){
        return voterDao.getVotesAgainstAll();
    }

    public String result() {
        Candidate candidate = voterDao.getCandidate(maxVotes());
        if (voterDao.getVotesAgainstAll() > candidate.getVotes()) {
            return ConverterUtils.convertToJson(new ElectionResultDtoResponse().getResult());
        } else {
            return ConverterUtils.convertToJson(new ElectionResultDtoResponse(maxVotes(), candidate.getId()).getResult() );
        }
    }
}
