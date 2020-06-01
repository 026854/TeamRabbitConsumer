package com.rabbitmq.manager.factory;

import com.rabbitmq.manager.vo.BeverageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CafeReceiverFactory {
    @Autowired
    private CoffeeReceiver coffeeReceiver;

    @Autowired
    private BlenderReceiver blenderReceiver;

    @Autowired
    private NormalReceiver normalReceiver;


    public CafeReceiver getCafeReceiver(BeverageType beverageType){
        if(beverageType == BeverageType.COFFEE) return coffeeReceiver;
        else if( beverageType == BeverageType.BLENDER) return blenderReceiver;
        return normalReceiver;

    }

}
