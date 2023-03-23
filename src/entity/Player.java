package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public int hasKey = 0;
	
	int counter2 = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2-(gp.tileSize/2);
		screenY = gp.screenHeight/2 -(gp.tileSize/2);
		
		
		solidArea = new Rectangle(0,0,gp.tileSize,gp.tileSize);
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		
		speed = 6;
		direction = "down";
	}
	
	public void getPlayerImage() {
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/up2.png"));
			
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/down1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/down2.png"));
			
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
			
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
			
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	public void update() {
		
		if(keyH.upPrassed ==true || keyH.downPressed == true|| 
				keyH.leftPressed == true || keyH.rightPressed == true) {
			
		
		if(keyH.upPrassed == true) {
			direction = "up";
			
			
		}else if(keyH.downPressed == true) {
			direction = "down";
			
			
		}else if(keyH.leftPressed == true) {
			direction = "left";
			
			
		}else if(keyH.rightPressed == true) {
			direction = "right";
			
		}
		
		collisionOn = false;
		gp.cCheker.checkTile(this);
		
		
		int objIndex = gp.cCheker.checkObject(this, true);
		pickUpObject(objIndex);
		
		if(collisionOn == false) {
			switch(direction) {
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
			}
		}
		
		spriteCounter++;
		if(spriteCounter > 10) {
			if(spriteNum == 1) {
				spriteNum = 2;
			}else if(spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}		
}
	public void pickUpObject(int i) {
		
		if(i != 999) {
			String objectName = gp.obj[i].name;
			switch(objectName) {
			case"Key":
				gp.playSE(1);
				hasKey++;
				gp.obj[i] = null;
				gp.ui.showMessage("+ Key!");
				break;
			case "Door": if(hasKey> 0) {
				gp.playSE(3);
				gp.obj[i] = null;
				hasKey--;
				gp.ui.showMessage("you opened th door!");
			}
			else {
				gp.ui.showMessage("need a key.");
			}
				System.out.println("Key:" + hasKey);
				break;
			case"Boots":
				gp.playSE(2);
				speed +=2;
				gp.obj[i] = null;
				gp.ui.showMessage("speed Up!");
				System.out.println("Boots");
				break;
			case"Chest":
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSE(4);
				break;
			}
		}
		
	}
	public void draw(Graphics g2) {
		
//			g2.setColor(Color.green);
//			g2.fillRect(x , y, gp.tileSize, gp.tileSize);
			BufferedImage image = null;
			
			switch(direction) {
			case "up":
				if(spriteNum ==1) {
					image = up1;	
				}if(spriteNum ==2) {
					image = up2;	
				}
				
				break;
			case "down":
				if(spriteNum ==1) {
					image = down1;	
				}if(spriteNum ==2) {
					image = down2;	
				}
				break;
			case "left":
				if(spriteNum ==1) {
					image = left1;	
				}if(spriteNum ==2) {
					image = left2;	
				}
				break;
			case "right":
				if(spriteNum ==1) {
					image = right1;	
				}if(spriteNum ==2) {
					image = right2;	
				}
				break;
			}
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
	
}
