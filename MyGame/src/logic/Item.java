package logic;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;
import window.SceneManager;

public class Item extends CollidableEntity{

	public int itemTick=0;
	private AudioClip item_sound = new AudioClip(ClassLoader.getSystemResource("powerup_pick.wav").toString());

	public Item() {
		Random rand= new Random();
		this.x=rand.nextInt(SceneManager.SCENE_WIDTH-12);
		this.y=rand.nextInt(SceneManager.SCENE_HEIGHT-12);
		this.setImage(new Image("Armor.png"));
	}
	public void update() {
		checkGetItem();
	}
	private void checkGetItem() {
		this.itemTick++;
		if(this.itemTick>=600) { 
			  this.destroyed=true;
			  this.itemTick=0;
			}
		else {
			//System.out.println(this.x+"    "+this.y);
			for (IRenderable i : RenderableHolder.getInstance().getEntities()) {
				if (this.collideWith((CollidableEntity) i)) {
					//System.out.println(this.x+"    "+this.y);
					if (i instanceof Plane) {
						item_sound.play();
						this.destroyed = true;
						this.itemTick=0;
						((Plane) i).increaseHp();
					}
				}
			}
		}
	}
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.setGlobalAlpha(1);
		gc.drawImage(this.image, x, y);
	}

}
