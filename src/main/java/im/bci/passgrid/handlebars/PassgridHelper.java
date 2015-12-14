package im.bci.passgrid.handlebars;

import im.bci.passgrid.data.Passgrid;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

/**
 *
 * @author devnewton
 */
@HandlebarsHelper
public class PassgridHelper {

    public String passgridColspan(Passgrid context) {
        Character[][] grid = context.getGrid();
        if (null != grid && grid.length > 0) {
            Character[] row = grid[0];
            if (null != row && row.length > 0) {
                return String.valueOf(row.length + 1);
            }
        }
        return "";
    }
}
