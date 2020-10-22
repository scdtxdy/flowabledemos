package com.scd.flowablesystem.identity;

import org.flowable.idm.engine.IdmEngineConfiguration;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityManagerImpl;
import org.flowable.idm.engine.impl.persistence.entity.data.UserDataManager;

/**
 * TODO
 *
 * @author shang
 * @version 1.0
 * @date 2020-10-22 17:39
 */
public class CustomUserEntityManager extends UserEntityManagerImpl {
  public CustomUserEntityManager(IdmEngineConfiguration idmEngineConfiguration, UserDataManager userDataManager) {
    super(idmEngineConfiguration, userDataManager);
  }
}
