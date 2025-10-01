public class Villain extends Character{
    private String goal;
    private boolean isRedeemable;
    private int crimesCommited;

    public Villain(Race race, String voiceActor, String characterName, String characterSurName, String goal, boolean isRedeemable, int crimesCommited){
        super(race, voiceActor, characterName, characterSurName);
        setGoal(goal);
        setRedeemable(isRedeemable);
        setCrimesCommited(crimesCommited);
    }

    public String getGoal() {
        return goal;
    }

    public boolean isRedeemable() {
        return isRedeemable;
    }

    public int getCrimesCommited() {
        return crimesCommited;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public void setRedeemable(boolean redeemable) {
        isRedeemable = redeemable;
    }

    public void setCrimesCommited(int crimesCommited) {
        this.crimesCommited = crimesCommited;
    }

    @Override
    public String toString() {
        return "Villain{" +
                "race=" + getRace() +
                ", voiceActor='" + getVoiceActor() + '\'' +
                ", characterName='" + getCharacterName() + '\'' +
                ", characterSurName='" + getCharacterSurName() + '\'' +
                ", goal='" + goal + '\'' +
                ", isRedeemable=" + isRedeemable +
                ", crimesCommited=" + crimesCommited +
                '}';
    }
}
