package im.bci.passgrid.frontend;

import im.bci.passgrid.data.Passgrid;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author devnewton
 */
public class ImportResultMV {

    private String filename;
    private List<Passgrid> passgrids = new ArrayList<Passgrid>();
    private String error;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<Passgrid> getPassgrids() {
        return passgrids;
    }

    public void setPassgrids(List<Passgrid> passgrids) {
        this.passgrids = passgrids;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
