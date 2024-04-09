package Exceptions_final;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/*
 Напишите приложение, которое будет запрашивать у пользователя следующие данные, разделенные пробелом:

Фамилия Имя Отчество дата _ рождения номер _ телефона пол

Форматы данных:

фамилия, имя, отчество - строки
дата _ рождения - строка формата dd.mm.yyyy
номер _ телефона - целое беззнаковое число без форматирования
пол - символ латиницей f или m.

Приложение должно проверить введенные данные по количеству. Если количество не совпадает, вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.

Приложение должно распарсить полученную строку и выделить из них требуемые значения. Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.

Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, вида
<Фамилия> <Имя> <Отчество> <дата _ рождения> <номер _ телефона> <пол>

Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
Не забудьте закрыть соединение с файлом.
При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.
 */
public class Main {
    
    public static void main(String[] args) throws Exception {
        try {
            dataSave();
            System.out.println("Запись успешно создана");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dataSave() throws Exception{
        
        System.out.println("Введите ФИО, дату рождения в формате dd.mm.yyyy, номер телефона без символов и пробелов и пол латиницей f или m через пробел)");
        
        String data;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));) {
            data = reader.readLine();
		}
        catch (IOException e) {
			throw new RuntimeException("Что-то не так с вводом в консоли");
		}
        String[] personal_data = data.split(" ");
        
        if (personal_data.length != 6){
            throw new RuntimeException("Введено недостаточное количество данных для записи");
        }
        
        String familyName = personal_data[0];
        String name = personal_data[1];
        String patronymic = personal_data[2];

        SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        Date birthday;
        try {
            birthday = format.parse(personal_data[3]);//parse(personal_data[3]);
        }catch (ParseException e){
            throw new ParseException("Неверный формат даты рождения", e.getErrorOffset());
        }

        int phoneNum;
        try{
            phoneNum = Integer.parseInt(personal_data[4]);
        }
        catch(NumberFormatException e){
            throw new NumberFormatException("В номере использованы посторонние символы");
        }

        String s = personal_data[5];
        char sex;
        if (s.equals("m") || s.equals("f") ){
            sex = personal_data[5].toLowerCase().charAt(0);
        }
        else{
            throw new Exception("Неверно введен пол контакта");
        }

        String fileName = familyName.toLowerCase() + ".txt";
        File file = new File(fileName);
        try (FileWriter fileWriter = new FileWriter(file, true)){
            if (file.length() > 0){
                fileWriter.write('\n');
            }
            fileWriter.write(String.format("%s %s %s %s %d %c", familyName, name, patronymic, format.format(birthday), phoneNum, sex)); 
        }
        catch (IOException e) {
            System.out.println("Ошибка при создании файла");
            e.printStackTrace();
        }
    }
}














