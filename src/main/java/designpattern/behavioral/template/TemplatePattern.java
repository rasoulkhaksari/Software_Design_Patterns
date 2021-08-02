package designpattern.behavioral.template;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class Enemy {
    int position;

    public Enemy(int initPosition) {
        this.position = initPosition;
    }
}

class GameMap {
    int center, top, left, right, bottom;
}

class Unit {
    public enum UnitType {
        FARM, BARRAKS, STRONGHOLD, PEON, GRUNT
    }

    UUID id;
    UnitType type;
    String title;

    public Unit() {
    }

    public Unit(UnitType type, String title) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.title = title;
    }
}

class Structure extends Unit {
    public Structure(Unit.UnitType type, String title) {
        super(type, title);
    }

    public void collect() {
    };
}

abstract class GameAI {
    protected List<Structure> builtStructures = new ArrayList<>();
    protected GameMap map = new GameMap();
    protected List<Object> Resources = new ArrayList<>();
    protected List<Unit> scouts = new ArrayList<>();
    protected List<Unit> warriors = new ArrayList<>();

    public void turn() {
        collectResources();
        buildStructures();
        buildUnits();
        attack();
    }

    public void collectResources() {
        for (Structure structure : builtStructures) {
            structure.collect();
        }
    }

    abstract void buildStructures();

    protected Structure buildStructure(Unit.UnitType type, String title) {
        return new Structure(type, title);
    }

    abstract void buildUnits();

    protected Unit buildUnit(Unit.UnitType type, String title) {
        return new Unit(type, title);
    }

    public void attack() {
        Enemy enemy = closestEnemy();
        if (enemy == null) {
            sendScouts(map.center);
        } else {
            sendWarriors(enemy.position);
        }
    }

    abstract void sendScouts(int position);

    abstract void sendWarriors(int position);

    public Enemy closestEnemy() {
        return new Enemy(10);
    }
}

class OrcsAI extends GameAI {
    @Override
    void buildStructures() {
        if (Resources.size() > 0) {
            builtStructures.add(buildStructure(Unit.UnitType.FARM, "new farm"));
            builtStructures.add(buildStructure(Unit.UnitType.BARRAKS, "new barak"));
            builtStructures.add(buildStructure(Unit.UnitType.STRONGHOLD, "new stronghold"));
        }
    }

    @Override
    void buildUnits() {
        if (Resources.size() > 100) {
            if (scouts.size() == 0) {
                this.scouts.add(this.buildUnit(Unit.UnitType.PEON, "new peon"));
            } else {
                this.warriors.add(this.buildUnit(Unit.UnitType.GRUNT, "new warrior"));
            }
        }
    }

    @Override
    void sendScouts(int position) {
        if (scouts.size() > 0) {
            System.out.println(String.format("Send scouts to position: %d", position));
        } else {
            System.out.println("no Orc scout available");
        }
    }

    @Override
    void sendWarriors(int position) {
        if (warriors.size() > 5) {
            System.out.println(String.format("Send warriors to position: %d", position));
        } else {
            System.out.println("Not enough Orc warriors");
        }
    }
}

class MonstersAI extends GameAI {
    @Override
    public void collectResources() {
        System.out.println("Monsters don't collect resources");
    }

    @Override
    void buildStructures() {
        System.out.println("Monsters don't build structures");
    }

    @Override
    void buildUnits() {
        System.out.println("Monsters don't build units");
    }

    @Override
    void sendScouts(int position) {
        System.out.println("Monsters don't have scouts");
    }

    @Override
    void sendWarriors(int position) {
        System.out.println(String.format("Send monster warriors to position: %d", position));
    }
}

public class TemplatePattern {
    public static void demo() {
        OrcsAI orcs = new OrcsAI();
        MonstersAI monsters = new MonstersAI();
        orcs.turn();
        monsters.turn();
    }
}
