public class Series extends ObjectPlus{
    private int numberOfEpisodes;

    public Series(int numberOfEpisodes) {
        setNumberOfEpisodes(numberOfEpisodes);
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }
}
