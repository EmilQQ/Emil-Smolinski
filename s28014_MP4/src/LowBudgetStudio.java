public class LowBudgetStudio extends Studio{
    private boolean hasHiredFreelancers;
    private int typicalFramesPerSecond;

    public LowBudgetStudio(String name, boolean hasHiredFreelancers, int typicalFramesPerSecond) {
        super(name);
        setHasHiredFreelancers(hasHiredFreelancers);
        setTypicalFramesPerSecond(typicalFramesPerSecond);
    }

    public LowBudgetStudio (HighBudgetStudio highBudgetStudio, boolean hasHiredFreelancers, int typicalFramesPerSecond) {
        super(highBudgetStudio);
        setHasHiredFreelancers(hasHiredFreelancers);
        setTypicalFramesPerSecond(typicalFramesPerSecond);
    }

    public HighBudgetStudio changeToHighBudgetStudio(boolean hasLicenseForTopAnimes, int numberOfEmployees) {
        HighBudgetStudio highBudgetStudio = new HighBudgetStudio(this, hasLicenseForTopAnimes, numberOfEmployees);
        highBudgetStudio.animesMap = this.animesMap;
        for (Anime anime : highBudgetStudio.animesMap.values()) {
            anime.setStudio(highBudgetStudio);
        }

        this.removeFromExtent();
        return highBudgetStudio;
    }

    public boolean isHasHiredFreelancers() {
        return hasHiredFreelancers;
    }

    public int getTypicalFramesPerSecond() {
        return typicalFramesPerSecond;
    }

    public void setHasHiredFreelancers(boolean hasHiredFreelancers) {
        this.hasHiredFreelancers = hasHiredFreelancers;
    }

    public void setTypicalFramesPerSecond(int typicalFramesPerSecond) {
        this.typicalFramesPerSecond = typicalFramesPerSecond;
    }
}
