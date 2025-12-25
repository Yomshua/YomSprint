package yom.yomSprint.commands.managers;

import yom.yomSprint.YomSprint;
import yom.yomSprint.commands.*;


public class CommandsManager{

    YomSprint plugin;
    CommandExecutorBase base;

    public CommandsManager(YomSprint plugin){
        this.plugin = plugin;
        base = plugin.getBase();
        loadSubCommands();
    }

    private void loadSubCommands(){
        base.add(new CreateTrackSubCommand(plugin));
        base.add(new SetWaitLobbyCommand(plugin));
        base.add(new LeaveTrackCommand(plugin));
        base.add(new SetMainLobbyCommand(plugin));
        base.add(new AddLanesCommand(plugin));
        base.add(new ReloadCommand(plugin));
        base.add(new SetLanesLengthCommand(plugin));
        base.add(new LobbyCommand(plugin));
    }
}
