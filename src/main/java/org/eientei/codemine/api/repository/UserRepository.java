package org.eientei.codemine.api.repository;

import org.eientei.codemine.api.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findFirstByToken(String token);
    UserEntity findFirstByName(String name);
}

