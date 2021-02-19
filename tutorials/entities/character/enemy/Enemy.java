package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.Sound.Sound;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.ai.AI;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

import java.awt.*;

public abstract class Enemy extends Character {

	protected int _points;
	
	protected double _speed;
	protected AI _ai;

	protected final double MAX_STEPS;
	protected final double rest;
	protected double _steps;
	
	protected int _finalAnimation = 30;
	protected Sprite _deadSprite;
	
	public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
		super(x, y, board);
		
		_points = points;
		_speed = speed;
		
		MAX_STEPS = Game.TILES_SIZE / _speed;
		rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
		_steps = MAX_STEPS;
		
		_timeAfter = 20;
		_deadSprite = dead;
	}
	
	@Override
	public void update() {
		animate();
		
		if(!_alive) {
			afterKill();
			return;
		}
		
		if(_alive)
			calculateMove();
	}
	
	@Override
	public void render(Screen screen) {
		
		if(_alive)
			chooseSprite();
		else {
			if(_timeAfter > 0) {
				_sprite = _deadSprite;
				_animate = 0;
			} else {
				_sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
			}
				
		}
			
		screen.renderEntity((int)_x, (int)_y - _sprite.SIZE, this);
	}
	
	@Override
	public void calculateMove() {
		// TODO: Tính toán hướng đi và di chuyển Enemy theo _ai và cập nhật giá trị cho _direction
		// TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không
		// TODO: sử dụng move() để di chuyển
		// TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
		int xa = 0, ya = 0;
		if(_steps <= 0){
			_direction = _ai.calculateDirection();
			_steps = MAX_STEPS;
		}
		if(_direction==-1);
		if(_direction == 0) ya--; // MOVE UP
		if(_direction == 2) ya++; //MOVE DOWN
		if(_direction == 3) xa--; //MOVE LEFT
		if(_direction == 1) xa++; // MOVE RIGHT

		if(canMove(xa, ya)) {
			_steps -= 1 + rest;
			move(xa * _speed, ya * _speed);
			_moving = true;
		} else {
			_steps = 0;
			_moving = false;
		}
	}
	
	@Override
	public void move(double xa, double ya) {
		if(!_alive) return;
		_y += ya;
		_x += xa;
	}
	
	@Override
	public boolean canMove(double x, double y) {
		double xl = _x, yl = _y -Game.TILES_SIZE;
		boolean allowToMove=false;
		//Chuẩn hóa tọa độ của enemy khi di chuyển
		switch (_direction){
			case 0:{//move up
				yl += _sprite.getSize() -1 ;
				xl += _sprite.getSize()/2;
				break;
			}
			case 1:{//move right
				yl += _sprite.getSize()/2;
				xl += 1;

				break;
			}
			case 2:{//move down
				xl += _sprite.getSize()/2;
				yl += 1;
				break;
			}
			default:{//move left
				xl += _sprite.getSize() -1;
				yl += _sprite.getSize()/2;
				break;
			}
		}
		// Kiểm tra xem điểm đến có bị chặn không
		int desX = Coordinates.pixelToTile(xl) +(int)x;
		int desY = Coordinates.pixelToTile(yl) +(int)y;
		for (int i=0;i<desY;i++){
			for (int j=0;j< desX;j++){
				Entity ent= _board.getEntity(desX, desY, this);
				if (ent instanceof Grass){
					allowToMove=true;
				}
			}
		}
		Entity destinationEnity = _board.getEntity(desX, desY, this); //entity of the position we want to go
		return destinationEnity.collide(this);
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý va chạm với Flame
		// TODO: xử lý va chạm với Bomber
		if (e instanceof FlameSegment){
			kill();
			return false;
		}
		if (e instanceof Flame){
			kill();
			return true;
		}
		if (e instanceof Bomber){
			((Bomber) e).kill();
			return false;
		}
		return false;
	}
	
	@Override
	public void kill() {
		if(!_alive) return;
		_alive = false;
		_board.addPoints(_points);

		Message msg = new Message("+" + _points, getXMessage(), getYMessage(), 2, Color.white, 14);
		_board.addMessage(msg);
		Sound.mob_death();
	}
	
	
	@Override
	protected void afterKill() {
		if(_timeAfter > 0) --_timeAfter;
		else {
			if(_finalAnimation > 0) --_finalAnimation;
			else
				remove();
		}
	}
	
	protected abstract void chooseSprite();
}
