import java.util.ArrayList;

/**
 * Write a description of class EnemyManager here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class EnemyManager
{
    public Enemy SpawnEnemy()
    {
        Object testEnemyObject = new Object(0,0,10,10);
        EnemyTypes testEnemyType = EnemyTypes.SQUARE;//Sets up the enemyType
        EnemyStats testEnemyStats = new EnemyStats(1,1,1);//Sets up the stats of the enemy
        EnemyAI testEnemyAI = new EnemyAI();//Sets up a AI
        Enemy testEnemy = new Enemy(testEnemyObject,testEnemyType,testEnemyStats,testEnemyAI);
        return testEnemy;
    }
}