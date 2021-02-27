package org.happykit.happyboot.security.login.repository;


import org.happykit.happyboot.security.model.collections.SecurityLogCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author chen.xudong
 * @version 1.0
 * @since 2021/2/26
 */
public interface SecurityLogRepository extends MongoRepository<SecurityLogCollection, String> {

}
