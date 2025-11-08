package yom.yomSprint.commands;

import yom.yomSprint.YomSprint;


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
    }
}
