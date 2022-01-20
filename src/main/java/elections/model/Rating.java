package elections.model;

public class Rating {
    private int rating;
    private Voter author;

    public Rating() {
        rating = 0;
        author = new Voter();
    }

    public Rating(Voter author, Integer rating) {
        this.rating = rating;
        this.author = author;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Voter getAuthor() {
        return author;
    }

    public void setAuthor(Voter author) {
        this.author = author;
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
        return author.getId() == other.getId();
    }
}
