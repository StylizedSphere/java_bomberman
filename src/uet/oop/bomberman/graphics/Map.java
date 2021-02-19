package uet.oop.bomberman.graphics;

public class Map {
    public static final char PLAYER     = 'p';
    public static final char WALL       = '#';
    public static final char BRICK      = '*';
    public static final char GRASS      = ' ';
    public static final char BOMB_ITEM  = '@';
    public static final char FLAME_ITEM = '!';
    public static final char WALL_ITEM  = '%';
    public static final char PORTAL     = 'x';

    public static final char BALLOON    = '1';
    public static final char ONEAL      = '2';


    public static final int WIDTH = 31;
    public static final int HEIGHT = 15;

    public static String[] level1 = {
            "###############################\n",
            "#p     !% !  1 @ 2 *  ! * *   #\n",
            "# # # #@# # #@#@# # # #*#*#*# #\n",
            "#  x*     @@*  *  2   * 2 * @ #\n",
            "# # # # # #*# # #*#*# # # # #*#\n",
            "#f         @ **  *  !   2     #\n",
            "# # # # # # # # # #*# #*# # # #\n",
            "#@  @      !  *      *        #\n",
            "# # # # #*# # # #*#*# # # # # #\n",
            "#*    @*  @       *           #\n",
            "# #*# # # # # # #*# # # # # # #\n",
            "#           *   *  *          #\n",
            "# #*# # # # # # #*# # # # # # #\n",
            "#           @   *  *          #\n",
            "###############################\n"
    };

    public static String[] level2 = {
            "###############################\n",
            "#p     @@ !  1 ! 2 *  ! * *   #\n",
            "# # # #@# # #*#*# # # #*#*#*# #\n",
            "#  %     ***  @  1   ! 2 * *! #\n",
            "#########       ###############\n",
            "#f         ! **  *  *   1     #\n",
            "######       # ## ## ##########\n",
            "#*  @     #*  *      !        #\n",
            "# # # # #*# # # #*#*# # # # # #\n",
            "#*    @@ #*       @           #\n",
            "# #*# # # # # # #*# # # # # # #\n",
            "#  @*     !!!  !  1   * 2 * * #\n",
            "# #*# # # # # # #*# # # # # # #\n",
            "#    #  #     *   *  *        #\n",
            "###############################\n"
    };

    public static boolean isWall(String[] level, int i, int j) {
        return level[j].charAt(i) == WALL;
    }
    public static boolean isBrick(String[] level, int i, int j) {
        return level[j].charAt(i) == BRICK;
    }
    public static boolean isBalloon(String[] level, int i, int j) {
        return level[j].charAt(i) == BALLOON;
    }
    public static boolean isPortal(String[] level, int i, int j) {
        return level[j].charAt(i) == PORTAL;
    }
    public static boolean isOneal(String[] level, int i, int j) {
        return level[j].charAt(i) == ONEAL;
    }
    public static boolean isFlameItem(String[] level, int i, int j) {
        return level[j].charAt(i) == FLAME_ITEM;
    }
    public static boolean isBombItem(String[] level, int i, int j) {
        return level[j].charAt(i) == BOMB_ITEM;
    }
    public static boolean isWallItem(String[] level, int i, int j) {
        return level[j].charAt(i) == WALL_ITEM;
    }


}
