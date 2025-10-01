import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User extends Person{
    private String username;
    private int accountPoints;
    private final List<Comment> commentsList = new ArrayList<>();
    private static Set<Comment> allcommentsSet = new HashSet<>();

    private Set<Anime> watchedAnimes = new HashSet<>();
    private Set<Anime> favoriteAnimes = new HashSet<>();

    public User(String name, String surname, String username, int accountPoints) {
        super(name, surname);
        setUsername(username);
        setAccountPoints(accountPoints);
    }

    public void addAnimeToWatchedList(Anime anime) {
        watchedAnimes.add(anime);
    }

    public void addAnimeToFavoriteList(Anime anime) {
        if(!watchedAnimes.contains(anime)) {
            throw new IllegalArgumentException("Anime is not watched");
        }
        favoriteAnimes.add(anime);
    }

    public void removeAnimeFromWatchedList(Anime anime) {
        if(favoriteAnimes.contains(anime)) {
            removeAnimeFromFavoriteList(anime);
        }
        watchedAnimes.remove(anime);
    }

    public void removeAnimeFromFavoriteList(Anime anime) {
        favoriteAnimes.remove(anime);
    }

    public void introduce(){
        String tierAccount;
        if(accountPoints > 100){
            tierAccount = "Gold";
        } else if (accountPoints > 50){
            tierAccount = "Silver";
        } else {
            tierAccount = "Bronze";
        }

        System.out.println("Jestem " + getName() + " " + getSurname() + ". Moja nazwa użytkownika to " + getUsername() + ", a dzięki uzyskaniu " + getAccountPoints() + " punktów na stronie mam rangę " + tierAccount);
    }

    public void addComment(Comment comment) throws Exception{
        if (!commentsList.contains(comment)){
            if (allcommentsSet.contains(comment)){
                throw new Exception("The comment is already connected with a User!!!!!");
            }
            commentsList.add(comment);
            allcommentsSet.add(comment);
        }
    }

    public void removeComment(Comment comment){
        if (commentsList.remove(comment)){
            allcommentsSet.remove(comment);
            comment.removeFromExtent();
        }
    }

    @Override
    public void removeFromExtent(){
        while (!commentsList.isEmpty()){
            removeComment(commentsList.get(0));
        }
        super.removeFromExtent();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.isEmpty()){
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        this.username = username;
    }

    public int getAccountPoints() {
        return accountPoints;
    }

    public void setAccountPoints(int accountPoints) {
        this.accountPoints = accountPoints;
    }

    public String getWatchedAnimes() {
        return "Anime które obejrzałem to : " + watchedAnimes.stream().map(a -> a.getTitle()).toList();
    }

    public String getFavoriteAnimes() {
        return "Moje ulubione anime to : " + favoriteAnimes.stream().map(a -> a.getTitle()).toList();
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", commentsList=" + commentsList.stream().map(c -> c.getId() + "/" + c.getText()).toList() +
                '}';
    }
}
