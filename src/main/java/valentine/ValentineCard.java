package valentine;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

public class ValentineCard extends Application {

    private GridPane grid = new GridPane();
    private Label message = new Label("");

    @Override
    public void start(Stage stage) throws Exception {
        SVGPath heart = new SVGPath();
        heart.setContent("M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 "
                + "2 6 4 4 6.5 4 8 4 9.5 4.8 10.4 6.09 "
                + "11.3 4.8 12.8 4 14.3 4 16.8 4 18.8 6 "
                + "18.8 8.5 18.8 12.28 15.4 15.36 10.25 20.04z");

        heart.setFill(Color.RED);

        ScaleTransition pulse = new ScaleTransition(Duration.seconds(0.9), heart);
        pulse.setFromX(10);
        pulse.setFromY(10);
        pulse.setToX(11);
        pulse.setToY(11);
        pulse.setAutoReverse(true);
        pulse.setCycleCount(ScaleTransition.INDEFINITE);
        pulse.play();


        Button openB = new Button("Открыть");
        openB.setStyle("""
                -fx-font-size: 16px;
                -fx-background-radius: 20;
                -fx-background-color: #ff6b81;
                -fx-text-fill: white;
                -fx-padding: 8 20 8 20;
                """);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        message.setWrapText(true);
        message.setMaxWidth(600);
        message.setStyle("-fx-font-size: 16px; -fx-text-alignment: center;");
        message.setAlignment(Pos.CENTER);
        message.setTextAlignment(TextAlignment.CENTER);

        openB.setOnAction(e -> {

            openB.setDisable(true);
            showPhotosThenText();
        });

        VBox root = new VBox(70, heart, openB, grid, message);
        root.setAlignment(Pos.CENTER);
        root.setStyle("""
                -fx-background-color: linear-gradient(to bottom, #fff0f5, #ffe4e1);
                -fx-padding: 25;
                """);
        stage.setTitle("❤❤❤");
        stage.setScene(new Scene(root, 600, 800));
        stage.show();
    }

    private void showPhotosThenText() {
        List<ImageView> images = new ArrayList<>();
        for(int i = 1; i <= 12; i++){
            Image img = new Image(getClass().getResourceAsStream("/photos/" + i + ".JPG"));
            ImageView view = new ImageView(img);
            view.setFitHeight(120);
            view.setFitWidth(120);
            view.setPreserveRatio(true);
            view.setOpacity(0);

            images.add(view);
        }
        Timeline timeline = new Timeline();
        grid.getChildren().clear();
        for(int i = 0; i < images.size(); i++){
            ImageView view = images.get(i);
            int col = i % 4;
            int row = i / 4;
            grid.add(view, col, row);
            ImageView current = view;
            KeyFrame frame = new KeyFrame(
                    Duration.millis(300 * i),
                    e -> fadeIn(current)
            );
            timeline.getKeyFrames().add(frame);
        }
        timeline.setOnFinished(e -> showText());
        timeline.play();
    }

    private void showText() {
        message.setText("❤ С 14 февраля! ❤");
        FadeTransition fade = new FadeTransition(Duration.seconds(2), message);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    private void fadeIn(ImageView view) {
        FadeTransition ft = new FadeTransition(Duration.seconds(2), view);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
