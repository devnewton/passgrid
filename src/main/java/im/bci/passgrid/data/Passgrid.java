package im.bci.passgrid.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Passgrid {

    @Id
    private String id;

    @Indexed(unique = true)
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

    public boolean isEmpty() {
        if (null != grid) {
            for (Character[] row : grid) {
                if (row.length > 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
