package com.example.latte.ec.icon;

import com.joanzapata.iconify.Icon;

public enum EcIcons implements Icon {

    icon_scan('\ue606'),
    icon_ali_pay('\ue606');

    private char charactor;
    EcIcons(char charactor){
        this.charactor = charactor;
    }
    @Override
    public String key() {
        return name().replace("_","-");
    }

    @Override
    public char character() {
        return charactor;
    }
}
