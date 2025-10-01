public class Manga extends ObjectPlus{

    private String title;
    private String author;

    public Manga(String title, String author) {
        try {
            setTitle(title);
            setAuthor(author);
        } catch (Exception e) {
            e.printStackTrace();
            removeFromExtent();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws IllegalArgumentException{
        if(title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (ObjectPlus.getExtentFromClass(getClass()).stream().anyMatch(x -> x != this && x.getTitle().equals(title))) {
            throw new IllegalArgumentException("Title already exists");
        }
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) throws IllegalArgumentException{
        if(author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }
        if(author.length() < 5) {
            throw new IllegalArgumentException("Author is too short");
        }
        if(!author.matches("^[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]+ [A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]+$")){
            throw new IllegalArgumentException("Author must be alphanumeric, Name and Surname starts with a Big Letter and only one space is used");
        }

        this.author = author;
    }

    @Override
    public String toString() {
        return "Manga{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
