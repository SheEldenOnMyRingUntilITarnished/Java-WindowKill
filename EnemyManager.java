import java.util.ArrayList;

public class EnemyManager extends Object
{
    private Player player = null;
    private GameSystem gameSystem = null;
    
    private Timer spawnTimer = new Timer();
    private int startingSpawnCooldown = 360; //Probably should update this so its not frame based
    
    public EnemyManager(Player chosenPlayer)
    {
        super(0,0);
        this.player = chosenPlayer;
        if (chosenPlayer != null) {
            this.gameSystem = chosenPlayer.gameSystem;
        }
        spawnTimer.setTimer(startingSpawnCooldown);
    }
    
    public void setGameSystem(GameSystem chosenGameSystem)
    {
        this.gameSystem = chosenGameSystem;
    }
    
    @Override
    public void awake()
    {
        
    }
    
    @Override
    public void update()
    {
        if (player == null) return;
        if (gameSystem == null && player.gameSystem != null) {
            this.gameSystem = player.gameSystem;
        }
        if (gameSystem == null) return;
        
        int targetX = player.getXPosition();
        int targetY = player.getYPosition();
        
        for (int i = 0; i < gameSystem.objects.size(); i++)
        {
            if (gameSystem.objects.get(i) instanceof Enemy)
            {
                Enemy enemy = (Enemy) gameSystem.objects.get(i);
                enemy.updateTargetPosition(targetX, targetY);
            }
        }
        
        spawnTimer.update();
        
        if (spawnTimer.timerHasPassed())
        {
            double currentFrame = gameSystem.getGameTimeFrames();
            int spawnCooldown = startingSpawnCooldown;
            spawnCooldown = Math.toIntExact(Math.round(startingSpawnCooldown * 1/Math.exp(currentFrame/3600)));
            spawnTimer.setTimer(spawnCooldown);
            Enemy newEnemy = SpawnEnemy();
            if (newEnemy != null) {
                gameSystem.objects.add(newEnemy);
            }
        }
    }
    
    public Enemy SpawnEnemy()
    {
        if (gameSystem == null && player != null) {
            gameSystem = player.gameSystem;
        }
        
        EnemyTypes chosenType;
        double rand = Math.random();
        int currentFrame = gameSystem.getGameTimeFrames();
        
        
        
        if(rand < 0.1 && currentFrame > 3600) chosenType = EnemyTypes.SQUARE;
        else if(rand < 0.3 && currentFrame > 1800) chosenType = EnemyTypes.TRIANGLE;
        else chosenType = EnemyTypes.CIRCLE; //Easyist enemy
        
        return createEnemy(chosenType);
    }
    
    private Enemy createEnemy(EnemyTypes chosenType)
    {
        EnemyStats stats;
        //EnemyStats(Health, Speed, AttackRate(only used by enemys i have not added yet :( ))
        if (chosenType == EnemyTypes.SQUARE) {
            stats = new EnemyStats(4, 2.0, 1.0);
        } else if (chosenType == EnemyTypes.TRIANGLE) {
            stats = new EnemyStats(3, 2.5, 1.0);
        } else {
            stats = new EnemyStats(2, 1.0, 1.0);
        }
        
        EnemyAI ai = new EnemyAI();
        Enemy enemy = new Enemy(chosenType, stats, ai, gameSystem);
        
        
        if (gameSystem != null && !gameSystem.activeWindows.isEmpty())
        {
            WindowArea win = gameSystem.activeWindows.get(0);
            int winX = win.getWindowXPosition();
            int winY = win.getWindowYPosition();
            int winW = win.getWindowWidth();
            int winH = win.getWindowHeight();
            
            int side = (int)(Math.random() * 4);
            int spawnX = winX;
            int spawnY = winY;
            
            if (side == 0) { // Top
                spawnX = winX + (int)(Math.random() * winW);
                spawnY = winY - 30;
            } else if (side == 1) { // Bottom
                spawnX = winX + (int)(Math.random() * winW);
                spawnY = winY + winH + 30;
            } else if (side == 2) { // Left
                spawnX = winX - 30;
                spawnY = winY + (int)(Math.random() * winH);
            } else { // Right
                spawnX = winX + winW + 30;
                spawnY = winY + (int)(Math.random() * winH);
            }
            
            enemy.setPosition(spawnX, spawnY);
        }
        else if (player != null)
        {
            enemy.setPosition(player.getXPosition() + 200, player.getYPosition() + 200);
        }
        
        return enemy;
    }
}