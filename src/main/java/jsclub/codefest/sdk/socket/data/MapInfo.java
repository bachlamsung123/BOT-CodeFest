package jsclub.codefest.sdk.socket.data;

import com.google.gson.Gson;

import jsclub.codefest.sdk.constant.MapEncode;
import jsclub.codefest.sdk.model.Hero;
import java.util.ArrayList;
import java.util.List;

public class MapInfo {
    public String myId;
    public MapSize size;
    public List<Player> players;
    public List<int[]> map; // lay ra nhung o co dinh
    public List<Bomb> bombs;
    public List<Spoil> spoils;
    public List<Gift> gifts;
    public List<Viruses> viruses;
    public List<Human> human;
    public List<Position> walls = new ArrayList<>();
    public List<Position> balk = new ArrayList<>();
    public List<Position> blank = new ArrayList<>();
    public List<Position> teleportGate = new ArrayList();
    public List<Position> quarantinePlace = new ArrayList();

    public Player getPlayerByKey(String key) {
        Player player = null;
        if (players != null ) {
            for (Player p : players) {
                if (key.startsWith(p.id)) {
                    player = p;
                    break;
                }
            }
        }
        return player;
    }

    public List<Viruses> getVirus() {
        return viruses;
    }

    public List<Human> getDhuman() {
        List<Human> dhumanList = new ArrayList<>();
        if(human!=null) {
            for (Human dhuman : human) {
                if (dhuman.infected) {
                    dhumanList.add(dhuman);
                }
            }
        }
        return dhumanList;
    }

    public List<Human> getNHuman() {
        List<Human> nhumanList = new ArrayList<>();
        if(human!=null) {
            for (Human nhuman : human) {
                if (!nhuman.infected && nhuman.curedRemainTime == 0) {
                    nhumanList.add(nhuman);
                }
            }
        }
        return nhumanList;
    }

    public void updateMapInfo() {
        //blank -> 2

        for (int i = 0; i < size.rows; i++) {
            int[] map = this.map.get(i);
            for (int j = 0; j < size.cols; j++) {
                switch (map[j]) {
                    case MapEncode.ROAD:
                        blank.add(new Position(j,i));
                        break;
                    case MapEncode.WALL: // 1
                        walls.add(new Position(j,i));
                        break;
                    case MapEncode.BALK: // 3
                        balk.add(new Position(j,i));
                        break;
                    case MapEncode.TELEPORT_GATE: // 0
                        teleportGate.add(new Position(j,i));
                        break;
                    case MapEncode.QUARANTINE_PLACE: // -1
                        quarantinePlace.add(new Position(j,i));
                        break;
                    default:
                        walls.add(new Position(j,i));
                        break;
                }
            }
        }


        for (int i = 0; i < size.rows; i++) {
            int[] map = this.map.get(i);
            for (int j = 0; j < size.cols; j++) {
                System.out.print(map[j] + " ");
            }
            System.out.println();
        }
    }

    // Todo: Convert all data in MapInfo to List<Node>
    // EG: List<Viruses> viruses -> List<Node> viruses with value = 3
    // Bomb = 2
    // DHuman = 3
    // HHuman = 5
    // Spoil = 7
    // Viruses = 3
    // BALK = 6
    // Wall = 0
    // TELEPORT_GATE = 1
    // QUARANTINE_PLACE = -1
    // blank = 4

    public int[][] getMap() {

        int [][] newMap = new int[size.rows][size.cols];

        for (int i = 0; i < size.rows; i++) {
            int[] map = this.map.get(i);
            for (int j = 0; j < size.cols; j++) {
                newMap[i][j] = map[j];
            }
        }

        return newMap;
    }

    public Position getEnemyPosition(Hero hero) {
        Position position = null;
        if (hero != null) {
            for (Player player : players) {
                if (!hero.getPlayerName().startsWith(player.id)) {
                    position = player.currentPosition;
                    break;
                }
            }
        }
        return position;
    }

    public Position getCurrentPosition(Hero hero) {
        Position position = null;
        if (hero != null) {
            for (Player player : players) {
                if (hero.getPlayerName().startsWith(player.id)) {
                    position = player.currentPosition;
                    break;
                }
            }
        }
        return position;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}