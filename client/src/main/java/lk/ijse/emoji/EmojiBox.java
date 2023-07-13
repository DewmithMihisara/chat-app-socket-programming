package lk.ijse.emoji;

import com.vdurmont.emoji.EmojiParser;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class EmojiBox extends VBox {
    private final ListView<String> emojiListView;
   public EmojiBox(){
        List<String> emojis = new ArrayList<>();

        String[] emojiHtmlList = new String[]{"&#128514;","&#10084;","&#128525;","&#129315;","&#128522;",
                "&#128591;","&#128149;","&#128557;","&#128293;","&#128536;","&#128077;","&#129392;","&#128526;","&#128518;",
                "&#128513;","&#128521;","&#129300;","&#128517;","&#128532;","&#128580;","&#128540;","&#9829;","&#9851;","&#128530;",
                "&#128553;","&#9786;","&#128513;","&#128076;","&#128079;","&#128148;","&#128150;","&#128153;",
                "&#128546;","&#128170;","&#129303;","&#128156;","&#128526;","&#128519;","&#127801;","&#129318;",
                "&#127881;","&#128158;","&#9996;","&#10024;","&#129335;","&#128561;","&#128524;","&#127800;",
                "&#128588;","&#128523;","&#127770;","&#127773;","&#128584;","&#128585;","&#128586;"};
        for (String em:emojiHtmlList) {
            emojis.add(EmojiParser.parseToUnicode(em));
        }
        emojiListView = new ListView<>();
        emojiListView.setItems(FXCollections.observableArrayList(emojis));
        HBox hBox = new HBox(emojiListView);
        hBox.setPadding(new Insets(10));
        getChildren().add(hBox);

    }
    public ListView<String> getEmojiListView() {
        return emojiListView;
    }
}
