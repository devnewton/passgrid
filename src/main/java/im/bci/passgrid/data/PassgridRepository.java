package im.bci.passgrid.data;

import java.util.List;

public interface PassgridRepository {
    
    void save(Passgrid passgrid);

    Passgrid findOne(String id);
    
    List<Passgrid> searchByName(String name);
    
}
