//Facade
import java.util.*;


class TV {
    public void on() { System.out.println("TV qosyldy"); }
    public void off() { System.out.println("TV oshirildi"); }
    public void setChannel(int channel) { System.out.println("Kanal " + channel + " ornatty"); }
}

class AudioSystem {
    public void on() { System.out.println("Audio zhuiye qosyldy"); }
    public void off() { System.out.println("Audio zhuiye oshirildi"); }
    public void setVolume(int level) { System.out.println("Dybys dengei: " + level); }
}

class DVDPlayer {
    public void on() { System.out.println("DVD oynatqysh qosyldy"); }
    public void off() { System.out.println("DVD oynatqysh oshirildi"); }
    public void play() { System.out.println("Film oinatyluda"); }
    public void pause() { System.out.println("Film ayaldatyldy"); }
    public void stop() { System.out.println("Film toqtatyldy"); }
}

class GameConsole {
    public void on() { System.out.println("Oin konsoli qosyldy"); }
    public void playGame(String game) { System.out.println("Oin iske qosyldy: " + game); }
}

class HomeTheaterFacade {
    private TV tv;
    private AudioSystem audio;
    private DVDPlayer dvd;
    private GameConsole console;

    public HomeTheaterFacade(TV tv, AudioSystem audio, DVDPlayer dvd, GameConsole console) {
        this.tv = tv;
        this.audio = audio;
        this.dvd = dvd;
        this.console = console;
    }

    public void watchMovie() {
        System.out.println("Film koru rejimi");
        tv.on();
        audio.on();
        audio.setVolume(8);
        dvd.on();
        dvd.play();
    }

    public void stopMovie() {
        System.out.println("Film toqtatu");
        dvd.stop();
        dvd.off();
        audio.off();
        tv.off();
    }

    public void playGame(String game) {
        System.out.println("Oin rejimi");
        tv.on();
        audio.on();
        console.on();
        console.playGame(game);
    }

    public void listenMusic() {
        System.out.println(" Muzyka tyndau rejimi ");
        tv.on();
        audio.on();
        audio.setVolume(5);
        System.out.println("TV audio kirisi arqyly muzyka oinatyluda");
    }

    public void turnOffAll() {
        System.out.println("Barlyq zhuiye oshirildi");
        tv.off();
        audio.off();
        dvd.off();
    }
}

//Composite

abstract class FileSystemComponent {
    protected String name;
    public FileSystemComponent(String name) { this.name = name; }
    public abstract void display(String indent);
    public abstract int getSize();
}

class File extends FileSystemComponent {
    private int size;
    public File(String name, int size) {
        super(name);
        this.size = size;
    }
    @Override
    public void display(String indent) {
        System.out.println(indent + "- Fail: " + name + " (" + size + "KB)");
    }
    @Override
    public int getSize() { return size; }
}

class Directory extends FileSystemComponent {
    private List<FileSystemComponent> components = new ArrayList<>();

    public Directory(String name) { super(name); }

    public void add(FileSystemComponent component) {
        if (!components.contains(component)) {
            components.add(component);
        }
    }

    public void remove(FileSystemComponent component) {
        if (components.contains(component)) {
            components.remove(component);
        }
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "+ Qalta: " + name);
        for (FileSystemComponent c : components) {
            c.display(indent + "   ");
        }
    }

    @Override
    public int getSize() {
        int total = 0;
        for (FileSystemComponent c : components) {
            total += c.getSize();
        }
        return total;
    }
}


public class Main9 {
    public static void main(String[] args) {


        TV tv = new TV();
        AudioSystem audio = new AudioSystem();
        DVDPlayer dvd = new DVDPlayer();
        GameConsole console = new GameConsole();

        HomeTheaterFacade home = new HomeTheaterFacade(tv, audio, dvd, console);

        home.watchMovie();
        home.stopMovie();
        home.playGame("FIFA 25");
        home.listenMusic();
        home.turnOffAll();

        System.out.println("\n==============================\n");

        File f1 = new File("music.mp3", 5000);
        File f2 = new File("video.mp4", 15000);
        File f3 = new File("photo.jpg", 2000);

        Directory media = new Directory("Media");
        media.add(f1);
        media.add(f2);
        media.add(f3);

        Directory docs = new Directory("Documents");
        docs.add(new File("report.docx", 800));
        docs.add(new File("notes.txt", 300));

        Directory root = new Directory("Root");
        root.add(media);
        root.add(docs);

        root.display("");
        System.out.println("\nZhalpy olshem: " + root.getSize() + "KB");
    }
}
