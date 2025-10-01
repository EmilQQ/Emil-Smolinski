import java.time.LocalDate;

public interface ILight_Novel {
    public int getHowManyDaysPassedSinceRelease();
    public String getOriginalTitle();
    public String getOriginalAuthor();
    public LocalDate getReleaseDate();
    public int getChaptersCount();
    public void setOriginalTitle(String originalTitle);
    public void setOriginalAuthor(String originalAuthor);
    public void setReleaseDate(LocalDate releaseDate);
    public void setChaptersCount(int chaptersCount);
}
