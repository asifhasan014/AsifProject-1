package com.softron.common.utils;

import com.softron.common.transferobjects.RawTree;
import com.softron.common.transferobjects.TreeTO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TreeBuilder {

    private TreeBuilder() {
    }

    public static List<TreeTO> build(List<RawTree> rawData) {

        Map<Long, TreeTO> lookup = new ConcurrentHashMap<>();

        rawData.forEach(d -> lookup.put(d.getId(), new TreeTO(d.getId(), d.getParentId(), d.getName())));

        lookup.values().forEach(item -> {
            if (item.getParentId() != null) {
            	if(lookup.containsKey(item.getParentId())) {
            		lookup.get(item.getParentId()).addChild(item);
            	} else {
            		item.setParentId(null);
            	}
            }
        });

        return lookup.values().stream().filter(x -> x.getParentId() == null || x.getParentId() == 0L).collect(Collectors.toList());
    }

}
