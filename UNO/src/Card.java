import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    Card(String pictureName) {
        try {
            File imageFile = new File("UNO/Res/" + pictureName);
            this.image = ImageIO.read(imageFile);
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
                if (secondVal.equals("pick"))
                    drawFour = true;
                colorChange = true;
            }
        }
    }

    public void changeColor(String color) {
        if (color != null) {
            if (isWild && colorChange) {
                this.color = color;
                this.number = 0;
                this.isWild = false;
                this.colorChange = false;
                this.drawFour = false;
                colorChange = false;
                try {
                    File imageFile = new File("UNO/Res/" + color + "_0.png");
                    this.image = ImageIO.read(imageFile);
                } catch (IOException | NullPointerException ignored) {

                }
            }
        }
    }
}
