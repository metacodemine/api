package org.eientei.codemine.api.repository;

import org.eientei.codemine.api.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findFirstByToken(String token);

    @Query("select v from UserEntity v where lower(v.name) = lower(:name)")
    UserEntity findFirstByName(@Param("name") String name);
}

