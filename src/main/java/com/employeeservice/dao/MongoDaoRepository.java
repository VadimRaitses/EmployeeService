package com.employeeservice.dao;

import com.employeeservice.exceptions.EntityAlreadyExistsException;
import com.employeeservice.models.Updatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MongoDaoRepository implements DaoRepository {


    private final MongoTemplate template;

    @Autowired
    public MongoDaoRepository(MongoTemplate template) {
        this.template = template;
    }


    @Override
    public Object get(String id, Class entityClass) {
        return template.findOne(Query.query(new Criteria("id").is(id)), entityClass);
    }


    @Override
    public List getByQuery(Object query, Class entityClass) {
        return template.find((Query) query, entityClass);
    }

    @Override
    public Object save(Object o) throws EntityAlreadyExistsException {
        try {
            return template.save(o);
        } catch (Exception e) {
            throw new EntityAlreadyExistsException("entity already exists");
        }
    }

    @Override
    public boolean update(String id, Object o, Class entityClass) {
        return template.updateFirst(
                new Query(new Criteria("id").is(id)),
                ((Updatable) o).getUpdatedData(),
                entityClass).getModifiedCount() > 0;
    }

    @Override
    public boolean delete(String id, Class entityClass) {
        return template.remove(new Query(new Criteria("id").is(id)), entityClass).getDeletedCount() > 0;
    }

    @Override
    public Object exists(String id, Class entityClass) {
        return get(id, entityClass);
    }

}
