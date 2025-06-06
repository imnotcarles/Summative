package summative;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;

public class PrimaryController {

    private Stage stage;
    private Image originalImage; // Use this to keep track of the original image

    @FXML
    private ImageView imageView;

    @FXML
    private MenuItem openImage;

    @FXML
    private MenuItem saveImage;

    @FXML
    private MenuItem restoreToOriginal;

    @FXML
    private MenuItem exit;

    @FXML
    private MenuItem horizontalFlip;

    @FXML
    private MenuItem verticalFlip;

    @FXML
    private MenuItem rotation;

    @FXML
    private MenuItem grayScale;

    @FXML
    private MenuItem sepiaTone;

    @FXML
    private MenuItem invertColor;

    @FXML
    private MenuItem brightness;

    @FXML
    private MenuItem bulge;

    @FXML
    private MenuItem colorOverlay;

    @FXML
    private MenuItem pixelation;

    @FXML
    private MenuItem vignette;

    @FXML
    void onOpenImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif"));

        try {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                originalImage = image;
                imageView.setImage(image);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Image Load Failed");
            alert.setContentText("There was a problem opening your image");
            alert.showAndWait();
        }
    }

    @FXML
    public void onSaveImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files", "*.png"));
        File file = fileChooser.showSaveDialog(imageView.getScene().getWindow());

        if (file != null) {
            WritableImage writableImage = imageView.snapshot(null, null);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Image Save Failed");
                alert.setContentText("There was a problem saving your image");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void onRestoreToOriginal(ActionEvent event) {
        imageView.setImage(originalImage);
    }

    @FXML
    public void onExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onHorizontalFlip(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                writer.setColor(width - i - 1, j, reader.getColor(i, j));
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onVerticalFlip(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                writer.setColor(i, height - j - 1, reader.getColor(i, j));
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onRotation(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(height, width);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                writer.setColor(j, width - i - 1, reader.getColor(i, j));
            }
        }

        imageView.setImage(writableImage);
    }

    @FXML
    void onGrayScale(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = reader.getColor(i, j);
                double gray = color.getRed() * 0.21 + color.getGreen() * 0.71 + color.getBlue() * 0.07;
                writer.setColor(i, j, new Color(gray, gray, gray, color.getOpacity()));
            }
        }

        imageView.setImage(writableImage);
    }

    @FXML
    void onSepiaTone(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = reader.getColor(i, j);
                double r = Math.min(1.0, 0.393 * color.getRed() + 0.769 * color.getGreen() + 0.189 * color.getBlue());
                double g = Math.min(1.0, 0.349 * color.getRed() + 0.686 * color.getGreen() + 0.168 * color.getBlue());
                double b = Math.min(1.0, 0.272 * color.getRed() + 0.534 * color.getGreen() + 0.131 * color.getBlue());
                writer.setColor(i, j, new Color(r, g, b, color.getOpacity()));
            }
        }

        imageView.setImage(writableImage);
    }

    @FXML
    void onInvertColor(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = reader.getColor(i, j);
                writer.setColor(i, j, new Color(
                        1 - color.getRed(),
                        1 - color.getGreen(),
                        1 - color.getBlue(),
                        color.getOpacity()));
            }
        }

        imageView.setImage(writableImage);
    }

    @FXML
    void onBrightness(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();
        double brightnessFactor = 0.2;

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = reader.getColor(i, j);
                double r = Math.min(1.0, color.getRed() + brightnessFactor);
                double g = Math.min(1.0, color.getGreen() + brightnessFactor);
                double b = Math.min(1.0, color.getBlue() + brightnessFactor);
                writer.setColor(i, j, new Color(r, g, b, color.getOpacity()));
            }
        }

        imageView.setImage(writableImage);
    }

    @FXML
    void onBulge(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();
        double cx = width / 2.0;
        double cy = height / 2.0;
        double p = 1.6;
        double s = 30.0;

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double dx = i - cx;
                double dy = j - cy;
                double r = Math.sqrt(dx * dx + dy * dy);
                double angle = Math.atan2(dy, dx);

                double rPrime = r * p / (s + r);
                int newX = (int) Math.round(cx + rPrime * Math.cos(angle));
                int newY = (int) Math.round(cy + rPrime * Math.sin(angle));

                if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                    writer.setColor(i, j, reader.getColor(newX, newY));
                } else {
                    writer.setColor(i, j, Color.BLACK);
                }
            }
        }

        imageView.setImage(writableImage);
    }

    @FXML
    void onColorOverlay(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();
        Color overlay = new Color(0.5, 0.0, 0.5, 1.0);

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = reader.getColor(i, j);
                double r = (color.getRed() + overlay.getRed()) / 2.0;
                double g = (color.getGreen() + overlay.getGreen()) / 2.0;
                double b = (color.getBlue() + overlay.getBlue()) / 2.0;
                writer.setColor(i, j, new Color(r, g, b, color.getOpacity()));
            }
        }

        imageView.setImage(writableImage);
    }

    @FXML
    void onPixelation(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();
        int blockSize = 10;

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i += blockSize) {
            for (int j = 0; j < height; j += blockSize) {
                Color color = reader.getColor(i, j);
                for (int dx = 0; dx < blockSize; dx++) {
                    for (int dy = 0; dy < blockSize; dy++) {
                        int px = i + dx;
                        int py = j + dy;
                        if (px < width && py < height) {
                            writer.setColor(px, py, color);
                        }
                    }
                }
            }
        }

        imageView.setImage(writableImage);
    }

    @FXML
    void onVignette(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();
        double cx = width / 2.0;
        double cy = height / 2.0;
        double maxDistance = Math.sqrt(cx * cx + cy * cy);
        double minFactor = 0.3;

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double distance = Math.sqrt((i - cx) * (i - cx) + (j - cy) * (j - cy));
                double factor = 1 - (distance / maxDistance);
                factor = Math.max(minFactor, factor);

                Color color = reader.getColor(i, j);
                double r = color.getRed() * factor;
                double g = color.getGreen() * factor;
                double b = color.getBlue() * factor;
                writer.setColor(i, j, new Color(r, g, b, color.getOpacity()));
            }
        }

        imageView.setImage(writableImage);
    }

    /*
     * Accessing a pixels colors
     * 
     * Color color = reader.getColor(x, y);
     * double red = color.getRed();
     * double green = color.getGreen();
     * double blue = color.getBlue();
     */

    /*
     * Modifying a pixels colors
     * 
     * Color newColor = new Color(1.0 - red, 1.0 - green, 1.0 - blue,
     * color.getOpacity());
     */

    // DO NOT REMOVE THIS METHOD!
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}