package elections.model;

public class Candidate {
    private User user;
    private int votes;
    private int id;

    public Candidate(Voter voter){
        user = voter.getUser();
        votes = 0;
        id = voter.getId();
    }

    public int getId(){
        return id;
    }

    public int getVotes(){
        return votes;
    }

    public void addVote(){
        votes++;
    }
}
