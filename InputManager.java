
import java.awt.MouseInfo;
import java.awt.PointerInfo;

/**
 * Write a description of class InputManager here.
 *
 * @author Zachary Quinn
 * @version 05/18/2026
 */
public class InputManager extends Object
{
    KeyHandler keyH = new KeyHandler();
    MouseHandler mouseH = new MouseHandler();
    private Player player = null;
    
    public InputManager(Player player)
    {
        super(0,0);
        this.player = player;
    }
    
    @Override
    public void update()
    {
        PointerInfo currentMouse = MouseInfo.getPointerInfo();
        
        //mouse checks for UI
        if (player != null && player.gameSystem != null) {
            for (int i = 0; i < player.gameSystem.objects.size(); i++) {
                if (i < player.gameSystem.objects.size() && player.gameSystem.objects.get(i) instanceof UI_Element) {
                    UI_Element ui = (UI_Element) player.gameSystem.objects.get(i);
                    ui.mouseChecks(currentMouse);
                    
                    if (mouseH.shootPressed && ui instanceof Button) {
                        Button btn = (Button) ui;
                        if (btn.isHovered()) {
                            mouseH.shootPressed = false; // consume click
                            btn.triggerClick();
                        }
                    }
                }
            }
        }
        
        if (player != null && player.gameSystem != null) {
            GameState state = player.gameSystem.gameState;
            
            // Player controls only active during GAME state :)
            if (state == GameState.GAME) {
                // Movement
                if(keyH.upPressed)
                {
                    this.player.acceleratePlayerY(-this.player.playerStats.playerAcceleration);
                }
                if(keyH.downPressed)
                {
                    this.player.acceleratePlayerY(this.player.playerStats.playerAcceleration);
                }
                if(keyH.leftPressed)
                {
                    this.player.acceleratePlayerX(-this.player.playerStats.playerAcceleration);
                }
                if(keyH.rightPressed)
                {
                    this.player.acceleratePlayerX(this.player.playerStats.playerAcceleration);
                }
                
                // Shooting
                if(mouseH.shootPressed)
                {
                    this.player.attemptShoot(currentMouse);
                }
            }
        }
    }
}