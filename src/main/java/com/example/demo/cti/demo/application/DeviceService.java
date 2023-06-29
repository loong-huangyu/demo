package com.example.demo.cti.demo.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.cti.demo.domain.model.Device;

@Service
public class DeviceService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     * <p>
     * db.createUser({user: "testUser", pwd: "pwd", roles : [{role: "readWrite", db: "robot"}]});
     */
    public Device save(Device device) {
        return mongoTemplate.save(device);
    }

    /**
     * 根据用户名查询对象
     *
     * @return
     */
    public Device findByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        Device mgt = mongoTemplate.findOne(query, Device.class);
        return mgt;
    }

    /**
     * 根据用户id查询对象
     *
     * @return
     */
    public Device findById(String id) {
        /**
         * redis 缓存
         */
        Query query = new Query(Criteria.where("id").is(id));
        Device mgt = mongoTemplate.findOne(query, Device.class);
        return mgt;
    }

    /**
     * 更新对象
     */
    public Device update(Device device) {
        Query query = new Query(Criteria.where("id").is(device.getId()));
        Update update = new Update().set("name", device.getName());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query, update, Device.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,TestEntity.class);
        return device;
    }

    /**
     * 删除对象
     *
     * @param id
     */
    public void deleteTestById(Integer id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Device.class);
    }

}
