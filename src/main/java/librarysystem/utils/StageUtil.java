package librarysystem.utils;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class StageUtil
{
    private static final Image APP_ICON = new Image(Objects.requireNonNull(StageUtil.class.getResourceAsStream("/app_icon/library.png")));

    public static void setAppIcon(Stage stage)
    {
        stage.getIcons().add(APP_ICON);
    }
}
