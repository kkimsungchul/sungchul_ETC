package com.sungchul.etc.camping.service;


import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Slf4j
@Service("CampingService")
public class CampingService {

    public void getReservationList() {
        String url = "http://r.camperstory.com/resMain.hbb?reserve_path=RP&campseq=3449#20220622^20220623^1";
        try {
            Document doc = Jsoup.connect(url).get();
            System.out.println(doc.text());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
