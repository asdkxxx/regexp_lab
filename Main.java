
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("C:\\Users\\alina\\OneDrive\\Рабочий стол\\txt.txt"));
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }
        Pattern name_pattern = Pattern.compile("([^|\\s]+ *[^|]+)");
        Pattern age_pattern = Pattern.compile("\\d+");
        Pattern tel_pattern = Pattern.compile("([+]?[(]?([0-9]\\W*){3}[)]?[-\\s.]?([0-9]\\W*){3}[-\\s.]?([0-9]\\W*){4,6})");
        Pattern mail_pattern = Pattern.compile("[\\w!#$%&'*+-/=?^_`{|]+[^.@]@(\\w+.)+\\w+");
        for (String line : lines) {
            System.out.println(line);
            String[] line_split = line.split("\\|");
            if (line_split.length != 4 || anyEmpty(line_split)) {
                System.out.println("Invalid line format");
                continue;
            }
            boolean has_empty_elements = false;
            for (String elem : line_split) {
                if (elem.isEmpty()) {
                    has_empty_elements = true;
                    break;
                }
            }
            if (line_split.length != 4 || has_empty_elements) {
                continue;
            }
            String name = line_split[0];
            String age = line_split[1];
            String tel = line_split[2];
            String mail = line_split[3];
            name = name.replaceAll("\\.+", ".");
            name = name.replaceAll("@+", "@");
            if (name_pattern.matcher(name).matches()) {
                name = name.replaceAll("([А-ЯA-Z][^А-ЯA-Z])", " $1");
                String[] nameSplit = name.split(" ");
                for (int i = 0; i < nameSplit.length; i++) {
                    nameSplit[i] = nameSplit[i].trim();
                }
                List<String> nameSplitList = new ArrayList<>();
                for (String s : nameSplit) {
                    if (!s.isEmpty()) {
                        nameSplitList.add(s);
                    }
                }
                nameSplitList.replaceAll(s -> s.substring(0, 1).toUpperCase() + s.substring(1));
                name = String.join(" ", nameSplitList);
            }
            age = age.replaceAll("\\.+", ".");
            age = age.replaceAll("@+", "@");
            age = age.replaceAll(" ", "");
            tel = tel.replaceAll("\\.+", ".");
            tel = tel.replaceAll("@+", "@");
            tel = tel.replaceAll("-+", "");
            tel = tel.replaceAll(" ", "");


            if (tel_pattern.matcher(tel).matches()) {
                if (tel.charAt(0) == '8') {
                    tel= tel.replaceFirst("8", "+7");
                } if (tel_pattern.matcher(tel).matches()) {
                    if (tel.charAt(0) == '8') {
                        tel = tel.replaceFirst("8", "+7");
                    }

                    if (tel.charAt(2) != '(') {
                        String bg = tel.substring(0, 2);
                        String code = tel.substring(2, 5);
                        String num1 = tel.substring(5, 8);
                        String num2 = tel.substring(8);
                        tel = bg + " (" + code + ") " + num1 + "-" + num2;
                    }
                    else {String bg = tel.substring(0, 2);
                        String code = tel.substring(2, 6);
                        String num1 = tel.substring(6, 10);
                        String num2 = tel.substring(10);
                        tel = bg + " " + code + " "+num1 + "-" + num2;}
                }
            }

            mail = mail.replaceAll("\\.+", ".");
            mail = mail.replaceAll("@+", "@");
            mail = mail.replaceAll(" ", "");
            Matcher name_match = name_pattern.matcher(name);
            Matcher age_match = age_pattern.matcher(age);
            Matcher tel_match = tel_pattern.matcher(tel);
            Matcher mail_match = mail_pattern.matcher(mail);
            if (name_match.find()) {
                System.out.print(name_match.group() + "|");
            } else {
                System.out.print("Invalid name" + "|");
            }
            if (age_match.find()) {
                System.out.print(age_match.group() + "|");
            } else {
                System.out.print("Invalid age" + "|");
            }
            if (tel_match.find()) {
                System.out.print(tel_match.group() + "|");
            } else {
                System.out.print("Invalid tel" + "|");
            }
            if (mail_match.find()) {
                System.out.println(mail_match.group());
            } else {
                System.out.println("Invalid mail");
            }
            System.out.println();
        }
    }
    private static boolean anyEmpty(String[] strings) {
        for (String s : strings) {
            if (s.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
