package ru.geekbrains.lesson7.service;

import org.springframework.stereotype.Service;
import ru.geekbrains.lesson7.aspect.StatisticAspect;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticService {

    public Map<String, Long> getStatistic() {
        var statMap = StatisticAspect.getStatMap();
        var resultMap = new HashMap<String, Long>();
        for (var entry : statMap.entrySet()) {
            Long time = entry.getValue().values().stream().mapToLong(l -> l).sum();
            resultMap.put(entry.getKey(), time);
        }
        return resultMap;
    }
}
