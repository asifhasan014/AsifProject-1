package com.softron.admin.utils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.softron.admin.transferobjects.MenuItem;
import com.softron.admin.transferobjects.RawMenu;

public class MenuBuilder {
	
	private MenuBuilder() {
		super();
	}
	
	public static List<MenuItem> build(List<RawMenu> rawData) {
		Map<Long, MenuItem> lookup = new ConcurrentHashMap<>();

        rawData.forEach(d -> lookup.put(d.getId(), MenuItem.builder()
        		.id(d.getId())
        		.parentId(d.getParentId())
        		.title(d.getTitle())
        		.icon(d.getIcon())
        		.link(d.getLink())
        		.home(d.isHome())
        		.sortOrder(d.getSortOrder())
        		.hidden(d.isHidden())
        		.build()));

        lookup.values().forEach(item -> {
            if (item.getParentId() != null && lookup.containsKey(item.getParentId())) {
                lookup.get(item.getParentId()).addChild(item);
            } else {
            	item.setParentId(null);
            }
        });

        List<MenuItem> items = lookup.values().stream().filter(x -> x.getParentId() == null || x.getParentId() == 0L)
        		.sorted(Comparator.comparing(MenuItem::getSortOrder))
        		.collect(Collectors.toList());
        return items;
	}

}
