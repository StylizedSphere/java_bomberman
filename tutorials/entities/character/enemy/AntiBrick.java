package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class AntiBrick extends Enemy{
	public AntiBrick(int x, int y, Board board) {
		super(x, y, board, Sprite.kondoria_dead, 0.4, 1000);
		
		_sprite = Sprite.kondoria_left1;
		
		_ai = new AIMedium(_board.getBomber(), this);
		_direction  = _ai.calculateDirection();
	}
	
	@Override
	protected void chooseSprite() {
		switch(_direction) {
			case 0:
			case 1:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, _animate, 60);
				else
					_sprite = Sprite.kondoria_left1;
				break;
			case 2:
			case 3:
				if(_moving)
					_sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, _animate, 60);
				else
					_sprite = Sprite.kondoria_right1;
				break;
		}
	}
	
	@Override
	public boolean canMove(double x, double y) {
		double xl = _x, yl = _y - Game.TILES_SIZE;
		// Chuẩn hóa tọa độ của enemy khi di chuyển
		switch (_direction) {
		case 0: {// move up
			yl += _sprite.getSize() - 1;
			xl += _sprite.getSize() / 2;
			break;
		}
		case 1: {// move right
			yl += _sprite.getSize() / 2;
			xl += 1;

			break;
		}
		case 2: {// move down
			xl += _sprite.getSize() / 2;
			yl += 1;
			break;
		}
		default: {// move left
			xl += _sprite.getSize() - 1;
			yl += _sprite.getSize() / 2;
			break;
		}
		}
		// Kiểm tra xem điểm đến có bị chặn không
		int desX = Coordinates.pixelToTile(xl) + (int) x;
		int desY = Coordinates.pixelToTile(yl) + (int) y;
		Entity destinationEnity = _board.getEntity(desX, desY, this);
//		Brick br = new Brick(1, 10, Sprite.brick );
//		System.out.println(br.toString());
//		System.out.println(destinationEnity.toString());
		if (destinationEnity instanceof LayeredEntity) return true;
		else return destinationEnity.collide(this);
	}
}
