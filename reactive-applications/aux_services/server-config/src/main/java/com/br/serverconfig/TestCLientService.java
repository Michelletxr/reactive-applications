package com.br.serverconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestCLientService {
    @Autowired
    private DiscoveryClient discoveryClient;

    public void listClienteService(String nameService){
        List<ServiceInstance> service = discoveryClient.getInstances(nameService);
        if(!service.isEmpty()){

            service.forEach(e -> System.out.println(
                    "ID: "+ e.getServiceId() + " | Host: " + e.getHost() + " | Port: "+ e.getPort()
            ));
        }else{
            System.out.println("service not found!");
        }

    }
}
