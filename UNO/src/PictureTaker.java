import com.github.sarxos.webcam.Webcam;

import java.awt.image.BufferedImage;

public class PictureTaker {
    Webcam webcam = Webcam.getDefault();

    PictureTaker() {
        webcam.open();
    }

    public BufferedImage capture() {
        return webcam.getImage();
    }
}
