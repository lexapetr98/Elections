package elections.model;

public class Suggestion {
    private String text;
    private User author;
    private int id;

    public Suggestion(String text, User author, int id) {
        this.id = id;
        this.text = text;
        this.author = author;
    }

    public Suggestion(){
        this.text = "";
        this.author = new User();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!Suggestion.class.isAssignableFrom(o.getClass())) {
            return false;
        }

        final Suggestion other = (Suggestion) o;
        if ((this.text == null) ? (other.getText() != null) : !this.text.equals(other.getText())) {
            return false;
        }
        return true;
    }
}
