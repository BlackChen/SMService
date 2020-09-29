package com.bsoft.xnsmservice.dao;

import com.bsoft.xnsmservice.entity.SmsSendHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName SMSDao
 * @Description TODO
 * Created by blackchen on 2020/9/29 14:28
 */

public interface SMSDao extends JpaRepository<SmsSendHistory, Integer> {


}
