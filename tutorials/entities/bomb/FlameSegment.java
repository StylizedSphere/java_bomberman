package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.entities.AnimatedEntitiy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;



public class FlameSegment extends AnimatedEntitiy {
	private int _direction;
	private boolean _last;

	/**
	 *
	 * @param x
	 * @param y
	 * @param direction
	 * @param last cho biết segment này là cuối cùng của Flame hay không,
	 *                segment cuối có sprite khác so với các segment còn lại
	 */
	public FlameSegment(int x, int y, int direction, boolean last) {
		_x = x;
		_y = y;
		_last = last;
		_direction=direction;
		switch (direction) {
			case 0:
				if(!last) {
					_sprite = Sprite.explosion_vertical2;
				} else {
					_sprite = Sprite.explosion_vertical_top_last2;
				}
			break;
			case 1:
				if(!last) {
					_sprite = Sprite.explosion_horizontal2;
				} else {
					_sprite = Sprite.explosion_horizontal_right_last2;
				}
				break;
			case 2:
				if(!last) {
					_sprite = Sprite.explosion_vertical2;
				} else {
					_sprite = Sprite.explosion_vertical_down_last2;
				}
				break;
			case 3: 
				if(!last) {
					_sprite = Sprite.explosion_horizontal2;
				} else {
					_sprite = Sprite.explosion_horizontal_left_last2;
				}
				break;
		}
	}
	
	@Override
	public void render(Screen screen) {
		switch (_direction) {
			case 0:
				if(!_last) {
					_sprite=Sprite.movingSprite(Sprite.explosion_vertical,Sprite.explosion_vertical1,Sprite.explosion_vertical2,_animate,20);
				} else {
					_sprite = Sprite.movingSprite(Sprite.explosion_vertical_top_last,Sprite.explosion_vertical_top_last1,Sprite.explosion_vertical_top_last2,_animate,20);
				}
				break;
			case 1:
				if(!_last) {
					_sprite =Sprite.movingSprite(Sprite.explosion_horizontal,Sprite.explosion_horizontal1, Sprite.explosion_horizontal2,_animate,20);
				} else {
					_sprite=Sprite.movingSprite(Sprite.explosion_horizontal_right_last,Sprite.explosion_horizontal_right_last1,Sprite.explosion_horizontal_right_last2,_animate,20);
				}
				break;
			case 2:
				if(!_last) {
					_sprite=Sprite.movingSprite(Sprite.explosion_vertical,Sprite.explosion_vertical1,Sprite.explosion_vertical2,_animate,20);
				} else {
					_sprite = Sprite.movingSprite(Sprite.explosion_vertical_down_last,Sprite.explosion_vertical_down_last1,Sprite.explosion_vertical_down_last2,_animate,20);

				}
				break;
			case 3:
				if(!_last) {
					_sprite =Sprite.movingSprite(Sprite.explosion_horizontal,Sprite.explosion_horizontal1, Sprite.explosion_horizontal2,_animate,20);
				} else {
					//_sprite = Sprite.explosion_horizontal_left_last2;
					_sprite=Sprite.movingSprite(Sprite.explosion_horizontal_left_last,Sprite.explosion_horizontal_left_last1,Sprite.explosion_horizontal_left_last2,_animate,20);
				}
				break;
		}
		int xt = (int)_x << 4;
		int yt = (int)_y << 4;

		screen.renderEntity(xt, yt , this);
	}
	
	@Override
	public void update() {animate();}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý khi FlameSegment va chạm với Character
		if (e instanceof Character){
			((Character) e).kill();
		}
		return true;
	}
	

}