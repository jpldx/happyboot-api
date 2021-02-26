package org.happykit.happyboot.security.login.repository;

import org.happykit.happyboot.sys.collection.LoginLogCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author chen.xudong
 * @version 1.0
 * @since 2021/2/26
 */
public interface LoginLogRepository extends MongoRepository<LoginLogCollection, String> {

}
