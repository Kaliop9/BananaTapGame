package com.example.bananatap;

import android.app.*;
import android.os.*;
import android.graphics.Color;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.text.NumberFormat;
import java.util.*;

public class MainActivity extends Activity {
    long taps=0, coins=0; int tier=0; boolean bananaOwned=true;
    android.content.SharedPreferences sp;
    String[] names={"Bronze","Silver","Gold","Diamond","Master","Ultra Master","Mythic","Legendary","Celestial","Ultimate"};
    long[] caps={1000,10000,100000,1000000,10000000,100000000,1000000000L,10000000000L,100000000000L,1000000000000L};
    long[] prices={0,100,1000,10000,100000,1000000,10000000,100000000,1000000000L,10000000000L};
    int[] mult={1,2,5,10,25,50,100,250,500,1000};
    TextView stats, tierTv;
    Button banana, sell, buy, upgrade;
    NumberFormat nf=NumberFormat.getInstance(Locale.US);

    @Override public void onCreate(Bundle b){super.onCreate(b);
        sp=getSharedPreferences("save",0); taps=sp.getLong("taps",0); coins=sp.getLong("coins",0); tier=sp.getInt("tier",0); bananaOwned=sp.getBoolean("owned",true);
        build();
    }
    TextView tv(String s,int size){ TextView t=new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(Color.WHITE); t.setGravity(Gravity.CENTER); t.setPadding(8,8,8,8); return t; }
    void build(){
        LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(24,18,24,18); root.setBackgroundColor(Color.rgb(23,23,23));
        TextView title=tv("🍌 BANANA TAP",28); root.addView(title,new LinearLayout.LayoutParams(-1,60));
        tierTv=tv("",20); root.addView(tierTv);
        stats=tv("",17); root.addView(stats);
        banana=new Button(this); banana.setText("🍌 TAP BANANA 🍌"); banana.setTextSize(24); banana.setAllCaps(false);
        root.addView(banana,new LinearLayout.LayoutParams(-1,0,1));
        LinearLayout row=new LinearLayout(this); row.setOrientation(LinearLayout.HORIZONTAL);
        sell=new Button(this); buy=new Button(this); upgrade=new Button(this);
        sell.setText("SELL"); buy.setText("BUY"); upgrade.setText("UPGRADE");
        row.addView(sell,new LinearLayout.LayoutParams(0,65,1)); row.addView(buy,new LinearLayout.LayoutParams(0,65,1)); row.addView(upgrade,new LinearLayout.LayoutParams(0,65,1));
        root.addView(row);
        TextView goal=tv("Goal: 1,000,000,000,000 taps • Fictional coins only",13); root.addView(goal,new LinearLayout.LayoutParams(-1,55));
        setContentView(root); update();

        banana.setOnClickListener(v->{ if(!bananaOwned){toast("Buy a new banana first!");return;} taps=Math.min(1000000000000L,taps+mult[tier]); save(); update();});
        sell.setOnClickListener(v->{ if(!bananaOwned){toast("No banana to sell.");return;} long value=Math.min(caps[tier],Math.max(1,taps/10)); coins+=value; bananaOwned=false; save(); update(); toast("Sold for "+nf.format(value)+" coins!");});
        buy.setOnClickListener(v->{ if(bananaOwned){toast("You already own a banana.");return;} long p=prices[tier]; if(coins>=p){coins-=p; bananaOwned=true; taps=0; save(); update();} else toast("Need "+nf.format(p)+" coins.");});
        upgrade.setOnClickListener(v->{ if(tier>=names.length-1){toast("Maximum tier reached!");return;} long p=prices[tier+1]; if(coins>=p && bananaOwned){coins-=p; tier++; taps=0; save(); update();} else toast("Need "+nf.format(p)+" coins and an owned banana.");});
    }
    void update(){
        tierTv.setText("Tier: "+names[tier]+(bananaOwned?" 🍌":"  — Buy a banana"));
        stats.setText("Taps: "+nf.format(taps)+" / 1,000,000,000,000\nCoins: "+nf.format(coins)+"   •   "+mult[tier]+" taps/click\nSell cap: "+nf.format(caps[tier]));
        sell.setText("SELL\n≤ "+nf.format(caps[tier]));
        buy.setText("BUY\n"+nf.format(prices[tier])+" coins");
        upgrade.setText(tier<names.length-1?"UPGRADE\n"+nf.format(prices[tier+1]):"MAX TIER");
        banana.setEnabled(bananaOwned);
    }
    void save(){sp.edit().putLong("taps",taps).putLong("coins",coins).putInt("tier",tier).putBoolean("owned",bananaOwned).apply();}
    void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
}