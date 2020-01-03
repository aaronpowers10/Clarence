package clarence.test_driver;

public interface ApplicationState {
	
	public void command(String command) throws InvalidCommandException;
	public void reload();

}
