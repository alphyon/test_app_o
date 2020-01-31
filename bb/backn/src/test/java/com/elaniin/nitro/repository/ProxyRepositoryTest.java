package com.elaniin.nitro.repository;

import com.elaniin.nitro.entity.Proxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProxyRepositoryTest {
//    @Autowired
//    private ProxyRepository proxyRepository;
//
//    @Test
//    public void test_create_proxy(){
//        Proxy proxy = new Proxy(
//                "collectorAgua",
//                "http://localhost.com",
//                "npe,code",
//                "ammount,count",
//                1
//
//        );
//        proxyRepository.save(proxy);
//        assertNotNull(proxyRepository.findByName("collectorAgua"));
//
//    }
//
//    @Test
//    public void test_update_proxy(){
//        Proxy proxy = new Proxy(
//                "collectorAgua",
//                "http://localhost.com",
//                "npe,code",
//                "ammount,count",
//                1
//
//        );
//        proxyRepository.save(proxy);
//        Proxy pr2 = proxyRepository.findByName("collectorAgua");
//        pr2.setName("CollectorEnergia");
//        proxyRepository.save(pr2);
//        assertEquals(proxyRepository.getOne(pr2.getId()).getName(),"CollectorEnergia");
//    }
//
//    @Test
//    public void test_list_proxies(){
//        List<Proxy> proxyList = new ArrayList<Proxy>();
//        proxyList.add(new Proxy("ab","test","a,b,c","z",1));
//        proxyList.add(new Proxy("ab","test","a,b,c","z",1));
//        proxyList.add(new Proxy("ab","test","a,b,c","z",1));
//        proxyList.add(new Proxy("ab","test","a,b,c","z",1));
//        proxyList.add(new Proxy("ab","test","a,b,c","z",1));
//
//        for(Proxy element: proxyList){
//            proxyRepository.save(element);
//        }
//
//        List<Proxy> pr = proxyRepository.findAllByIdCollector(1);
//        assertEquals(pr.size(), proxyList.size());
//    }
}