package im.bci.passgrid.data;

import org.springframework.data.annotation.Id;

public class Passgrid {

    @Id
    private String id;

    private String name;

    private Character[][] grid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrid(Character[][] grid) {
        this.grid = grid;
    }

    public Character[][] getGrid() {
        return grid;
    }
}
