import javax.swing.*;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception{
        Light_Novel lightNovel = new Light_Novel("Re:Zero − Starting Life in Another World", "Tappei Nagatsuki", LocalDate.of(2005, 12, 3), 200);
        Light_Novel lightNovel2 = new Light_Novel("Steins;Gate: Annularly-Chained Ouroboros", "Choushirou Miwa", LocalDate.of(2007, 3, 30), 100);
        Light_Novel lightNovel3 = new Light_Novel("My Youth Romantic Comedy Is Wrong, as I Expected", "Wataru Watari", LocalDate.of(2011, 3, 18), 400);
        Light_Novel lightNovel4 = new Light_Novel("Yahari Ore no Seishun Rabukome wa Machigatteiru", "Ponkan 8", LocalDate.of(2010, 4, 20), 300);

        Anime a1 = new Anime("Re zero", "Akamaka Po", 1, lightNovel, new EmissionInformation("onGoing", LocalDate.of(2012, 3, 12), null, "12+"));
        Anime a2 = new Anime("Steins Gate", "Belzebub Bo", 2, lightNovel2, new EmissionInformation("finished", LocalDate.of(2016, 4, 13), LocalDate.of(2024, 10, 2), "18+"));
        Anime a3 = new Anime("Oregairu", "Celina Co", 3, lightNovel3, new EmissionInformation("onGoing", LocalDate.of(2011, 5, 20), null, "16+"));
        Anime a4 = new Anime("Snafu", "Duolingo Do", 4, lightNovel4, new EmissionInformation("finished", LocalDate.of(2011, 5, 20), LocalDate.of(2025, 1, 1), "6+"), "For teenagers");

        a1.addCategory(new Category(1, "Akcja"));
        a1.addCategory(new Category(2, "Horror"));
        a2.addCategory(new Category(3, "Muzyczne"));
        a3.addCategory(new Category(4, "Romans"));

        System.out.println(a1.getEmissionInformation());
        System.out.println(a1.getCategories());
        System.out.println(a4.getTargetGroup());
        System.out.println(a1.getNumberOfCategories());
        System.out.println(Anime.searchAfterId(1));

        Anime.setMinNumberOfEpisodes(15);
        System.out.println(Anime.getMinNumberOfEpisodes());
        System.out.println(ObjectPlus.getExtentFromClass(Anime.class));

//        try {
//            ObjectPlus.saveExtent();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        System.out.println(Anime.getMinNumberOfEpisodes());
        System.out.println(ObjectPlus.getExtentFromClass(Anime.class));

        System.out.println("=================================//Asocjacja zwykla"); //Asocjacja zwykla

        Comment comment1 = new Comment(1, "swietne", new User("Franciszek", "Papieski", "kali", 60));
        Comment comment2 = new Comment(2, "slabe", new User("Budapesto", "Skolimi", "elomalelo", 70));

        a1.addComment(comment1);
        a1.addComment(comment2);


        System.out.println(a1);
        System.out.println(comment1);
        System.out.println(a2);

        comment1.setAnime(a2);
        a1.removeComment(comment2);

        System.out.println(a1);
        System.out.println(comment1);
        System.out.println(a2);

        comment1.removeAnime(a2);

        System.out.println(comment1);
        System.out.println(a2);
        System.out.println(ObjectPlus.getExtentFromClass(Comment.class));

        System.out.println("=====================================//Asocjacja z atrybutem"); //Asocjacja z atrybutem

        Worker worker = new Worker("Jan", "Pieszko", "Szczery", Map.of(WorkerSpeciality.Director, 4));
        Worker worker2 = new Worker("Onomatopeja", "Cichy", "Ambitny", Map.of(WorkerSpeciality.Animator, 1, WorkerSpeciality.Editor, 7));

        a4.addWorker(worker);
        //a4.addWorker(worker);
        a4.addWorker(worker2);
        a3.addWorker(worker);
        worker2.addAnime(a2);

        System.out.println(ObjectPlus.getExtentFromClass(Contract.class));
        System.out.println(a4);
        System.out.println(worker);
        System.out.println(a2);
        System.out.println(worker2);

        a4.removeWorker(worker);
        worker2.removeAnime(a4);

        System.out.println(ObjectPlus.getExtentFromClass(Contract.class));
        System.out.println(a4);
        System.out.println(worker);
        System.out.println(a2);
        System.out.println(worker2);

        System.out.println("=========================================//Asocjacja kwalifikowana"); //Asocjacja kwalifikowana

        Studio studio = new Studio("Kadokawa");
        Studio studio2 = new Studio("A-animations");
        Studio studio3 = new Studio("Fiiiie");

        studio.addAnime(a1);
        studio.addAnime(a2);
        studio2.addAnime(a1);
        studio3.addAnime(a2);

        System.out.println(studio);
        System.out.println(studio2);
        System.out.println(studio.findAnimeByTitle("Re zero"));

        a2.removeStudio(studio);

        System.out.println(studio);
        System.out.println(studio.findAnimeByTitle("Re zero"));

        System.out.println("=======================================//Kompozycja");  //Kompozycja

        User user = new User("Kisp", "Franololo", "malucha", 101);
        User user2 = new User("Lodas", "Mikita", "kapi", 25);
        Comment comment3 = new Comment(3, "srednie", user);
        Comment comment4 = new Comment(4, "git", user);
        //user2.addComment(comment3);

        System.out.println(user);
        System.out.println(comment3);
        System.out.println(comment4);

        user.removeComment(comment3);


        System.out.println(ObjectPlus.getExtentFromClass(User.class));
        System.out.println(ObjectPlus.getExtentFromClass(Comment.class));

        user.removeFromExtent();

        System.out.println(ObjectPlus.getExtentFromClass(User.class));
        System.out.println(ObjectPlus.getExtentFromClass(Comment.class));

        System.out.println("==================================//Polimorfizm");   //Chec utworzenia kontraktu z takimi pracownikami ale wtedy zamienie na person. Jak uniemozliwic stworzenie kontraktu z uzytkownikiem. Czy interfejs zamiast klasy abstr jest rozw


        Person worker3 = new Worker("Filon", "Babarelo", "Sprytny", Map.of(WorkerSpeciality.Director, 2, WorkerSpeciality.Editor, 7, WorkerSpeciality.Animator, 10));
        worker3.introduce();
        Person user10 = new User("Filon", "Babarelo", "ITMasteroooo", 25);
        user10.introduce();

        System.out.println("=====================================//Overlapping");

        System.out.println(worker2.getWorkerSpecialities());
        worker2.setToolsUsed(List.of("Adobe", "Powerpoint"));
        System.out.println(worker2.getToolsUsed());
        //worker2.setDirectedProjectsCount(2);
        worker2.addAnime(a4);
        System.out.println(worker2);
        worker2.setEditingSoftware(List.of("PowerShark"));
        worker2.introduce();

        System.out.println("==================================//Wielodziedziczenie");

        /*Light_Novel lightNovel = new Light_Novel("Re:Zero − Starting Life in Another World", "Tappei Nagatsuki", LocalDate.of(2012, 12, 3), 200);
        Light_Novel lightNovel2 = new Light_Novel("Steins;Gate: Annularly-Chained Ouroboros", "Choushirou Miwa", LocalDate.of(2007, 3, 30), 100);
        Light_Novel lightNovel3 = new Light_Novel("My Youth Romantic Comedy Is Wrong, as I Expected", "Wataru Watari", LocalDate.of(2011, 3, 18), 400);
        Light_Novel lightNovel4 = new Light_Novel("Yahari Ore no Seishun Rabukome wa Machigatteiru", "Ponkan 8", LocalDate.of(2016, 4, 20), 300);*/

        System.out.println(lightNovel.getHowManyDaysPassedSinceRelease());
        System.out.println(a1.getHowManyDaysPassedSinceRelease());
        System.out.println(a1.getReleaseDate());
        System.out.println(a1.getOriginalTitle());
        System.out.println(a1.getTitle());

        System.out.println("==================================//Wieloaspektowe"); //ten sam aspekt co w abstrakcyjnej

        Character goodHuman = new GoodCharacter(new Human(100), "Tamasa Hita", "Naruto", "Uzumaki", "Protect life", true);
        Character goodMonster = new GoodCharacter(new Monster(false, List.of("Niewidzialnosc")), "Penpei Sada", "Sarada", "Ssadsa", "Protect love", false);
        Character villainHuman = new Villain(new Human(200), "Fujitaro Ruka", "Eleganto", "X", "Destroy life", true, 5);
        Character villainMonster = new Villain(new Monster(true, List.of("Superszybkosc", "Hipnoza")), "Rukita Sampoi", "Tsutsuki", "Byakuya", "Destroy love", false, 10);

        System.out.println(goodHuman);
        System.out.println(villainMonster);

        System.out.println("==================================//Dynamiczne");

        Studio studio10 = new LowBudgetStudio("FmAnimations", true, 30);
        studio10.addAnime(a1); //to niby sie nie zmienia bo po prostu musialbym dodac kolejne anime, gdyz to juz ma ustawione studio na jeszcze inne
        studio10.addAnime(a2);
        System.out.println(studio10 + " " + studio10.getClass());

        System.out.println(ObjectPlus.getExtentFromClass(LowBudgetStudio.class));
        System.out.println(ObjectPlus.getExtentFromClass(HighBudgetStudio.class));

        studio10 = ((LowBudgetStudio) studio10).changeToHighBudgetStudio(true, 20);
        System.out.println(studio10 + " " + studio10.getClass());
        System.out.println(ObjectPlus.getExtentFromClass(LowBudgetStudio.class));
        System.out.println(ObjectPlus.getExtentFromClass(HighBudgetStudio.class));

        System.out.println("====================================//Ograniczenie atrybutu");

        System.out.println(a1.getAuthor());
        //a1.setAuthor("sa");
        //a1.setAuthor("miyazano furitaka");
        a1.setAuthor("Miyazano Furitaka");
        System.out.println(a1.getAuthor());

        System.out.println("===============================//Unique");

        System.out.println(a1.getTitle());
        //a1.setTitle("Snafu");
        a1.setTitle("Adobe");
        System.out.println(a1.getTitle());

        System.out.println("===============================//Subset");

        User maciek = new User("Maciek", "Babarelo", "SprytnyMaciek", 120);
        maciek.addAnimeToWatchedList(a1);
        maciek.addAnimeToFavoriteList(a1);
        System.out.println(maciek.getWatchedAnimes());
        System.out.println(maciek.getFavoriteAnimes());
        maciek.removeAnimeFromWatchedList(a1);
        System.out.println(maciek.getWatchedAnimes());
        System.out.println(maciek.getFavoriteAnimes());
        //maciek.addAnimeToFavoriteList(a2);

        System.out.println("===============================//Ordered");

        TreeSet<Anime> animeSet = new TreeSet<>(Comparator.comparing(Anime::getTitle));
        animeSet.add(a1);
        animeSet.add(a2);
        animeSet.add(a3);
        animeSet.add(a4);
        System.out.println(animeSet);

        //System.out.println("===============================//Bag");  //Zmieniona asocjacja z atrybutem na potrzeby bag

        //Anime anime5 = new Anime("Slime", "Fitaba Kutsu", 5, lightNovel, new EmissionInformation("finished", LocalDate.of(2011, 5, 20), LocalDate.of(2025, 1, 1), "6+"), "For teenagers");
        //Worker workerSpec = new Worker("Emil", "Masahita", "Szczery", Map.of(WorkerSpeciality.Director, 4));
        //anime5.addWorker(workerSpec);
        //workerSpec.addAnime(anime5);

        //System.out.println(ObjectPlus.getExtentFromClass(Contract.class));

        System.out.println("=============================//XOR");

        Film film = new Film(120);
        Series series = new Series(12);

        a1.setFilm(film);
        System.out.println(a1.getFilmXORseries());
        //a1.setSeries(series);

        System.out.println("==============================//Własne");

        Anime a10 = new Anime("Figo Fago SAtory", "Celina Co", 5, lightNovel3, new EmissionInformation("onGoing", LocalDate.of(2011, 5, 20), null, "21+"));
        System.out.println(a10);

        System.out.println("=========================================");

//        try {
//            ObjectPlus.saveExtent();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        SwingUtilities.invokeLater(() -> {
            new ManageAnimePanel();
        });
    }
}