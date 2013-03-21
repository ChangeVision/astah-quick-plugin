package com.change_vision.astah.quick.command;

public class CommittedNameTrimer {
    
    public String trim(Candidate[] committeds,String searchKey){
        String key = searchKey;
        if (key.isEmpty()) return "";
        for (Candidate committed : committeds) {
            String name = committed.getName();
            int index = name.length();
            key = key.substring(index);
            key = key.trim();
        }
        return key;
    }

}
