import java.util.List;

public class Monster extends Race{
    private boolean selfRegenerating;
    private List<String> abilities;

    public Monster(boolean selfRegenerating, List<String> abilities) {
        setSelfRegenerating(selfRegenerating);
        setAbilities(abilities);
    }

    public boolean isSelfRegenerating() {
        return selfRegenerating;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public void setSelfRegenerating(boolean selfRegenerating) {
        this.selfRegenerating = selfRegenerating;
    }

    public void setAbilities(List<String> abilities) {
        this.abilities = abilities;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "selfRegenerating=" + selfRegenerating +
                ", abilities=" + abilities +
                '}';
    }
}
