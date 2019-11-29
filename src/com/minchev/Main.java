package com.minchev;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here
        BigDecimal amount = new BigDecimal(21.21);
        String str = "RegExr Акт за установяване на административно нарушение (АУАН) [/b]№ 1400000057/22.11.2019г.[b]  was [b]created { 12.12.2020[/b] by [b]gskinner.com[/b], and is [b]proudly[/b] hosted by Media Temple.\n" +
                "\n" +
                "            Edit the Expression & Text to see matches. [b]Roll over matches or the expression for [b]details[/b]. PCRE & JavaScript flavors of RegEx are supported.\n" +
                "\n";
        //str +=str;

        StringBuilder b = new StringBuilder(str);
       //findBoldText(b);
        System.out.printf("===== 1=========");
       findBoldTextWithIndexOf(str);
        System.out.printf("===== 2=========");

        findBoldTextWithIndexOfFinal(str);

      /*  findBoldTextWithIndexOf2(str);
       System.out.printf("=======3=======");
        temp3(str);*/


    }

  /*  public static void findBoldText(StringBuilder template) {
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

            template = template.replace(matcher.start(), matcher.end(), replaceMatch);
        }

        System.out.println(template);

        long stop = System.currentTimeMillis();
        System.out.println("Stop : " + stop);
        System.out.println("Result : " + (stop - start));
    }*/


    public static void findBoldTextWithIndexOfFinal(String str) {
        StringBuilder result = new StringBuilder();
        int openIndex = str.indexOf("[b]");
        int closeIndex;
        String pieceOfString;

        while (openIndex >= 0) {
            closeIndex = 0;
            result.append(str.substring(closeIndex, openIndex));
            closeIndex = str.indexOf("[/b]", openIndex);
            if (closeIndex == -1) {
                str = str.substring(openIndex);
                break;
            }
            closeIndex += 4;
            pieceOfString = str.substring(openIndex, closeIndex);
            pieceOfString = pieceOfString.replaceFirst("\\[b\\]", "<b>");
            pieceOfString = pieceOfString.replaceFirst("\\[/b\\]", "</b>");
            result.append(pieceOfString);

            str = str.substring(closeIndex);
            openIndex = str.indexOf("[b]");
        }

        if (str.length() > 0) {
            result.append(str);
        }
        System.out.println(result);
    }


    public static void findBoldTextWithIndexOf(String str) {
        boolean flag = false;
        String substring;
        int closeIndex = 0;
        int openIndex = 0;
        int nextIndex = 0;

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
                nextIndex = substring.lastIndexOf("[b]");

                if (nextIndex > 0) {
                    result.append(substring, 0, nextIndex);
                    openIndex += nextIndex;
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
                result.append(str.substring(openIndex));
            }

            if (openIndex == -1 && closeIndex == -1) {
                flag = true;
                result.append(str.substring(nextIndex));
            }
        }
        System.out.println(result);

    }

    public static void findBoldTextWithIndexOf2(String str) {
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        int openIndex = 0;
        String pieceOfString;
        int closeIndex = 0;
        String boldStart = "<b>";
        String boldEnd = "</b>";

        while (flag == false) {
            openIndex = str.indexOf("[b]");
            closeIndex = str.indexOf("[/b]");
            if (openIndex > -1 && closeIndex > -1 && openIndex < closeIndex) {
                closeIndex += 4;
                if (openIndex > 0) openIndex = 0;

                //searchingWorkString(String str);

                pieceOfString = str.substring(openIndex, closeIndex);

                pieceOfString = pieceOfString.replaceFirst("\\[b\\]", boldStart);
                pieceOfString = pieceOfString.replaceFirst("\\[/b\\]", boldEnd);
                str = str.substring(closeIndex);
                result.append(pieceOfString);

            }
            if (openIndex > -1 && closeIndex > -1 && openIndex > closeIndex) {
                pieceOfString = str.substring(0, openIndex - 1);
                result.append(pieceOfString);
                str = str.substring(openIndex);
            }

            if (openIndex == -1 && closeIndex == -1) {
                flag = true;
                result.append(str);
            }
        }
        System.out.println(result);
    }

    public static void temp3(String template) throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader(template));
        StringBuilder result = new StringBuilder();
        String line;
        String s;
        // convert new lines as paragraphs
        while ((line = reader.readLine()) != null) {
            if (line.length()>0) {
                s = searchingWorkString(line);
                result.append(s + "\n");
            }
            // result.append(line+"\n");
        }
        System.out.println(result);
    }

    public static String  searchingWorkString(String str) {
        int openIndex = 0;
        String pieceOfString;
        int closeIndex = 0;
        String s = "";
        openIndex = str.indexOf("[b]");
        closeIndex = str.lastIndexOf("[/b]");
        if (openIndex > -1 && closeIndex > -1 && openIndex < closeIndex) {
            closeIndex+=4;
            pieceOfString = str.substring(openIndex, closeIndex);
            s = "";
            if (openIndex>0) {
                s += str.substring(0,openIndex);
                s+= "<b>";
                s+=pieceOfString.substring(3, pieceOfString.length()-4);
                s+="</b>";
                openIndex = 0;
            }
            pieceOfString   =s;

            str =  str.replace( str.substring(openIndex, closeIndex), pieceOfString);
            return searchingWorkString(str);
        }
        return str;

    }

}