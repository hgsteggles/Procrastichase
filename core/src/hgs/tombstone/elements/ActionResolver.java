package hgs.tombstone.elements;

/**
 * Created by harry on 24/04/16.
 */
public interface ActionResolver {
	boolean getSignedInGPGS();
	void loginGPGS();
	void submitScoreGPGS(int score);
	void unlockAchievementGPGS(String achievementId);
	void getLeaderboardGPGS();
	void getAchievementsGPGS();
}
