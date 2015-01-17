package beeteam.urbanflow;

import beeteam.urbanflow.fte.GameEngine.GameMode;

public class Main {
	
	

    public static void main(String[] args) {
	
    	new UrbanFlow().gameLoop(GameMode.ARENA, BeeTeamData.BOT_TOKEN, BeeTeamData.BOT_SECRET);
    }

}
