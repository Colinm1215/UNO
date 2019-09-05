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
    boolean drawTwo = false;
    boolean reverse = false;
    boolean skip = false;
    boolean drawFour = false;
    boolean colorChange = false;
    boolean isWild = false;

    Card(String pictureName, BufferedImage image) {
        this.image = image;
        try {
            File newFile = new File("UNO/Res/card_back_alt.png");
            backImage = ImageIO.read(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] strs = pictureName.split("\\.");
        String[] vals = strs[0].split("_");
        color = vals[0];
        if (color.equals("wild"))
            isWild = true;
        try {
            number = Integer.parseInt(vals[1]);
        } catch (NumberFormatException | NullPointerException nfe) {
            number = -1;
            if (!color.equals("wild")) {
                String secondVal = vals[1];
                switch (secondVal) {
                    case "picker":
                        drawTwo = true;
                        break;
                    case "reverse":
                        reverse = true;
                        break;
                    case "skip":
                        skip = true;
                        break;
                }
            } else {
                String secondVal = vals[1];
                switch (secondVal) {
                    case "pick":
                        drawFour = true;
                        break;
                    case "color":
                        colorChange = true;
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<String> colors = new ArrayList<>();
        colors.add("red");
        colors.add("blue");
        colors.add("green");
        colors.add("yellow");
        colors.add("wild");
        try (Stream<Path> walk = Files.walk(Paths.get("UNO/Res/"))) {

            List<String> result = walk.map(Path::toString)
                    .filter(f -> f.endsWith(".png")).collect(Collectors.toList());

            for (String path : result) {
                File newFile = new File(path);
                String[] fls = newFile.getName().split("_");
                if (colors.contains(fls[0])) {
                    BufferedImage nextImage = ImageIO.read(newFile);
                    cards.add(new Card(newFile.getName(), nextImage));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
