import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Card {
    int number;
    String color;
    BufferedImage image;
    BufferedImage backImage;
    ArrayList<String> colors = new ArrayList<>();

    Card(String pictureName, BufferedImage image) {
        colors.add("red");
        colors.add("blue");
        colors.add("green");
        colors.add("yellow");
        this.image = image;
        try {
            File newFile = new File("UNO/Res/card_back_alt.png");
            backImage = ImageIO.read(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] strs = pictureName.split("\\.");
        String[] vals = strs[0].split("_");
        if (colors.contains(vals[0]))
            color = vals[0];
        try {
            number = Integer.parseInt(vals[1]);
        } catch (NumberFormatException | NullPointerException nfe) {
            number = -1;
        }
    }

    public static void main(String[] args) {
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<String> colors = new ArrayList<>();
        colors.add("red");
        colors.add("blue");
        colors.add("green");
        colors.add("yellow");
        try (Stream<Path> walk = Files.walk(Paths.get("UNO/Res/"))) {

            List<String> result = walk.map(Path::toString)
                    .filter(f -> f.endsWith(".png")).collect(Collectors.toList());

            for (String path : result) {
//                System.out.println(str);
                File newFile = new File(path);
                BufferedImage nextImage = ImageIO.read(newFile);
                cards.add(new Card(newFile.getName(), nextImage));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Card card : cards) {
            System.out.print(card.color + " " + card.number);
            System.out.println();
        }
    }
}
