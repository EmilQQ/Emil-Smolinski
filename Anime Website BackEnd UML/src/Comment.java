public class Comment extends ObjectPlus{
    private int id;
    private String text;
    private Anime anime;
    private User user;

    public Comment(int id, String text, User user) throws Exception{
        setId(id);
        setText(text);
        this.user = user;
        user.addComment(this);
    }

    @Override
    public void removeFromExtent(){
        if(user != null){
            User temp = user;
            user = null;
            temp.removeComment(this);
        }
        super.removeFromExtent();
    }

    public void setAnime(Anime anime){
        if (this.anime != null && !this.anime.equals(anime)){
            Anime oldanime = this.anime;
            this.anime = null;
            oldanime.removeComment(this);
        }
        this.anime = anime;
        anime.addComment(this);
    }

    public void removeAnime(Anime anime){
        if (this.anime != null){
            this.anime = null;
            anime.removeComment(this);
        }
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public void setText(String text) {
        if (text.isEmpty() || text.equals("null")) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        this.text = text;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + user +
                ", text='" + text + '\'' +
                ", anime=" + anime +
                '}';
    }
}
