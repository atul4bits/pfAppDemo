package com.pf.app;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PFCache {

    private Map<Integer, List<Integer>> pfCache = new ConcurrentHashMap<>();

    public List<Integer> getPFByNum(Integer key){
        return pfCache.get(key);
    }

    public void addPFsToCache(Integer key,  List<Integer> listPFs){
        pfCache.put(key,listPFs);
    }

}
