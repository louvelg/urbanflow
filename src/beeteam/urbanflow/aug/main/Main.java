package beeteam.urbanflow.aug.main;

import beeteam.urbanflow.BeeTeamData;
import beeteam.urbanflow.fte.GameEngine.GameMode;

public class Main {

	public static void main(String[] args) {
		new UrbanFlow().gameLoop(GameMode.TRAINING, BeeTeamData.BOT_TOKEN, BeeTeamData.BOT_SECRET);
	}

}
