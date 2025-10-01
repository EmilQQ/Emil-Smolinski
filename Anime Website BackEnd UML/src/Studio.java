import java.util.HashMap;
import java.util.Map;

public class Studio extends ObjectPlus{
    private String name;
    protected Map<String, Anime> animesMap = new HashMap<>();

    public Studio(String name) {
        setName(name);
    }

    public Studio(Studio studio) {
        this.name = studio.name;
        //this.animesMap = studio.animesMap;    //Zapytaj Pana
    }

    public void addAnime(Anime anime) {
        if (!animesMap.containsKey(anime.getTitle())) {
            animesMap.put(anime.getTitle(), anime);
            anime.setStudio(this);
        }
    }

    public Anime findAnimeByTitle(String title){
        if (!animesMap.containsKey(title)) {
            System.out.println(("Unable to find an anime: " + title));
        }
        return animesMap.get(title);
    }

    public void removeAnime(Anime anime){
        if(animesMap.remove(anime.getTitle(), anime)){
            anime.removeStudio(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return "Studio{" +
                "name='" + name + '\'' +
                ", animesMap=" + animesMap.keySet() +
                '}';
    }
}
