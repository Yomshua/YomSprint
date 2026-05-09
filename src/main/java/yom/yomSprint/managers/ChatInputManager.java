package yom.yomSprint.managers;

import org.bukkit.entity.Player;
import yom.yomSprint.models.ChatInput;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatInputManager {

    private List<ChatInput> chatInputs = new ArrayList<>();

    public List<ChatInput> getChatInputs() {
        return chatInputs;
    }

    public ChatInput getChatInput(Player player){
        for(ChatInput input : chatInputs){
            if (input.getUuid().equals(player.getUniqueId())) return input;
        }
        return null;
    }

}
