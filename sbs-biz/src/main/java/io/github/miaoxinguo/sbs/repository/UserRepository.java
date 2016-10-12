package io.github.miaoxinguo.sbs.repository;

import io.github.miaoxinguo.sbs.entity.User;
import io.github.miaoxinguo.sbs.qo.UserQueryObject;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PageableRepository<User, UserQueryObject> {

}
