public class HighBudgetStudio extends Studio{
    private boolean hasLicenseForTopAnimes;
    private int numberOfEmployees;

    public HighBudgetStudio(String name, boolean hasLicenseForTopAnimes, int numberOfEmployees) {
        super(name);
        setHasLicenseForTopAnimes(hasLicenseForTopAnimes);
        setNumberOfEmployees(numberOfEmployees);
    }

    public HighBudgetStudio (LowBudgetStudio lowBudgetStudio, boolean hasLicenseForTopAnimes, int numberOfEmployees) {
        super(lowBudgetStudio);
        setHasLicenseForTopAnimes(hasLicenseForTopAnimes);
        setNumberOfEmployees(numberOfEmployees);
    }

    public LowBudgetStudio changeToLowBudgetStudio(boolean hasHiredFreelancers, int typicalFramesPerSecond) {
        LowBudgetStudio lowBudgetStudio = new LowBudgetStudio(this, hasHiredFreelancers, typicalFramesPerSecond);
        lowBudgetStudio.animesMap = this.animesMap;
        for (Anime anime : lowBudgetStudio.animesMap.values()) {
            anime.setStudio(lowBudgetStudio);
        }

        this.removeFromExtent();
        return lowBudgetStudio;
    }

    public boolean isHasLicenseForTopAnimes() {
        return hasLicenseForTopAnimes;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setHasLicenseForTopAnimes(boolean hasLicenseForTopAnimes) {
        this.hasLicenseForTopAnimes = hasLicenseForTopAnimes;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }
}
