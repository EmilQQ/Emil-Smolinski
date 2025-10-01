import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Light_Novel extends ObjectPlus implements ILight_Novel{
    private String originalTitle;
    private String originalAuthor;
    private LocalDate releaseDate;
    private int chaptersCount;
    private Anime anime;

    protected void setAnime(Anime anime) {
        this.anime = anime;
    }

    public Light_Novel(String originalTitle, String originalAuthor, LocalDate releaseDate, int chaptersCount) {
        setOriginalTitle(originalTitle);
        setOriginalAuthor(originalAuthor);
        setReleaseDate(releaseDate);
        setChaptersCount(chaptersCount);
    }

    public int getHowManyDaysPassedSinceRelease() {
        LocalDate now = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(getReleaseDate(), now);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalAuthor() {
        return originalAuthor;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public int getChaptersCount() {
        return chaptersCount;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOriginalAuthor(String originalAuthor) {
        this.originalAuthor = originalAuthor;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setChaptersCount(int chaptersCount) {
        this.chaptersCount = chaptersCount;
    }

    @Override
    public String toString() {
        return "Light_Novel{" +
                "originalTitle='" + originalTitle + '\'' +
                ", originalAuthor='" + originalAuthor + '\'' +
                ", releaseDate=" + releaseDate +
                ", chaptersCount=" + chaptersCount +
                ", anime=" + anime +
                '}';
    }
}
