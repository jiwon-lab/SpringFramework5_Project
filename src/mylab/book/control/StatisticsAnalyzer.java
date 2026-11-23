
package mylab.book.control;

import java.util.HashMap;
import java.util.Map;
import java.text.DecimalFormat;

import mylab.book.entity.*;

public class StatisticsAnalyzer {

    public Map<String, Double> calculateAveragePriceByType(
            Publication[] publications) {

        Map<String, Double> totalPrices = new HashMap<>();
        Map<String, Integer> counts = new HashMap<>();
        Map<String, Double> averages = new HashMap<>();

        for (Publication pub : publications) {

            String type = getPublicationType(pub);

            totalPrices.put(type,
                    totalPrices.getOrDefault(type, 0.0)
                            + pub.getPrice());

            counts.put(type,
                    counts.getOrDefault(type, 0) + 1);
        }

        for (String type : totalPrices.keySet()) {

            double average = totalPrices.get(type) / counts.get(type);

            averages.put(type, average);
        }

        return averages;
    }

    public Map<String, Double> calculatePublicationDistribution(
            Publication[] publications) {

        Map<String, Integer> counts = new HashMap<>();
        Map<String, Double> distribution = new HashMap<>();

        for (Publication pub : publications) {

            String type = getPublicationType(pub);

            counts.put(type,
                    counts.getOrDefault(type, 0) + 1);
        }

        for (String type : counts.keySet()) {

            double percentage = publications.length == 0
                    ? 0.0
                    : (double) counts.get(type)
                        / publications.length * 100;

            distribution.put(type, percentage);
        }

        return distribution;
    }

    public double calculatePublicationRatioByYear(
            Publication[] publications, String year) {

        if (publications.length == 0) {
            return 0;
        }

        int count = 0;

        for (Publication pub : publications) {

            if (pub.getPublishDate().substring(0, 4).equals(year)) {
                count++;
            }
        }

        return (double) count / publications.length * 100;
    }

    private String getPublicationType(Publication pub) {

        if (pub instanceof Novel) {
            return "소설";
        } else if (pub instanceof Magazine) {
            return "잡지";
        } else if (pub instanceof ReferenceBook) {
            return "참고서";
        } else {
            return "기타";
        }
    }

    public void printStatistics(Publication[] publications) {

        DecimalFormat format = new DecimalFormat("#,###.##");

        Map<String, Double> averages =
                calculateAveragePriceByType(publications);

        Map<String, Double> distribution =
                calculatePublicationDistribution(publications);

        System.out.println("1. 타입별 평균 가격:");

        String[] types = {"소설", "참고서", "잡지"};

        for (String type : types) {
            if (averages.containsKey(type)) {
                System.out.println("   - " + type + ": "
                        + format.format(averages.get(type)) + "원");
            }
        }

        System.out.println();
        System.out.println("2. 출판물 유형 분포:");

        for (String type : types) {
            if (distribution.containsKey(type)) {
                System.out.println("   - " + type + ": "
                        + String.format("%.2f", distribution.get(type))
                        + "%");
            }
        }

        System.out.println();
        System.out.println("3. 2007년에 출판된 출판물 비율: "
                + String.format("%.2f",
                    calculatePublicationRatioByYear(publications, "2007"))
                + "%");
    }
}
