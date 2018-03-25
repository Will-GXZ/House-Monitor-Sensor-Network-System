package com.twl.xg.test.db;


import com.twl.xg.config.AppConfig;
import com.twl.xg.config.HibernateConfig;
import com.twl.xg.config.ServletInitializer;
import com.twl.xg.domain.BorderRouterEntity;
import com.twl.xg.domain.SensorDataEntity;
import com.twl.xg.domain.SensorEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServletInitializer.class, AppConfig.class, HibernateConfig.class})
@WebAppConfiguration
public class TestHibernate {
  @Autowired
  SessionFactory sessionFactory;

  /**
   * create borderRouters, store them in database
   */
  @Test
  @Transactional
  @Commit     // add "Commit" configuration to disable auto rolling back after test
  public void test1() {
    Session session = sessionFactory.getCurrentSession();
    BorderRouterEntity be = new BorderRouterEntity();
    be.setBorderRouterIp("borderRouterIp-1");
    be.setBorderRouterName("borderRouter-1");
    session.save(be);be = new BorderRouterEntity();
    be.setBorderRouterIp("borderRouterIp-2");
    be.setBorderRouterName("borderRouter-2");
    session.save(be);be = new BorderRouterEntity();
    be.setBorderRouterIp("borderRouterIp-3");
    be.setBorderRouterName("borderRouter-3");
    session.save(be);be = new BorderRouterEntity();
    be.setBorderRouterIp("borderRouterIp-4");
    be.setBorderRouterName("borderRouter-4");
    session.save(be);be = new BorderRouterEntity();
    be.setBorderRouterIp("borderRouterIp-5");
    be.setBorderRouterName("borderRouter-5");
    session.save(be);be = new BorderRouterEntity();
    be.setBorderRouterIp("borderRouterIp-6");
    be.setBorderRouterName("borderRouter-6");
    session.save(be);
  }

  /**
   * create sensors for each borderRouter, store them in database
   */
  @Test
  @Transactional
  @Commit     // add "Commit" configuration to disable auto rolling back after test
  public void test2() {
    Session session = sessionFactory.getCurrentSession();
    // get all borderRouter from database first
    CriteriaQuery<BorderRouterEntity> query = session.getCriteriaBuilder().createQuery(BorderRouterEntity.class);
    query.from(BorderRouterEntity.class);
    List<BorderRouterEntity> borderRouterEntities = session.createQuery(query).getResultList();

    // add sensors for each borderRouter
    for (BorderRouterEntity borderRouter : borderRouterEntities) {
      System.out.println(borderRouter.getBorderRouterIp());
      saveSensorforBorderRouter(borderRouter);
    }
  }

  /**
   * generate dummy data for each sensor
   */
  @Test
  @Transactional
  @Commit     // add "Commit" configuration to disable auto rolling back after test
  public void test3() {
    Session session = sessionFactory.getCurrentSession();
    // get all sensors
    CriteriaQuery<SensorEntity> query = session.getCriteriaBuilder().createQuery(SensorEntity.class);
    query.from(SensorEntity.class);
    List<SensorEntity> sensors = session.createQuery(query).getResultList();

    System.out.println("******************* Sensors ***********************");
    // add data for each sensor
    for (SensorEntity sensor : sensors) {
      System.out.println(sensor.getSensorIp());
      saveDataForSensor(sensor);
    }
  }



  private void saveDataForSensor(SensorEntity sensor) {
    Session session = sessionFactory.getCurrentSession();
    SensorDataEntity data = new SensorDataEntity();
    data.setHumidity(0);
    data.setLightness(0);
    data.setTemperature(0);
    data.setSensorBySensorIp(sensor);
    session.save(data);
    data = new SensorDataEntity();
    data.setSensorBySensorIp(sensor);
    data.setHumidity(0);
    data.setLightness(0);
    data.setTemperature(0);
    session.save(data);
    data = new SensorDataEntity();
    data.setSensorBySensorIp(sensor);
    data.setHumidity(0);
    data.setLightness(0);
    data.setTemperature(0);
    session.save(data);
  }

  private void saveSensorforBorderRouter(BorderRouterEntity borderRouter) {
    Session session = sessionFactory.getCurrentSession();
    SensorEntity sensor = new SensorEntity();
    sensor.setSensorName("sensor1");
    sensor.setBorderRouterByBorderRouterIp(borderRouter);
    sensor.setSensorIp(borderRouter.getBorderRouterIp() + "_sensor1");
    session.save(sensor);
    sensor = new SensorEntity();
    sensor.setSensorName("sensor2");
    sensor.setBorderRouterByBorderRouterIp(borderRouter);
    sensor.setSensorIp(borderRouter.getBorderRouterIp() + "_sensor2");
    session.save(sensor);
    sensor = new SensorEntity();
    sensor.setSensorName("sensor3");
    sensor.setBorderRouterByBorderRouterIp(borderRouter);
    sensor.setSensorIp(borderRouter.getBorderRouterIp() + "_sensor3");
    session.save(sensor);
  }
}
