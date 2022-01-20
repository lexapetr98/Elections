package elections.model;

public class Voter {
    private User user;
    private boolean isCandidate = false;
    private boolean isRequestedToBecomeCandidate = false;
    private boolean isVoted = false;
    private int id = 0;

    public Voter(String name, String surname, String patronymic, String street, String house, String flat, String login, String password, int id) {
        user = new User(name, surname, patronymic, street, house, flat, login, password);
        this.id = id;
    }

    public Voter() {
        user = new User();
    }

    public boolean isVoted() {
        return isVoted;
    }

    public void setVoted(boolean isVoted) {
        this.isVoted = isVoted;
    }

    public void setRequestedToBecomeCandidate(boolean value) {
        isRequestedToBecomeCandidate = value;
    }

    public void setCandidate() {
        isCandidate = true;
    }

    public boolean isCandidate() {
        return isCandidate;
    }

    public User getUser(){
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Voter)) {
            return false;
        }

        final Voter other = (Voter) o;
        return id == other.getId();
    }
}
