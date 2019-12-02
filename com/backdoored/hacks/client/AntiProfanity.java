package com.backdoored.hacks.client;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import java.net.*;
import java.io.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.*;
import a.a.d.b.c.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "AntiProfanity", description = "Filter out naughty words", category = CategoriesInit.CLIENT)
public class AntiProfanity extends BaseHack
{
    private Set<String> words;
    
    public AntiProfanity() {
        super();
        this.words = new HashSet<String>();
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection)new URL("https://raw.githubusercontent.com/RobertJGabriel/Google-profanity-words/master/list.txt").openConnection();
            final int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 301 || responseCode == 302 || responseCode == 303) {
                final URL url = new URL(httpURLConnection.getHeaderField("Location"));
                System.out.println("Redirected to " + url + " when fetching offsets");
                httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestProperty("Cookie", httpURLConnection.getHeaderField("Set-Cookie"));
            }
            this.words = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())).lines().map((Function<? super String, ?>)String::toLowerCase).collect((Collector<? super Object, ?, Set<String>>)Collectors.toSet());
            System.out.println("Downloaded profanity list: " + this.words);
        }
        catch (Exception ex) {
            System.out.println("Failure downloading profanity word list");
            ex.printStackTrace();
        }
    }
    
    private String c(String replace) {
        final Iterator<String> iterator = this.words.iterator();
        while (iterator.hasNext()) {
            replace = replace.replace(iterator.next(), "[censored]");
        }
        return replace;
    }
    
    @SubscribeEvent
    public void a(final a a) {
        if (this.getEnabled()) {
            a.cn = this.c(a.cn);
        }
    }
}
