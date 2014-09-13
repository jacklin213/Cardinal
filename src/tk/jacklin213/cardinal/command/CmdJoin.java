package tk.jacklin213.cardinal.command;


public class CmdJoin extends Command {

	private static final String NAME = "join";
	private static final boolean OPCMD = true;

	public CmdJoin() {
		super(NAME, OPCMD);
	}

	@Override
	protected void execute(CommandSender sender, String[] args) {
		if (args.length == 1) {
			sender.getBotInvolved().getBot().sendIRC().joinChannel(args[0]);
		}
	}
}
