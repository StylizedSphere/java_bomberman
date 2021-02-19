package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AIMedium extends AI {
	Bomber _bomber;
	Enemy _e;

	public AIMedium(Bomber bomber, Enemy e) {
		_bomber = bomber;
		_e = e;
	}

	public int verticalMoving(){
		if ((_e.getXTile()==_bomber.getXTile()) && (_e.getYTile() == _bomber.getYTile())){
			return -1;
		}
		if (_e.getYTile() > _bomber.getYTile()){
			return 0;
		}
		else if (_e.getYTile() < _bomber.getYTile()){
			return 2;
		}
		return horizontalMoving();
	}
	public int horizontalMoving(){
		if ((_e.getXTile()==_bomber.getXTile()) && (_e.getYTile() == _bomber.getYTile())){
			return -1;
		}
		if (_e.getXTile()>_bomber.getXTile()){
			return 3;
		}
		else if(_e.getXTile()<_bomber.getXTile()) {
			return 1;
		}
		return verticalMoving();
	}
	@Override
	public int calculateDirection() {
		if (random.nextInt(2)==0){
			return horizontalMoving();
		}
		return verticalMoving();
	}
}
