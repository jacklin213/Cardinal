package tk.jacklin213.cardinal.command;


public abstract class Command {

	private CommandSender sender;
	private String name;
	private boolean opCommand = false;
	
	public Command(String name, boolean opCommand) {
		this.name = name;
		this.opCommand = opCommand;
	}
	
	
	public void run(CommandSender sender, String[] args) {
		this.sender = sender;
		if (this.opCommand && !sender.isOp()) return;
		execute(sender, args);
	}
	
	/**
	 * Should only be used in command class directly, see {@link #run(CommandSender, String[])} 
	 * to run a command
	 * @param sender
	 * @param args
	 */
	abstract protected void execute(CommandSender sender, String[] args);

	public CommandSender getCommandSender() {
		return sender;
	}

	public String getName() {
		return name;
	}

	public boolean isOpCommand() {
		return opCommand;
	}
	
	
}
