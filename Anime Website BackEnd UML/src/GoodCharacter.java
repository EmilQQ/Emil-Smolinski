public class GoodCharacter extends Character{
    private String moralCode;
    private boolean willToSacrifice;

    public GoodCharacter(Race race, String voiceActor, String characterName, String characterSurName, String moralCode, boolean willToSacrifice){
        super(race, voiceActor, characterName, characterSurName);
        setMoralCode(moralCode);
        setWillToSacrifice(willToSacrifice);
    }

    public String getMoralCode() {
        return moralCode;
    }

    public boolean isWillToSacrifice() {
        return willToSacrifice;
    }

    public void setMoralCode(String moralCode) {
        this.moralCode = moralCode;
    }

    public void setWillToSacrifice(boolean willToSacrifice) {
        this.willToSacrifice = willToSacrifice;
    }

    @Override
    public String toString() {
        return "GoodCharacter{" +
                "race=" + getRace() +
                ", voiceActor='" + getVoiceActor() + '\'' +
                ", characterName='" + getCharacterName() + '\'' +
                ", characterSurName='" + getCharacterSurName() + '\'' +
                ", moralCode='" + moralCode + '\'' +
                ", willToSacrifice=" + willToSacrifice +
                '}';
    }
}
