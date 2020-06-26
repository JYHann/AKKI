package com.example.sanhak3;

import java.util.ArrayList;
import java.util.Map;

class pair{
    String key;
    String value;
    public pair(String a ,String b)
    {
        key =a;
        value =b;
    }
}
public class cards implements Comparable<cards> {
    String Cname;
    ArrayList<pair> options;
    int sum;
    String url;
    public cards()
    {
        options = new ArrayList<pair>();
    }

    @Override
    public int compareTo(cards o) {
        if(this.sum>o.sum)
            return -1;
        else if(this.sum<o.sum)
            return 1;
        else
            return 0;
    }
}
