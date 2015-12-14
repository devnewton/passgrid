package im.bci.passgrid.data;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class PassgridRepositoryImpl implements PassgridRepository {

    private static final String COLLECTION_NAME = "passgrid";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(Passgrid passgrid) {
        mongoTemplate.save(passgrid, COLLECTION_NAME);
    }

    @Override
    public Passgrid findOne(String id) {
        Passgrid result = mongoTemplate.findById(id, Passgrid.class, COLLECTION_NAME);
        return result;
    }

    @Override
    public List<Passgrid> searchByName(String name) {
        Query query = new Query();
        if (StringUtils.isNotBlank(name)) {
            query = query.addCriteria(Criteria.where("name").regex(name));
        }
        List<Passgrid> results = mongoTemplate.find(query, Passgrid.class, COLLECTION_NAME);
        return results;
    }

}
