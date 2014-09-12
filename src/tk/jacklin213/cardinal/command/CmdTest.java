package tk.jacklin213.cardinal.command;

public class CmdTest extends Command {

	private static final String NAME = "test";
	private static final boolean OPCMD = true;
	
	public CmdTest() {
		super(NAME, OPCMD);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void execute(CommandSender sender, String[] args) {
		if (args.length == 0) {
			if (sender.isOp() || sender.isVoice()) {
				sender.broadcastMessage("Command recongised, Test works!");
			}
		}
	}

}
