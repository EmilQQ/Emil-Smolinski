import java.io.*;
import java.util.*;

public class ObjectPlus implements Serializable {
    private static Map<Class, List> extent = new HashMap<>();

    public ObjectPlus() {
        addToExtent();
    }

    protected void addToExtent(){
        List list = extent.computeIfAbsent(this.getClass(), k -> new ArrayList<>());
        list.add(this);
    }

    protected void removeFromExtent(){
        List list = extent.get(this.getClass());
        if(list != null) {
            list.remove(this);
        }
    }

    public static <T> List<T> getExtentFromClass(Class<T> c){
        extent.computeIfAbsent(c, k -> new ArrayList<>());
        return Collections.unmodifiableList(extent.get(c));
    }

    public static void saveExtent() throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("extent.ser"))) {
            oos.writeObject(extent);
            oos.writeInt(Anime.getMinNumberOfEpisodes());
            oos.writeInt(Human.getMaxAge());
        }
    }

    public static void loadExtent() throws IOException, ClassNotFoundException {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("extent.ser"))) {
            extent = (Map<Class, List>) ois.readObject();
            Anime.setMinNumberOfEpisodes(ois.readInt());
        }
    }
}
