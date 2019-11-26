package com.minchev;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        // write your code here
        BigDecimal amount = new BigDecimal(21.21);
        String str = "RegExr Акт за установяване на административно нарушение (АУАН) [/b]№ 1400000057/22.11.2019г.[b]  was [b]created { 12.12.2020[/b] by gskinner.com, and is proudly hosted by Media Temple.\n" +
                "\n" +
                "            Edit the Expression & Text to see matches. [b]Roll over matches or the expression for [b]details[/b]. PCRE & JavaScript flavors of RegEx are supported.\n" +
                "\n";
        //str +=str;

        StringBuilder b = new StringBuilder(str);
        //findBoldText(b);
        findBoldTextWithIndexOf(str);
        System.out.printf("==============");
        findBoldTextWithIndexOf2(str);
    }





    public static void findBoldText(StringBuilder template) {
        long start = System.currentTimeMillis();
        System.out.println("Start : " + start);

        String regexPattern = "(\\[b\\])[а-яА-Я\\w\\s\\d.+ $ { }№: ,  \\/()]+(\\[\\/b\\])";
        String regexBold = "(\\[b\\])";
        String regexBoldEnd = "(\\[\\/b\\])";
        String replaceBold = "<b>";
        String replaceBoldEnd = "</b>";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(template);

        while (matcher.find()) {
            String matcherGroup = matcher.group();
            String replaceMatch = matcherGroup.replaceAll(regexBold, replaceBold);
            replaceMatch = replaceMatch.replaceAll(regexBoldEnd, replaceBoldEnd);

            template = template.replace(matcher.start(),  matcher.end(), replaceMatch);
        }

        System.out.println(template);

        long stop = System.currentTimeMillis();
        System.out.println("Stop : " + stop);
        System.out.println("Result : " + (stop-start));
    }


    public static void findBoldTextWithIndexOf2(String str) {
        long start = System.currentTimeMillis();
        System.out.println("Start : " + start);

        StringBuilder result = new StringBuilder();
        boolean flag = false;
        int openIndex = 0;
        String pieceOfString;
        String pieceOfReplace;
        int closeIndex = 0;
        int nextIndex = 0;
        while (flag == false) {
            openIndex = str.indexOf("[b]");
            closeIndex = str.indexOf("[/b]");
            if (openIndex > -1 && closeIndex > -1 && openIndex<closeIndex) {
                closeIndex += 4;
                if (openIndex>0) openIndex = 0;
                pieceOfReplace = str.substring(openIndex, closeIndex);
                pieceOfReplace = pieceOfReplace.replaceFirst("\\[b\\]", "<b>");
                pieceOfReplace = pieceOfReplace.replaceFirst("\\[/b\\]", "</b>");
                str = str.substring(closeIndex, str.length());
                result.append(pieceOfReplace);
                nextIndex= closeIndex;
            }

            if (openIndex > -1 && closeIndex > -1 && openIndex>closeIndex) {
                pieceOfString = str.substring(0, openIndex-1);
                str = str.substring(openIndex, str.length());
                result.append(pieceOfString);
                nextIndex= openIndex-1;
            }


            if (openIndex == -1 && closeIndex == -1) {
                flag = true;
                //pieceOfString = str;
                result.append(str);
                //result.append(str);
            }
        }
        System.out.println(result);
        long stop = System.currentTimeMillis();
        System.out.println("Stop : " + stop);
        System.out.println("Result : " + (stop-start));
    }


    public static void findBoldTextWithIndexOf(String str) {
        long start = System.currentTimeMillis();
        System.out.println("Start  1: " + start);
        boolean flag = false;
        String substring;
        int closeIndex = 0;
        int openIndex = 0;
        int nextIndex = 0;
        int cOpenIndex;
        StringBuilder result = new StringBuilder();
        while (flag == false) {
            openIndex = str.indexOf("[b]", closeIndex);
            //да се вземат данните до първото [b]
            if (openIndex > -1 && closeIndex > -1 && openIndex > closeIndex) {
                substring = str.substring(closeIndex, openIndex);
                result.append(substring);
            }
            closeIndex = str.indexOf("[/b]", closeIndex);
            //да се вземат данните между 2та символа
            if (openIndex >= -1 && closeIndex >= -1 && openIndex < closeIndex) {
                closeIndex += 4;
                substring = str.substring(openIndex, closeIndex);
                cOpenIndex = substring.lastIndexOf("[b]");

                if (cOpenIndex > 0) {
                    result.append(substring.substring(0, cOpenIndex));
                    openIndex += cOpenIndex;
                    substring = str.substring(openIndex, closeIndex);
                }
                String s = substring.replace("[b]", "<b>");
                s = s.replace("[/b]", "</b>");
                nextIndex = closeIndex;
                result.append(s);
            } else if (openIndex > closeIndex && closeIndex > -1) {
                closeIndex = openIndex;
            }

            //
            if (openIndex > -1 && closeIndex == -1) {
                flag = true;
                result.append(str.substring(openIndex, str.length()));
            }


            if (openIndex == -1 && closeIndex == -1) {
                flag = true;
                result.append(str.substring(nextIndex, str.length()));
            }
        }
        System.out.println(result);
        long stop = System.currentTimeMillis();
        System.out.println("Stop : " + stop);
        System.out.println("Result : " + (stop-start));
    }
}
