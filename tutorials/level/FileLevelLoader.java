package uet.oop.bomberman.level;

import sun.util.resources.cldr.bas.CalendarData_bas_CM;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.*;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.Live;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map;
	private int bbx;
	private int bby;
	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}
	
	@Override
	public void loadLevel(int level) {
		// TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
		// TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map
		if (level>3){
			_board.endGame();
		}
		String[] symbolMap;
		try {
			URL path = FileLevelLoader.class.getResource("/levels/Level" + level + ".txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(path.openStream()));
			String data = in.readLine();
			StringTokenizer tokens = new StringTokenizer(data);

			_level = Integer.parseInt(tokens.nextToken());
			_height = Integer.parseInt(tokens.nextToken());
			_width = Integer.parseInt(tokens.nextToken());
			symbolMap = new String[_height];
			_map = new char[_height][_width];
			for(int i = 0; i < _height; ++i) {
				symbolMap[i] = in.readLine().substring(0, _width);
			}
			for(int i = 0; i < _height; ++i) {
				for (int j=0;j<_width;j++){
					_map[i][j]=symbolMap[i].toCharArray()[j];
				}
			}
			in.close();
		} catch (IOException e) {
			//throw new LoadLevelException("Error loading level "  , e);
			e.printStackTrace();
		}
	}
	public void createBomber(){
		_board.addCharacter(new Bomber(bbx,bby+Game.TILES_SIZE,_board));
	}
	@Override
	public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game
		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
		for (int x = 0; x < _width; x++) {
			for (int y = 0; y < _height; y++) {
				int pos = x + y * _width;
				switch (_map[y][x]){
					case 'p':{
						bby=y;
						bbx=x;
						_board.addCharacter(new Bomber(Coordinates.tileToPixel(x),Coordinates.tileToPixel(y)+Game.TILES_SIZE,_board));
						Screen.setOffset(0, 0);
						_board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
						break;
					}
					default:{
						_board.addEntity(pos, new Grass(x, y, Sprite.grass));
						break;
					}
					case '#':{
						_board.addEntity(pos,new Wall(x,y,Sprite.wall));
						break;
					}
					case '*':{
						_board.addEntity(pos, new LayeredEntity(x,y,
								new Grass(x,y,Sprite.grass),
								new Brick(x,y,Sprite.brick)
						));
						break;
					}
					case 'b':{
						_board.addEntity(pos, new LayeredEntity(x,y,
								new Grass(x,y,Sprite.grass),
								new BombItem(x,y,Sprite.powerup_bombs),
								new Brick(x,y,Sprite.brick)
						));
						break;
					}
					case 'f':{
						_board.addEntity(pos, new LayeredEntity(x,y,
								new Grass(x,y,Sprite.grass),
								new FlameItem(x,y,Sprite.powerup_flames),
								new Brick(x,y,Sprite.brick)
						));
						break;
					}
					case 's':{
						_board.addEntity(pos, new LayeredEntity(x,y,
								new Grass(x,y,Sprite.grass),
								new SpeedItem(x,y,Sprite.powerup_speed),
								new Brick(x,y,Sprite.brick)
						));
						break;
					}
					case 'l':{
						_board.addEntity(pos, new LayeredEntity(x,y,
								new Grass(x,y,Sprite.grass),
								new Live(x,y,Sprite.powerup_detonator),
								new Brick(x,y,Sprite.brick)
						));
						break;
					}
					case 'x':{
						_board.addEntity(pos,new LayeredEntity(x,y,
								new Grass(x,y,Sprite.grass),
								new Portal(x,y,Sprite.portal,_board)
						));
						break;
					}
					case '1':{
						_board.addCharacter(new Balloon(Coordinates.tileToPixel(x),Coordinates.tileToPixel(y)+Game.TILES_SIZE,_board));
						_board.addEntity(pos,new Grass(x,y,Sprite.grass));
						break;
					}
					case '2':{
						_board.addCharacter(new Oneal(Coordinates.tileToPixel(x),Coordinates.tileToPixel(y)+Game.TILES_SIZE,_board));
						_board.addEntity(pos,new Grass(x,y,Sprite.grass));
						break;
					}
					case '3':{
						_board.addCharacter(new AntiBrick(Coordinates.tileToPixel(x),Coordinates.tileToPixel(y)+Game.TILES_SIZE,_board));
						_board.addEntity(pos,new Grass(x,y,Sprite.grass));
						break;
					}
					case '4':{
						_board.addCharacter(new Doll(Coordinates.tileToPixel(x),Coordinates.tileToPixel(y)+Game.TILES_SIZE,_board));
						_board.addEntity(pos,new Grass(x,y,Sprite.grass));
						break;
					}
					case '5':{
						_board.addCharacter(new Minvo(Coordinates.tileToPixel(x),Coordinates.tileToPixel(y)+Game.TILES_SIZE,_board));
						_board.addEntity(pos,new Grass(x,y,Sprite.grass));
						break;
					}
				}
			}
		}

	}

}
